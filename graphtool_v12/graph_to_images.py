#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
graph_to_images.py

Deterministische renderer voor .graph-, .opn- en structured .opn-bestanden uit het OpenGraph/Mapping-project.
Schrijft SVG, PNG of beide.

v9-rendering:
- standaard clean stijl;
- standaard content-crop: miniaturen en previews tonen de graph zelf, niet een groot leeg gridvlak;
- anti-aliased PNG via supersampling;
- klassiek gedrag blijft beschikbaar met --style classic --crop grid.

Belangrijkste toleranties:
- Een lege eerste regel is toegestaan.
- Dot-coördinaten mogen integer of float zijn, bijvoorbeeld 319 of 319.0.
- Dot-labels mogen leeg zijn.
- Per dot wordt een zesde regel met adjacency/metadata genegeerd.
- Na de dots mag een edge-sectie volgen; die wordt gelezen en als lijn getekend.
- Structured OPN wordt ondersteund: YAML-like structure.nodes/edges en STRUCTURE_NODES/STRUCTURE_EDGES.
"""

from __future__ import annotations

import argparse
import html
import math
import sys
from dataclasses import dataclass
from pathlib import Path
from typing import Iterable, List, Optional, Sequence, Tuple


@dataclass(frozen=True)
class Dot:
    internal_id: int
    x: float
    y: float
    label: str
    color_value: int
    adjacency: str = ""


@dataclass(frozen=True)
class Edge:
    edge_id: int
    source_id: int
    target_id: int
    label: str
    mid_x: float
    mid_y: float
    color_value: int


@dataclass(frozen=True)
class GraphData:
    source: Path
    grid_x_count: int
    grid_x_step: float
    grid_y_count: int
    grid_y_step: float
    dots: List[Dot]
    edges: List[Edge]

    @property
    def width(self) -> float:
        grid_width = (self.grid_x_count - 1) * self.grid_x_step if self.grid_x_count > 1 else 0.0
        dot_width = max((d.x for d in self.dots), default=0.0)
        edge_width = max((e.mid_x for e in self.edges), default=0.0)
        return max(grid_width, dot_width, edge_width)

    @property
    def height(self) -> float:
        grid_height = (self.grid_y_count - 1) * self.grid_y_step if self.grid_y_count > 1 else 0.0
        dot_height = max((d.y for d in self.dots), default=0.0)
        edge_height = max((e.mid_y for e in self.edges), default=0.0)
        return max(grid_height, dot_height, edge_height)


def _clean_line(line: str) -> str:
    return line.strip().lstrip("\ufeff")


def parse_int(text: str, path: Path, line_no: int, field_name: str) -> int:
    value = _clean_line(text)
    if value == "":
        raise ValueError(f"{path}: regel {line_no} ({field_name}) is leeg; verwacht geheel getal")
    try:
        number = float(value)
    except ValueError as exc:
        raise ValueError(f"{path}: regel {line_no} ({field_name}) is geen getal: {value!r}") from exc
    if not math.isfinite(number) or abs(number - round(number)) > 1e-9:
        raise ValueError(f"{path}: regel {line_no} ({field_name}) is geen geheel getal: {value!r}")
    return int(round(number))


def parse_float(text: str, path: Path, line_no: int, field_name: str) -> float:
    value = _clean_line(text)
    if value == "":
        raise ValueError(f"{path}: regel {line_no} ({field_name}) is leeg; verwacht getal")
    try:
        number = float(value)
    except ValueError as exc:
        raise ValueError(f"{path}: regel {line_no} ({field_name}) is geen getal: {value!r}") from exc
    if not math.isfinite(number):
        raise ValueError(f"{path}: regel {line_no} ({field_name}) is geen eindig getal: {value!r}")
    return number


def _line_at(lines: Sequence[str], index: int, path: Path, field_name: str) -> str:
    if index >= len(lines):
        raise ValueError(f"{path}: bestand eindigt te vroeg bij {field_name}")
    return lines[index]



def _strip_quotes(value: str) -> str:
    value = value.strip()
    if len(value) >= 2 and ((value[0] == value[-1] == '"') or (value[0] == value[-1] == "'")):
        return value[1:-1]
    return value


def _color_int(hex_color: str) -> int:
    return int(hex_color.lstrip('#'), 16)


def _kind_color(kind: str) -> int:
    key = (kind or '').strip().lower().replace('_', '-').replace(' ', '-')
    palette = {
        'lex': '#16a34a',
        'verb-core': '#7c3aed',
        'v-center': '#7c3aed',
        'syn': '#f97316',
        'position': '#64748b',
        'projection-label': '#dc2626',
        'axis-label': '#475569',
        'frame': '#2563eb',
        'frame-note': '#2563eb',
        'role': '#f59e0b',
        'role-position': '#f59e0b',
        'thematic-root': '#2563eb',
        'thematic-state': '#0891b2',
    }
    return _color_int(palette.get(key, '#2563eb'))


def _as_float_value(value: str, path: Path, context: str) -> float:
    try:
        number = float(_strip_quotes(value))
    except ValueError as exc:
        raise ValueError(f"{path}: STRUCTURE_OPN {context} is geen getal: {value!r}") from exc
    if not math.isfinite(number):
        raise ValueError(f"{path}: STRUCTURE_OPN {context} is geen eindig getal: {value!r}")
    return number


def _split_structured_field(line: str) -> Optional[Tuple[str, str]]:
    if ':' not in line:
        return None
    key, value = line.split(':', 1)
    return key.strip(), _strip_quotes(value.strip())


def _looks_like_structured_opn(raw_lines: Sequence[str]) -> bool:
    lowered = '\n'.join(_clean_line(line).lower() for line in raw_lines[:80])
    return 'structure_nodes:' in lowered or ('structure:' in lowered and 'nodes:' in lowered and 'edges:' in lowered)


def parse_structure_opn(path: Path, raw_lines: Sequence[str]) -> GraphData:
    """Parse OpenGraph structured OPN variants.

    Supported variants:
    1. YAML-like:
       structure.nodes: - id/label/kind/x/y and structure.edges: - from/to
    2. Pipe sections:
       STRUCTURE_NODES: id | label | x | y | kind
       STRUCTURE_EDGES: from | to

    The pipe format uses logical grid coordinates and is scaled by 40 px.
    The YAML-like format uses literal coordinates.
    """
    if any(_clean_line(line).upper() == 'STRUCTURE_NODES:' for line in raw_lines):
        return _parse_structure_opn_pipe(path, raw_lines)
    return _parse_structure_opn_yamlish(path, raw_lines)


def _build_structured_graph(path: Path, nodes_raw: List[dict], edges_raw: List[dict], scale: float) -> GraphData:
    if not nodes_raw:
        raise ValueError(f"{path}: STRUCTURE_OPN bevat geen nodes")

    id_map: dict[str, int] = {}
    dots: List[Dot] = []
    for raw in nodes_raw:
        node_id = str(raw.get('id', '')).strip()
        if not node_id:
            raise ValueError(f"{path}: STRUCTURE_OPN node zonder id")
        if node_id in id_map:
            raise ValueError(f"{path}: STRUCTURE_OPN dubbele node id: {node_id}")
        internal_id = len(id_map) + 1
        id_map[node_id] = internal_id
        x = _as_float_value(str(raw.get('x', '')), path, f"node {node_id} x") * scale
        y = _as_float_value(str(raw.get('y', '')), path, f"node {node_id} y") * scale
        label = str(raw.get('label', node_id)).strip()
        kind = str(raw.get('kind', '')).strip()
        dots.append(Dot(internal_id=internal_id, x=x, y=y, label=label, color_value=_kind_color(kind), adjacency=kind))

    edges: List[Edge] = []
    for raw in edges_raw:
        src = str(raw.get('from', raw.get('source', ''))).strip()
        dst = str(raw.get('to', raw.get('target', ''))).strip()
        if not src or not dst:
            continue
        if src not in id_map or dst not in id_map:
            raise ValueError(f"{path}: STRUCTURE_OPN edge verwijst naar onbekende node: {src} -> {dst}")
        a = dots[id_map[src] - 1]
        b = dots[id_map[dst] - 1]
        edges.append(Edge(
            edge_id=len(edges) + 1,
            source_id=id_map[src],
            target_id=id_map[dst],
            label='',
            mid_x=(a.x + b.x) / 2.0,
            mid_y=(a.y + b.y) / 2.0,
            color_value=_color_int('#334155'),
        ))

    max_x = max((d.x for d in dots), default=0.0)
    max_y = max((d.y for d in dots), default=0.0)
    step = 40.0
    return GraphData(
        source=path,
        grid_x_count=max(2, int(math.ceil(max_x / step)) + 2),
        grid_x_step=step,
        grid_y_count=max(2, int(math.ceil(max_y / step)) + 2),
        grid_y_step=step,
        dots=dots,
        edges=edges,
    )


def _parse_structure_opn_pipe(path: Path, raw_lines: Sequence[str]) -> GraphData:
    nodes_raw: List[dict] = []
    edges_raw: List[dict] = []
    section = None
    terminal_sections = {'PLACEMENT:', 'EXPECTED_UTTERANCE:', 'NOTES:', 'META:'}

    for raw_line in raw_lines:
        line = _clean_line(raw_line)
        if not line or line.startswith('#'):
            continue
        upper = line.upper()
        if upper == 'STRUCTURE_NODES:':
            section = 'nodes'
            continue
        if upper == 'STRUCTURE_EDGES:':
            section = 'edges'
            continue
        if upper in terminal_sections:
            section = None
            continue
        if upper.endswith(':') and '|' not in line and upper not in {'OPN_VERSION:', 'STRUCTURE_TYPE:', 'TITLE:'}:
            section = None
            continue

        if section == 'nodes':
            parts = [part.strip() for part in line.split('|')]
            if len(parts) < 4:
                continue
            nodes_raw.append({
                'id': parts[0],
                'label': parts[1] if len(parts) > 1 else parts[0],
                'x': parts[2],
                'y': parts[3],
                'kind': parts[4] if len(parts) > 4 else '',
            })
        elif section == 'edges':
            parts = [part.strip() for part in line.split('|')]
            if len(parts) < 2:
                continue
            edges_raw.append({'from': parts[0], 'to': parts[1]})

    return _build_structured_graph(path, nodes_raw, edges_raw, scale=40.0)


def _parse_structure_opn_yamlish(path: Path, raw_lines: Sequence[str]) -> GraphData:
    nodes_raw: List[dict] = []
    edges_raw: List[dict] = []
    section = None
    current: Optional[dict] = None

    def finish_current() -> None:
        nonlocal current, section
        if current is None:
            return
        if section == 'nodes':
            nodes_raw.append(current)
        elif section == 'edges':
            edges_raw.append(current)
        current = None

    for raw_line in raw_lines:
        stripped = _clean_line(raw_line)
        if not stripped or stripped.startswith('#'):
            continue
        low = stripped.lower()
        if low == 'nodes:':
            finish_current()
            section = 'nodes'
            continue
        if low == 'edges:':
            finish_current()
            section = 'edges'
            continue
        if low in {'meta:', 'structure:', 'notes:'}:
            finish_current()
            if low == 'notes:':
                section = None
            continue
        if section not in {'nodes', 'edges'}:
            continue

        if stripped.startswith('- '):
            finish_current()
            current = {}
            rest = stripped[2:].strip()
            item = _split_structured_field(rest)
            if item:
                current[item[0].lower()] = item[1]
            continue

        if current is None:
            continue
        item = _split_structured_field(stripped)
        if item:
            current[item[0].lower()] = item[1]

    finish_current()
    return _build_structured_graph(path, nodes_raw, edges_raw, scale=1.0)

def parse_graph(path: Path) -> GraphData:
    path = Path(path)
    if not path.exists():
        raise FileNotFoundError(str(path))

    raw_lines = path.read_text(encoding="utf-8-sig", errors="replace").splitlines()
    if _looks_like_structured_opn(raw_lines):
        return parse_structure_opn(path, raw_lines)

    if raw_lines and _clean_line(raw_lines[0]) == "":
        start = 1
    else:
        start = 0

    if len(raw_lines) - start < 5:
        raise ValueError(f"{path}: te weinig gegevens; verwacht minimaal 5 headerregels")

    grid_x_count = parse_int(raw_lines[start + 0], path, start + 1, "grid_x_count")
    grid_x_step = parse_float(raw_lines[start + 1], path, start + 2, "grid_x_step")
    grid_y_count = parse_int(raw_lines[start + 2], path, start + 3, "grid_y_count")
    grid_y_step = parse_float(raw_lines[start + 3], path, start + 4, "grid_y_step")
    dot_count = parse_int(raw_lines[start + 4], path, start + 5, "dot_count")

    if grid_x_count <= 0 or grid_y_count <= 0:
        raise ValueError(f"{path}: grid count moet positief zijn")
    if grid_x_step <= 0 or grid_y_step <= 0:
        raise ValueError(f"{path}: grid step moet positief zijn")
    if dot_count < 0:
        raise ValueError(f"{path}: dot_count mag niet negatief zijn")

    dots: List[Dot] = []
    idx = start + 5
    for dot_index in range(dot_count):
        base = idx
        internal_id = parse_int(_line_at(raw_lines, base + 0, path, f"dot {dot_index + 1} internal_id"), path, base + 1, "dot internal_id")
        x = parse_float(_line_at(raw_lines, base + 1, path, f"dot {internal_id} x"), path, base + 2, "dot x")
        y = parse_float(_line_at(raw_lines, base + 2, path, f"dot {internal_id} y"), path, base + 3, "dot y")
        label = _clean_line(_line_at(raw_lines, base + 3, path, f"dot {internal_id} label"))
        color_value = parse_int(_line_at(raw_lines, base + 4, path, f"dot {internal_id} color"), path, base + 5, "dot color")
        adjacency = _clean_line(_line_at(raw_lines, base + 5, path, f"dot {internal_id} adjacency"))
        dots.append(Dot(internal_id=internal_id, x=x, y=y, label=label, color_value=color_value, adjacency=adjacency))
        idx += 6

    edges: List[Edge] = []
    while idx < len(raw_lines) and _clean_line(raw_lines[idx]) == "":
        idx += 1

    if idx < len(raw_lines):
        try:
            edge_count = parse_int(raw_lines[idx], path, idx + 1, "edge_count")
        except ValueError:
            edge_count = 0
        else:
            idx += 1
            for edge_index in range(edge_count):
                base = idx
                edge_id = parse_int(_line_at(raw_lines, base + 0, path, f"edge {edge_index + 1} edge_id"), path, base + 1, "edge_id")
                source_id = parse_int(_line_at(raw_lines, base + 1, path, f"edge {edge_id} source_id"), path, base + 2, "edge source_id")
                target_id = parse_int(_line_at(raw_lines, base + 2, path, f"edge {edge_id} target_id"), path, base + 3, "edge target_id")
                edge_label = _clean_line(_line_at(raw_lines, base + 3, path, f"edge {edge_id} label"))
                mid_x = parse_float(_line_at(raw_lines, base + 4, path, f"edge {edge_id} mid_x"), path, base + 5, "edge mid_x")
                mid_y = parse_float(_line_at(raw_lines, base + 5, path, f"edge {edge_id} mid_y"), path, base + 6, "edge mid_y")
                color_value = parse_int(_line_at(raw_lines, base + 9, path, f"edge {edge_id} color"), path, base + 10, "edge color")
                edges.append(Edge(edge_id=edge_id, source_id=source_id, target_id=target_id, label=edge_label, mid_x=mid_x, mid_y=mid_y, color_value=color_value))
                idx += 10

    return GraphData(
        source=path,
        grid_x_count=grid_x_count,
        grid_x_step=grid_x_step,
        grid_y_count=grid_y_count,
        grid_y_step=grid_y_step,
        dots=dots,
        edges=edges,
    )


def signed_int_to_hex_rgb(value: int, fallback: str = "#2563eb") -> str:
    rgb = value & 0xFFFFFF
    if rgb == 0:
        return fallback
    r = (rgb >> 16) & 255
    g = (rgb >> 8) & 255
    b = rgb & 255
    if r > 242 and g > 242 and b > 242:
        return fallback
    return f"#{rgb:06x}"


def output_paths(graph_path: Path, outdir: Optional[Path], suffix: str = "") -> Tuple[Path, Path]:
    if outdir is None:
        base_dir = graph_path.parent
    else:
        base_dir = outdir
        base_dir.mkdir(parents=True, exist_ok=True)
    stem = graph_path.stem + suffix
    return base_dir / f"{stem}.svg", base_dir / f"{stem}.png"


def svg_number(value: float) -> str:
    if abs(value - round(value)) < 1e-9:
        return str(int(round(value)))
    return f"{value:.6f}".rstrip("0").rstrip(".")


def graph_bounds(data: GraphData, crop: str = "content", include_labels: bool = False, dot_radius: float = 6.0) -> Tuple[float, float, float, float]:
    if crop == "grid" or not data.dots:
        return 0.0, 0.0, max(1.0, data.width), max(1.0, data.height)

    xs = [d.x for d in data.dots]
    ys = [d.y for d in data.dots]
    xs.extend(e.mid_x for e in data.edges)
    ys.extend(e.mid_y for e in data.edges)
    if include_labels:
        for d in data.dots:
            if d.label:
                approx_w = max(16.0, len(d.label) * 7.2)
                xs.append(d.x + dot_radius + 8.0 + approx_w)
                ys.append(d.y + 10.0)
                ys.append(d.y - 10.0)
    min_x = min(xs)
    max_x = max(xs)
    min_y = min(ys)
    max_y = max(ys)
    if max_x <= min_x:
        max_x = min_x + 1.0
    if max_y <= min_y:
        max_y = min_y + 1.0
    return min_x, min_y, max_x, max_y


def _grid_positions(start: float, end: float, step: float) -> Iterable[Tuple[float, bool]]:
    if step <= 0:
        return
    first = math.floor(start / step)
    last = math.ceil(end / step)
    for i in range(first, last + 1):
        yield i * step, (i % 5 == 0)


def _svg_line(x1: float, y1: float, x2: float, y2: float, stroke: str, width: float, opacity: Optional[float] = None) -> str:
    extra = "" if opacity is None else f' opacity="{svg_number(opacity)}"'
    return (
        f'<line x1="{svg_number(x1)}" y1="{svg_number(y1)}" x2="{svg_number(x2)}" y2="{svg_number(y2)}" '
        f'stroke="{stroke}" stroke-width="{svg_number(width)}" stroke-linecap="round"{extra}/>'
    )


def render_svg_string(
    data: GraphData,
    margin: float = 28,
    show_grid: bool = True,
    show_labels: bool = False,
    show_edges: bool = True,
    dot_radius: float = 6,
    style: str = "clean",
    crop: str = "content",
) -> str:
    min_x, min_y, max_x, max_y = graph_bounds(data, crop, include_labels=show_labels, dot_radius=dot_radius)
    width = max_x - min_x
    height = max_y - min_y
    total_w = width + 2 * margin
    total_h = height + 2 * margin
    title = html.escape(data.source.name)
    view_x = min_x - margin
    view_y = min_y - margin

    clean = style == "clean"
    bg = "#f8fafc" if clean else "white"
    card = "#f9fafb" if clean else "white"
    border = "#d9dfe8" if clean else "#6e6e6e"

    lines: List[str] = []
    lines.append(
        f'<svg xmlns="http://www.w3.org/2000/svg" width="{svg_number(total_w)}" height="{svg_number(total_h)}" '
        f'viewBox="{svg_number(view_x)} {svg_number(view_y)} {svg_number(total_w)} {svg_number(total_h)}">'
    )
    lines.append(f"<title>{title}</title>")
    lines.append(f'<rect x="{svg_number(view_x)}" y="{svg_number(view_y)}" width="{svg_number(total_w)}" height="{svg_number(total_h)}" fill="{bg}"/>')
    lines.append(f'<rect x="{svg_number(view_x + 1)}" y="{svg_number(view_y + 1)}" width="{svg_number(total_w - 2)}" height="{svg_number(total_h - 2)}" fill="{card}" stroke="{border}" stroke-width="1"/>')

    if show_grid:
        for y, major in _grid_positions(min_y, max_y, data.grid_y_step):
            stroke = "#dae1ea" if major and clean else "#ebeff5" if clean else "#cdcdcd" if major else "#e8e8e8"
            lines.append(_svg_line(min_x, y, max_x, y, stroke, 1.0 if clean else (1.5 if major else 1.0)))
        for x, major in _grid_positions(min_x, max_x, data.grid_x_step):
            stroke = "#dae1ea" if major and clean else "#ebeff5" if clean else "#cdcdcd" if major else "#e8e8e8"
            lines.append(_svg_line(x, min_y, x, max_y, stroke, 1.0 if clean else (1.5 if major else 1.0)))

    dots_by_id = {dot.internal_id: dot for dot in data.dots}
    if show_edges:
        for edge in data.edges:
            source = dots_by_id.get(edge.source_id)
            target = dots_by_id.get(edge.target_id)
            if source is None or target is None:
                continue
            color = signed_int_to_hex_rgb(edge.color_value)
            if clean:
                lines.append(_svg_line(source.x, source.y, target.x, target.y, "#0f172a", 3.8, 0.18))
                lines.append(_svg_line(source.x, source.y, target.x, target.y, color, 2.2, 0.92))
            else:
                lines.append(_svg_line(source.x, source.y, target.x, target.y, color, 2.0))

    lines.append('<g font-family="Segoe UI, Arial, sans-serif" font-size="13">')
    for dot in data.dots:
        fill = signed_int_to_hex_rgb(dot.color_value)
        if clean:
            lines.append(f'<circle cx="{svg_number(dot.x)}" cy="{svg_number(dot.y)}" r="{svg_number(dot_radius * 1.35)}" fill="none" stroke="#ffffff" stroke-width="{svg_number(max(2.0, dot_radius * 0.45))}"/>')
            lines.append(f'<circle cx="{svg_number(dot.x)}" cy="{svg_number(dot.y)}" r="{svg_number(dot_radius)}" fill="{fill}" stroke="#0f172a" stroke-opacity="0.72" stroke-width="1"/>')
        else:
            lines.append(f'<circle cx="{svg_number(dot.x)}" cy="{svg_number(dot.y)}" r="{svg_number(dot_radius)}" fill="{fill}" stroke="black" stroke-width="1"/>')
        if show_labels and dot.label != "":
            label = html.escape(dot.label)
            if clean:
                lines.append(f'<text x="{svg_number(dot.x + dot_radius + 5)}" y="{svg_number(dot.y + 4)}" fill="#0f172a" font-weight="500">{label}</text>')
            else:
                lines.append(f'<text x="{svg_number(dot.x + dot_radius + 5)}" y="{svg_number(dot.y + 4)}" fill="black" font-weight="bold">{label}</text>')
    lines.append("</g>")
    lines.append("</svg>")
    return "\n".join(lines) + "\n"


def render_svg(data: GraphData, path: Path, **kwargs) -> None:
    path.write_text(render_svg_string(data, **kwargs), encoding="utf-8")


def load_font(size: int):
    try:
        from PIL import ImageFont
        return ImageFont.truetype("arial.ttf", size)
    except Exception:
        try:
            from PIL import ImageFont
            return ImageFont.truetype("DejaVuSans.ttf", size)
        except Exception:
            from PIL import ImageFont
            return ImageFont.load_default()


def render_png(
    data: GraphData,
    path: Path,
    margin: float = 28,
    scale: float = 1.0,
    show_grid: bool = True,
    show_labels: bool = False,
    show_edges: bool = True,
    dot_radius: float = 6,
    style: str = "clean",
    crop: str = "content",
) -> None:
    try:
        from PIL import Image, ImageDraw
    except ImportError as exc:
        raise RuntimeError("PNG-export vereist Pillow. Installeer met: py -m pip install pillow") from exc

    min_x, min_y, max_x, max_y = graph_bounds(data, crop, include_labels=show_labels, dot_radius=dot_radius)
    width = max_x - min_x
    height = max_y - min_y

    aa = 3 if style == "clean" else 1
    render_scale = max(0.05, scale) * aa
    total_w = int(round((width + 2 * margin) * render_scale))
    total_h = int(round((height + 2 * margin) * render_scale))
    clean = style == "clean"
    image = Image.new("RGB", (max(1, total_w), max(1, total_h)), "#f8fafc" if clean else "white")
    draw = ImageDraw.Draw(image)

    def sx(value: float) -> int:
        return int(round((value - min_x + margin) * render_scale))

    def sy(value: float) -> int:
        return int(round((value - min_y + margin) * render_scale))

    def sw(value: float) -> int:
        return max(1, int(round(value * render_scale)))

    # Card background.
    draw.rectangle((0, 0, total_w - 1, total_h - 1), fill="#f9fafb" if clean else "white", outline="#d9dfe8" if clean else "#6e6e6e", width=sw(1.0))

    if show_grid:
        for y, major in _grid_positions(min_y, max_y, data.grid_y_step):
            fill = "#dae1ea" if major and clean else "#ebeff5" if clean else "#cdcdcd" if major else "#e8e8e8"
            draw.line((sx(min_x), sy(y), sx(max_x), sy(y)), fill=fill, width=sw(1.0 if clean else (1.5 if major else 1.0)))
        for x, major in _grid_positions(min_x, max_x, data.grid_x_step):
            fill = "#dae1ea" if major and clean else "#ebeff5" if clean else "#cdcdcd" if major else "#e8e8e8"
            draw.line((sx(x), sy(min_y), sx(x), sy(max_y)), fill=fill, width=sw(1.0 if clean else (1.5 if major else 1.0)))

    dots_by_id = {dot.internal_id: dot for dot in data.dots}
    if show_edges:
        for edge in data.edges:
            source = dots_by_id.get(edge.source_id)
            target = dots_by_id.get(edge.target_id)
            if source is None or target is None:
                continue
            color = signed_int_to_hex_rgb(edge.color_value)
            if clean:
                draw.line((sx(source.x), sy(source.y), sx(target.x), sy(target.y)), fill="#0f172a", width=sw(3.8))
            draw.line((sx(source.x), sy(source.y), sx(target.x), sy(target.y)), fill=color, width=sw(2.2 if clean else 2.0))

    font = load_font(max(8, int(round(13 * render_scale))))
    r = max(2, int(round(dot_radius * render_scale)))
    for dot in data.dots:
        cx = sx(dot.x)
        cy = sy(dot.y)
        fill = signed_int_to_hex_rgb(dot.color_value)
        if clean:
            outline_r = int(round(r * 1.35))
            draw.ellipse((cx - outline_r, cy - outline_r, cx + outline_r, cy + outline_r), outline="#ffffff", width=sw(max(2.0, dot_radius * 0.45)))
            draw.ellipse((cx - r, cy - r, cx + r, cy + r), fill=fill, outline="#0f172a", width=sw(1.0))
        else:
            draw.ellipse((cx - r, cy - r, cx + r, cy + r), fill=fill, outline="black", width=sw(1.0))
        if show_labels and dot.label != "":
            draw.text((sx(dot.x + dot_radius + 5), sy(dot.y + 4) - int(round(13 * render_scale))), dot.label, fill="#0f172a" if clean else "black", font=font)

    if aa > 1:
        target_size = (max(1, int(round((width + 2 * margin) * scale))), max(1, int(round((height + 2 * margin) * scale))))
        resample = getattr(getattr(Image, "Resampling", Image), "LANCZOS")
        image = image.resize(target_size, resample)

    path.parent.mkdir(parents=True, exist_ok=True)
    image.save(path)


def read_list_file(path: Path) -> List[Path]:
    base = path.parent
    result: List[Path] = []
    for raw in path.read_text(encoding="utf-8-sig", errors="replace").splitlines():
        text = raw.strip().strip('"')
        if not text:
            continue
        candidate = Path(text)
        if not candidate.is_absolute():
            candidate = base / candidate
        result.append(candidate)
    return result


SUPPORTED_EXTENSIONS = {".graph", ".opn"}


def is_supported_graph_file(path: Path) -> bool:
    return path.suffix.lower() in SUPPORTED_EXTENSIONS


def iter_supported_files(directory: Path) -> List[Path]:
    result: List[Path] = []
    for child in directory.iterdir():
        if child.is_file() and is_supported_graph_file(child):
            result.append(child)
    return sorted(result, key=lambda p: p.name.lower())


def collect_graphs(args: argparse.Namespace) -> List[Path]:
    graphs: List[Path] = []
    if args.list_file:
        graphs.extend(read_list_file(args.list_file))
    for item in args.graphs:
        p = Path(item)
        if p.is_dir():
            graphs.extend(iter_supported_files(p))
        else:
            graphs.append(p)
    if not graphs and args.current_dir:
        graphs.extend(iter_supported_files(Path.cwd()))
    return graphs


def bool_arg(text: str) -> bool:
    return text.strip().lower() in {"1", "true", "yes", "ja", "y", "on"}


def labels_value(text: str, style: str) -> bool:
    t = text.strip().lower()
    if t == "auto":
        return style == "classic"
    return bool_arg(text)


def main(argv: Optional[Sequence[str]] = None) -> int:
    parser = argparse.ArgumentParser(description="Render .graph- en .opn-bestanden naar SVG/PNG.")
    parser.add_argument("graphs", nargs="*", help=".graph/.opn-bestanden of mappen")
    parser.add_argument("--list", dest="list_file", type=Path, help="tekstbestand met geselecteerde .graph/.opn-paden")
    parser.add_argument("--mode", choices=["svg", "png", "both"], default="both")
    parser.add_argument("--outdir", type=Path, default=None)
    parser.add_argument("--scale", type=float, default=1.0)
    parser.add_argument("--margin", type=float, default=28.0)
    parser.add_argument("--dot-radius", type=float, default=6.0)
    parser.add_argument("--suffix", default="", help="suffix achter bestandsnaam, bv. .preview")
    parser.add_argument("--grid", default="yes", help="yes/no")
    parser.add_argument("--labels", default="auto", help="yes/no/auto")
    parser.add_argument("--edges", default="yes", help="yes/no")
    parser.add_argument("--style", choices=["clean", "classic"], default="clean")
    parser.add_argument("--crop", choices=["content", "grid"], default="content")
    parser.add_argument("--current-dir", action="store_true", help="gebruik *.graph en *.opn in huidige map als geen bestanden opgegeven zijn")
    parser.add_argument("--selftest", action="store_true")
    args = parser.parse_args(argv)

    graphs = collect_graphs(args)
    if not graphs:
        print("Geen .graph- of .opn-bestanden opgegeven of gevonden.", file=sys.stderr)
        return 2

    ok = 0
    failed = 0
    for graph_path in graphs:
        try:
            data = parse_graph(graph_path)
            if args.selftest:
                print(f"OK: {graph_path}: dots={len(data.dots)}, edges={len(data.edges)}, grid={data.grid_x_count}x{data.grid_y_count}")
                ok += 1
                continue
            svg_path, png_path = output_paths(graph_path, args.outdir, args.suffix)
            render_kwargs = {
                "margin": args.margin,
                "show_grid": bool_arg(args.grid),
                "show_labels": labels_value(args.labels, args.style),
                "show_edges": bool_arg(args.edges),
                "dot_radius": args.dot_radius,
                "style": args.style,
                "crop": args.crop,
            }
            if args.mode in {"svg", "both"}:
                render_svg(data, svg_path, **render_kwargs)
                print(f"SVG: {svg_path}")
            if args.mode in {"png", "both"}:
                render_png(data, png_path, scale=args.scale, **render_kwargs)
                print(f"PNG: {png_path}")
            ok += 1
        except Exception as exc:
            failed += 1
            print(f"FOUT: {graph_path}: {exc}", file=sys.stderr)

    print(f"Klaar. Succesvol: {ok}. Fouten: {failed}.")
    return 0 if failed == 0 else 1


if __name__ == "__main__":
    raise SystemExit(main())

#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
graph_viewer.py

Windows/Tkinter-viewer voor .graph- en .opn-bestanden uit het OpenGraph/Mapping-project.

Kenmerken:
- Werkt met lege eerste regel.
- Werkt met integer- en floatcoördinaten.
- Werkt met lege labels.
- Toont optioneel edges uit de edge-sectie.
- Kan worden gebruikt vanuit Explorer, cmd en Total Commander.
"""

from __future__ import annotations

import sys
import traceback
from pathlib import Path
from typing import List, Optional, Sequence

import tkinter as tk
from tkinter import filedialog, messagebox

from graph_to_images import GraphData, parse_graph, render_png, render_svg, signed_int_to_hex_rgb


APP_NAME = "Graph Viewer"
APP_VERSION = "1.2"


class GraphViewer(tk.Tk):
    def __init__(self, files: Sequence[Path]) -> None:
        super().__init__()
        self.title(APP_NAME)
        self.geometry("1100x800")
        self.minsize(640, 420)

        self.files: List[Path] = [Path(f) for f in files if str(f).strip()]
        self.index: int = 0
        self.data: Optional[GraphData] = None
        self.zoom: float = 1.0
        self.margin: float = 28.0
        self.dot_radius: float = 6.0

        self.show_grid = tk.BooleanVar(value=True)
        self.show_labels = tk.BooleanVar(value=True)
        self.show_edges = tk.BooleanVar(value=True)

        self._build_ui()
        self.bind("<Control-o>", lambda _event: self.open_file_dialog())
        self.bind("<Control-r>", lambda _event: self.reload_current())
        self.bind("<Control-plus>", lambda _event: self.zoom_in())
        self.bind("<Control-minus>", lambda _event: self.zoom_out())
        self.bind("<F5>", lambda _event: self.reload_current())
        self.bind("<Left>", lambda _event: self.previous_file())
        self.bind("<Right>", lambda _event: self.next_file())
        self.bind("<MouseWheel>", self._on_mousewheel)

        self.update_idletasks()
        if self.files:
            self.load_file(self.files[0])
        else:
            self.status.set("Open een .graph- of .opn-bestand.")

    def _build_ui(self) -> None:
        menubar = tk.Menu(self)

        file_menu = tk.Menu(menubar, tearoff=False)
        file_menu.add_command(label="Open...", command=self.open_file_dialog, accelerator="Ctrl+O")
        file_menu.add_command(label="Herlaad", command=self.reload_current, accelerator="F5")
        file_menu.add_separator()
        file_menu.add_command(label="Exporteer SVG...", command=self.export_svg)
        file_menu.add_command(label="Exporteer PNG...", command=self.export_png)
        file_menu.add_separator()
        file_menu.add_command(label="Afsluiten", command=self.destroy)
        menubar.add_cascade(label="Bestand", menu=file_menu)

        view_menu = tk.Menu(menubar, tearoff=False)
        view_menu.add_command(label="Zoom +", command=self.zoom_in, accelerator="Ctrl++")
        view_menu.add_command(label="Zoom -", command=self.zoom_out, accelerator="Ctrl+-")
        view_menu.add_command(label="Passend", command=self.fit_to_window)
        view_menu.add_separator()
        view_menu.add_checkbutton(label="Grid", variable=self.show_grid, command=self.redraw)
        view_menu.add_checkbutton(label="Labels", variable=self.show_labels, command=self.redraw)
        view_menu.add_checkbutton(label="Edges", variable=self.show_edges, command=self.redraw)
        menubar.add_cascade(label="Beeld", menu=view_menu)

        help_menu = tk.Menu(menubar, tearoff=False)
        help_menu.add_command(label="Info", command=self.show_about)
        menubar.add_cascade(label="Help", menu=help_menu)
        self.config(menu=menubar)

        toolbar = tk.Frame(self, bd=1, relief=tk.RAISED)
        toolbar.pack(side=tk.TOP, fill=tk.X)

        buttons = [
            ("Open", self.open_file_dialog),
            ("vorige", self.previous_file),
            ("volgende", self.next_file),
            ("Herlaad", self.reload_current),
            ("Zoom -", self.zoom_out),
            ("Zoom +", self.zoom_in),
            ("Passend", self.fit_to_window),
        ]
        for text, command in buttons:
            tk.Button(toolbar, text=text, command=command).pack(side=tk.LEFT, padx=2, pady=2)

        tk.Checkbutton(toolbar, text="Grid", variable=self.show_grid, command=self.redraw).pack(side=tk.LEFT, padx=8)
        tk.Checkbutton(toolbar, text="Labels", variable=self.show_labels, command=self.redraw).pack(side=tk.LEFT, padx=2)
        tk.Checkbutton(toolbar, text="Edges", variable=self.show_edges, command=self.redraw).pack(side=tk.LEFT, padx=2)

        self.info = tk.StringVar(value="")
        tk.Label(toolbar, textvariable=self.info, anchor="w").pack(side=tk.LEFT, padx=12, fill=tk.X, expand=True)

        main = tk.Frame(self)
        main.pack(side=tk.TOP, fill=tk.BOTH, expand=True)

        self.canvas = tk.Canvas(main, background="white")
        hbar = tk.Scrollbar(main, orient=tk.HORIZONTAL, command=self.canvas.xview)
        vbar = tk.Scrollbar(main, orient=tk.VERTICAL, command=self.canvas.yview)
        self.canvas.configure(xscrollcommand=hbar.set, yscrollcommand=vbar.set)

        self.canvas.grid(row=0, column=0, sticky="nsew")
        vbar.grid(row=0, column=1, sticky="ns")
        hbar.grid(row=1, column=0, sticky="ew")
        main.rowconfigure(0, weight=1)
        main.columnconfigure(0, weight=1)

        self.status = tk.StringVar(value="")
        tk.Label(self, textvariable=self.status, anchor="w", relief=tk.SUNKEN).pack(side=tk.BOTTOM, fill=tk.X)

    def open_file_dialog(self) -> None:
        filename = filedialog.askopenfilename(
            title="Open .graph/.opn",
            filetypes=[("Graph/OpenGraph-bestanden", "*.graph *.opn *.GRAPH *.OPN"), ("Alle bestanden", "*.*")],
        )
        if filename:
            path = Path(filename)
            if path not in self.files:
                self.files.append(path)
            self.index = self.files.index(path)
            self.load_file(path)

    def load_file(self, path: Path) -> None:
        try:
            self.data = parse_graph(path)
        except Exception as exc:
            self.data = None
            self.canvas.delete("all")
            messagebox.showerror(APP_NAME, f"Kan .graph/.opn niet openen:\n\n{exc}")
            self.status.set(f"Fout: {path}")
            return

        self.title(f"{APP_NAME} - {path}")
        self.zoom = 1.0
        self.update_info()
        self.redraw()
        self.fit_to_window()

    def update_info(self) -> None:
        if self.data is None:
            self.info.set("")
            return
        self.info.set(
            f"{self.data.source.name} | dots: {len(self.data.dots)} | edges: {len(self.data.edges)} | "
            f"grid: {self.data.grid_x_count}×{self.data.grid_y_count}"
        )
        self.status.set(str(self.data.source))

    def reload_current(self) -> None:
        if self.data is not None:
            self.load_file(self.data.source)
        elif self.files:
            self.load_file(self.files[self.index])

    def previous_file(self) -> None:
        if not self.files:
            return
        self.index = (self.index - 1) % len(self.files)
        self.load_file(self.files[self.index])

    def next_file(self) -> None:
        if not self.files:
            return
        self.index = (self.index + 1) % len(self.files)
        self.load_file(self.files[self.index])

    def zoom_in(self) -> None:
        self.zoom = min(8.0, self.zoom * 1.25)
        self.redraw()

    def zoom_out(self) -> None:
        self.zoom = max(0.05, self.zoom / 1.25)
        self.redraw()

    def fit_to_window(self) -> None:
        if self.data is None:
            return
        self.update_idletasks()
        canvas_w = max(1, self.canvas.winfo_width() - 20)
        canvas_h = max(1, self.canvas.winfo_height() - 20)
        total_w = self.data.width + 2 * self.margin
        total_h = self.data.height + 2 * self.margin
        if total_w <= 0 or total_h <= 0:
            return
        self.zoom = max(0.05, min(canvas_w / total_w, canvas_h / total_h))
        self.redraw()

    def _on_mousewheel(self, event: tk.Event) -> None:
        if event.state & 0x0004:
            if event.delta > 0:
                self.zoom_in()
            else:
                self.zoom_out()
        else:
            self.canvas.yview_scroll(int(-1 * (event.delta / 120)), "units")

    def sx(self, value: float) -> float:
        return (value + self.margin) * self.zoom

    def sy(self, value: float) -> float:
        return (value + self.margin) * self.zoom

    def sw(self, value: float) -> float:
        return max(1.0, value * self.zoom)

    def redraw(self) -> None:
        self.canvas.delete("all")
        if self.data is None:
            return

        data = self.data
        width = data.width
        height = data.height
        total_w = (width + 2 * self.margin) * self.zoom
        total_h = (height + 2 * self.margin) * self.zoom
        self.canvas.configure(scrollregion=(0, 0, total_w, total_h))

        if self.show_grid.get():
            major_x = data.grid_x_step * 5
            major_y = data.grid_y_step * 5
            y = 0.0
            while y <= height + 1e-9:
                is_major = major_y > 0 and abs((y / major_y) - round(y / major_y)) < 1e-9
                color = "#cdcdcd" if is_major else "#e8e8e8"
                self.canvas.create_line(self.sx(0), self.sy(y), self.sx(width), self.sy(y), fill=color, width=self.sw(1.5 if is_major else 1.0))
                y += data.grid_y_step
            x = 0.0
            while x <= width + 1e-9:
                is_major = major_x > 0 and abs((x / major_x) - round(x / major_x)) < 1e-9
                color = "#cdcdcd" if is_major else "#e8e8e8"
                self.canvas.create_line(self.sx(x), self.sy(0), self.sx(x), self.sy(height), fill=color, width=self.sw(1.5 if is_major else 1.0))
                x += data.grid_x_step
            self.canvas.create_rectangle(self.sx(0), self.sy(0), self.sx(width), self.sy(height), outline="#6e6e6e", width=self.sw(1.0))

        dots_by_id = {dot.internal_id: dot for dot in data.dots}
        if self.show_edges.get():
            for edge in data.edges:
                source = dots_by_id.get(edge.source_id)
                target = dots_by_id.get(edge.target_id)
                if source is None or target is None:
                    continue
                self.canvas.create_line(
                    self.sx(source.x), self.sy(source.y), self.sx(target.x), self.sy(target.y),
                    fill=signed_int_to_hex_rgb(edge.color_value), width=self.sw(2.0), capstyle=tk.ROUND,
                )

        r = max(3.0, self.dot_radius * self.zoom)
        font_size = max(7, min(24, int(round(13 * self.zoom))))
        label_font = ("Arial", font_size, "bold")
        for dot in data.dots:
            cx = self.sx(dot.x)
            cy = self.sy(dot.y)
            self.canvas.create_oval(cx - r, cy - r, cx + r, cy + r, fill=signed_int_to_hex_rgb(dot.color_value), outline="black", width=self.sw(1.0))
            if self.show_labels.get() and dot.label != "":
                self.canvas.create_text(cx + r + 5 * self.zoom, cy, text=dot.label, anchor="w", fill="black", font=label_font)

        self.status.set(f"{data.source} | zoom {self.zoom:.2f}")

    def export_svg(self) -> None:
        if self.data is None:
            return
        initial = self.data.source.with_suffix(".svg")
        filename = filedialog.asksaveasfilename(
            title="Exporteer SVG",
            initialfile=initial.name,
            initialdir=str(initial.parent),
            defaultextension=".svg",
            filetypes=[("SVG", "*.svg"), ("Alle bestanden", "*.*")],
        )
        if not filename:
            return
        try:
            render_svg(
                self.data,
                Path(filename),
                margin=self.margin,
                show_grid=self.show_grid.get(),
                show_labels=self.show_labels.get(),
                show_edges=self.show_edges.get(),
                dot_radius=self.dot_radius,
            )
            self.status.set(f"SVG geschreven: {filename}")
        except Exception as exc:
            messagebox.showerror(APP_NAME, f"Kan SVG niet schrijven:\n\n{exc}")

    def export_png(self) -> None:
        if self.data is None:
            return
        initial = self.data.source.with_suffix(".png")
        filename = filedialog.asksaveasfilename(
            title="Exporteer PNG",
            initialfile=initial.name,
            initialdir=str(initial.parent),
            defaultextension=".png",
            filetypes=[("PNG", "*.png"), ("Alle bestanden", "*.*")],
        )
        if not filename:
            return
        try:
            render_png(
                self.data,
                Path(filename),
                margin=self.margin,
                scale=1.0,
                show_grid=self.show_grid.get(),
                show_labels=self.show_labels.get(),
                show_edges=self.show_edges.get(),
                dot_radius=self.dot_radius,
            )
            self.status.set(f"PNG geschreven: {filename}")
        except Exception as exc:
            messagebox.showerror(APP_NAME, f"Kan PNG niet schrijven:\n\n{exc}")

    def show_about(self) -> None:
        messagebox.showinfo(
            APP_NAME,
            f"{APP_NAME} {APP_VERSION}\n\n"
            "Viewer voor .graph- en .opn-bestanden.\n"
            "Ondersteunt floatcoördinaten, lege labels en edge-secties.",
        )


def parse_cli(argv: Sequence[str]) -> List[Path]:
    files: List[Path] = []
    for arg in argv:
        if arg in {"--help", "-h", "/?"}:
            print(f"{APP_NAME} {APP_VERSION}")
            print("Gebruik: py graph_viewer.py [bestand1.graph bestand2.opn ...]")
            raise SystemExit(0)
        if arg == "--selftest":
            continue
        files.append(Path(arg))
    return files


def selftest(files: Sequence[Path]) -> int:
    if not files:
        print("SELFTEST: geen bestanden opgegeven")
        return 2
    failed = 0
    for path in files:
        try:
            data = parse_graph(path)
            print(f"OK: {path}: dots={len(data.dots)}, edges={len(data.edges)}, grid={data.grid_x_count}x{data.grid_y_count}")
        except Exception as exc:
            failed += 1
            print(f"FOUT: {path}: {exc}")
    return 0 if failed == 0 else 1


def main(argv: Optional[Sequence[str]] = None) -> int:
    if argv is None:
        argv = sys.argv[1:]
    if "--selftest" in argv:
        files = [Path(a) for a in argv if a != "--selftest"]
        return selftest(files)
    try:
        files = parse_cli(argv)
        app = GraphViewer(files)
        app.mainloop()
        return 0
    except SystemExit:
        raise
    except Exception:
        traceback.print_exc()
        messagebox.showerror(APP_NAME, traceback.format_exc())
        return 1


if __name__ == "__main__":
    raise SystemExit(main())

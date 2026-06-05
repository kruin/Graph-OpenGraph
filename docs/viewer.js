(() => {
  'use strict';

  const els = {
    svg: document.getElementById('graphSvg'),
    canvasWrap: document.getElementById('canvasWrap'),
    titleLine: document.getElementById('titleLine'),
    stepLabel: document.getElementById('stepLabel'),
    metaLine: document.getElementById('metaLine'),
    stepRange: document.getElementById('stepRange'),
    stepHeading: document.getElementById('stepHeading'),
    stepText: document.getElementById('stepText'),
    playButton: document.getElementById('playButton'),
    nextButton: document.getElementById('nextButton'),
    prevButton: document.getElementById('prevButton'),
    firstButton: document.getElementById('firstButton'),
    lastButton: document.getElementById('lastButton'),
    fitButton: document.getElementById('fitButton'),
    resetViewButton: document.getElementById('resetViewButton'),
    undoButton: document.getElementById('undoButton'),
    redoButton: document.getElementById('redoButton'),
    fileInput: document.getElementById('fileInput'),
    installButton: document.getElementById('installButton'),
    maxNodesInput: document.getElementById('maxNodesInput'),
    noLimitInput: document.getElementById('noLimitInput'),
    autoSizeInput: document.getElementById('autoSizeInput'),
    nodeSizeInput: document.getElementById('nodeSizeInput'),
    cellSizeInput: document.getElementById('cellSizeInput'),
    intervalInput: document.getElementById('intervalInput'),
    growGridInput: document.getElementById('growGridInput'),
    showGridInput: document.getElementById('showGridInput'),
    showLabelsInput: document.getElementById('showLabelsInput'),
    showEdgesInput: document.getElementById('showEdgesInput'),
    showAxesInput: document.getElementById('showAxesInput')
  };

  const state = {
    demo: null,
    step: 0,
    controlsReady: false,
    playing: false,
    timer: null,
    deferredInstallPrompt: null,
    touchStartX: null,
    touchStartY: null,
    undoStack: [],
    redoStack: [],
    view: {
      maxNodes: 30,
      noLimit: true,
      autoSize: true,
      nodeRadius: 6,
      cellSize: 28,
      intervalMs: 700,
      growGrid: true,
      showGrid: true,
      showLabels: true,
      showEdges: false,
      showAxes: true
    },
    computed: {
      cellSize: 28,
      nodeRadius: 6,
      fontSize: 7,
      apparentCellPx: 24
    }
  };

  const NS = 'http://www.w3.org/2000/svg';

  const DEFAULT_SAMPLE_URL = 'samples/no_limit_96_demo.json';

  function clamp(value, min, max) {
    return Math.max(min, Math.min(max, value));
  }

  function toNumber(value, fallback) {
    const number = Number(value);
    return Number.isFinite(number) ? number : fallback;
  }

  function orderedNodes(demo = state.demo) {
    const nodes = Array.isArray(demo?.nodes) ? [...demo.nodes] : [];
    return nodes.sort((a, b) => {
      const sa = toNumber(a.step ?? a.order, 0);
      const sb = toNumber(b.step ?? b.order, 0);
      if (sa !== sb) return sa - sb;
      return String(a.id).localeCompare(String(b.id), 'nl', { numeric: true });
    });
  }

  function rawMaxStep(demo = state.demo) {
    if (!demo) return 0;
    const fromSteps = Array.isArray(demo.steps) && demo.steps.length ? Math.max(...demo.steps.map(s => toNumber(s.step, 0))) : 0;
    const fromNodes = Array.isArray(demo.nodes) && demo.nodes.length ? Math.max(...demo.nodes.map(n => toNumber(n.step ?? n.order, 0))) : 0;
    const fromEdges = Array.isArray(demo.edges) && demo.edges.length ? Math.max(...demo.edges.map(e => toNumber(e.step ?? e.order, 0))) : 0;
    return Math.max(fromSteps, fromNodes, fromEdges, 0);
  }

  function totalNodes(demo = state.demo) {
    return orderedNodes(demo).length;
  }

  function limitedNodesForMax(demo = state.demo) {
    const nodes = orderedNodes(demo);
    if (state.view.noLimit) return nodes;
    const maxNodes = clamp(Math.floor(toNumber(state.view.maxNodes, 30)), 1, Math.max(1, nodes.length));
    return nodes.slice(0, maxNodes);
  }

  function effectiveMaxStep(demo = state.demo) {
    if (!demo) return 0;
    if (state.view.noLimit) return rawMaxStep(demo);
    const limited = limitedNodesForMax(demo);
    if (!limited.length) return 0;
    return Math.max(...limited.map(n => toNumber(n.step ?? n.order, 0)));
  }

  function intervalMs() {
    const fromView = toNumber(state.view.intervalMs, NaN);
    const fromDemo = toNumber(state.demo?.grow?.interval_ms, 700);
    const value = Number.isFinite(fromView) ? fromView : fromDemo;
    return clamp(value, 80, 10000);
  }

  function visibleNodes() {
    const max = effectiveMaxStep();
    const stepLimit = Math.min(state.step, max);
    return limitedNodesForMax().filter(n => toNumber(n.step ?? n.order, 0) <= stepLimit);
  }

  function inferredGrowthEdges(nodeIds) {
    const nodes = orderedNodes(state.demo).filter(n => nodeIds.has(n.id));
    const edges = [];
    for (let i = 1; i < nodes.length; i++) {
      const previous = nodes[i - 1];
      const current = nodes[i];
      edges.push({
        id: `g${String(i - 1).padStart(3, '0')}`,
        from: previous.id,
        to: current.id,
        step: toNumber(current.step ?? current.order, i),
        order: i,
        inferred: true
      });
    }
    return edges;
  }

  function visibleEdges(nodeIds) {
    if (!state.view.showEdges) return [];
    const rawEdges = Array.isArray(state.demo?.edges) ? state.demo.edges : [];
    const stepLimit = Math.min(state.step, effectiveMaxStep());
    const actualEdges = rawEdges.filter(e => toNumber(e.step ?? e.order, 0) <= stepLimit && nodeIds.has(e.from) && nodeIds.has(e.to));
    // v4346: make the "Lijnen tonen" toggle visibly active even for free-node
    // demos whose JSON deliberately contains no edges. In that case we draw
    // derived growth lines between consecutive revealed steps. These lines are
    // visual aids only; they do not impose HOR/VER placement.
    if (actualEdges.length || rawEdges.length) return actualEdges;
    return inferredGrowthEdges(nodeIds).filter(e => toNumber(e.step ?? e.order, 0) <= stepLimit);
  }

  function hasInferredEdgesVisible() {
    return !!state.view.showEdges && Array.isArray(state.demo?.edges) && state.demo.edges.length === 0;
  }

  function configuredCellSize() {
    const fromView = toNumber(state.view.cellSize, NaN);
    if (Number.isFinite(fromView)) return clamp(fromView, 12, 80);
    const grid = state.demo?.grid || {};
    return clamp(toNumber(grid.cell_width || grid.step_x || 28, 28), 12, 80);
  }

  function modelPosition(node) {
    if (node?.model && Number.isFinite(Number(node.model.x)) && Number.isFinite(Number(node.model.y))) {
      return { x: Number(node.model.x), y: Number(node.model.y) };
    }
    if (node?.grid && Number.isFinite(Number(node.grid.x)) && Number.isFinite(Number(node.grid.y))) {
      const origin = state.demo?.grid?.origin || { x: 0, y: 0 };
      const ox = toNumber(origin.x, 0);
      const oy = toNumber(origin.y, 0);
      return { x: Number(node.grid.x) - ox, y: Number(node.grid.y) - oy };
    }
    if (node?.position && Number.isFinite(Number(node.position.x)) && Number.isFinite(Number(node.position.y))) {
      const gridCell = toNumber(state.demo?.grid?.cell_width || state.demo?.grid?.step_x, configuredCellSize());
      return { x: Number(node.position.x) / gridCell, y: Number(node.position.y) / gridCell };
    }
    return { x: 0, y: 0 };
  }

  function freedomCoordinate(node) {
    // For freedom checks, source grid/model coordinates are authoritative.
    // Pixel position is only a drawing fallback and may not hide HOR/VER conflicts.
    if (node?.model && Number.isFinite(Number(node.model.x)) && Number.isFinite(Number(node.model.y))) {
      return { x: Number(node.model.x), y: Number(node.model.y) };
    }
    if (node?.source && Number.isFinite(Number(node.source.x)) && Number.isFinite(Number(node.source.y))) {
      return { x: Number(node.source.x), y: Number(node.source.y) };
    }
    if (Number.isFinite(Number(node?.source_x)) && Number.isFinite(Number(node?.source_y))) {
      return { x: Number(node.source_x), y: Number(node.source_y) };
    }
    return modelPosition(node);
  }

  function freedomReport(nodes = limitedNodesForMax()) {
    const seenX = new Map();
    const seenY = new Map();
    const duplicateX = new Set();
    const duplicateY = new Set();
    for (const node of nodes) {
      const p = freedomCoordinate(node);
      const xKey = String(p.x);
      const yKey = String(p.y);
      if (seenX.has(xKey)) duplicateX.add(xKey);
      else seenX.set(xKey, node.id);
      if (seenY.has(yKey)) duplicateY.add(yKey);
      else seenY.set(yKey, node.id);
    }
    return {
      ok: duplicateX.size === 0 && duplicateY.size === 0,
      duplicateXCount: duplicateX.size,
      duplicateYCount: duplicateY.size,
      xLines: seenX.size,
      yLines: seenY.size,
      nodeCount: nodes.length
    };
  }

  function sourceNodesForBounds(nodes, useAllForStatic = false) {
    if (useAllForStatic) return limitedNodesForMax();
    return nodes.length ? nodes : limitedNodesForMax().slice(0, 1);
  }

  function modelBounds(nodes, useAllForStatic = false) {
    const grid = state.demo?.grid || {};
    const marginUnits = Math.max(1.6, toNumber(grid.margin, 2));
    const sourceNodes = sourceNodesForBounds(nodes, useAllForStatic);
    if (!sourceNodes.length) {
      return { minX: -3, minY: -3, maxX: 3, maxY: 3, widthUnits: 6, heightUnits: 6, marginUnits };
    }

    const pts = sourceNodes.map(modelPosition);
    let minX = Math.min(...pts.map(p => p.x));
    let maxX = Math.max(...pts.map(p => p.x));
    let minY = Math.min(...pts.map(p => p.y));
    let maxY = Math.max(...pts.map(p => p.y));
    if (minX === maxX) { minX -= 2; maxX += 2; }
    if (minY === maxY) { minY -= 2; maxY += 2; }
    minX -= marginUnits;
    maxX += marginUnits;
    minY -= marginUnits;
    maxY += marginUnits;
    return { minX, minY, maxX, maxY, widthUnits: Math.max(1, maxX - minX), heightUnits: Math.max(1, maxY - minY), marginUnits };
  }

  function recomputeVisuals(boundsUnits) {
    const cell = configuredCellSize();
    const wrap = els.canvasWrap?.getBoundingClientRect?.() || { width: 640, height: 480 };
    const usableWidth = Math.max(240, wrap.width || 640);
    const usableHeight = Math.max(240, wrap.height || 480);
    const apparentCellX = usableWidth / Math.max(1, boundsUnits.widthUnits);
    const apparentCellY = usableHeight / Math.max(1, boundsUnits.heightUnits);
    const apparentCellPx = Math.max(4, Math.min(apparentCellX, apparentCellY));

    if (!state.view.autoSize) {
      state.computed.cellSize = cell;
      state.computed.nodeRadius = clamp(toNumber(state.view.nodeRadius, 6), 3, 20);
      state.computed.fontSize = clamp(state.computed.nodeRadius * 1.1, 6, 16);
      state.computed.apparentCellPx = apparentCellPx;
      return;
    }

    // v4346: smooth auto sizing. The visible node size is derived from
    // available screen pixels and the current fitted grid. It no longer has
    // a hard minimum expressed in grid cells, because that made small graphs
    // look like connected beads rather than free OpenGraph nodes.
    const desiredRadiusPx = clamp(apparentCellPx * 0.18, 3.8, 7.4);
    const desiredFontPx = clamp(desiredRadiusPx * 1.35, 7.0, 10.5);
    const radiusInCells = clamp(desiredRadiusPx / apparentCellPx, 0.035, 0.22);
    const fontInCells = clamp(desiredFontPx / apparentCellPx, 0.055, 0.30);

    state.computed.cellSize = cell;
    state.computed.nodeRadius = radiusInCells * cell;
    state.computed.fontSize = fontInCells * cell;
    state.computed.apparentCellPx = apparentCellPx;
  }

  function nodePosition(node) {
    const p = modelPosition(node);
    const cell = state.computed.cellSize || configuredCellSize();
    return { x: p.x * cell, y: p.y * cell };
  }

  function layoutBounds(nodes, useAllForStatic = false) {
    const unitBounds = modelBounds(nodes, useAllForStatic);
    recomputeVisuals(unitBounds);
    const cell = state.computed.cellSize || configuredCellSize();
    return {
      minX: unitBounds.minX * cell,
      minY: unitBounds.minY * cell,
      maxX: unitBounds.maxX * cell,
      maxY: unitBounds.maxY * cell,
      cellW: cell,
      cellH: cell,
      unitBounds
    };
  }

  function setViewBox(bounds) {
    const width = Math.max(120, bounds.maxX - bounds.minX);
    const height = Math.max(120, bounds.maxY - bounds.minY);
    els.svg.setAttribute('viewBox', `${bounds.minX} ${bounds.minY} ${width} ${height}`);
  }

  function svgEl(name, attrs = {}, text = null) {
    const el = document.createElementNS(NS, name);
    for (const [key, value] of Object.entries(attrs)) el.setAttribute(key, String(value));
    if (text !== null) el.textContent = text;
    return el;
  }

  function setControlsEnabled(enabled) {
    state.controlsReady = !!enabled;
    const inputs = [
      els.firstButton, els.prevButton, els.playButton, els.nextButton, els.lastButton,
      els.fitButton, els.stepRange, els.undoButton, els.redoButton, els.resetViewButton,
      els.maxNodesInput, els.noLimitInput, els.autoSizeInput, els.nodeSizeInput, els.cellSizeInput, els.intervalInput,
      els.growGridInput, els.showGridInput, els.showLabelsInput, els.showEdgesInput, els.showAxesInput
    ];
    for (const input of inputs) if (input) input.disabled = !enabled;
  }

  function makeFreeHorVerCoords(count) {
    // v4346: free placement means HOR/VER freedom on the source grid:
    // no two free nodes may share the same vertical x-line or horizontal y-line.
    // The compact 4-arm pattern stays readable while preserving that freedom.
    const n = Math.max(1, Math.floor(toNumber(count, 1)));
    const coords = [[0, 0]];
    let arm = 0;
    while (coords.length < n) {
      const a = 2 * arm + 1;
      const b = 2 * arm + 2;
      const block = [
        [a, -a],
        [-a, -b],
        [-b, a],
        [b, b]
      ];
      for (const p of block) {
        if (coords.length >= n) break;
        coords.push(p);
      }
      arm++;
    }
    return coords;
  }

  function buildDemoFromCoords(coords, title, stem, noLimit = true, max = 30, withEdges = false) {
    const nodes = [];
    const edges = [];
    const steps = [];
    coords.forEach(([x, y], i) => {
      const id = `n${String(i).padStart(3, '0')}`;
      nodes.push({ id, label: String(i), step: i, order: i, model: { x, y } });
      steps.push({ step: i, node: id, text: i === 0 ? 'Start: toon de eerste vrije knoop.' : `Grow: voeg knoop ${i} toe.` });
      if (withEdges && i > 0) edges.push({ id: `e${String(i - 1).padStart(3, '0')}`, from: `n${String(i - 1).padStart(3, '0')}`, to: id, step: i, order: i });
    });
    return {
      format: 'opengraph-greedy-grow-demo',
      format_version: 7,
      title,
      project: { name: 'opengraph-greedy-grow', language: 'nl', stem },
      grid: { rows: 35, columns: 35, cell_width: 28, cell_height: 28, origin: { x: 0, y: 0 }, fit_content: true, grow_with_step: true, show_grid: true, show_axes_through_origin: true, major_every: 5, margin: 2 },
      freedom: { hor_ver_free: true, x_line_unique: true, y_line_unique: true, diagonal_free: 'none' },
      greedy: { count: coords.length, config_count: coords.length, max, generation_max: coords.length, no_limit: noLimit, style: 'free-hor-ver-demo', rule: 'hor-ver-free' },
      grow: { interval_ms: 700, start_step: 0, auto_start: false, reveal_edges: 'when_both_nodes_visible', stop_at_end: true, loop: false, undo_redo_per_step: true, last_step_equals_static: true },
      style: { node_radius: 6, show_labels: true, show_edges: false, auto_size: true, free_nodes: true, hor_ver_free: true },
      nodes, edges, steps
    };
  }

  function fallbackDemo() {
    return buildDemoFromCoords(makeFreeHorVerCoords(96), 'OpenGraph Greedy Grow — ingebouwde HOR/VER-vrije NoLimit-demo', 'fallback_no_limit_96_hor_ver_free_nodes', true, 30, false);
  }

  function normalizeDemo(raw) {
    const demo = { ...raw };
    demo.grid = demo.grid && typeof demo.grid === 'object' ? { ...demo.grid } : {};
    demo.grow = demo.grow && typeof demo.grow === 'object' ? { ...demo.grow } : {};
    demo.greedy = demo.greedy && typeof demo.greedy === 'object' ? { ...demo.greedy } : {};
    demo.style = demo.style && typeof demo.style === 'object' ? { ...demo.style } : {};
    demo.nodes = Array.isArray(demo.nodes) ? demo.nodes.map((node, index) => {
      const id = String(node.id ?? node.name ?? `n${index}`);
      const step = toNumber(node.step ?? node.order, index);
      return { ...node, id, label: String(node.label ?? id), step: Number.isFinite(step) ? step : index, order: toNumber(node.order, step) };
    }) : [];
    demo.edges = Array.isArray(demo.edges) ? demo.edges.map((edge, index) => {
      const step = toNumber(edge.step ?? edge.order, index + 1);
      return { ...edge, id: String(edge.id ?? `e${index}`), from: String(edge.from ?? ''), to: String(edge.to ?? ''), step: Number.isFinite(step) ? step : index + 1 };
    }) : [];
    demo.steps = Array.isArray(demo.steps) ? demo.steps.map((step, index) => {
      const n = toNumber(step.step, index);
      return { ...step, step: Number.isFinite(n) ? n : index };
    }) : [];
    if (!demo.steps.length) {
      demo.steps = demo.nodes.map(node => ({
        step: toNumber(node.step, 0),
        node: node.id,
        edge: null,
        text: `Stap ${toNumber(node.step, 0)}: toon ${node.label ?? node.id}.`
      }));
    }
    demo.grow.interval_ms = toNumber(demo.grow.interval_ms, 700);
    demo.grow.start_step = toNumber(demo.grow.start_step, 0);
    demo.grid.cell_width = toNumber(demo.grid.cell_width || demo.grid.step_x, 28);
    demo.grid.cell_height = toNumber(demo.grid.cell_height || demo.grid.step_y, demo.grid.cell_width);
    if (typeof demo.style.auto_size !== 'boolean') demo.style.auto_size = true;
    if (typeof demo.style.show_edges !== 'boolean') demo.style.show_edges = false;
    if (typeof demo.style.free_nodes !== 'boolean') demo.style.free_nodes = true;
    return demo;
  }

  function applyDemoDefaultsToView(demo) {
    const nodeCount = orderedNodes(demo).length || 1;
    const configuredMax = toNumber(demo.greedy?.max ?? demo.greedy?.generation_max, Math.min(30, nodeCount));
    state.view.maxNodes = clamp(Math.floor(configuredMax), 1, Math.max(1, nodeCount));
    state.view.noLimit = demo.greedy?.no_limit === true || nodeCount > state.view.maxNodes;
    state.view.autoSize = demo.style?.auto_size !== false;
    state.view.nodeRadius = clamp(toNumber(demo.style?.node_radius, 6), 3, 20);
    state.view.cellSize = clamp(toNumber(demo.grid?.cell_width, 28), 12, 80);
    state.view.intervalMs = clamp(toNumber(demo.grow?.interval_ms, 700), 80, 10000);
    state.view.growGrid = demo.grid?.grow_with_step !== false;
    state.view.showGrid = demo.grid?.show_grid !== false;
    state.view.showLabels = demo.style?.show_labels !== false;
    state.view.showEdges = demo.style?.show_edges === true;
    state.view.showAxes = demo.grid?.show_axes_through_origin !== false;
    syncConfigControls();
  }

  function syncConfigControls() {
    const nodeCount = Math.max(1, totalNodes());
    els.maxNodesInput.value = String(clamp(Math.floor(state.view.maxNodes), 1, nodeCount));
    els.noLimitInput.checked = !!state.view.noLimit;
    els.maxNodesInput.disabled = !state.controlsReady || !!state.view.noLimit;
    if (els.autoSizeInput) els.autoSizeInput.checked = !!state.view.autoSize;
    els.nodeSizeInput.value = String(Math.round(state.view.nodeRadius));
    els.cellSizeInput.value = String(Math.round(state.view.cellSize));
    els.nodeSizeInput.disabled = !state.controlsReady || !!state.view.autoSize;
    els.cellSizeInput.disabled = !state.controlsReady || !!state.view.autoSize;
    els.intervalInput.value = String(intervalMs());
    els.growGridInput.checked = !!state.view.growGrid;
    els.showGridInput.checked = !!state.view.showGrid;
    els.showLabelsInput.checked = !!state.view.showLabels;
    if (els.showEdgesInput) els.showEdgesInput.checked = !!state.view.showEdges;
    els.showAxesInput.checked = !!state.view.showAxes;
  }

  function drawGrid(group, bounds) {
    if (!state.view.showGrid) return;
    const cellW = bounds.cellW || configuredCellSize();
    const cellH = bounds.cellH || configuredCellSize();
    const grid = state.demo?.grid || {};
    const majorEvery = Math.max(1, Math.round(toNumber(grid.major_every || grid.show_major_grid_every, 5)));
    const startX = Math.floor(bounds.minX / cellW) * cellW;
    const endX = Math.ceil(bounds.maxX / cellW) * cellW;
    const startY = Math.floor(bounds.minY / cellH) * cellH;
    const endY = Math.ceil(bounds.maxY / cellH) * cellH;

    for (let x = startX; x <= endX; x += cellW) {
      const ix = Math.round(x / cellW);
      group.appendChild(svgEl('line', { x1: x, y1: startY, x2: x, y2: endY, class: `grid-line ${ix % majorEvery === 0 ? 'major' : ''}` }));
    }
    for (let y = startY; y <= endY; y += cellH) {
      const iy = Math.round(y / cellH);
      group.appendChild(svgEl('line', { x1: startX, y1: y, x2: endX, y2: y, class: `grid-line ${iy % majorEvery === 0 ? 'major' : ''}` }));
    }

    if (state.view.showAxes) {
      if (0 >= startX && 0 <= endX) group.appendChild(svgEl('line', { x1: 0, y1: startY, x2: 0, y2: endY, class: 'axis' }));
      if (0 >= startY && 0 <= endY) group.appendChild(svgEl('line', { x1: startX, y1: 0, x2: endX, y2: 0, class: 'axis' }));
    }
  }

  function currentStepRecord() {
    const records = Array.isArray(state.demo?.steps) ? state.demo.steps : [];
    return records.find(s => toNumber(s.step, 0) === state.step) || null;
  }

  function render() {
    const demo = state.demo;
    if (!demo) return;
    const max = effectiveMaxStep(demo);
    state.step = clamp(state.step, 0, max);

    els.svg.replaceChildren();
    const nodes = visibleNodes();
    const nodeIds = new Set(nodes.map(n => n.id));
    const edges = visibleEdges(nodeIds);
    const allNodesById = new Map((demo.nodes || []).map(n => [n.id, n]));
    const bounds = layoutBounds(nodes, !state.view.growGrid);
    setViewBox(bounds);

    const gridG = svgEl('g', { 'aria-hidden': 'true', class: 'grid' });
    drawGrid(gridG, bounds);
    els.svg.appendChild(gridG);

    const edgeG = svgEl('g', { class: 'edges' });
    for (const edge of edges) {
      const a = allNodesById.get(edge.from);
      const b = allNodesById.get(edge.to);
      if (!a || !b) continue;
      const p1 = nodePosition(a);
      const p2 = nodePosition(b);
      edgeG.appendChild(svgEl('line', { x1: p1.x, y1: p1.y, x2: p2.x, y2: p2.y, class: edge.inferred ? 'edge inferred-edge' : 'edge' }));
    }
    els.svg.appendChild(edgeG);

    const circleG = svgEl('g', { class: 'node-circles' });
    const highlightG = svgEl('g', { class: 'node-highlights' });
    const labelG = svgEl('g', { class: 'node-labels' });
    const currentNodeId = currentStepRecord()?.node;
    const radius = clamp(toNumber(state.computed.nodeRadius, 6), 2, 24);
    const fontSize = clamp(toNumber(state.computed.fontSize, 7), 4, 18);
    for (const node of nodes) {
      const p = nodePosition(node);
      circleG.appendChild(svgEl('circle', { cx: p.x, cy: p.y, r: radius, class: 'node' }));
      if (node.id === currentNodeId) {
        // Highlight is a separate outside ring. The actual node circle keeps
        // exactly the same radius as every other free node.
        highlightG.appendChild(svgEl('circle', { cx: p.x, cy: p.y, r: radius + Math.max(1.2, radius * 0.18), class: 'current-ring' }));
      }
      if (state.view.showLabels) {
        const labelText = String(node.label ?? node.id);
        const isLongLabel = labelText.length > 3 || /\s|:|\|/.test(labelText);
        if (isLongLabel) {
          labelG.appendChild(svgEl('text', { x: p.x + radius * 1.45, y: p.y, class: 'node-label node-label-long', style: `font-size:${Math.max(5.5, fontSize * 0.86)}px` }, labelText));
        } else {
          labelG.appendChild(svgEl('text', { x: p.x, y: p.y, class: 'node-label', style: `font-size:${fontSize}px` }, labelText));
        }
      }
    }
    els.svg.appendChild(circleG);
    els.svg.appendChild(highlightG);
    // Labels are deliberately appended after all circles and highlights so numbers can never disappear behind nodes.
    els.svg.appendChild(labelG);

    updateText();
  }

  function updateText() {
    const demo = state.demo;
    if (!demo) {
      els.titleLine.textContent = 'JAN Open Notation Viewer';
      els.stepLabel.textContent = 'Stap 0 / 0';
      els.stepRange.max = '0';
      els.stepRange.value = '0';
      els.stepHeading.textContent = 'Stap';
      els.stepText.textContent = 'Technische startdemo wordt geladen. Gebruik de carousel voor uitlegbeelden.';
      els.metaLine.textContent = 'Wacht op een geldige Greedy Grow JSON-demo.';
      els.playButton.textContent = '▶';
      return;
    }
    const max = effectiveMaxStep(demo);
    const rawMax = rawMaxStep(demo);
    const step = currentStepRecord();
    els.titleLine.textContent = demo.title || demo.project?.title || 'JAN Open Notation Viewer';
    els.stepLabel.textContent = `Stap ${state.step} / ${max}`;
    els.stepRange.max = String(max);
    els.stepRange.value = String(state.step);
    els.stepHeading.textContent = `Stap ${state.step}`;
    els.stepText.textContent = step?.text || (state.step === max ? 'Eindbeeld: gelijk aan de statische Greedy-weergave.' : 'Geen staptekst beschikbaar.');
    const count = visibleNodes().length;
    const total = totalNodes(demo);
    const limitText = state.view.noLimit ? `NoLimit: alle ${total}` : `Max ${state.view.maxNodes}`;
    const sizeText = state.view.autoSize
      ? `auto-size · cel≈${Math.round(state.computed.apparentCellPx)}px · knoop≈${Math.round(state.computed.nodeRadius * state.computed.apparentCellPx / state.computed.cellSize)}px`
      : `manual · cel ${state.view.cellSize} · knoop ${state.view.nodeRadius}`;
    const topologyText = state.view.showEdges ? (hasInferredEdgesVisible() ? 'groeilijnen afgeleid' : 'JSON-lijnen aan') : 'vrije bronknopen';
    const freedom = freedomReport(limitedNodesForMax());
    const freedomText = freedom.ok ? 'HOR/VER vrij' : `HOR/VER-conflict: x=${freedom.duplicateXCount}, y=${freedom.duplicateYCount}`;
    els.metaLine.textContent = `${count} / ${total} knopen zichtbaar · ${topologyText} · ${freedomText} · ${limitText} · raw ${rawMax} · ${sizeText} · interval ${intervalMs()} ms · ${state.playing ? 'grow-mode' : 'handmatig'}`;
    els.playButton.textContent = state.playing ? '⏸' : '▶';
    els.undoButton.disabled = !state.undoStack.length;
    els.redoButton.disabled = !state.redoStack.length;
    syncConfigControls();
  }

  function setStep(step, historyMode = 'push') {
    if (!state.demo) return;
    const nextStep = clamp(Number(step) || 0, 0, effectiveMaxStep(state.demo));
    if (nextStep === state.step) {
      render();
      return;
    }
    if (historyMode === 'push') {
      state.undoStack.push(state.step);
      state.redoStack = [];
    }
    state.step = nextStep;
    render();
  }

  function next() {
    if (!state.demo) return;
    if (state.step >= effectiveMaxStep(state.demo)) {
      if (state.demo?.grow?.loop) setStep(0);
      else stopPlaying();
      return;
    }
    setStep(state.step + 1);
  }

  function prev() { setStep(state.step - 1); }
  function first() { setStep(0); }
  function last() { setStep(effectiveMaxStep(state.demo)); }

  function undo() {
    if (!state.undoStack.length) return;
    const previous = state.undoStack.pop();
    state.redoStack.push(state.step);
    state.step = previous;
    render();
  }

  function redo() {
    if (!state.redoStack.length) return;
    const restored = state.redoStack.pop();
    state.undoStack.push(state.step);
    state.step = restored;
    render();
  }

  function startPlaying() {
    if (!state.demo || state.playing) return;
    state.playing = true;
    els.playButton.textContent = '⏸';
    state.timer = window.setInterval(next, intervalMs());
    updateText();
  }

  function stopPlaying(update = true) {
    state.playing = false;
    if (state.timer) window.clearInterval(state.timer);
    state.timer = null;
    if (update) updateText();
  }

  function togglePlaying() { state.playing ? stopPlaying() : startPlaying(); }

  function validateDemo(raw) {
    if (!raw || typeof raw !== 'object') throw new Error('JSON is geen object.');
    const demo = normalizeDemo(raw);
    if (!Array.isArray(demo.nodes) || !demo.nodes.length) throw new Error('JSON mist nodes[] of nodes[] is leeg.');
    return demo;
  }

  async function loadDemoUrl(url) {
    const response = await fetch(url, { cache: 'no-cache' });
    if (!response.ok) throw new Error(`Kon demo niet laden: ${response.status}`);
    const raw = await response.json();
    setDemo(validateDemo(raw));
  }

  function setDemo(demo) {
    stopPlaying(false);
    state.demo = validateDemo(demo);
    applyDemoDefaultsToView(state.demo);
    state.step = clamp(toNumber(state.demo.grow?.start_step, 0), 0, effectiveMaxStep(state.demo));
    state.undoStack = [];
    state.redoStack = [];
    setControlsEnabled(true);
    render();
    if (state.demo.grow?.auto_start) startPlaying();
  }

  async function loadFile(file) {
    const text = await file.text();
    const raw = JSON.parse(text);
    setDemo(validateDemo(raw));
  }

  function applyConfigFromControls() {
    const oldNoLimit = !!state.view.noLimit;
    state.view.noLimit = !!els.noLimitInput.checked;
    if (!state.view.noLimit && oldNoLimit && Math.floor(toNumber(els.maxNodesInput.value, state.view.maxNodes)) >= totalNodes()) {
      // When leaving NoLimit, return to the config max instead of silently using all nodes.
      state.view.maxNodes = clamp(toNumber(state.demo?.greedy?.max, 30), 1, Math.max(1, totalNodes()));
    } else {
      state.view.maxNodes = clamp(Math.floor(toNumber(els.maxNodesInput.value, state.view.maxNodes)), 1, Math.max(1, totalNodes() || 9999));
    }
    state.view.autoSize = els.autoSizeInput ? !!els.autoSizeInput.checked : true;
    state.view.nodeRadius = clamp(toNumber(els.nodeSizeInput.value, state.view.nodeRadius), 3, 20);
    state.view.cellSize = clamp(toNumber(els.cellSizeInput.value, state.view.cellSize), 12, 80);
    state.view.intervalMs = clamp(toNumber(els.intervalInput.value, state.view.intervalMs), 80, 10000);
    state.view.growGrid = !!els.growGridInput.checked;
    state.view.showGrid = !!els.showGridInput.checked;
    state.view.showLabels = !!els.showLabelsInput.checked;
    state.view.showEdges = els.showEdgesInput ? !!els.showEdgesInput.checked : false;
    state.view.showAxes = !!els.showAxesInput.checked;
    syncConfigControls();
    if (state.step > effectiveMaxStep()) state.step = effectiveMaxStep();
    if (state.playing) { stopPlaying(false); startPlaying(); }
    else render();
  }

  function fitView() {
    // The viewer has no manual pan/zoom yet. Fit therefore means:
    // recompute bounds, autosize and SVG viewBox from the current visible set.
    render();
  }

  function resetViewSettings() {
    if (!state.demo) return;
    applyDemoDefaultsToView(state.demo);
    render();
  }

  function registerEvents() {
    els.nextButton.addEventListener('click', next);
    els.prevButton.addEventListener('click', prev);
    els.firstButton.addEventListener('click', first);
    els.lastButton.addEventListener('click', last);
    els.undoButton.addEventListener('click', undo);
    els.redoButton.addEventListener('click', redo);
    els.playButton.addEventListener('click', togglePlaying);
    els.fitButton.addEventListener('click', fitView);
    els.resetViewButton.addEventListener('click', resetViewSettings);
    els.stepRange.addEventListener('input', event => setStep(event.target.value));
    els.fileInput.addEventListener('change', event => {
      const file = event.target.files?.[0];
      if (!file) return;
      loadFile(file).catch(err => alert(err.message));
    });

    [els.maxNodesInput, els.noLimitInput, els.autoSizeInput, els.nodeSizeInput, els.cellSizeInput, els.intervalInput, els.growGridInput, els.showGridInput, els.showLabelsInput, els.showEdgesInput, els.showAxesInput].filter(Boolean).forEach(input => {
      input.addEventListener('input', applyConfigFromControls);
      input.addEventListener('change', applyConfigFromControls);
    });

    els.canvasWrap.addEventListener('click', event => { if (event.detail <= 1) next(); });

    els.canvasWrap.addEventListener('touchstart', event => {
      const t = event.changedTouches[0];
      state.touchStartX = t.clientX;
      state.touchStartY = t.clientY;
    }, { passive: true });

    els.canvasWrap.addEventListener('touchend', event => {
      const t = event.changedTouches[0];
      if (state.touchStartX == null) return;
      const dx = t.clientX - state.touchStartX;
      const dy = t.clientY - state.touchStartY;
      state.touchStartX = state.touchStartY = null;
      if (Math.abs(dx) < 45 || Math.abs(dx) < Math.abs(dy) * 1.2) return;
      dx < 0 ? next() : prev();
    }, { passive: true });

    window.addEventListener('keydown', event => {
      const key = event.key.toLowerCase();
      const ctrl = event.ctrlKey || event.metaKey;
      if (ctrl && key === 'z') { event.preventDefault(); undo(); return; }
      if (ctrl && (key === 'y' || (event.shiftKey && key === 'z'))) { event.preventDefault(); redo(); return; }
      if ([' ', 'enter', 'arrowright'].includes(key)) { event.preventDefault(); next(); }
      else if (['backspace', 'arrowleft'].includes(key)) { event.preventDefault(); prev(); }
      else if (key === 'home') first();
      else if (key === 'end') last();
      else if (key === 'g') togglePlaying();
      else if (key === 'r') first();
    });

    window.addEventListener('beforeinstallprompt', event => {
      event.preventDefault();
      state.deferredInstallPrompt = event;
      els.installButton.classList.remove('hidden');
    });

    els.installButton.addEventListener('click', async () => {
      if (!state.deferredInstallPrompt) return;
      state.deferredInstallPrompt.prompt();
      await state.deferredInstallPrompt.userChoice;
      state.deferredInstallPrompt = null;
      els.installButton.classList.add('hidden');
    });

    window.addEventListener('resize', () => render());
  }

  async function boot() {
    setControlsEnabled(false);
    updateText();
    registerEvents();
    if ('serviceWorker' in navigator && location.protocol !== 'file:') {
      navigator.serviceWorker.register('sw.js').catch(() => {});
    }
    try {
      await loadDemoUrl(DEFAULT_SAMPLE_URL);
    } catch (err) {
      console.warn('Kon technische startdemo niet laden; probeer fallback-demo.', err);
      try {
        await loadDemoUrl('samples/no_limit_96_demo.json');
      } catch (err2) {
        console.warn('Kon NoLimit-sample niet laden; ingebouwde fallback-demo wordt gebruikt.', err2);
        setDemo(fallbackDemo());
        els.stepText.textContent = `Sample kon niet worden geladen (${err2.message}). Ingebouwde fallback-demo getoond.`;
      }
    }
  }

  boot();
})();

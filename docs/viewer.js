(() => {
  'use strict';

  const VERSION = 'v4380';
  const CELL = 74;
  const ROOT_SIDE_GAP = 1;
  const SVG_NS = 'http://www.w3.org/2000/svg';

  const els = {
    svg: document.getElementById('graphSvg'),
    canvasWrap: document.getElementById('canvasWrap'),
    exampleSelect: document.getElementById('exampleSelect'),
    centralModeSelect: document.getElementById('centralModeSelect'),
    projectionHelp: document.getElementById('projectionHelp'),
    titleLine: document.getElementById('titleLine'),
    metaLine: document.getElementById('metaLine'),
    actionFeedback: document.getElementById('actionFeedback'),
    explainHeading: document.getElementById('explainHeading'),
    explainText: document.getElementById('explainText'),
    showGridInput: document.getElementById('showGridInput'),
    showRelationsInput: document.getElementById('showRelationsInput'),
    showLabelsInput: document.getElementById('showLabelsInput'),
    snapInput: document.getElementById('snapInput'),
    lexRuleSelect: document.getElementById('lexRuleSelect'),
    lexOrderList: document.getElementById('lexOrderList'),
    selectionEmpty: document.getElementById('selectionEmpty'),
    nodeEditor: document.getElementById('nodeEditor'),
    nodeIdField: document.getElementById('nodeIdField'),
    nodeLabelInput: document.getElementById('nodeLabelInput'),
    nodeCatInput: document.getElementById('nodeCatInput'),
    nodeRoleInput: document.getElementById('nodeRoleInput'),
    nodeXInput: document.getElementById('nodeXInput'),
    nodeYInput: document.getElementById('nodeYInput'),
    applyNodeButton: document.getElementById('applyNodeButton'),
    addNodeButton: document.getElementById('addNodeButton'),
    duplicateNodeButton: document.getElementById('duplicateNodeButton'),
    deleteNodeButton: document.getElementById('deleteNodeButton'),
    edgeFromSelect: document.getElementById('edgeFromSelect'),
    edgeToSelect: document.getElementById('edgeToSelect'),
    edgeTypeSelect: document.getElementById('edgeTypeSelect'),
    addEdgeButton: document.getElementById('addEdgeButton'),
    edgeList: document.getElementById('edgeList'),
    fileInput: document.getElementById('fileInput'),
    resetExampleButton: document.getElementById('resetExampleButton'),
    fitButton: document.getElementById('fitButton'),
    undoButton: document.getElementById('undoButton'),
    redoButton: document.getElementById('redoButton'),
    downloadJsonButton: document.getElementById('downloadJsonButton'),
    downloadOpnButton: document.getElementById('downloadOpnButton'),
    lexLeftButton: document.getElementById('lexLeftButton'),
    lexRightButton: document.getElementById('lexRightButton'),
    applyLexRuleButton: document.getElementById('applyLexRuleButton')
  };

  const EXAMPLES = [
    {
      id: 'hond-bijt-man',
      title: 'HOND BIJT MAN',
      phase: 'Fase 1+2',
      lexRule: 'hoofdzininvariant',
      sentence: 'HOND BIJT MAN',
      lexItems: [
        { id: 'hond', label: 'HOND', source: 'hond' },
        { id: 'bijt', label: 'BIJT', source: 'bijt' },
        { id: 'man', label: 'MAN', source: 'man' }
      ]
    },
    {
      id: 'omdat-de-hond-de-man-bijt',
      title: 'OMDAT DE HOND DE MAN BIJT',
      phase: 'Fase 3',
      lexRule: 'bijzin-omdat',
      sentence: 'OMDAT DE HOND DE MAN BIJT',
      lexItems: [
        { id: 'omdat', label: 'OMDAT', source: null, slot: 'comp' },
        { id: 'de-subj', label: 'DE', source: null, slot: 'det-subj' },
        { id: 'hond', label: 'HOND', source: 'hond' },
        { id: 'de-obj', label: 'DE', source: null, slot: 'det-obj' },
        { id: 'man', label: 'MAN', source: 'man' },
        { id: 'bijt', label: 'BIJT', source: 'bijt' }
      ]
    }
  ];

  const LEX_RULES = [
    { id: 'hoofdzininvariant', label: 'hoofdzin: subject – predicaat – object' },
    { id: 'bijzin-omdat', label: 'bijzin: Comp/(om)dat + subject + object + predicaat' }
  ];

  const CENTER_MODES = [
    { id: 'syntax', label: 'OPN · syntaxboom' },
    { id: 'functional', label: 'OPN · functionele structuur' }
  ];

  const state = {
    example: EXAMPLES[0],
    projection: 'axes',
    centerMode: 'syntax',
    selectedNodeId: null,
    showGrid: true,
    showRelations: true,
    showLabels: true
  };

  function svgEl(name, attrs = {}, text = '') {
    const el = document.createElementNS(SVG_NS, name);
    for (const [key, value] of Object.entries(attrs)) {
      if (value === null || value === undefined) continue;
      el.setAttribute(key, String(value));
    }
    if (text !== '') el.textContent = text;
    return el;
  }

  function pathEl(d, attrs = {}) {
    return svgEl('path', { d, fill: 'none', ...attrs });
  }

  function treeSpec() {
    const leaf = (id, label, cat) => ({ id, label, cat, kind: 'leaf', children: [] });
    const node = (id, label, children) => ({ id, label, cat: label, kind: 'cat', children });
    return node('s', 'S', [
      node('np-subj', 'NP', [leaf('hond', 'HOND', 'N')]),
      node('vp', 'VP', [
        node('np-obj', 'NP', [leaf('man', 'MAN', 'N')]),
        node('v', 'V', [leaf('bijt', 'BIJT', 'V')])
      ])
    ]);
  }

  function cloneTree(node) {
    return { ...node, children: node.children.map(cloneTree) };
  }

  function isLabel(node, label) {
    return node && String(node.label).toLowerCase() === String(label).toLowerCase();
  }

  function unionBox(a, b) {
    return {
      minX: Math.min(a.minX, b.minX),
      maxX: Math.max(a.maxX, b.maxX),
      minY: Math.min(a.minY, b.minY),
      maxY: Math.max(a.maxY, b.maxY)
    };
  }

  function shiftBox(box, dx, dy) {
    return { minX: box.minX + dx, maxX: box.maxX + dx, minY: box.minY + dy, maxY: box.maxY + dy };
  }

  function boxesOverlap(a, b, padding = 0) {
    return a.minX - padding <= b.maxX && b.minX - padding <= a.maxX &&
           a.minY - padding <= b.maxY && b.minY - padding <= a.maxY;
  }

  function cloneLayout(layout) {
    return {
      node: layout.node,
      nodes: layout.nodes.map(n => ({ ...n })),
      edges: layout.edges.map(e => ({ ...e })),
      boxes: layout.boxes.map(b => ({ ...b })),
      box: { ...layout.box }
    };
  }

  function shiftLayout(layout, dx, dy) {
    for (const n of layout.nodes) {
      n.x += dx;
      n.y += dy;
    }
    for (const e of layout.edges) {
      e.fromX += dx;
      e.fromY += dy;
      e.toX += dx;
      e.toY += dy;
    }
    for (const b of layout.boxes) {
      b.minX += dx;
      b.maxX += dx;
      b.minY += dy;
      b.maxY += dy;
      if (typeof b.rootX === 'number') b.rootX += dx;
      if (typeof b.rootY === 'number') b.rootY += dy;
    }
    layout.box = shiftBox(layout.box, dx, dy);
    return layout;
  }

  function rightExtent(layout) {
    return Math.max(0, layout.box.maxX);
  }

  function leftExtent(layout) {
    return Math.max(0, -layout.box.minX);
  }

  function isLabel(node, label) {
    return node && String(node.label).toLowerCase() === String(label).toLowerCase();
  }

  function stackedBelowShiftY(upperBox, upperShiftY, lowerBox, extraGap = 0) {
    return upperShiftY + upperBox.maxY + 1 + Math.max(0, extraGap) - lowerBox.minY;
  }

  function layoutBoxFromCells(cells) {
    let box = { minX: 0, maxX: 0, minY: 0, maxY: 0 };
    cells.forEach((cell, i) => {
      const b = { minX: cell.x, maxX: cell.x, minY: cell.y, maxY: cell.y };
      box = i === 0 ? b : unionBox(box, b);
    });
    return box;
  }

  function layoutLeaf(node) {
    return {
      node,
      nodes: [{ id: node.id, label: node.label, cat: node.cat, kind: node.kind, x: 0, y: 0 }],
      edges: [],
      boxes: [{ id: `box-${node.id}`, label: `BOX ${node.label}`, nodeId: node.id, leaf: true, rootX: 0, rootY: 0, minX: 0, maxX: 0, minY: 0, maxY: 0 }],
      box: { minX: 0, maxX: 0, minY: 0, maxY: 0 }
    };
  }

  function occupiedFromPlaced(rootNode, placedLayouts) {
    const occupied = {
      cells: new Set([`0,0`]),
      rootRows: new Set([0]),
      rootCols: new Set([0]),
      rows: new Set([0]),
      cols: new Set([0]),
      boxes: [{ minX: 0, maxX: 0, minY: 0, maxY: 0, rootX: 0, rootY: 0, label: rootNode.label }]
    };
    for (const layout of placedLayouts) {
      for (const n of layout.nodes) {
        occupied.cells.add(`${n.x},${n.y}`);
        occupied.rows.add(n.y);
        occupied.cols.add(n.x);
      }
      for (const b of layout.boxes) {
        occupied.boxes.push(b);
        if (typeof b.rootX === 'number' && typeof b.rootY === 'number') {
          occupied.rootRows.add(b.rootY);
          occupied.rootCols.add(b.rootX);
        }
      }
    }
    return occupied;
  }

  function candidatePositions(side, startY = 1) {
    const dir = side < 0 ? -1 : 1;
    const candidates = [];
    for (let y = startY; y < startY + 18; y++) {
      for (let distance = 1; distance <= 10; distance++) {
        candidates.push({ dx: dir * distance, dy: y });
      }
    }
    return candidates;
  }

  function shiftedRoot(layout, dx, dy) {
    const root = layout.nodes.find(n => n.id === layout.node.id) || layout.nodes[0];
    return { x: root.x + dx, y: root.y + dy };
  }

  function candidateIsFree(layout, dx, dy, occupied, options = {}) {
    const shifted = shiftBox(layout.box, dx, dy);
    if (boxesOverlap(shifted, { minX: 0, maxX: 0, minY: 0, maxY: 0 }, 0)) return false;

    for (const node of layout.nodes) {
      const shiftedX = node.x + dx;
      const shiftedY = node.y + dy;
      const key = `${shiftedX},${shiftedY}`;
      if (occupied.cells.has(key)) return false;
      if (occupied.rows.has(shiftedY)) return false;
      if (occupied.cols.has(shiftedX)) return false;
    }

    for (const box of occupied.boxes) {
      if (boxesOverlap(shifted, box, options.boxPadding ?? 0)) return false;
    }

    // Free OpenGraph placement: do not reuse occupied HOR/VER corridors.
    // This makes the next child box choose a new open row/column rather than a
    // nested side-by-side container position.
    const root = shiftedRoot(layout, dx, dy);
    if (occupied.rootRows.has(root.y)) return false;
    if (occupied.rootCols.has(root.x)) return false;
    return true;
  }

  function placeLayoutFree(layout, side, placedLayouts, parentNode, startY = 1) {
    const occupied = occupiedFromPlaced(parentNode, placedLayouts);
    const candidates = candidatePositions(side, startY);
    for (const c of candidates) {
      if (candidateIsFree(layout, c.dx, c.dy, occupied, { boxPadding: 0 })) {
        return shiftLayout(layout, c.dx, c.dy);
      }
    }

    // Safety fallback: keep moving downward until it is free.
    const dir = side < 0 ? -1 : 1;
    for (let y = startY + 18; y < startY + 80; y++) {
      for (let distance = 1; distance <= 24; distance++) {
        const dx = dir * distance;
        if (candidateIsFree(layout, dx, y, occupied, { boxPadding: 0 })) {
          return shiftLayout(layout, dx, y);
        }
      }
    }
    return shiftLayout(layout, dir, startY);
  }

  function composeLayout(node, placedLayouts) {
    const rootNode = { id: node.id, label: node.label, cat: node.cat, kind: node.kind, x: 0, y: 0 };
    const nodes = [rootNode];
    const edges = [];
    const childBoxes = [];
    let box = { minX: 0, maxX: 0, minY: 0, maxY: 0 };

    for (const child of placedLayouts) {
      const childRoot = child.nodes.find(n => n.id === child.node.id) || child.nodes[0];
      nodes.push(...child.nodes);
      edges.push({ from: node.id, to: child.node.id, fromX: 0, fromY: 0, toX: childRoot.x, toY: childRoot.y }, ...child.edges);
      childBoxes.push(...child.boxes);
      box = unionBox(box, child.box);
    }

    const rootBox = { id: `box-${node.id}`, label: `BOX ${node.label}`, nodeId: node.id, rootX: 0, rootY: 0, minX: box.minX, maxX: box.maxX, minY: box.minY, maxY: box.maxY };
    return { node, nodes, edges, boxes: [rootBox, ...childBoxes], box };
  }

  function layoutUnary(node, childLayout, sidePreference) {
    const side = sidePreference < 0 ? -1 : 1;
    const placed = placeLayoutFree(cloneLayout(childLayout), side, [], node, 1);
    return composeLayout(node, [placed]);
  }

  function layoutBinary(node, leftLayout, rightLayout) {
    const left = placeLayoutFree(cloneLayout(leftLayout), -1, [], node, 1);
    const extraGap = isLabel(node, 'S') && isLabel(leftLayout.node, 'NP') && isLabel(rightLayout.node, 'VP') ? 1 : 0;

    // Java/OpenGraph principle: second complete child box is not put next to
    // the first one. It starts below the real bottom of the already placed box,
    // then searches for the first free HOR/VER position.
    const startY = Math.max(2, left.box.maxY + 1 + extraGap - rightLayout.box.minY);
    const right = placeLayoutFree(cloneLayout(rightLayout), 1, [left], node, startY);
    return composeLayout(node, [left, right]);
  }

  function layoutNAry(node, childrenLayouts) {
    const placed = [];
    childrenLayouts.forEach((layout, i) => {
      const side = i % 2 === 0 ? -1 : 1;
      const startY = placed.length ? Math.max(...placed.map(p => p.box.maxY)) + 1 : 1;
      placed.push(placeLayoutFree(cloneLayout(layout), side, placed, node, startY));
    });
    return composeLayout(node, placed);
  }

  function layoutTree(node, sidePreference = 0) {
    const children = node.children || [];
    if (children.length === 0) return layoutLeaf(node);
    if (children.length === 1) {
      const child = layoutTree(children[0], sidePreference || 1);
      return layoutUnary(node, child, sidePreference || 1);
    }
    if (children.length === 2) {
      const left = layoutTree(children[0], -1);
      const right = layoutTree(children[1], 1);
      return layoutBinary(node, left, right);
    }
    return layoutNAry(node, children.map((child, i) => layoutTree(child, i % 2 ? 1 : -1)));
  }

  function normalizeLayout(layout) {
    const dx = -Math.floor((layout.box.minX + layout.box.maxX) / 2);
    return shiftLayout(layout, dx, 0);
  }

  function getSyntaxLayout() {
    return normalizeLayout(layoutTree(cloneTree(treeSpec()), 0));
  }

  function px(x, origin) { return origin.x + x * CELL; }
  function py(y, origin) { return origin.y + y * CELL; }

  function drawGrid(g, width = 1600, height = 1000) {
    const grid = svgEl('g', { class: 'grid' });
    for (let x = -400; x <= width; x += CELL / 2) {
      grid.appendChild(svgEl('line', { x1: x, y1: -200, x2: x, y2: height, class: 'grid-line' }));
    }
    for (let y = -160; y <= height; y += CELL / 2) {
      grid.appendChild(svgEl('line', { x1: -400, y1: y, x2: width, y2: y, class: 'grid-line' }));
    }
    grid.appendChild(svgEl('line', { x1: -400, y1: 0, x2: width, y2: 0, class: 'grid-axis' }));
    grid.appendChild(svgEl('line', { x1: 0, y1: -200, x2: 0, y2: height, class: 'grid-axis' }));
    g.appendChild(grid);
  }

  function drawSubtreeBoxes(g, layout, origin) {
    const ordered = [...layout.boxes].sort((a, b) => ((b.maxX - b.minX) * (b.maxY - b.minY)) - ((a.maxX - a.minX) * (a.maxY - a.minY)));
    for (const box of ordered) {
      if (box.leaf) continue;
      const x = px(box.minX - 0.75, origin);
      const y = py(box.minY - 0.55, origin);
      const w = (box.maxX - box.minX + 1.5) * CELL;
      const h = (box.maxY - box.minY + 1.1) * CELL;
      g.appendChild(svgEl('rect', { x, y, width: w, height: h, rx: 18, class: 'jan-subtree-box' }));
      g.appendChild(svgEl('text', { x: x + 14, y: y + 24, class: 'jan-box-caption' }, `BOX ${box.label.replace(/^BOX\s+/i, '')}`));
    }
  }

  function drawTreeEdges(g, layout, origin) {
    if (!state.showRelations) return;
    for (const edge of layout.edges) {
      g.appendChild(svgEl('line', {
        x1: px(edge.fromX, origin), y1: py(edge.fromY, origin) + 18,
        x2: px(edge.toX, origin), y2: py(edge.toY, origin) - 18,
        class: 'tree-edge syntax-tree-edge'
      }));
    }
  }

  function drawTreeNodes(g, layout, origin, selectable = true) {
    for (const node of layout.nodes) {
      const cx = px(node.x, origin);
      const cy = py(node.y, origin);
      const group = svgEl('g', { class: `tree-node ${node.kind === 'leaf' ? 'leaf-node' : 'cat-node'} ${state.selectedNodeId === node.id ? 'selected' : ''}`, 'data-node-id': node.id });
      if (node.kind === 'leaf') {
        group.appendChild(svgEl('circle', { cx, cy, r: 27, class: 'node-circle' }));
        group.appendChild(svgEl('text', { x: cx, y: cy - 2, class: 'node-main-label' }, node.label));
        group.appendChild(svgEl('text', { x: cx, y: cy + 18, class: 'node-sub-label' }, node.cat));
      } else {
        group.appendChild(svgEl('rect', { x: cx - 46, y: cy - 23, width: 92, height: 46, rx: 13, class: 'synt-box category-box' }));
        group.appendChild(svgEl('text', { x: cx, y: cy + 5, class: 'box-label' }, node.label));
      }
      if (selectable) group.addEventListener('click', () => selectNode(node.id));
      g.appendChild(group);
    }
  }

  function drawSyntaxTree(g, origin, options = {}) {
    const layout = getSyntaxLayout();
    drawSubtreeBoxes(g, layout, origin);
    drawTreeEdges(g, layout, origin);
    drawTreeNodes(g, layout, origin, options.selectable !== false);
    return layout;
  }

  function layoutNodeMap(layout, origin) {
    const map = new Map();
    for (const node of layout.nodes) map.set(node.id, { ...node, px: px(node.x, origin), py: py(node.y, origin) });
    return map;
  }

  function drawAxisTitle(g, x, y, text) {
    g.appendChild(svgEl('text', { x, y, class: 'axis-title' }, text));
  }

  function drawLexAxis(g, x, y0, items, sourceMap = null) {
    drawAxisTitle(g, x - 80, y0 - 70, 'LEX-as · lokale uitingtype-regel');
    g.appendChild(svgEl('line', { x1: x, y1: y0 - 48, x2: x, y2: y0 + Math.max(3, items.length) * 64 + 40, class: 'lex-axis-line' }));

    const positions = new Map();
    items.forEach((item, i) => {
      const y = y0 + i * 64;
      positions.set(item.id, { x, y, item });
      if (!item.source && item.slot === 'comp') {
        g.appendChild(svgEl('rect', { x: x - 86, y: y - 28, width: 172, height: 56, rx: 16, class: 'lex-free-slot comp-slot' }));
        g.appendChild(svgEl('text', { x, y: y - 34, class: 'slot-caption' }, 'vrij LEX-slot · Comp/(om)dat'));
      } else if (!item.source) {
        g.appendChild(svgEl('rect', { x: x - 66, y: y - 26, width: 132, height: 52, rx: 14, class: 'lex-local-slot' }));
      } else {
        g.appendChild(svgEl('rect', { x: x - 62, y: y - 28, width: 124, height: 56, rx: 14, class: 'lex-slot-box' }));
      }
      g.appendChild(svgEl('text', { x: x - 92, y: y + 5, class: 'lex-index' }, String(i + 1)));
      g.appendChild(svgEl('text', { x, y: y + 5, class: item.source ? 'lex-label' : 'lex-local-label' }, item.label));
    });

    if (sourceMap) {
      for (const item of items) {
        if (!item.source) continue;
        const p = sourceMap.get(item.source);
        const lp = positions.get(item.id);
        if (!p || !lp) continue;
        g.appendChild(pathEl(`M ${p.px} ${p.py} L ${x} ${p.py} L ${x} ${lp.y}`, { class: 'projection-line lex orthogonal' }));
      }
    }
    return positions;
  }

  function drawSyntaxRules(g, x, y) {
    drawAxisTitle(g, x, y - 60, 'SYNTAX-projectie · regels');
    const rules = ['S → NP VP', 'VP → NP V', 'V → BIJT'];
    rules.forEach((rule, i) => {
      const yy = y + i * 66;
      g.appendChild(svgEl('rect', { x: x - 62, y: yy - 26, width: 170, height: 52, rx: 14, class: 'syntax-rule-box' }));
      g.appendChild(svgEl('text', { x: x - 42, y: yy + 5, class: 'rule-label' }, rule));
    });
  }

  function drawFunctional(g, origin) {
    drawAxisTitle(g, origin.x - 120, origin.y - 70, 'OPN · functionele structuur');
    const points = {
      pred: { x: origin.x, y: origin.y, label: 'BIJT', sub: 'predicaat' },
      agens: { x: origin.x - 230, y: origin.y + 100, label: 'HOND', sub: 'agens' },
      patiens: { x: origin.x + 230, y: origin.y + 100, label: 'MAN', sub: 'patiens' }
    };
    g.appendChild(svgEl('line', { x1: points.pred.x, y1: points.pred.y + 24, x2: points.agens.x, y2: points.agens.y - 24, class: 'relation-edge' }));
    g.appendChild(svgEl('line', { x1: points.pred.x, y1: points.pred.y + 24, x2: points.patiens.x, y2: points.patiens.y - 24, class: 'relation-edge' }));
    for (const p of Object.values(points)) {
      g.appendChild(svgEl('circle', { cx: p.x, cy: p.y, r: 30, class: 'functional-node' }));
      g.appendChild(svgEl('text', { x: p.x, y: p.y - 2, class: 'node-main-label' }, p.label));
      g.appendChild(svgEl('text', { x: p.x, y: p.y + 18, class: 'node-sub-label' }, p.sub));
    }
  }

  function drawAxes() {
    const g = baseSvg('axes-view');
    const origin = { x: 760, y: 115 };
    drawAxisTitle(g, origin.x - 170, origin.y - 76, 'CENTRAAL · OPN-syntaxboom · vrije HOR/VER-boxlayout');

    let sourceMap = null;
    if (state.centerMode === 'functional') {
      drawFunctional(g, origin);
    } else {
      const layout = drawSyntaxTree(g, origin);
      sourceMap = layoutNodeMap(layout, origin);
    }

    drawLexAxis(g, 210, 185, state.example.lexItems, sourceMap);
    drawSyntaxRules(g, 1240, 180);
    els.svg.appendChild(g);
  }

  function drawSource() {
    const g = baseSvg('source-view');
    if (state.centerMode === 'functional') {
      drawFunctional(g, { x: 760, y: 170 });
      drawAxisTitle(g, 570, 70, 'BRON · OPN-functionele structuur');
    } else {
      drawAxisTitle(g, 490, 58, 'BRON · OPN-syntax-tree · vrije HOR/VER-boxplaatsing');
      drawSyntaxTree(g, { x: 780, y: 125 });
    }
    els.svg.appendChild(g);
  }

  function drawLex() {
    const g = baseSvg('lex-view');
    drawLexAxis(g, 560, 130, state.example.lexItems, null);
    g.appendChild(svgEl('text', { x: 700, y: 70, class: 'axis-title' }, state.example.lexRule === 'bijzin-omdat' ? 'Regel: bijzin met lokaal Comp-slot' : 'Regel: hoofdzin zonder verplaatsing'));
    els.svg.appendChild(g);
  }

  function drawSynt() {
    const g = baseSvg('synt-view');
    drawSyntaxRules(g, 540, 130);
    g.appendChild(svgEl('text', { x: 540, y: 370, class: 'rule-label' }, 'Alleen regels. Geen rollenboom en geen LEX-verplaatsing op de syntax-as.'));
    els.svg.appendChild(g);
  }

  function drawLog() {
    const g = baseSvg('log-view');
    drawFunctional(g, { x: 650, y: 165 });
    els.svg.appendChild(g);
  }

  function baseSvg(className) {
    els.svg.replaceChildren();
    els.svg.setAttribute('viewBox', '0 0 1500 900');
    els.svg.classList.toggle('no-grid', !state.showGrid);
    const g = svgEl('g', { class: className });
    if (state.showGrid) drawGrid(g, 1500, 900);
    return g;
  }

  function render() {
    syncControls();
    if (state.projection === 'source') drawSource();
    else if (state.projection === 'lex') drawLex();
    else if (state.projection === 'synt') drawSynt();
    else if (state.projection === 'log') drawLog();
    else drawAxes();
    renderSideLists();
    renderStatus();
    renderSelection();
  }

  function renderStatus() {
    els.titleLine.textContent = `${state.example.title} · ${state.projectionLabel || projectionLabel()} · ${state.centerMode === 'syntax' ? 'OPN-syntaxboom' : 'OPN-functioneel'}`;
    els.metaLine.textContent = `${state.example.phase} · centrale boom invariant · LEX=${state.example.lexItems.map(i => i.label).join(' ')}`;
    els.actionFeedback.textContent = state.projection === 'source'
      ? 'Bron toont nu de gekozen OPN-bron: bij OPN-syntax is dat de syntax-tree zelf, niet de functionele rollenboom.'
      : 'Faseversie: eerst boom, dan LEX-projectie, daarna lokale LEX-regel.';
    els.projectionHelp.textContent = helpText();
    els.explainHeading.textContent = `Uitleg · ${state.example.title}`;
    els.explainText.textContent = state.example.id === 'hond-bijt-man'
      ? 'Eerst wordt alleen de centrale syntax-tree opgebouwd. Daarna projecteert LEX de drie eindknopen HOND, BIJT en MAN. Er is nog geen lokale bijzinregel.'
      : 'De centrale syntax-tree blijft HOND-BIJT-MAN. De bijzin wordt uitsluitend lokaal op de LEX-as gevormd: OMDAT en de determinatoren zijn LEX-lokaal.';
  }

  function projectionLabel() {
    return ({ axes: 'OPN/assen', source: 'Bron', lex: 'LEX', synt: 'SYNTAX-projectie', log: 'LOG/FT' })[state.projection] || state.projection;
  }

  function helpText() {
    if (state.projection === 'source') return 'Bron: OPN-syntax toont de syntax-tree zelf. OPN-functioneel toont de rollenstructuur apart.';
    if (state.projection === 'lex') return 'LEX: lokale uitingtype-regel. OMDAT/DE zijn lokaal en wijzigen de boom niet.';
    if (state.projection === 'synt') return 'SYNTAX-projectie: alleen S → NP VP, VP → NP V, V → BIJT.';
    if (state.projection === 'log') return 'LOG/FT: functionele rollen als aparte projectie.';
    return 'Assen: centrale syntax-tree, LEX-as links, SYNTAX-regels rechts.';
  }

  function renderSideLists() {
    els.lexOrderList.replaceChildren();
    state.example.lexItems.forEach((item, i) => {
      const row = document.createElement('div');
      row.className = `lex-order-item ${item.source ? '' : 'local'}`;
      row.textContent = `${i + 1}. ${item.label}${item.source ? '' : ' · lokaal'}`;
      els.lexOrderList.appendChild(row);
    });
    fillEdgeList();
  }

  function fillEdgeList() {
    if (!els.edgeList) return;
    els.edgeList.replaceChildren();
    const rows = ['S → NP VP', 'VP → NP V', 'V → BIJT'];
    for (const row of rows) {
      const div = document.createElement('div');
      div.className = 'edge-item';
      div.textContent = row;
      els.edgeList.appendChild(div);
    }
  }

  function fillSelect(select, options, selected) {
    if (!select) return;
    select.replaceChildren();
    for (const opt of options) {
      const el = document.createElement('option');
      el.value = opt.id;
      el.textContent = opt.label || opt.title || opt.id;
      if (opt.id === selected) el.selected = true;
      select.appendChild(el);
    }
  }

  function syncControls() {
    fillSelect(els.exampleSelect, EXAMPLES, state.example.id);
    fillSelect(els.centralModeSelect, CENTER_MODES, state.centerMode);
    fillSelect(els.lexRuleSelect, LEX_RULES, state.example.lexRule);
    if (els.showGridInput) els.showGridInput.checked = state.showGrid;
    if (els.showRelationsInput) els.showRelationsInput.checked = state.showRelations;
    if (els.showLabelsInput) els.showLabelsInput.checked = state.showLabels;
    document.querySelectorAll('.projection-tab').forEach(tab => {
      const active = tab.dataset.projection === state.projection;
      tab.classList.toggle('active', active);
      tab.setAttribute('aria-selected', String(active));
    });
  }

  function selectNode(id) {
    state.selectedNodeId = id;
    renderSelection();
    render();
  }

  function renderSelection() {
    const layout = getSyntaxLayout();
    const node = layout.nodes.find(n => n.id === state.selectedNodeId);
    if (!node) {
      els.selectionEmpty?.classList.remove('hidden');
      els.nodeEditor?.classList.add('hidden');
      return;
    }
    els.selectionEmpty?.classList.add('hidden');
    els.nodeEditor?.classList.remove('hidden');
    if (els.nodeIdField) els.nodeIdField.value = node.id;
    if (els.nodeLabelInput) els.nodeLabelInput.value = node.label;
    fillSelect(els.nodeCatInput, [{ id: node.cat, label: node.cat }], node.cat);
    fillSelect(els.nodeRoleInput, [{ id: 'syntax', label: 'syntax' }], 'syntax');
    if (els.nodeXInput) els.nodeXInput.value = node.x;
    if (els.nodeYInput) els.nodeYInput.value = node.y;
  }

  function download(filename, text, type = 'application/json') {
    const blob = new Blob([text], { type });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    a.remove();
    URL.revokeObjectURL(url);
  }

  function downloadJson() {
    const payload = {
      version: VERSION,
      example: state.example.id,
      central_opn: state.centerMode,
      invariant_tree: ['S -> NP VP', 'VP -> NP V', 'V -> BIJT'],
      lex: state.example.lexItems
    };
    download(`${state.example.id}.${VERSION}.json`, JSON.stringify(payload, null, 2));
  }

  function downloadOpn() {
    const lines = [
      `opn_version: ${VERSION}`,
      `example: ${state.example.title}`,
      'tree:',
      '  S -> NP VP',
      '  VP -> NP V',
      '  V -> BIJT',
      `lex: ${state.example.lexItems.map(i => i.label).join(' ')}`,
      `lex_rule: ${state.example.lexRule}`
    ];
    download(`${state.example.id}.${VERSION}.opn`, lines.join('\n'), 'text/plain');
  }

  function registerEvents() {
    document.querySelectorAll('.projection-tab').forEach(tab => {
      tab.addEventListener('click', () => {
        state.projection = tab.dataset.projection || 'axes';
        render();
      });
    });
    els.exampleSelect?.addEventListener('change', event => {
      state.example = EXAMPLES.find(e => e.id === event.target.value) || EXAMPLES[0];
      state.selectedNodeId = null;
      render();
    });
    els.centralModeSelect?.addEventListener('change', event => {
      state.centerMode = event.target.value;
      render();
    });
    els.lexRuleSelect?.addEventListener('change', event => {
      const targetExample = event.target.value === 'bijzin-omdat' ? EXAMPLES[1] : EXAMPLES[0];
      state.example = targetExample;
      render();
    });
    els.showGridInput?.addEventListener('change', event => { state.showGrid = event.target.checked; render(); });
    els.showRelationsInput?.addEventListener('change', event => { state.showRelations = event.target.checked; render(); });
    els.showLabelsInput?.addEventListener('change', event => { state.showLabels = event.target.checked; render(); });
    els.resetExampleButton?.addEventListener('click', () => { state.selectedNodeId = null; render(); });
    els.fitButton?.addEventListener('click', () => { els.svg.setAttribute('viewBox', '0 0 1500 900'); });
    els.downloadJsonButton?.addEventListener('click', downloadJson);
    els.downloadOpnButton?.addEventListener('click', downloadOpn);
    els.applyLexRuleButton?.addEventListener('click', () => {
      state.example = state.example.lexRule === 'bijzin-omdat' ? EXAMPLES[1] : EXAMPLES[0];
      render();
    });
    for (const button of [els.undoButton, els.redoButton, els.addNodeButton, els.duplicateNodeButton, els.deleteNodeButton, els.applyNodeButton, els.addEdgeButton, els.lexLeftButton, els.lexRightButton]) {
      button?.addEventListener('click', () => {
        if (els.actionFeedback) els.actionFeedback.textContent = 'Deze redesign-fase is bewust beperkt: eerst layout corrigeren, daarna editing weer uitbreiden.';
      });
    }
    window.addEventListener('keydown', event => {
      if (event.key === '1') state.projection = 'axes';
      else if (event.key === '2') state.projection = 'source';
      else if (event.key === '3') state.projection = 'lex';
      else if (event.key.toLowerCase() === 'f') els.svg.setAttribute('viewBox', '0 0 1500 900');
      else return;
      render();
    });
  }

  function init() {
    registerEvents();
    render();
    if ('serviceWorker' in navigator) {
      navigator.serviceWorker.register('./sw.js').catch(() => {});
    }
  }

  init();
})();

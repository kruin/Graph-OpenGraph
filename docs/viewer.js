(() => {
  'use strict';

  const NS = 'http://www.w3.org/2000/svg';
  const CELL = 72;
  const PAD = 92;

  function gp(x, y, extra = {}) {
    return { x: x * CELL, y: y * CELL, ...extra };
  }

  const CATS = ['CP', 'TP', 'VP', 'DP', 'NP', 'PP', 'AP', 'N', 'V', 'AUX', 'Vpart', 'P', 'Det', 'Comp', 'LEX', 'LOG', 'X'];
  const ROLES = ['agent', 'patient', 'pred', 'aux', 'theme', 'topic', 'focus', 'subject', 'object', 'modifier', 'prep', 'by', 'tense', 'neg', 'det', 'subject-det', 'object-det', 'comp', 'wh', 'other'];
  const EDGE_TYPES = ['agent', 'patient', 'theme', 'aux', 'pred', 'modifier', 'prep', 'by', 'det', 'topic', 'focus', 'synt', 'other'];

  const CENTER_MODES = [
    { id: 'opn-syntax', label: 'OPN · syntaxboom', help: 'Centraal staat de OPN-syntaxboom. Dit is niet de losse SYNTAX-projectie rechts, maar de centrale OPN-presentatie.' },
    { id: 'opn-functional', label: 'OPN · functionele structuur', help: 'Centraal staat de OPN-configuratie: subject/object/verb/aux/comp als relationele structuur.' },
    { id: 'source', label: 'Vrije bronknopen', help: 'Centraal staan de vrije bronknopen; in deze modus zijn ze in Assen sleepbaar.' }
  ];

  function normalizeCenterMode(value) {
    if (value === 'synt' || value === 'syntax' || value === 'opn') return 'opn-syntax';
    if (value === 'functional' || value === 'functioneel') return 'opn-functional';
    if (value === 'source' || value === 'bron') return 'source';
    return 'opn-syntax';
  }

  const LEX_RULES = [
    { id: 'manual', label: 'Handmatig: behoud huidige LEX-as', order: null, help: 'De opgegeven LEX-volgorde blijft staan.' },
    { id: 'svo', label: 'SVO: agent – pred – patient', order: ['agent', 'subject', 'topic', 'pred', 'patient', 'object', 'theme', 'modifier', 'other'], help: 'Plaats de agent eerst, daarna het predicaat, daarna patient/theme.' },
    { id: 'sov', label: 'SOV: agent – patient – pred', order: ['agent', 'subject', 'topic', 'patient', 'object', 'theme', 'modifier', 'pred', 'other'], help: 'Laat zien dat de LEX-as kan verschillen van rollen en logische structuur.' },
    { id: 'nl-perfectum', label: 'Ned. perfectum: agent – AUX – patient – Vpart', order: ['agent', 'subject', 'topic', 'aux', 'tense', 'patient', 'object', 'theme', 'modifier', 'pred', 'other'], help: 'Geschikt voor VROUW HEEFT TRUI GEBREID.' },
    { id: 'patient-topic', label: 'Topicalisatie: patient – AUX – agent – pred', order: ['patient', 'object', 'theme', 'topic', 'aux', 'tense', 'agent', 'subject', 'pred', 'modifier', 'other'], help: 'Zet patient/theme vooraan zonder de bronrol te veranderen.' },
    { id: 'passive', label: 'Passief: patient – AUX – door – agent – pred', order: ['patient', 'object', 'theme', 'aux', 'tense', 'prep', 'by', 'agent', 'subject', 'pred', 'modifier', 'other'], help: 'Geschikt voor MAN WORDT DOOR HOND GEBETEN.' },
    { id: 'wh-perfectum', label: 'WH-perfectum: wie – AUX – Det – object – Vpart', order: ['wh', 'subject', 'agent', 'aux', 'tense', 'det', 'subject-det', 'object-det', 'object', 'patient', 'theme', 'pred', 'modifier', 'other'], help: 'Geschikt voor WIE HEEFT DE HOND GEBETEN.' },
    { id: 'omdat-vfinal', label: 'Bijzin: omdat – de hond – de man – gebeten – heeft', order: ['comp', 'subject-det', 'agent', 'object-det', 'patient', 'object', 'theme', 'pred', 'aux', 'tense', 'modifier', 'other'], help: 'Laat de Nederlandse bijzin met participium + hulpwerkwoord aan het einde zien.' },
    { id: 'omdat-vcluster', label: 'Bijzin: omdat – de hond – de man – heeft – gebeten', order: ['comp', 'subject-det', 'agent', 'object-det', 'patient', 'object', 'theme', 'aux', 'tense', 'pred', 'modifier', 'other'], help: 'Laat de verplaatsingsvariant zien met hulpwerkwoord vóór participium op de LEX-as.' }
  ];

  const EXAMPLES = [
    {
      id: 'hond-bijt-man',
      title: 'HOND BIJT MAN',
      sentence: 'HOND BIJT MAN',
      description: 'Eenvoudige transitieve zin: hond = agent, man = patient, bijt = predicaat.',
      lexRule: 'svo',
      nodes: [
        { id: 'hond', label: 'HOND', cat: 'DP', role: 'agent', x: -2, y: 0 },
        { id: 'bijt', label: 'BIJT', cat: 'V', role: 'pred', x: 0, y: -1 },
        { id: 'man', label: 'MAN', cat: 'DP', role: 'patient', x: 2, y: 1 }
      ],
      edges: [
        { id: 'e1', from: 'bijt', to: 'hond', type: 'agent' },
        { id: 'e2', from: 'bijt', to: 'man', type: 'patient' }
      ],
      lex: { order: ['hond', 'bijt', 'man'], rule: 'svo' }
    },
    {
      id: 'man-bijt-hond',
      title: 'MAN BIJT HOND',
      sentence: 'MAN BIJT HOND',
      description: 'Zelfde patroon, omgekeerde rollen: man = agent, hond = patient.',
      lexRule: 'svo',
      nodes: [
        { id: 'man', label: 'MAN', cat: 'DP', role: 'agent', x: -2, y: 0 },
        { id: 'bijt', label: 'BIJT', cat: 'V', role: 'pred', x: 0, y: -1 },
        { id: 'hond', label: 'HOND', cat: 'DP', role: 'patient', x: 2, y: 1 }
      ],
      edges: [
        { id: 'e1', from: 'bijt', to: 'man', type: 'agent' },
        { id: 'e2', from: 'bijt', to: 'hond', type: 'patient' }
      ],
      lex: { order: ['man', 'bijt', 'hond'], rule: 'svo' }
    },
    {
      id: 'hond-man-bijt',
      title: 'HOND MAN BIJT',
      sentence: 'HOND MAN BIJT',
      description: 'Variant die laat zien dat LEX-plaatsing los kan worden bekeken van rollen.',
      lexRule: 'sov',
      nodes: [
        { id: 'hond', label: 'HOND', cat: 'DP', role: 'agent', x: -2, y: 0 },
        { id: 'bijt', label: 'BIJT', cat: 'V', role: 'pred', x: 1, y: -1 },
        { id: 'man', label: 'MAN', cat: 'DP', role: 'patient', x: 0, y: 1 }
      ],
      edges: [
        { id: 'e1', from: 'bijt', to: 'hond', type: 'agent' },
        { id: 'e2', from: 'bijt', to: 'man', type: 'patient' }
      ],
      lex: { order: ['hond', 'man', 'bijt'], rule: 'sov' }
    },

    {
      id: 'omdat-de-hond-de-man-gebeten-heeft',
      title: 'OMDAT DE HOND DE MAN GEBETEN HEEFT',
      sentence: 'OMDAT DE HOND DE MAN GEBETEN HEEFT',
      description: 'Bijzin op de LEX-as met verplaatsingsregel: complementizer vooraan, werkwoordcluster achteraan.',
      lexRule: 'omdat-vfinal',
      nodes: [
        { id: 'omdat', label: 'OMDAT', cat: 'Comp', role: 'comp', x: -3, y: -2 },
        { id: 'de-hond', label: 'DE', cat: 'Det', role: 'subject-det', x: -2, y: -1 },
        { id: 'hond', label: 'HOND', cat: 'N', role: 'agent', x: -1, y: 0 },
        { id: 'de-man', label: 'DE', cat: 'Det', role: 'object-det', x: 0, y: 1 },
        { id: 'man', label: 'MAN', cat: 'N', role: 'patient', x: 1, y: 1 },
        { id: 'gebeten', label: 'GEBETEN', cat: 'Vpart', role: 'pred', x: 2, y: -1 },
        { id: 'heeft', label: 'HEEFT', cat: 'AUX', role: 'aux', x: 3, y: -2 }
      ],
      edges: [
        { id: 'e1', from: 'gebeten', to: 'hond', type: 'agent' },
        { id: 'e2', from: 'gebeten', to: 'man', type: 'patient' },
        { id: 'e3', from: 'gebeten', to: 'heeft', type: 'aux' },
        { id: 'e4', from: 'hond', to: 'de-hond', type: 'det' },
        { id: 'e5', from: 'man', to: 'de-man', type: 'det' }
      ],
      lex: { order: ['omdat', 'de-hond', 'hond', 'de-man', 'man', 'gebeten', 'heeft'], rule: 'omdat-vfinal' },
      opn_config: { center: 'opn-syntax' }
    },
    {
      id: 'omdat-de-hond-de-man-heeft-gebeten',
      title: 'OMDAT DE HOND DE MAN HEEFT GEBETEN',
      sentence: 'OMDAT DE HOND DE MAN HEEFT GEBETEN',
      description: 'Tweede bijzinvariant op de LEX-as: dezelfde bron, maar met hulpwerkwoord vóór participium.',
      lexRule: 'omdat-vcluster',
      nodes: [
        { id: 'omdat', label: 'OMDAT', cat: 'Comp', role: 'comp', x: -3, y: -2 },
        { id: 'de-hond', label: 'DE', cat: 'Det', role: 'subject-det', x: -2, y: -1 },
        { id: 'hond', label: 'HOND', cat: 'N', role: 'agent', x: -1, y: 0 },
        { id: 'de-man', label: 'DE', cat: 'Det', role: 'object-det', x: 0, y: 1 },
        { id: 'man', label: 'MAN', cat: 'N', role: 'patient', x: 1, y: 1 },
        { id: 'heeft', label: 'HEEFT', cat: 'AUX', role: 'aux', x: 2, y: -2 },
        { id: 'gebeten', label: 'GEBETEN', cat: 'Vpart', role: 'pred', x: 3, y: -1 }
      ],
      edges: [
        { id: 'e1', from: 'gebeten', to: 'hond', type: 'agent' },
        { id: 'e2', from: 'gebeten', to: 'man', type: 'patient' },
        { id: 'e3', from: 'gebeten', to: 'heeft', type: 'aux' },
        { id: 'e4', from: 'hond', to: 'de-hond', type: 'det' },
        { id: 'e5', from: 'man', to: 'de-man', type: 'det' }
      ],
      lex: { order: ['omdat', 'de-hond', 'hond', 'de-man', 'man', 'heeft', 'gebeten'], rule: 'omdat-vcluster' },
      opn_config: { center: 'opn-functional' }
    },

    {
      id: 'wie-heeft-de-hond-gebeten',
      title: 'WIE HEEFT DE HOND GEBETEN',
      sentence: 'WIE HEEFT DE HOND GEBETEN',
      description: 'Vraagzin met LEX links, SYNTAX rechts en LOGICAL onder: wie = subject, hond = object, gebeten = predicaat.',
      lexRule: 'wh-perfectum',
      nodes: [
        { id: 'wie', label: 'WIE', cat: 'DP', role: 'subject', x: -1, y: -1 },
        { id: 'heeft', label: 'HEEFT', cat: 'AUX', role: 'aux', x: 0, y: -2 },
        { id: 'de', label: 'DE', cat: 'Det', role: 'det', x: -2, y: 1 },
        { id: 'hond', label: 'HOND', cat: 'N', role: 'object', x: -1, y: 1 },
        { id: 'gebeten', label: 'GEBETEN', cat: 'Vpart', role: 'pred', x: 1, y: 0 }
      ],
      edges: [
        { id: 'e1', from: 'gebeten', to: 'wie', type: 'agent' },
        { id: 'e2', from: 'gebeten', to: 'hond', type: 'patient' },
        { id: 'e3', from: 'gebeten', to: 'heeft', type: 'aux' },
        { id: 'e4', from: 'hond', to: 'de', type: 'det' }
      ],
      lex: { order: ['wie', 'heeft', 'de', 'hond', 'gebeten'], rule: 'wh-perfectum' }
    },
    {
      id: 'vrouw-heeft-trui-gebreid',
      title: 'VROUW HEEFT TRUI GEBREID',
      sentence: 'VROUW HEEFT TRUI GEBREID',
      description: 'Perfectum: hulpwerkwoord op de LEX-as, predicaat als participium.',
      lexRule: 'nl-perfectum',
      nodes: [
        { id: 'vrouw', label: 'VROUW', cat: 'DP', role: 'agent', x: -2, y: 0 },
        { id: 'heeft', label: 'HEEFT', cat: 'AUX', role: 'aux', x: -1, y: -2 },
        { id: 'trui', label: 'TRUI', cat: 'DP', role: 'patient', x: 1, y: 1 },
        { id: 'gebreid', label: 'GEBREID', cat: 'Vpart', role: 'pred', x: 2, y: -1 }
      ],
      edges: [
        { id: 'e1', from: 'gebreid', to: 'vrouw', type: 'agent' },
        { id: 'e2', from: 'gebreid', to: 'trui', type: 'patient' },
        { id: 'e3', from: 'gebreid', to: 'heeft', type: 'aux' }
      ],
      lex: { order: ['vrouw', 'heeft', 'trui', 'gebreid'], rule: 'nl-perfectum' }
    },
    {
      id: 'trui-heeft-vrouw-gebreid',
      title: 'TRUI HEEFT VROUW GEBREID',
      sentence: 'TRUI HEEFT VROUW GEBREID',
      description: 'Topicalisatievariant: patient staat vooraan, maar de FT-rol blijft patient.',
      lexRule: 'patient-topic',
      nodes: [
        { id: 'vrouw', label: 'VROUW', cat: 'DP', role: 'agent', x: 1, y: 0 },
        { id: 'heeft', label: 'HEEFT', cat: 'AUX', role: 'aux', x: -1, y: -2 },
        { id: 'trui', label: 'TRUI', cat: 'DP', role: 'patient', x: -2, y: 1 },
        { id: 'gebreid', label: 'GEBREID', cat: 'Vpart', role: 'pred', x: 2, y: -1 }
      ],
      edges: [
        { id: 'e1', from: 'gebreid', to: 'vrouw', type: 'agent' },
        { id: 'e2', from: 'gebreid', to: 'trui', type: 'patient' },
        { id: 'e3', from: 'gebreid', to: 'heeft', type: 'aux' }
      ],
      lex: { order: ['trui', 'heeft', 'vrouw', 'gebreid'], rule: 'patient-topic' }
    },
    {
      id: 'man-wordt-door-hond-gebeten',
      title: 'MAN WORDT DOOR HOND GEBETEN',
      sentence: 'MAN WORDT DOOR HOND GEBETEN',
      description: 'Passiefvariant: patient links op LEX; agent blijft via door-PP beschikbaar.',
      lexRule: 'passive',
      nodes: [
        { id: 'man', label: 'MAN', cat: 'DP', role: 'patient', x: -2, y: 0 },
        { id: 'wordt', label: 'WORDT', cat: 'AUX', role: 'aux', x: -1, y: -2 },
        { id: 'door', label: 'DOOR', cat: 'P', role: 'prep', x: 0, y: 1 },
        { id: 'hond', label: 'HOND', cat: 'DP', role: 'agent', x: 1, y: 2 },
        { id: 'gebeten', label: 'GEBETEN', cat: 'Vpart', role: 'pred', x: 2, y: -1 }
      ],
      edges: [
        { id: 'e1', from: 'gebeten', to: 'man', type: 'patient' },
        { id: 'e2', from: 'gebeten', to: 'hond', type: 'agent' },
        { id: 'e3', from: 'gebeten', to: 'wordt', type: 'aux' },
        { id: 'e4', from: 'door', to: 'hond', type: 'by' },
        { id: 'e5', from: 'gebeten', to: 'door', type: 'prep' }
      ],
      lex: { order: ['man', 'wordt', 'door', 'hond', 'gebeten'], rule: 'passive' }
    }
  ];

  const els = {
    installButton: document.getElementById('installButton'),
    exampleSelect: document.getElementById('exampleSelect'),
    undoButton: document.getElementById('undoButton'),
    redoButton: document.getElementById('redoButton'),
    resetExampleButton: document.getElementById('resetExampleButton'),
    fitButton: document.getElementById('fitButton'),
    fileInput: document.getElementById('fileInput'),
    downloadJsonButton: document.getElementById('downloadJsonButton'),
    downloadOpnButton: document.getElementById('downloadOpnButton'),
    titleLine: document.getElementById('titleLine'),
    metaLine: document.getElementById('metaLine'),
    actionFeedback: document.getElementById('actionFeedback'),
    canvasWrap: document.getElementById('canvasWrap'),
    svg: document.getElementById('graphSvg'),
    projectionHelp: document.getElementById('projectionHelp'),
    centralModeSelect: document.getElementById('centralModeSelect'),
    showGridInput: document.getElementById('showGridInput'),
    showRelationsInput: document.getElementById('showRelationsInput'),
    showLabelsInput: document.getElementById('showLabelsInput'),
    snapInput: document.getElementById('snapInput'),
    selectionEmpty: document.getElementById('selectionEmpty'),
    nodeEditor: document.getElementById('nodeEditor'),
    nodeIdField: document.getElementById('nodeIdField'),
    nodeLabelInput: document.getElementById('nodeLabelInput'),
    nodeCatInput: document.getElementById('nodeCatInput'),
    nodeRoleInput: document.getElementById('nodeRoleInput'),
    nodeXInput: document.getElementById('nodeXInput'),
    nodeYInput: document.getElementById('nodeYInput'),
    applyNodeButton: document.getElementById('applyNodeButton'),
    deleteNodeButton: document.getElementById('deleteNodeButton'),
    addNodeButton: document.getElementById('addNodeButton'),
    duplicateNodeButton: document.getElementById('duplicateNodeButton'),
    lexRuleSelect: document.getElementById('lexRuleSelect'),
    lexOrderList: document.getElementById('lexOrderList'),
    lexLeftButton: document.getElementById('lexLeftButton'),
    lexRightButton: document.getElementById('lexRightButton'),
    applyLexRuleButton: document.getElementById('applyLexRuleButton'),
    edgeFromSelect: document.getElementById('edgeFromSelect'),
    edgeToSelect: document.getElementById('edgeToSelect'),
    edgeTypeSelect: document.getElementById('edgeTypeSelect'),
    addEdgeButton: document.getElementById('addEdgeButton'),
    edgeList: document.getElementById('edgeList'),
    explainHeading: document.getElementById('explainHeading'),
    explainText: document.getElementById('explainText')
  };

  const state = {
    graph: null,
    projection: 'axes',
    centralMode: 'opn-syntax',
    selectedNodeId: null,
    selectedLexId: null,
    undoStack: [],
    redoStack: [],
    notice: { level: 'neutral', text: 'Bronmodus: sleep knopen vrij over het raster. Selecteer een knoop om label, categorie en rol te wijzigen.' },
    view: {
      showGrid: true,
      showRelations: true,
      showLabels: true,
      snap: true
    },
    dragging: null,
    deferredInstallPrompt: null
  };

  function deepClone(value) {
    return JSON.parse(JSON.stringify(value));
  }

  function clamp(value, min, max) {
    return Math.max(min, Math.min(max, value));
  }

  function asNumber(value, fallback = 0) {
    const n = Number(value);
    return Number.isFinite(n) ? n : fallback;
  }

  function slug(text) {
    const base = String(text || 'node').toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, '').replace(/[^a-z0-9]+/g, '-').replace(/^-|-$/g, '');
    return base || 'node';
  }

  function uniqId(prefix, existingIds) {
    let i = 1;
    let id = `${prefix}${i}`;
    while (existingIds.has(id)) {
      i += 1;
      id = `${prefix}${i}`;
    }
    return id;
  }

  function normalizeNode(node, index) {
    const id = String(node.id || node.name || `n${index + 1}`);
    const model = node.model && typeof node.model === 'object' ? node.model : {};
    return {
      id,
      label: String(node.label || node.text || id).toUpperCase(),
      cat: String(node.cat || node.category || 'X'),
      role: String(node.role || 'other'),
      x: asNumber(node.x ?? model.x, index - 1),
      y: asNumber(node.y ?? model.y, index % 2 ? 1 : 0)
    };
  }

  function normalizeEdge(edge, index) {
    return {
      id: String(edge.id || `e${index + 1}`),
      from: String(edge.from || ''),
      to: String(edge.to || ''),
      type: String(edge.type || edge.role || 'other')
    };
  }

  function normalizeGraph(raw) {
    const source = raw && typeof raw === 'object' ? raw : EXAMPLES[0];
    const nodes = Array.isArray(source.nodes) ? source.nodes.map(normalizeNode) : [];
    const valid = new Set(nodes.map(n => n.id));
    const edges = Array.isArray(source.edges)
      ? source.edges.map(normalizeEdge).filter(e => valid.has(e.from) && valid.has(e.to) && e.from !== e.to)
      : [];
    const rawOrder = Array.isArray(source.lex?.order) ? source.lex.order.map(String) : nodes.map(n => n.id);
    const order = [...rawOrder.filter(id => valid.has(id)), ...nodes.map(n => n.id).filter(id => !rawOrder.includes(id))];
    const rule = String(source.lex?.rule || source.lexRule || 'manual');
    return {
      format: 'opengraph-lite',
      format_version: 1,
      id: String(source.id || slug(source.title || source.sentence || 'opengraph-lite')),
      title: String(source.title || source.sentence || 'OpenGraph Lite voorbeeld'),
      sentence: String(source.sentence || source.title || ''),
      description: String(source.description || ''),
      nodes,
      edges,
      lex: { order, rule },
      opn_config: { center: normalizeCenterMode(source.opn_config?.center || source.config?.center) },
      projection_meta: source.projection_meta || {
        source: 'vrije JAN-bronknopen / centrale OPN-configuratie',
        lex: 'lineaire LEX-plaatsing',
        synt: 'afgeleide boomprojectie',
        log: 'predicate-argument / FT-projectie'
      }
    };
  }

  function selectedNode() {
    return state.graph?.nodes.find(n => n.id === state.selectedNodeId) || null;
  }

  function nodeById(id) {
    return state.graph?.nodes.find(n => n.id === id) || null;
  }

  function edgeById(id) {
    return state.graph?.edges.find(e => e.id === id) || null;
  }

  function predicateNodes() {
    return state.graph.nodes.filter(n => ['pred', 'V', 'Vpart'].includes(n.role) || ['V', 'Vpart'].includes(n.cat));
  }

  function mainPredicate() {
    return predicateNodes()[0] || state.graph.nodes.find(n => n.role === 'pred') || state.graph.nodes[0] || null;
  }

  function nodesByRole(role) {
    return state.graph.nodes.filter(n => n.role === role || state.graph.edges.some(e => e.type === role && e.to === n.id));
  }

  function firstNodeByRole(role) {
    return nodesByRole(role)[0] || null;
  }

  function saveUndo(label = 'edit') {
    if (!state.graph) return;
    state.undoStack.push({ label, graph: deepClone(state.graph), selectedNodeId: state.selectedNodeId, selectedLexId: state.selectedLexId });
    if (state.undoStack.length > 80) state.undoStack.shift();
    state.redoStack = [];
    updateUndoRedoButtons();
  }

  function restoreSnapshot(snapshot) {
    state.graph = normalizeGraph(snapshot.graph);
    state.selectedNodeId = snapshot.selectedNodeId && nodeById(snapshot.selectedNodeId) ? snapshot.selectedNodeId : null;
    state.selectedLexId = snapshot.selectedLexId && nodeById(snapshot.selectedLexId) ? snapshot.selectedLexId : state.selectedNodeId;
    renderAll();
  }

  function undo() {
    if (!state.undoStack.length) return;
    state.redoStack.push({ label: 'redo', graph: deepClone(state.graph), selectedNodeId: state.selectedNodeId, selectedLexId: state.selectedLexId });
    const snapshot = state.undoStack.pop();
    setNotice('neutral', `Undo: ${snapshot.label}.`);
    restoreSnapshot(snapshot);
  }

  function redo() {
    if (!state.redoStack.length) return;
    state.undoStack.push({ label: 'undo', graph: deepClone(state.graph), selectedNodeId: state.selectedNodeId, selectedLexId: state.selectedLexId });
    const snapshot = state.redoStack.pop();
    setNotice('neutral', 'Redo toegepast.');
    restoreSnapshot(snapshot);
  }

  function updateUndoRedoButtons() {
    els.undoButton.disabled = !state.undoStack.length;
    els.redoButton.disabled = !state.redoStack.length;
  }

  function setNotice(level, text) {
    state.notice = { level, text };
    if (els.actionFeedback) {
      els.actionFeedback.textContent = text;
      els.actionFeedback.className = `action-feedback ${level || 'neutral'}`;
    }
  }

  function svgEl(name, attrs = {}, text = null) {
    const el = document.createElementNS(NS, name);
    for (const [key, value] of Object.entries(attrs)) {
      if (value === undefined || value === null) continue;
      el.setAttribute(key, String(value));
    }
    if (text !== null && text !== undefined) el.textContent = String(text);
    return el;
  }

  function pathEl(d, attrs = {}) {
    return svgEl('path', { d, ...attrs });
  }

  function buildDefs() {
    const defs = svgEl('defs');
    const marker = svgEl('marker', { id: 'arrow', viewBox: '0 0 10 10', refX: '9', refY: '5', markerWidth: '6', markerHeight: '6', orient: 'auto-start-reverse' });
    marker.appendChild(svgEl('path', { d: 'M 0 0 L 10 5 L 0 10 z', fill: 'currentColor' }));
    defs.appendChild(marker);
    return defs;
  }

  function sourcePoint(node) {
    return { x: node.x * CELL, y: node.y * CELL };
  }

  function computeBounds(points) {
    if (!points.length) return { minX: -300, minY: -220, maxX: 300, maxY: 220 };
    let minX = Infinity, minY = Infinity, maxX = -Infinity, maxY = -Infinity;
    for (const p of points) {
      minX = Math.min(minX, p.x);
      minY = Math.min(minY, p.y);
      maxX = Math.max(maxX, p.x);
      maxY = Math.max(maxY, p.y);
    }
    if (maxX - minX < 420) {
      const c = (minX + maxX) / 2;
      minX = c - 210;
      maxX = c + 210;
    }
    if (maxY - minY < 280) {
      const c = (minY + maxY) / 2;
      minY = c - 140;
      maxY = c + 140;
    }
    return { minX: minX - PAD, minY: minY - PAD, maxX: maxX + PAD, maxY: maxY + PAD };
  }

  function setViewBox(bounds) {
    const width = Math.max(200, bounds.maxX - bounds.minX);
    const height = Math.max(160, bounds.maxY - bounds.minY);
    els.svg.setAttribute('viewBox', `${bounds.minX} ${bounds.minY} ${width} ${height}`);
  }

  function drawGrid(bounds) {
    if (!state.view.showGrid || !['source', 'axes', 'synt'].includes(state.projection)) return null;
    const g = svgEl('g', { class: 'grid' });
    const startX = Math.floor(bounds.minX / CELL) * CELL;
    const endX = Math.ceil(bounds.maxX / CELL) * CELL;
    const startY = Math.floor(bounds.minY / CELL) * CELL;
    const endY = Math.ceil(bounds.maxY / CELL) * CELL;
    for (let x = startX; x <= endX; x += CELL) {
      const idx = Math.round(x / CELL);
      g.appendChild(svgEl('line', { x1: x, y1: startY, x2: x, y2: endY, class: `grid-line ${idx % 5 === 0 ? 'major' : ''}` }));
    }
    for (let y = startY; y <= endY; y += CELL) {
      const idx = Math.round(y / CELL);
      g.appendChild(svgEl('line', { x1: startX, y1: y, x2: endX, y2: y, class: `grid-line ${idx % 5 === 0 ? 'major' : ''}` }));
    }
    g.appendChild(svgEl('line', { x1: startX, y1: 0, x2: endX, y2: 0, class: 'axis-line' }));
    g.appendChild(svgEl('line', { x1: 0, y1: startY, x2: 0, y2: endY, class: 'axis-line' }));
    return g;
  }

  function relationLabel(edge) {
    return edge.type || 'rel';
  }

  function drawEdgeLabel(group, x, y, text) {
    const w = Math.max(36, String(text).length * 7.2 + 12);
    group.appendChild(svgEl('rect', { x: x - w / 2, y: y - 10, width: w, height: 20, rx: 8, class: 'edge-label-bg' }));
    group.appendChild(svgEl('text', { x, y, class: 'edge-label', 'font-size': 13 }, text));
  }

  function drawSource() {
    const points = state.graph.nodes.map(sourcePoint);
    const bounds = computeBounds(points);
    setViewBox(bounds);
    const grid = drawGrid(bounds);
    if (grid) els.svg.appendChild(grid);
    const byId = new Map(state.graph.nodes.map(n => [n.id, n]));

    if (state.view.showRelations) {
      const edgeG = svgEl('g', { class: 'source-relations' });
      for (const edge of state.graph.edges) {
        const a = byId.get(edge.from), b = byId.get(edge.to);
        if (!a || !b) continue;
        const p1 = sourcePoint(a), p2 = sourcePoint(b);
        edgeG.appendChild(svgEl('line', { x1: p1.x, y1: p1.y, x2: p2.x, y2: p2.y, class: 'relation-edge', 'marker-end': 'url(#arrow)' }));
        drawEdgeLabel(edgeG, (p1.x + p2.x) / 2, (p1.y + p2.y) / 2 - 12, relationLabel(edge));
      }
      els.svg.appendChild(edgeG);
    }

    const nodeG = svgEl('g', { class: 'source-nodes' });
    for (const node of state.graph.nodes) drawActualNode(nodeG, node, sourcePoint(node), 'source');
    els.svg.appendChild(nodeG);
  }

  function lexOrder() {
    const ids = new Set(state.graph.nodes.map(n => n.id));
    const order = Array.isArray(state.graph.lex?.order) ? state.graph.lex.order.filter(id => ids.has(id)) : [];
    return [...order, ...state.graph.nodes.map(n => n.id).filter(id => !order.includes(id))];
  }

  function drawAxisTitle(group, x, y, text, cls = '') {
    const w = Math.max(80, String(text).length * 8.2 + 22);
    group.appendChild(svgEl('rect', { x: x - w / 2, y: y - 15, width: w, height: 30, rx: 10, class: 'axis-label-bg' }));
    group.appendChild(svgEl('text', { x, y, class: `axis-label ${cls}`, 'font-size': 15 }, text));
  }

  function axesLexPositions() {
    const order = lexOrder();
    const startIndex = -Math.floor((order.length - 1) / 2);
    const map = new Map();
    order.forEach((id, index) => map.set(id, gp(-7, startIndex + index, { index })));
    return map;
  }

  function determinerOwnerRole(detNode) {
    if (!detNode) return '';
    const edge = state.graph.edges.find(e => e.type === 'det' && e.to === detNode.id);
    const host = edge ? nodeById(edge.from) : null;
    return host?.role || '';
  }

  function syntaxParts() {
    const pred = mainPredicate();
    const agent = firstNodeByRole('agent') || firstNodeByRole('subject') || firstNodeByRole('wh');
    const patient = firstNodeByRole('patient') || firstNodeByRole('theme') || firstNodeByRole('object');
    const auxs = state.graph.nodes.filter(n => ['aux', 'tense'].includes(n.role) || n.cat === 'AUX');
    const comps = state.graph.nodes.filter(n => n.role === 'comp' || n.cat === 'Comp');
    const allDets = state.graph.nodes.filter(n => ['det', 'subject-det', 'object-det'].includes(n.role) || n.cat === 'Det');
    const subjDets = allDets.filter(n => n.role === 'subject-det' || ['agent', 'subject', 'wh'].includes(determinerOwnerRole(n)));
    const objDets = allDets.filter(n => n.role === 'object-det' || ['patient', 'object', 'theme'].includes(determinerOwnerRole(n)));
    const otherDets = allDets.filter(n => !subjDets.includes(n) && !objDets.includes(n));
    const modifiers = state.graph.nodes.filter(n => ['modifier', 'prep', 'by'].includes(n.role));
    return { pred, agent, patient, auxs, comps, subjDets, objDets, otherDets, modifiers };
  }

  function syntaxSkeleton() {
    return {
      cp: gp(7, -3, { label: 'CP/S' }),
      subj: gp(5, -2, { label: 'NP/DP' }),
      tp: gp(8, -2, { label: 'TP/VP' }),
      vp: gp(8, 0, { label: 'VP' }),
      obj: gp(10, 1, { label: 'NP/DP' })
    };
  }

  function axesSyntPositions() {
    const { pred, agent, patient, auxs, comps, subjDets, objDets, otherDets, modifiers } = syntaxParts();
    const pos = new Map();
    comps.forEach((node, i) => pos.set(node.id, gp(6 + i, -1)));
    subjDets.forEach((node, i) => pos.set(node.id, gp(4 + i, 0)));
    if (agent) pos.set(agent.id, gp(5 + Math.max(0, subjDets.length - 1), 1));
    auxs.forEach((node, i) => pos.set(node.id, gp(7 - i, 0)));
    if (pred) pos.set(pred.id, gp(8, 2));
    objDets.forEach((node, i) => pos.set(node.id, gp(9 + i, 2)));
    if (patient) pos.set(patient.id, gp(10 + Math.max(0, objDets.length - 1), 3));
    otherDets.forEach((node, i) => {
      if (!pos.has(node.id)) pos.set(node.id, gp(11 + i, 2));
    });
    modifiers.forEach((node, i) => {
      if (!pos.has(node.id)) pos.set(node.id, gp(10 + i, 4));
    });
    let extra = 0;
    for (const node of state.graph.nodes) {
      if (pos.has(node.id)) continue;
      pos.set(node.id, gp(9 + extra, 5));
      extra += 1;
    }
    return pos;
  }

  function logicalRoleLabel(node) {
    if (!node) return 'Role';
    if (['agent', 'subject', 'wh'].includes(node.role)) return 'Subject';
    if (['patient', 'object', 'theme'].includes(node.role)) return 'Object';
    if (['pred'].includes(node.role) || ['V', 'Vpart'].includes(node.cat)) return 'Verb';
    if (['aux', 'tense'].includes(node.role) || node.cat === 'AUX') return 'Aux';
    if (['det', 'subject-det', 'object-det'].includes(node.role) || node.cat === 'Det') return 'Det';
    if (node.role === 'comp' || node.cat === 'Comp') return 'Comp';
    return node.role || 'Role';
  }

  function axesLogPositions() {
    const baseY = 7;
    const slots = new Map([
      ['Subject', -2],
      ['Object', 0],
      ['Verb', 2],
      ['Aux', 4],
      ['Det', -4],
      ['Comp', -6]
    ]);
    const used = new Map();
    const pos = new Map();
    for (const node of state.graph.nodes) {
      const role = logicalRoleLabel(node);
      const baseX = slots.has(role) ? slots.get(role) : -5 + used.size;
      const count = used.get(role) || 0;
      used.set(role, count + 1);
      pos.set(node.id, gp(baseX, baseY + count, { role }));
    }
    return pos;
  }

  function activeCenterMode() {
    return normalizeCenterMode(state.centralMode || state.graph?.opn_config?.center || 'opn-syntax');
  }

  function shiftedPoint(point, dx, dy, extra = {}) {
    return { ...point, x: point.x + dx * CELL, y: point.y + dy * CELL, ...extra };
  }

  function shiftedMap(map, dx, dy) {
    const out = new Map();
    for (const [id, point] of map.entries()) out.set(id, shiftedPoint(point, dx, dy));
    return out;
  }

  function centralSyntPositions() {
    return shiftedMap(axesSyntPositions(), -7, 1);
  }

  function centralSyntaxSkeleton() {
    const skel = syntaxSkeleton();
    return {
      cp: shiftedPoint(skel.cp, -7, 1, { label: 'CP/S' }),
      subj: shiftedPoint(skel.subj, -7, 1, { label: 'NP/DP' }),
      tp: shiftedPoint(skel.tp, -7, 1, { label: 'TP/VP' }),
      vp: shiftedPoint(skel.vp, -7, 1, { label: 'VP' }),
      obj: shiftedPoint(skel.obj, -7, 1, { label: 'NP/DP' })
    };
  }

  function centralFunctionalPositions() {
    const { pred, agent, patient, auxs, comps, subjDets, objDets, otherDets, modifiers } = syntaxParts();
    const pos = new Map();
    comps.forEach((node, i) => pos.set(node.id, gp(-3 + i, -2)));
    subjDets.forEach((node, i) => pos.set(node.id, gp(-2 + i, -1)));
    if (agent) pos.set(agent.id, gp(-1, 0));
    objDets.forEach((node, i) => pos.set(node.id, gp(0 + i, 1)));
    if (patient) pos.set(patient.id, gp(1, 1));
    if (pred) pos.set(pred.id, gp(2, -1));
    auxs.forEach((node, i) => pos.set(node.id, gp(3 + i, -2)));
    otherDets.forEach((node, i) => {
      if (!pos.has(node.id)) pos.set(node.id, gp(-2 + i, 2));
    });
    modifiers.forEach((node, i) => {
      if (!pos.has(node.id)) pos.set(node.id, gp(2 + i, 2));
    });
    let extra = 0;
    for (const node of state.graph.nodes) {
      if (pos.has(node.id)) continue;
      pos.set(node.id, gp(-3 + extra, 3));
      extra += 1;
    }
    return pos;
  }

  function centralPositions() {
    const mode = activeCenterMode();
    if (mode === 'opn-syntax') return centralSyntPositions();
    if (mode === 'opn-functional') return centralFunctionalPositions();
    return new Map(state.graph.nodes.map(n => [n.id, sourcePoint(n)]));
  }

  function drawCentralSyntax(group, pos) {
    const skel = centralSyntaxSkeleton();
    const { agent, pred, patient, auxs, comps, subjDets, objDets, otherDets, modifiers } = syntaxParts();
    [skel.cp, skel.subj, skel.tp, skel.vp].forEach(item => drawSyntheticNode(group, item));
    drawTreeLine(group, skel.cp, skel.subj);
    drawTreeLine(group, skel.cp, skel.tp);
    drawTreeLine(group, skel.tp, skel.vp);
    if (patient || objDets.length || otherDets.length) {
      drawSyntheticNode(group, skel.obj);
      drawTreeLine(group, skel.vp, skel.obj);
    }
    for (const comp of comps) if (pos.has(comp.id)) drawTreeLine(group, skel.cp, pos.get(comp.id));
    for (const det of subjDets) if (pos.has(det.id)) drawTreeLine(group, skel.subj, pos.get(det.id));
    if (agent && pos.has(agent.id)) drawTreeLine(group, skel.subj, pos.get(agent.id));
    for (const aux of auxs) if (pos.has(aux.id)) drawTreeLine(group, skel.tp, pos.get(aux.id));
    if (pred && pos.has(pred.id)) drawTreeLine(group, skel.vp, pos.get(pred.id));
    for (const det of objDets) if (pos.has(det.id)) drawTreeLine(group, skel.obj, pos.get(det.id));
    for (const det of otherDets) if (pos.has(det.id)) drawTreeLine(group, skel.obj, pos.get(det.id));
    if (patient && pos.has(patient.id)) drawTreeLine(group, skel.obj, pos.get(patient.id));
    for (const mod of modifiers) if (pos.has(mod.id)) drawTreeLine(group, skel.vp, pos.get(mod.id));
  }

  function drawCentralFunctional(group, pos) {
    const pred = mainPredicate();
    const { agent, patient, auxs, comps, subjDets, objDets, otherDets, modifiers } = syntaxParts();
    const center = pred && pos.has(pred.id) ? pos.get(pred.id) : gp(0, 0);
    const slots = [
      ...comps.map(n => ['Comp', n]),
      ...subjDets.map(n => ['Subject Det', n]),
      ...(agent ? [['Subject', agent]] : []),
      ...objDets.map(n => ['Object Det', n]),
      ...(patient ? [['Object', patient]] : []),
      ...auxs.map(n => ['Aux', n]),
      ...otherDets.map(n => ['Det', n]),
      ...modifiers.map(n => [n.role || 'Modifier', n])
    ];
    for (const [role, node] of slots) {
      const p = pos.get(node.id);
      if (!p) continue;
      if (pred && node.id !== pred.id) {
        group.appendChild(svgEl('line', { x1: center.x, y1: center.y, x2: p.x, y2: p.y, class: 'log-edge' }));
      }
      drawRoleBox(group, role, p.x, p.y - 48);
    }
    if (pred && pos.has(pred.id)) drawRoleBox(group, 'Predicate', center.x, center.y - 48);
  }

  function drawCentralStructure(group, pos) {
    const mode = activeCenterMode();
    if (mode === 'opn-syntax') drawCentralSyntax(group, pos);
    else if (mode === 'opn-functional') drawCentralFunctional(group, pos);
  }

  function centralTitle() {
    const mode = CENTER_MODES.find(m => m.id === activeCenterMode()) || CENTER_MODES[0];
    return `CENTRAAL · ${mode.label}`;
  }

  function drawAxesOverview() {
    const src = centralPositions();
    const lex = axesLexPositions();
    const synt = axesSyntPositions();
    const log = axesLogPositions();
    const allPoints = [
      ...state.graph.nodes.map(sourcePoint),
      ...lex.values(),
      ...synt.values(),
      ...log.values(),
      gp(-9, -4), gp(11, 9)
    ];
    const bounds = computeBounds(allPoints);
    setViewBox(bounds);
    const grid = drawGrid(bounds);
    if (grid) els.svg.appendChild(grid);

    const g = svgEl('g', { class: 'axes-view' });
    g.appendChild(svgEl('rect', { x: -8.6 * CELL, y: -3.6 * CELL, width: 3.2 * CELL, height: 7.4 * CELL, rx: 18, class: 'axis-band' }));
    g.appendChild(svgEl('rect', { x: 4.2 * CELL, y: -3.6 * CELL, width: 7.2 * CELL, height: 8.2 * CELL, rx: 18, class: 'axis-band' }));
    g.appendChild(svgEl('rect', { x: -5.2 * CELL, y: 6.2 * CELL, width: 10.4 * CELL, height: 2.6 * CELL, rx: 18, class: 'axis-band' }));
    g.appendChild(svgEl('line', { x1: -7 * CELL, y1: -3 * CELL, x2: -7 * CELL, y2: 3 * CELL, class: 'lex-axis' }));
    g.appendChild(svgEl('line', { x1: 5 * CELL, y1: -3 * CELL, x2: 10 * CELL, y2: -3 * CELL, class: 'tree-edge' }));
    g.appendChild(svgEl('line', { x1: -4 * CELL, y1: 7 * CELL, x2: 4 * CELL, y2: 7 * CELL, class: 'log-edge' }));
    drawAxisTitle(g, -7 * CELL, -3.35 * CELL, 'LEX links · eindknopen');
    drawAxisTitle(g, 8 * CELL, -3.35 * CELL, 'SYNTAX rechts');
    drawAxisTitle(g, 0, 6.55 * CELL, 'LOGICAL onder');
    drawAxisTitle(g, 0, -3.35 * CELL, centralTitle());

    if (state.view.showRelations) {
      const centralG = svgEl('g', { class: 'axes-central-structure' });
      if (activeCenterMode() === 'source') {
        for (const edge of state.graph.edges) {
          const a = src.get(edge.from), b = src.get(edge.to);
          if (!a || !b) continue;
          centralG.appendChild(svgEl('line', { x1: a.x, y1: a.y, x2: b.x, y2: b.y, class: 'relation-edge', 'marker-end': 'url(#arrow)' }));
          drawEdgeLabel(centralG, (a.x + b.x) / 2, (a.y + b.y) / 2 - 12, relationLabel(edge));
        }
      } else {
        drawCentralStructure(centralG, src);
      }
      g.appendChild(centralG);
    }

    const projG = svgEl('g', { class: 'projection-guides' });
    for (const node of state.graph.nodes) {
      const p = src.get(node.id);
      const lp = lex.get(node.id), sp = synt.get(node.id), gp = log.get(node.id);
      if (p && lp) projG.appendChild(pathEl(`M ${p.x} ${p.y} C ${(p.x + lp.x) / 2} ${p.y}, ${(p.x + lp.x) / 2} ${lp.y}, ${lp.x} ${lp.y}`, { class: 'projection-line lex' }));
      if (p && sp) projG.appendChild(pathEl(`M ${p.x} ${p.y} C ${(p.x + sp.x) / 2} ${p.y}, ${(p.x + sp.x) / 2} ${sp.y}, ${sp.x} ${sp.y}`, { class: 'projection-line synt' }));
      if (p && gp) projG.appendChild(pathEl(`M ${p.x} ${p.y} C ${p.x} ${(p.y + gp.y) / 2}, ${gp.x} ${(p.y + gp.y) / 2}, ${gp.x} ${gp.y}`, { class: 'projection-line log' }));
    }
    g.appendChild(projG);

    const sourceG = svgEl('g', { class: 'axes-source-nodes' });
    for (const node of state.graph.nodes) {
      const elem = drawActualNode(sourceG, node, src.get(node.id), activeCenterMode() === 'opn-functional' ? 'log' : activeCenterMode() === 'opn-syntax' ? 'synt' : 'source');
      elem.classList.add('center-opn-node');
      if (activeCenterMode() === 'source') elem.classList.add('source-free-node');
    }
    g.appendChild(sourceG);

    const lexG = svgEl('g', { class: 'axes-lex-nodes' });
    for (const id of lexOrder()) {
      const node = nodeById(id), p = lex.get(id);
      if (!node || !p) continue;
      lexG.appendChild(svgEl('rect', { x: p.x - 52, y: p.y - 28, width: 104, height: 56, rx: 13, class: 'lex-slot-box' }));
      lexG.appendChild(svgEl('text', { x: p.x - 74, y: p.y, class: 'rule-label', 'font-size': 12 }, String(p.index + 1)));
      drawActualNode(lexG, node, p, 'lex', 25);
    }
    g.appendChild(lexG);

    const synG = svgEl('g', { class: 'axes-synt-nodes' });
    const skel = syntaxSkeleton();
    [skel.cp, skel.subj, skel.tp, skel.vp].forEach(item => drawSyntheticNode(synG, item));
    drawTreeLine(synG, skel.cp, skel.subj);
    drawTreeLine(synG, skel.cp, skel.tp);
    drawTreeLine(synG, skel.tp, skel.vp);
    const { agent, pred, patient, auxs, comps, subjDets, objDets, otherDets, modifiers } = syntaxParts();
    if (patient || objDets.length || otherDets.length) {
      drawSyntheticNode(synG, skel.obj);
      drawTreeLine(synG, skel.vp, skel.obj);
    }
    for (const node of comps) {
      if (synt.has(node.id)) drawTreeLine(synG, skel.cp, synt.get(node.id));
    }
    for (const node of subjDets) {
      if (synt.has(node.id)) drawTreeLine(synG, skel.subj, synt.get(node.id));
    }
    if (agent && synt.has(agent.id)) drawTreeLine(synG, skel.subj, synt.get(agent.id));
    for (const node of auxs) {
      if (synt.has(node.id)) drawTreeLine(synG, skel.tp, synt.get(node.id));
    }
    if (pred && synt.has(pred.id)) drawTreeLine(synG, skel.vp, synt.get(pred.id));
    for (const node of objDets) {
      if (synt.has(node.id)) drawTreeLine(synG, skel.obj, synt.get(node.id));
    }
    for (const node of otherDets) {
      if (synt.has(node.id)) drawTreeLine(synG, skel.obj, synt.get(node.id));
    }
    if (patient && synt.has(patient.id)) drawTreeLine(synG, skel.obj, synt.get(patient.id));
    for (const node of modifiers) {
      if (synt.has(node.id)) drawTreeLine(synG, skel.vp, synt.get(node.id));
    }
    for (const [id, pnt] of synt.entries()) {
      const node = nodeById(id);
      if (!node) continue;
      drawActualNode(synG, node, pnt, 'synt', 25);
    }
    g.appendChild(synG);

    const logG = svgEl('g', { class: 'axes-log-nodes' });
    for (const [id, pnt] of log.entries()) {
      const node = nodeById(id);
      if (!node) continue;
      drawRoleBox(logG, pnt.role || logicalRoleLabel(node), pnt.x, pnt.y - 52);
      drawActualNode(logG, node, pnt, 'log', 25);
    }
    const predNode = mainPredicate();
    logG.appendChild(svgEl('text', { x: 0, y: 8.45 * CELL, class: 'rule-label', 'font-size': 13 }, logFormula(predNode, firstNodeByRole('agent') || firstNodeByRole('subject'), firstNodeByRole('patient') || firstNodeByRole('theme') || firstNodeByRole('object'), nodesByRole('aux'))));
    g.appendChild(logG);

    els.svg.appendChild(g);
  }

  function lexPositions() {
    const order = lexOrder();
    const gap = 170;
    const start = -((order.length - 1) * gap) / 2;
    const map = new Map();
    order.forEach((id, index) => map.set(id, { x: start + index * gap, y: 0, index }));
    return map;
  }

  function drawLex() {
    const pos = lexPositions();
    const order = lexOrder();
    const points = [...pos.values()].map(p => ({ x: p.x, y: p.y }));
    points.push({ x: -260, y: -160 }, { x: 260, y: 150 });
    const bounds = computeBounds(points);
    setViewBox(bounds);
    const g = svgEl('g', { class: 'lex-view' });
    const left = Math.min(...[...pos.values()].map(p => p.x), -100) - 70;
    const right = Math.max(...[...pos.values()].map(p => p.x), 100) + 70;
    g.appendChild(svgEl('line', { x1: left, y1: 0, x2: right, y2: 0, class: 'lex-axis' }));
    g.appendChild(svgEl('text', { x: left, y: -42, class: 'axis-label', 'font-size': 15 }, 'LEX-as / woordvolgorde'));
    const byId = new Map(state.graph.nodes.map(n => [n.id, n]));

    if (state.view.showRelations) {
      for (const edge of state.graph.edges) {
        const a = pos.get(edge.from), b = pos.get(edge.to);
        if (!a || !b) continue;
        const midX = (a.x + b.x) / 2;
        const dx = Math.abs(a.x - b.x);
        const arcH = Math.max(62, Math.min(160, dx * 0.42));
        const d = `M ${a.x} ${a.y - 25} Q ${midX} ${-arcH} ${b.x} ${b.y - 25}`;
        g.appendChild(pathEl(d, { class: 'lex-arc', 'marker-end': 'url(#arrow)' }));
        drawEdgeLabel(g, midX, -arcH - 8, edge.type);
      }
    }

    for (const id of order) {
      const node = byId.get(id);
      const p = pos.get(id);
      if (!node || !p) continue;
      g.appendChild(svgEl('rect', { x: p.x - 58, y: -34, width: 116, height: 68, rx: 14, class: 'lex-slot-box' }));
      g.appendChild(svgEl('text', { x: p.x, y: 58, class: 'rule-label', 'font-size': 13 }, String(p.index + 1)));
      drawActualNode(g, node, p, 'lex');
    }

    const rule = LEX_RULES.find(r => r.id === state.graph.lex.rule) || LEX_RULES[0];
    g.appendChild(svgEl('text', { x: (left + right) / 2, y: 118, class: 'rule-label', 'font-size': 14 }, `Plaatsingsregel: ${rule.label}`));
    els.svg.appendChild(g);
  }

  function drawSynt() {
    const synt = axesSyntPositions();
    const skel = syntaxSkeleton();
    const { agent, pred, patient, auxs, comps, subjDets, objDets, otherDets, modifiers } = syntaxParts();
    const points = [...synt.values(), ...Object.values(skel), gp(4, -4), gp(12, 6)];
    const bounds = computeBounds(points);
    setViewBox(bounds);
    const grid = drawGrid(bounds);
    if (grid) els.svg.appendChild(grid);

    const g = svgEl('g', { class: 'synt-view' });
    [skel.cp, skel.subj, skel.tp, skel.vp].forEach(item => drawSyntheticNode(g, item));
    drawTreeLine(g, skel.cp, skel.subj);
    drawTreeLine(g, skel.cp, skel.tp);
    drawTreeLine(g, skel.tp, skel.vp);

    if (patient || objDets.length || otherDets.length) {
      drawSyntheticNode(g, skel.obj);
      drawTreeLine(g, skel.vp, skel.obj);
    }
    for (const comp of comps) if (synt.has(comp.id)) drawTreeLine(g, skel.cp, synt.get(comp.id));
    for (const det of subjDets) if (synt.has(det.id)) drawTreeLine(g, skel.subj, synt.get(det.id));
    if (agent && synt.has(agent.id)) drawTreeLine(g, skel.subj, synt.get(agent.id));
    for (const aux of auxs) if (synt.has(aux.id)) drawTreeLine(g, skel.tp, synt.get(aux.id));
    if (pred && synt.has(pred.id)) drawTreeLine(g, skel.vp, synt.get(pred.id));
    for (const det of objDets) if (synt.has(det.id)) drawTreeLine(g, skel.obj, synt.get(det.id));
    for (const det of otherDets) if (synt.has(det.id)) drawTreeLine(g, skel.obj, synt.get(det.id));
    if (patient && synt.has(patient.id)) drawTreeLine(g, skel.obj, synt.get(patient.id));
    for (const mod of modifiers) if (synt.has(mod.id)) drawTreeLine(g, skel.vp, synt.get(mod.id));

    for (const [id, pnt] of synt.entries()) {
      const node = nodeById(id);
      if (!node) continue;
      drawActualNode(g, node, pnt, 'synt', 26);
    }

    g.appendChild(svgEl('text', { x: 8 * CELL, y: 4.35 * CELL, class: 'rule-label', 'font-size': 14 }, 'SYNT-projectie: iedere boomknoop ligt op een eigen rasterkruispunt.'));
    els.svg.appendChild(g);
  }

  function drawSyntheticNode(group, item) {
    group.appendChild(svgEl('rect', { x: item.x - 34, y: item.y - 22, width: 68, height: 44, rx: 12, class: 'synt-box' }));
    group.appendChild(svgEl('text', { x: item.x, y: item.y, class: 'box-label', 'font-size': 15 }, item.label));
  }

  function drawTreeLine(group, a, b) {
    group.appendChild(svgEl('line', { x1: a.x, y1: a.y + 22, x2: b.x, y2: b.y - 22, class: 'tree-edge' }));
  }

  function drawLog() {
    const pred = mainPredicate();
    const agent = firstNodeByRole('agent');
    const patient = firstNodeByRole('patient') || firstNodeByRole('theme') || firstNodeByRole('object');
    const auxs = nodesByRole('aux').concat(nodesByRole('tense')).filter((n, i, arr) => arr.findIndex(x => x.id === n.id) === i);
    const others = state.graph.nodes.filter(n => ![pred?.id, agent?.id, patient?.id, ...auxs.map(a => a.id)].includes(n.id));
    const points = [{ x: 0, y: -125 }, { x: -260, y: 110 }, { x: 260, y: 110 }, { x: 0, y: 230 }, { x: 260, y: 430 }];
    const bounds = computeBounds(points);
    setViewBox(bounds);
    const g = svgEl('g', { class: 'log-view' });

    const center = { x: 0, y: -20 };
    if (pred) drawActualNode(g, pred, center, 'log', 34);
    else drawSyntheticNode(g, { x: 0, y: -20, label: 'PRED' });

    const slots = [];
    if (agent) slots.push({ role: 'agent', node: agent, x: -220, y: 115 });
    if (patient) slots.push({ role: 'patient', node: patient, x: 220, y: 115 });
    auxs.forEach((node, i) => slots.push({ role: node.role || 'aux', node, x: -80 + i * 160, y: 230 }));
    others.forEach((node, i) => slots.push({ role: node.role || 'other', node, x: -240 + i * 120, y: 330 }));

    for (const slot of slots) {
      g.appendChild(svgEl('line', { x1: center.x, y1: center.y + 32, x2: slot.x, y2: slot.y - 34, class: 'log-edge' }));
      drawRoleBox(g, slot.role, slot.x, slot.y - 62);
      drawActualNode(g, slot.node, { x: slot.x, y: slot.y }, 'log');
    }
    const formula = logFormula(pred, agent, patient, auxs);
    g.appendChild(svgEl('text', { x: 0, y: -112, class: 'axis-label', 'font-size': 16 }, formula));
    g.appendChild(svgEl('text', { x: 0, y: 410, class: 'rule-label', 'font-size': 14 }, 'LOG/FT-projectie: rollen blijven zichtbaar, ook als LEX-volgorde wijzigt.'));
    els.svg.appendChild(g);
  }

  function logFormula(pred, agent, patient, auxs) {
    const p = pred ? pred.label.toLowerCase() : 'pred';
    const bits = [];
    if (agent) bits.push(`agent=${agent.label.toLowerCase()}`);
    if (patient) bits.push(`patient=${patient.label.toLowerCase()}`);
    if (auxs.length) bits.push(`aux=${auxs.map(n => n.label.toLowerCase()).join('+')}`);
    return `${p}(e${bits.length ? '; ' + bits.join(', ') : ''})`;
  }

  function drawRoleBox(group, role, x, y) {
    const w = Math.max(76, role.length * 8 + 22);
    group.appendChild(svgEl('rect', { x: x - w / 2, y: y - 15, width: w, height: 30, rx: 11, class: 'log-role-box' }));
    group.appendChild(svgEl('text', { x, y, class: 'role-label', 'font-size': 13 }, role));
  }

  function drawActualNode(group, node, p, projection, radius = 30) {
    const ng = svgEl('g', { class: 'drag-target', 'data-node-id': node.id, transform: `translate(${p.x} ${p.y})` });
    const classes = ['node-circle', projection];
    if (node.id === state.selectedNodeId) classes.push('selected');
    ng.appendChild(svgEl('circle', { cx: 0, cy: 0, r: radius, class: classes.join(' ') }));
    if (state.view.showLabels) {
      const text = String(node.label || node.id);
      const font = text.length > 6 ? 12 : 15;
      ng.appendChild(svgEl('text', { x: 0, y: -3, class: 'node-label', 'font-size': font }, text));
      ng.appendChild(svgEl('text', { x: 0, y: 18, class: 'node-sub-label', 'font-size': 10.5 }, `${node.cat} · ${node.role}`));
    }
    ng.appendChild(svgEl('title', {}, `${node.id}: ${node.label} · ${node.cat} · ${node.role}`));
    group.appendChild(ng);
    return ng;
  }

  function renderSvg() {
    if (!state.graph) return;
    els.svg.replaceChildren();
    els.svg.appendChild(buildDefs());
    if (state.projection === 'axes') drawAxesOverview();
    else if (state.projection === 'source') drawSource();
    else if (state.projection === 'lex') drawLex();
    else if (state.projection === 'synt') drawSynt();
    else drawLog();
  }

  function projectionHelpText() {
    if (state.projection === 'axes') return `Assen: centraal ${centralTitle().replace('CENTRAAL · ', '')}; LEX links, SYNTAX rechts, LOGICAL onder. OPN is de centrale presentatie; SYNTAX is alleen de rechter projectie.`;
    if (state.projection === 'source') return 'Bron: vrije JAN-knopen. Sleep knopen; bewerk label, categorie, rol en relaties.';
    if (state.projection === 'lex') return 'LEX: lineaire plaatsing op één as. De woordvolgorde is een projectie, geen wijziging van agent/patient.';
    if (state.projection === 'synt') return 'SYNT: afgeleide boomprojectie. De boom ordent dezelfde bronknopen als syntactische weergave.';
    return 'LOG/FT: predicate–argument-projectie met rollen zoals agent, patient en aux.';
  }

  function explanationText() {
    const graph = state.graph;
    const pred = mainPredicate();
    const agent = firstNodeByRole('agent');
    const patient = firstNodeByRole('patient') || firstNodeByRole('theme') || firstNodeByRole('object');
    const predText = pred ? pred.label : '—';
    const agentText = agent ? agent.label : '—';
    const patientText = patient ? patient.label : '—';
    if (state.projection === 'axes') return `Assen bij “${graph.sentence}”: centraal staat ${centralTitle().replace('CENTRAAL · ', '').toLowerCase()}. LEX projecteert links de eindknopen (${lexOrder().map(id => nodeById(id)?.label || id).join(' – ')}), SYNTAX staat rechts, LOGICAL/FT onder.`;
    if (state.projection === 'source') return `Bron bij “${graph.sentence}”: ${graph.nodes.length} vrije knopen en ${graph.edges.length} relaties. De posities zijn editbaar; de projecties blijven afgeleid.`;
    if (state.projection === 'lex') return `LEX bij “${graph.sentence}”: ${lexOrder().map(id => nodeById(id)?.label || id).join(' – ')}. Regel: ${(LEX_RULES.find(r => r.id === graph.lex.rule) || LEX_RULES[0]).label}.`;
    if (state.projection === 'synt') return `SYNT bij “${graph.sentence}”: CP/TP/VP-projectie met ${agentText} als DP-argument en ${predText} als verbaal centrum.`;
    return `LOG/FT bij “${graph.sentence}”: ${predText.toLowerCase()}(e), agent=${agentText.toLowerCase()}, patient=${patientText.toLowerCase()}.`;
  }

  function renderStatus() {
    if (!state.graph) return;
    els.titleLine.textContent = `${state.graph.title} · ${projectionName(state.projection)}`;
    const selected = selectedNode();
    const selectedText = selected ? `selectie=${selected.label}/${selected.role}` : 'geen selectie';
    els.metaLine.textContent = `${state.graph.nodes.length} knopen · ${state.graph.edges.length} relaties · LEX=${lexOrder().map(id => nodeById(id)?.label || id).join(' ')} · ${selectedText}`;
    els.projectionHelp.textContent = projectionHelpText();
    els.explainHeading.textContent = `Uitleg · ${projectionName(state.projection)}`;
    els.explainText.textContent = explanationText();
    setNotice(state.notice.level, state.notice.text);
    updateUndoRedoButtons();
  }

  function projectionName(id) {
    return id === 'axes' ? 'OPN/Assen' : id === 'source' ? 'Bron' : id === 'lex' ? 'LEX' : id === 'synt' ? 'SYNTAX-projectie' : 'LOGICAL/FT';
  }

  function renderEditor() {
    const node = selectedNode();
    els.selectionEmpty.classList.toggle('hidden', !!node);
    els.nodeEditor.classList.toggle('hidden', !node);
    if (node) {
      els.nodeIdField.value = node.id;
      els.nodeLabelInput.value = node.label;
      els.nodeCatInput.value = node.cat;
      els.nodeRoleInput.value = node.role;
      els.nodeXInput.value = String(node.x);
      els.nodeYInput.value = String(node.y);
    }
    renderLexList();
    renderEdgeControls();
  }

  function renderLexList() {
    els.lexOrderList.replaceChildren();
    const order = lexOrder();
    state.graph.lex.order = order;
    for (const [index, id] of order.entries()) {
      const node = nodeById(id);
      if (!node) continue;
      const pill = document.createElement('button');
      pill.type = 'button';
      pill.className = `lex-pill${state.selectedLexId === id ? ' selected' : ''}`;
      pill.dataset.nodeId = id;
      pill.innerHTML = `<span>${index + 1}. <strong>${escapeHtml(node.label)}</strong><br><small>${escapeHtml(node.cat)} · ${escapeHtml(node.role)}</small></span><small>LEX</small>`;
      pill.addEventListener('click', () => {
        state.selectedLexId = id;
        state.selectedNodeId = id;
        renderAll();
      });
      els.lexOrderList.appendChild(pill);
    }
  }

  function renderEdgeControls() {
    const options = state.graph.nodes.map(n => `<option value="${escapeAttr(n.id)}">${escapeHtml(n.label)} · ${escapeHtml(n.role)}</option>`).join('');
    els.edgeFromSelect.innerHTML = options;
    els.edgeToSelect.innerHTML = options;
    const pred = mainPredicate();
    if (pred) els.edgeFromSelect.value = pred.id;
    if (state.selectedNodeId && nodeById(state.selectedNodeId)) els.edgeToSelect.value = state.selectedNodeId;
    els.edgeList.replaceChildren();
    for (const edge of state.graph.edges) {
      const from = nodeById(edge.from);
      const to = nodeById(edge.to);
      const row = document.createElement('div');
      row.className = 'edge-pill';
      row.innerHTML = `<span><strong>${escapeHtml(from?.label || edge.from)}</strong> → <strong>${escapeHtml(to?.label || edge.to)}</strong><br><small>${escapeHtml(edge.type)}</small></span>`;
      const del = document.createElement('button');
      del.type = 'button';
      del.textContent = '×';
      del.title = 'Relatie verwijderen';
      del.addEventListener('click', () => {
        saveUndo('relatie verwijderen');
        state.graph.edges = state.graph.edges.filter(e => e.id !== edge.id);
        setNotice('neutral', `Relatie ${edge.type} verwijderd.`);
        renderAll();
      });
      row.appendChild(del);
      els.edgeList.appendChild(row);
    }
  }

  function escapeHtml(value) {
    return String(value).replace(/[&<>"]/g, c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;' }[c]));
  }

  function escapeAttr(value) {
    return escapeHtml(value).replace(/'/g, '&#39;');
  }

  function renderAll() {
    renderSvg();
    renderEditor();
    renderStatus();
    syncControls();
  }

  function syncControls() {
    els.showGridInput.checked = !!state.view.showGrid;
    els.showRelationsInput.checked = !!state.view.showRelations;
    els.showLabelsInput.checked = !!state.view.showLabels;
    els.snapInput.checked = !!state.view.snap;
    els.lexRuleSelect.value = state.graph?.lex?.rule || 'manual';
    if (els.centralModeSelect) els.centralModeSelect.value = activeCenterMode();
    document.querySelectorAll('.projection-tab').forEach(btn => {
      const active = btn.dataset.projection === state.projection;
      btn.classList.toggle('active', active);
      btn.setAttribute('aria-selected', active ? 'true' : 'false');
    });
  }

  function populateStaticControls() {
    for (const ex of EXAMPLES) {
      const option = document.createElement('option');
      option.value = ex.id;
      option.textContent = ex.title;
      els.exampleSelect.appendChild(option);
    }
    for (const cat of CATS) els.nodeCatInput.appendChild(new Option(cat, cat));
    for (const role of ROLES) els.nodeRoleInput.appendChild(new Option(role, role));
    for (const type of EDGE_TYPES) els.edgeTypeSelect.appendChild(new Option(type, type));
    for (const rule of LEX_RULES) els.lexRuleSelect.appendChild(new Option(rule.label, rule.id));
    for (const mode of CENTER_MODES) els.centralModeSelect.appendChild(new Option(mode.label, mode.id));
  }

  function loadExample(id) {
    const example = EXAMPLES.find(ex => ex.id === id) || EXAMPLES[0];
    state.graph = normalizeGraph(example);
    state.selectedNodeId = state.graph.nodes[0]?.id || null;
    state.centralMode = normalizeCenterMode(state.graph.opn_config?.center);
    state.selectedLexId = state.selectedNodeId;
    state.undoStack = [];
    state.redoStack = [];
    els.exampleSelect.value = example.id;
    setNotice('ok', `Voorbeeld geladen: ${example.title}.`);
    renderAll();
  }

  function applyNodeEdit() {
    const node = selectedNode();
    if (!node) return;
    saveUndo('knoop bewerken');
    node.label = String(els.nodeLabelInput.value || node.id).trim().toUpperCase();
    node.cat = String(els.nodeCatInput.value || 'X');
    node.role = String(els.nodeRoleInput.value || 'other');
    node.x = asNumber(els.nodeXInput.value, node.x);
    node.y = asNumber(els.nodeYInput.value, node.y);
    setNotice('ok', `Knoop bijgewerkt: ${node.label}.`);
    renderAll();
  }

  function addNode() {
    saveUndo('knoop toevoegen');
    const existing = new Set(state.graph.nodes.map(n => n.id));
    const id = uniqId('n', existing);
    const node = { id, label: id.toUpperCase(), cat: 'X', role: 'other', x: 0, y: state.graph.nodes.length ? Math.max(...state.graph.nodes.map(n => n.y)) + 1 : 0 };
    state.graph.nodes.push(node);
    state.graph.lex.order.push(id);
    state.selectedNodeId = id;
    state.selectedLexId = id;
    setNotice('neutral', `Nieuwe vrije knoop toegevoegd: ${id}.`);
    renderAll();
  }

  function duplicateNode() {
    const node = selectedNode();
    if (!node) { addNode(); return; }
    saveUndo('knoop dupliceren');
    const id = uniqId(slug(node.label).slice(0, 8) || 'n', new Set(state.graph.nodes.map(n => n.id)));
    const copy = { ...node, id, label: `${node.label}_2`, x: node.x + 1, y: node.y + 1 };
    state.graph.nodes.push(copy);
    state.graph.lex.order.push(id);
    state.selectedNodeId = id;
    state.selectedLexId = id;
    setNotice('neutral', `Knoop gedupliceerd: ${copy.label}.`);
    renderAll();
  }

  function deleteNode() {
    const node = selectedNode();
    if (!node) return;
    saveUndo('knoop verwijderen');
    state.graph.nodes = state.graph.nodes.filter(n => n.id !== node.id);
    state.graph.edges = state.graph.edges.filter(e => e.from !== node.id && e.to !== node.id);
    state.graph.lex.order = lexOrder().filter(id => id !== node.id);
    state.selectedNodeId = state.graph.nodes[0]?.id || null;
    state.selectedLexId = state.selectedNodeId;
    setNotice('warn', `Knoop verwijderd: ${node.label}.`);
    renderAll();
  }

  function addEdge() {
    const from = els.edgeFromSelect.value;
    const to = els.edgeToSelect.value;
    if (!from || !to || from === to) {
      setNotice('warn', 'Relatie niet toegevoegd: kies twee verschillende knopen.');
      return;
    }
    saveUndo('relatie toevoegen');
    const id = uniqId('e', new Set(state.graph.edges.map(e => e.id)));
    state.graph.edges.push({ id, from, to, type: els.edgeTypeSelect.value || 'other' });
    setNotice('ok', `Relatie toegevoegd: ${from} → ${to}.`);
    renderAll();
  }

  function moveLex(direction) {
    const id = state.selectedLexId || state.selectedNodeId;
    if (!id) return;
    const order = lexOrder();
    const i = order.indexOf(id);
    const j = i + direction;
    if (i < 0 || j < 0 || j >= order.length) return;
    saveUndo('LEX-volgorde wijzigen');
    [order[i], order[j]] = [order[j], order[i]];
    state.graph.lex.order = order;
    state.graph.lex.rule = 'manual';
    setNotice('neutral', `LEX-volgorde gewijzigd: ${order.map(x => nodeById(x)?.label || x).join(' – ')}.`);
    renderAll();
  }

  function applyLexRule(ruleId = els.lexRuleSelect.value) {
    const rule = LEX_RULES.find(r => r.id === ruleId) || LEX_RULES[0];
    saveUndo('LEX-regel toepassen');
    state.graph.lex.rule = rule.id;
    if (rule.order) {
      const orderIndex = new Map(rule.order.map((role, i) => [role, i]));
      const current = lexOrder();
      state.graph.lex.order = [...state.graph.nodes]
        .sort((a, b) => {
          const ia = orderIndex.has(a.role) ? orderIndex.get(a.role) : 999;
          const ib = orderIndex.has(b.role) ? orderIndex.get(b.role) : 999;
          if (ia !== ib) return ia - ib;
          return current.indexOf(a.id) - current.indexOf(b.id);
        })
        .map(n => n.id);
    }
    setNotice('ok', `LEX-plaatsingsregel toegepast: ${rule.label}.`);
    renderAll();
  }

  function screenToSvg(event) {
    const pt = els.svg.createSVGPoint();
    pt.x = event.clientX;
    pt.y = event.clientY;
    const ctm = els.svg.getScreenCTM();
    if (!ctm) return { x: 0, y: 0 };
    return pt.matrixTransform(ctm.inverse());
  }

  function onSvgPointerDown(event) {
    const target = event.target.closest?.('[data-node-id]');
    if (!target) {
      if (event.target === els.svg) {
        state.selectedNodeId = null;
        state.selectedLexId = null;
        renderAll();
      }
      return;
    }
    const id = target.dataset.nodeId;
    const node = nodeById(id);
    if (!node) return;
    state.selectedNodeId = id;
    state.selectedLexId = id;
    const isFreeSourceNode = state.projection === 'source' || (state.projection === 'axes' && activeCenterMode() === 'source' && !!target.closest('.source-free-node'));
    if (isFreeSourceNode) {
      const p = screenToSvg(event);
      saveUndo('knoop verplaatsen');
      state.dragging = { id, startSvg: p, startX: node.x, startY: node.y, moved: false };
      document.body.classList.add('dragging');
      event.preventDefault();
    }
    renderAll();
  }

  function onWindowPointerMove(event) {
    if (!state.dragging || !['source', 'axes'].includes(state.projection)) return;
    const node = nodeById(state.dragging.id);
    if (!node) return;
    const p = screenToSvg(event);
    const dx = (p.x - state.dragging.startSvg.x) / CELL;
    const dy = (p.y - state.dragging.startSvg.y) / CELL;
    let x = state.dragging.startX + dx;
    let y = state.dragging.startY + dy;
    if (state.view.snap) {
      x = Math.round(x);
      y = Math.round(y);
    } else {
      x = Math.round(x * 10) / 10;
      y = Math.round(y * 10) / 10;
    }
    if (x !== node.x || y !== node.y) {
      node.x = x;
      node.y = y;
      state.dragging.moved = true;
      setNotice('neutral', `Vrije plaatsing: ${node.label} → (${x}, ${y}).`);
      renderAll();
    }
  }

  function onWindowPointerUp() {
    if (!state.dragging) return;
    if (!state.dragging.moved) {
      // Avoid accumulating a useless undo state for a click-only selection.
      state.undoStack.pop();
      updateUndoRedoButtons();
    }
    state.dragging = null;
    document.body.classList.remove('dragging');
  }

  function setProjection(projection) {
    state.projection = projection;
    setNotice('neutral', projectionHelpText());
    renderAll();
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
    setTimeout(() => URL.revokeObjectURL(url), 250);
  }

  function downloadJson() {
    state.graph.opn_config = { ...(state.graph.opn_config || {}), center: activeCenterMode() };
    const graph = normalizeGraph(state.graph);
    graph.exported_at = new Date().toISOString();
    download(`${graph.id || 'opengraph-lite'}.json`, JSON.stringify(graph, null, 2), 'application/json');
    setNotice('ok', 'JSON-export gemaakt.');
  }

  function toOpn(graph) {
    const lines = [
      '# OpenGraph Lite .opn',
      `title: ${graph.title}`,
      `sentence: ${graph.sentence}`,
      '',
      '# vrije bronknopen',
      ...graph.nodes.map(n => `node ${n.id} "${n.label.replace(/"/g, '\\"')}" cat=${n.cat} role=${n.role} x=${n.x} y=${n.y}`),
      '',
      '# relaties',
      ...graph.edges.map(e => `edge ${e.from} -> ${e.to} type=${e.type}`),
      '',
      `lex: ${lexOrder().join(' ')}`,
      `lex_rule: ${graph.lex.rule}`,
      `opn_center: ${activeCenterMode()}`,
      ''
    ];
    return lines.join('\n');
  }

  function downloadOpn() {
    download(`${state.graph.id || 'opengraph-lite'}.opn`, toOpn(state.graph), 'text/plain');
    setNotice('ok', '.OPN-export gemaakt.');
  }

  function parseOpn(text) {
    const graph = { title: 'Geïmporteerde .opn', sentence: '', nodes: [], edges: [], lex: { order: [], rule: 'manual' }, opn_config: { center: 'opn-syntax' } };
    for (const line of text.split(/\r?\n/)) {
      const trimmed = line.trim();
      if (!trimmed || trimmed.startsWith('#')) continue;
      if (trimmed.startsWith('title:')) graph.title = trimmed.slice(6).trim();
      else if (trimmed.startsWith('sentence:')) graph.sentence = trimmed.slice(9).trim();
      else if (trimmed.startsWith('lex:')) graph.lex.order = trimmed.slice(4).trim().split(/\s+/).filter(Boolean);
      else if (trimmed.startsWith('lex_rule:')) graph.lex.rule = trimmed.slice(9).trim();
      else if (trimmed.startsWith('opn_center:')) graph.opn_config.center = normalizeCenterMode(trimmed.slice(11).trim());
      else if (trimmed.startsWith('node ')) {
        const m = trimmed.match(/^node\s+(\S+)\s+"([^"]*)"\s+cat=(\S+)\s+role=(\S+)\s+x=([-\d.]+)\s+y=([-\d.]+)/);
        if (m) graph.nodes.push({ id: m[1], label: m[2], cat: m[3], role: m[4], x: Number(m[5]), y: Number(m[6]) });
      } else if (trimmed.startsWith('edge ')) {
        const m = trimmed.match(/^edge\s+(\S+)\s+->\s+(\S+)\s+type=(\S+)/);
        if (m) graph.edges.push({ id: `e${graph.edges.length + 1}`, from: m[1], to: m[2], type: m[3] });
      }
    }
    return graph;
  }

  async function loadFile(file) {
    if (!file) return;
    const text = await file.text();
    let raw;
    try {
      raw = JSON.parse(text);
    } catch (_err) {
      raw = parseOpn(text);
    }
    state.graph = normalizeGraph(raw);
    state.selectedNodeId = state.graph.nodes[0]?.id || null;
    state.centralMode = normalizeCenterMode(state.graph.opn_config?.center);
    state.selectedLexId = state.selectedNodeId;
    state.undoStack = [];
    state.redoStack = [];
    els.exampleSelect.value = '';
    setNotice('ok', `Bestand geladen: ${file.name}.`);
    renderAll();
  }

  function fitView() {
    setNotice('neutral', 'FIT: viewBox opnieuw om de actuele projectie gezet.');
    renderAll();
  }

  function registerEvents() {
    els.exampleSelect.addEventListener('change', event => loadExample(event.target.value));
    els.resetExampleButton.addEventListener('click', () => loadExample(els.exampleSelect.value || EXAMPLES[0].id));
    els.undoButton.addEventListener('click', undo);
    els.redoButton.addEventListener('click', redo);
    els.fitButton.addEventListener('click', fitView);
    els.fileInput.addEventListener('change', event => loadFile(event.target.files?.[0]));
    els.downloadJsonButton.addEventListener('click', downloadJson);
    els.downloadOpnButton.addEventListener('click', downloadOpn);

    document.querySelectorAll('.projection-tab').forEach(btn => {
      btn.addEventListener('click', () => setProjection(btn.dataset.projection));
    });

    els.centralModeSelect.addEventListener('change', event => {
      state.centralMode = normalizeCenterMode(event.target.value);
      if (state.graph) state.graph.opn_config = { ...(state.graph.opn_config || {}), center: state.centralMode };
      const mode = CENTER_MODES.find(m => m.id === state.centralMode) || CENTER_MODES[0];
      setNotice('neutral', mode.help);
      renderAll();
    });

    [els.showGridInput, els.showRelationsInput, els.showLabelsInput, els.snapInput].forEach(input => {
      input.addEventListener('change', () => {
        state.view.showGrid = els.showGridInput.checked;
        state.view.showRelations = els.showRelationsInput.checked;
        state.view.showLabels = els.showLabelsInput.checked;
        state.view.snap = els.snapInput.checked;
        renderAll();
      });
    });

    els.applyNodeButton.addEventListener('click', applyNodeEdit);
    els.deleteNodeButton.addEventListener('click', deleteNode);
    els.addNodeButton.addEventListener('click', addNode);
    els.duplicateNodeButton.addEventListener('click', duplicateNode);
    els.addEdgeButton.addEventListener('click', addEdge);
    els.lexLeftButton.addEventListener('click', () => moveLex(-1));
    els.lexRightButton.addEventListener('click', () => moveLex(1));
    els.applyLexRuleButton.addEventListener('click', () => applyLexRule());
    els.lexRuleSelect.addEventListener('change', event => {
      state.graph.lex.rule = event.target.value;
      setNotice('neutral', (LEX_RULES.find(r => r.id === event.target.value) || LEX_RULES[0]).help);
      renderAll();
    });

    els.svg.addEventListener('pointerdown', onSvgPointerDown);
    window.addEventListener('pointermove', onWindowPointerMove);
    window.addEventListener('pointerup', onWindowPointerUp);
    window.addEventListener('resize', renderSvg);

    window.addEventListener('keydown', event => {
      const key = event.key.toLowerCase();
      const ctrl = event.ctrlKey || event.metaKey;
      if (ctrl && key === 'z') { event.preventDefault(); undo(); return; }
      if (ctrl && (key === 'y' || (event.shiftKey && key === 'z'))) { event.preventDefault(); redo(); return; }
      if (key === 'delete' || key === 'backspace') {
        if (document.activeElement && ['INPUT', 'TEXTAREA', 'SELECT'].includes(document.activeElement.tagName)) return;
        event.preventDefault(); deleteNode(); return;
      }
      if (key === 'f') fitView();
      if (key === '1') setProjection('axes');
      if (key === '2') setProjection('source');
      if (key === '3') setProjection('lex');
      if (key === '4') setProjection('synt');
      if (key === '5') setProjection('log');
      if (key === '6') { state.centralMode = 'opn-syntax'; if (state.graph) state.graph.opn_config = { ...(state.graph.opn_config || {}), center: 'opn-syntax' }; renderAll(); }
      if (key === '7') { state.centralMode = 'opn-functional'; if (state.graph) state.graph.opn_config = { ...(state.graph.opn_config || {}), center: 'opn-functional' }; renderAll(); }
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
  }

  function boot() {
    populateStaticControls();
    registerEvents();
    if ('serviceWorker' in navigator && location.protocol !== 'file:') {
      navigator.serviceWorker.register('sw.js').catch(() => {});
    }
    loadExample(EXAMPLES[0].id);
  }

  boot();
})();

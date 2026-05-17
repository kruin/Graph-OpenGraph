using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Globalization;
using System.IO;
using System.Runtime.InteropServices;
using System.Runtime.InteropServices.ComTypes;
using System.Text;
using System.Windows.Forms;
using System.Reflection;

[assembly: ComVisible(false)]
[assembly: AssemblyVersion("1.0.0.0")]
[assembly: AssemblyFileVersion("1.0.0.0")]

namespace GraphShellExtension
{
    internal static class Guids
    {
        public const string PreviewHandler = "D2AB8C67-64E9-4F7D-9E98-6B61A74E5601";
        public const string PreviewHandlerBraced = "{D2AB8C67-64E9-4F7D-9E98-6B61A74E5601}";
        public const string AppIdBraced = "{8B69780E-4BC1-4762-B36D-FA9D2D95B2B0}";
        public const string PreviewHandlerCategoryBraced = "{8895B1C6-B41F-4C1C-A562-0D564250836F}";
    }

    [StructLayout(LayoutKind.Sequential)]
    public struct RECT
    {
        public int left;
        public int top;
        public int right;
        public int bottom;

        public int Width { get { return Math.Max(0, right - left); } }
        public int Height { get { return Math.Max(0, bottom - top); } }
    }

    [StructLayout(LayoutKind.Sequential)]
    public struct MSG
    {
        public IntPtr hwnd;
        public uint message;
        public IntPtr wParam;
        public IntPtr lParam;
        public uint time;
        public int pt_x;
        public int pt_y;
    }

    [ComImport]
    [InterfaceType(ComInterfaceType.InterfaceIsIUnknown)]
    [Guid("8895B1C6-B41F-4C1C-A562-0D564250836F")]
    public interface IPreviewHandler
    {
        void SetWindow(IntPtr hwnd, ref RECT prc);
        void SetRect(ref RECT prc);
        void DoPreview();
        void Unload();
        void SetFocus();
        void QueryFocus(out IntPtr phwnd);
        [PreserveSig]
        int TranslateAccelerator(ref MSG pmsg);
    }

    [ComImport]
    [InterfaceType(ComInterfaceType.InterfaceIsIUnknown)]
    [Guid("B7D14566-0509-4CCE-A71F-0A554233BD9B")]
    public interface IInitializeWithFile
    {
        void Initialize([MarshalAs(UnmanagedType.LPWStr)] string pszFilePath, uint grfMode);
    }

    [ComImport]
    [InterfaceType(ComInterfaceType.InterfaceIsIUnknown)]
    [Guid("B824B49D-22AC-4161-AC8A-9916E8FA3F7F")]
    public interface IInitializeWithStream
    {
        void Initialize(IStream pstream, uint grfMode);
    }

    [ComImport]
    [InterfaceType(ComInterfaceType.InterfaceIsIUnknown)]
    [Guid("FC4801A3-2BA9-11CF-A229-00AA003D7352")]
    public interface IObjectWithSite
    {
        void SetSite([MarshalAs(UnmanagedType.IUnknown)] object pUnkSite);
        void GetSite(ref Guid riid, out IntPtr ppvSite);
    }

    [ComImport]
    [InterfaceType(ComInterfaceType.InterfaceIsIUnknown)]
    [Guid("00000114-0000-0000-C000-000000000046")]
    public interface IOleWindow
    {
        void GetWindow(out IntPtr phwnd);
        void ContextSensitiveHelp([MarshalAs(UnmanagedType.Bool)] bool fEnterMode);
    }

    internal static class NativeMethods
    {
        public const int E_NOTIMPL = unchecked((int)0x80004001);
        public const int E_FAIL = unchecked((int)0x80004005);
        public const uint SWP_NOZORDER = 0x0004;
        public const uint SWP_NOACTIVATE = 0x0010;

        [DllImport("user32.dll")]
        public static extern IntPtr SetParent(IntPtr hWndChild, IntPtr hWndNewParent);

        [DllImport("user32.dll")]
        [return: MarshalAs(UnmanagedType.Bool)]
        public static extern bool SetWindowPos(IntPtr hWnd, IntPtr hWndInsertAfter, int X, int Y, int cx, int cy, uint uFlags);

        [DllImport("user32.dll")]
        public static extern IntPtr GetFocus();
    }

    internal sealed class Dot
    {
        public int Id;
        public double X;
        public double Y;
        public string Label;
        public int ColorValue;
    }

    internal sealed class Edge
    {
        public int Id;
        public int SourceId;
        public int TargetId;
        public string Label;
        public double MidX;
        public double MidY;
        public int ColorValue;
    }

    internal sealed class GraphData
    {
        public string SourceName;
        public int GridXCount;
        public double GridXStep;
        public int GridYCount;
        public double GridYStep;
        public readonly List<Dot> Dots = new List<Dot>();
        public readonly List<Edge> Edges = new List<Edge>();

        public double Width
        {
            get
            {
                double grid = GridXCount > 1 ? (GridXCount - 1) * GridXStep : 0.0;
                double dotMax = 0.0;
                foreach (Dot d in Dots) dotMax = Math.Max(dotMax, d.X);
                return Math.Max(grid, dotMax);
            }
        }

        public double Height
        {
            get
            {
                double grid = GridYCount > 1 ? (GridYCount - 1) * GridYStep : 0.0;
                double dotMax = 0.0;
                foreach (Dot d in Dots) dotMax = Math.Max(dotMax, d.Y);
                return Math.Max(grid, dotMax);
            }
        }
    }

    internal static class GraphParser
    {
        public static GraphData ParseFile(string path)
        {
            string text = File.ReadAllText(path, Encoding.UTF8);
            return ParseText(text, Path.GetFileName(path));
        }

        public static GraphData ParseText(string text, string sourceName)
        {
            string normalized = text.Replace("\r\n", "\n").Replace('\r', '\n');
            string[] raw = normalized.Split('\n');

            if (LooksLikeStructuredOpn(raw))
            {
                return ParseStructuredOpn(raw, sourceName);
            }

            int start = raw.Length > 0 && Clean(raw[0]).Length == 0 ? 1 : 0;
            if (raw.Length - start < 5) throw new FormatException("Te weinig gegevens; verwacht minimaal 5 headerregels.");

            GraphData g = new GraphData();
            g.SourceName = sourceName ?? ".graph/.opn";
            g.GridXCount = ParseInt(raw[start + 0], start + 1, "grid_x_count");
            g.GridXStep = ParseDouble(raw[start + 1], start + 2, "grid_x_step");
            g.GridYCount = ParseInt(raw[start + 2], start + 3, "grid_y_count");
            g.GridYStep = ParseDouble(raw[start + 3], start + 4, "grid_y_step");
            int dotCount = ParseInt(raw[start + 4], start + 5, "dot_count");

            if (g.GridXCount <= 0 || g.GridYCount <= 0) throw new FormatException("Grid count moet positief zijn.");
            if (g.GridXStep <= 0 || g.GridYStep <= 0) throw new FormatException("Grid step moet positief zijn.");
            if (dotCount < 0) throw new FormatException("dot_count mag niet negatief zijn.");

            int idx = start + 5;
            for (int i = 0; i < dotCount; i++)
            {
                Require(raw, idx + 5, "dot " + (i + 1));
                Dot d = new Dot();
                d.Id = ParseInt(raw[idx + 0], idx + 1, "dot internal_id");
                d.X = ParseDouble(raw[idx + 1], idx + 2, "dot x");
                d.Y = ParseDouble(raw[idx + 2], idx + 3, "dot y");
                d.Label = Clean(raw[idx + 3]);
                d.ColorValue = ParseInt(raw[idx + 4], idx + 5, "dot color");
                g.Dots.Add(d);
                idx += 6; // sixth line = adjacency/metadata, intentionally skipped
            }

            while (idx < raw.Length && Clean(raw[idx]).Length == 0) idx++;
            if (idx < raw.Length)
            {
                int edgeCount;
                if (TryParseInt(raw[idx], out edgeCount))
                {
                    idx++;
                    for (int i = 0; i < edgeCount; i++)
                    {
                        if (idx + 9 >= raw.Length) break;
                        Edge e = new Edge();
                        e.Id = ParseInt(raw[idx + 0], idx + 1, "edge_id");
                        e.SourceId = ParseInt(raw[idx + 1], idx + 2, "edge source_id");
                        e.TargetId = ParseInt(raw[idx + 2], idx + 3, "edge target_id");
                        e.Label = Clean(raw[idx + 3]);
                        e.MidX = ParseDouble(raw[idx + 4], idx + 5, "edge mid_x");
                        e.MidY = ParseDouble(raw[idx + 5], idx + 6, "edge mid_y");
                        e.ColorValue = ParseInt(raw[idx + 9], idx + 10, "edge color");
                        g.Edges.Add(e);
                        idx += 10;
                    }
                }
            }

            return g;
        }

        private static bool LooksLikeStructuredOpn(string[] raw)
        {
            StringBuilder b = new StringBuilder();
            int limit = Math.Min(raw.Length, 80);
            for (int i = 0; i < limit; i++) b.AppendLine(Clean(raw[i]).ToLowerInvariant());
            string head = b.ToString();
            return head.Contains("structure_nodes:") || (head.Contains("structure:") && head.Contains("nodes:") && head.Contains("edges:"));
        }

        private static GraphData ParseStructuredOpn(string[] raw, string sourceName)
        {
            for (int i = 0; i < raw.Length; i++)
            {
                if (Clean(raw[i]).Equals("STRUCTURE_NODES:", StringComparison.OrdinalIgnoreCase))
                {
                    return ParseStructuredPipe(raw, sourceName);
                }
            }
            return ParseStructuredYamlish(raw, sourceName);
        }

        private static GraphData ParseStructuredPipe(string[] raw, string sourceName)
        {
            List<Dictionary<string, string>> nodes = new List<Dictionary<string, string>>();
            List<Dictionary<string, string>> edges = new List<Dictionary<string, string>>();
            string section = null;
            for (int i = 0; i < raw.Length; i++)
            {
                string line = Clean(raw[i]);
                if (line.Length == 0 || line.StartsWith("#")) continue;
                string upper = line.ToUpperInvariant();
                if (upper == "STRUCTURE_NODES:") { section = "nodes"; continue; }
                if (upper == "STRUCTURE_EDGES:") { section = "edges"; continue; }
                if (upper == "PLACEMENT:" || upper == "EXPECTED_UTTERANCE:" || upper == "NOTES:" || upper == "META:") { section = null; continue; }
                if (upper.EndsWith(":") && line.IndexOf('|') < 0 && upper != "OPN_VERSION:" && upper != "STRUCTURE_TYPE:" && upper != "TITLE:") { section = null; continue; }

                if (section == "nodes")
                {
                    string[] parts = line.Split('|');
                    if (parts.Length < 4) continue;
                    Dictionary<string, string> n = new Dictionary<string, string>();
                    n["id"] = parts[0].Trim();
                    n["label"] = parts.Length > 1 ? parts[1].Trim() : n["id"];
                    n["x"] = parts[2].Trim();
                    n["y"] = parts[3].Trim();
                    n["kind"] = parts.Length > 4 ? parts[4].Trim() : "";
                    nodes.Add(n);
                }
                else if (section == "edges")
                {
                    string[] parts = line.Split('|');
                    if (parts.Length < 2) continue;
                    Dictionary<string, string> e = new Dictionary<string, string>();
                    e["from"] = parts[0].Trim();
                    e["to"] = parts[1].Trim();
                    edges.Add(e);
                }
            }
            return BuildStructuredGraph(nodes, edges, sourceName, 40.0);
        }

        private static GraphData ParseStructuredYamlish(string[] raw, string sourceName)
        {
            List<Dictionary<string, string>> nodes = new List<Dictionary<string, string>>();
            List<Dictionary<string, string>> edges = new List<Dictionary<string, string>>();
            string section = null;
            Dictionary<string, string> current = null;

            Action finish = delegate()
            {
                if (current == null) return;
                if (section == "nodes") nodes.Add(current);
                else if (section == "edges") edges.Add(current);
                current = null;
            };

            for (int i = 0; i < raw.Length; i++)
            {
                string line = Clean(raw[i]);
                if (line.Length == 0 || line.StartsWith("#")) continue;
                string lower = line.ToLowerInvariant();
                if (lower == "nodes:") { finish(); section = "nodes"; continue; }
                if (lower == "edges:") { finish(); section = "edges"; continue; }
                if (lower == "meta:" || lower == "structure:" || lower == "notes:") { finish(); if (lower == "notes:") section = null; continue; }
                if (section != "nodes" && section != "edges") continue;

                if (line.StartsWith("- "))
                {
                    finish();
                    current = new Dictionary<string, string>();
                    string rest = line.Substring(2).Trim();
                    AddKeyValue(current, rest);
                    continue;
                }
                if (current != null) AddKeyValue(current, line);
            }
            finish();
            return BuildStructuredGraph(nodes, edges, sourceName, 1.0);
        }

        private static GraphData BuildStructuredGraph(List<Dictionary<string, string>> nodesRaw, List<Dictionary<string, string>> edgesRaw, string sourceName, double scale)
        {
            if (nodesRaw.Count == 0) throw new FormatException("STRUCTURE_OPN bevat geen nodes.");

            GraphData g = new GraphData();
            g.SourceName = sourceName ?? ".structure.opn";
            Dictionary<string, int> idMap = new Dictionary<string, int>();

            foreach (Dictionary<string, string> raw in nodesRaw)
            {
                string nodeId = Get(raw, "id");
                if (nodeId.Length == 0) throw new FormatException("STRUCTURE_OPN node zonder id.");
                if (idMap.ContainsKey(nodeId)) throw new FormatException("STRUCTURE_OPN dubbele node id: " + nodeId);
                int internalId = idMap.Count + 1;
                idMap[nodeId] = internalId;

                Dot d = new Dot();
                d.Id = internalId;
                d.X = ParseDouble(Get(raw, "x"), 0, "STRUCTURE node " + nodeId + " x") * scale;
                d.Y = ParseDouble(Get(raw, "y"), 0, "STRUCTURE node " + nodeId + " y") * scale;
                d.Label = Get(raw, "label").Length == 0 ? nodeId : Get(raw, "label");
                d.ColorValue = KindColor(Get(raw, "kind"));
                g.Dots.Add(d);
            }

            foreach (Dictionary<string, string> raw in edgesRaw)
            {
                string src = Get(raw, "from");
                if (src.Length == 0) src = Get(raw, "source");
                string dst = Get(raw, "to");
                if (dst.Length == 0) dst = Get(raw, "target");
                if (src.Length == 0 || dst.Length == 0) continue;
                if (!idMap.ContainsKey(src) || !idMap.ContainsKey(dst)) throw new FormatException("STRUCTURE_OPN edge verwijst naar onbekende node: " + src + " -> " + dst);
                Dot a = g.Dots[idMap[src] - 1];
                Dot b = g.Dots[idMap[dst] - 1];
                Edge e = new Edge();
                e.Id = g.Edges.Count + 1;
                e.SourceId = idMap[src];
                e.TargetId = idMap[dst];
                e.Label = "";
                e.MidX = (a.X + b.X) / 2.0;
                e.MidY = (a.Y + b.Y) / 2.0;
                e.ColorValue = 0x334155;
                g.Edges.Add(e);
            }

            double maxX = 0.0, maxY = 0.0;
            foreach (Dot d in g.Dots) { maxX = Math.Max(maxX, d.X); maxY = Math.Max(maxY, d.Y); }
            g.GridXStep = 40.0;
            g.GridYStep = 40.0;
            g.GridXCount = Math.Max(2, (int)Math.Ceiling(maxX / g.GridXStep) + 2);
            g.GridYCount = Math.Max(2, (int)Math.Ceiling(maxY / g.GridYStep) + 2);
            return g;
        }

        private static void AddKeyValue(Dictionary<string, string> dict, string line)
        {
            int pos = line.IndexOf(':');
            if (pos < 0) return;
            string key = line.Substring(0, pos).Trim().ToLowerInvariant();
            string value = StripQuotes(line.Substring(pos + 1).Trim());
            dict[key] = value;
        }

        private static string Get(Dictionary<string, string> dict, string key)
        {
            string value;
            return dict.TryGetValue(key, out value) ? value : "";
        }

        private static string StripQuotes(string value)
        {
            value = value.Trim();
            if (value.Length >= 2 && ((value[0] == '\"' && value[value.Length - 1] == '\"') || (value[0] == '\'' && value[value.Length - 1] == '\'')))
            {
                return value.Substring(1, value.Length - 2);
            }
            return value;
        }

        private static int KindColor(string kind)
        {
            string key = (kind ?? "").Trim().ToLowerInvariant().Replace('_', '-').Replace(' ', '-');
            if (key == "lex") return 0x16a34a;
            if (key == "verb-core" || key == "v-center") return 0x7c3aed;
            if (key == "syn") return 0xf97316;
            if (key == "position") return 0x64748b;
            if (key == "projection-label") return 0xdc2626;
            if (key == "axis-label") return 0x475569;
            if (key == "role" || key == "role-position") return 0xf59e0b;
            if (key == "thematic-state") return 0x0891b2;
            return 0x2563eb;
        }

        private static string Clean(string s)
        {
            if (s == null) return string.Empty;
            return s.Trim().TrimStart('\uFEFF');
        }

        private static void Require(string[] raw, int index, string field)
        {
            if (index >= raw.Length) throw new FormatException("Bestand eindigt te vroeg bij " + field + ".");
        }

        private static int ParseInt(string s, int line, string field)
        {
            string value = Clean(s);
            if (value.Length == 0) throw new FormatException("Regel " + line + " (" + field + ") is leeg; verwacht geheel getal.");
            double number;
            if (!double.TryParse(value, NumberStyles.Float, CultureInfo.InvariantCulture, out number))
            {
                throw new FormatException("Regel " + line + " (" + field + ") is geen getal: '" + value + "'.");
            }
            if (double.IsNaN(number) || double.IsInfinity(number) || Math.Abs(number - Math.Round(number)) > 1e-9)
            {
                throw new FormatException("Regel " + line + " (" + field + ") is geen geheel getal: '" + value + "'.");
            }
            return (int)Math.Round(number);
        }

        private static bool TryParseInt(string s, out int result)
        {
            try { result = ParseInt(s, 0, "waarde"); return true; }
            catch { result = 0; return false; }
        }

        private static double ParseDouble(string s, int line, string field)
        {
            string value = Clean(s);
            if (value.Length == 0) throw new FormatException("Regel " + line + " (" + field + ") is leeg; verwacht getal.");
            double number;
            if (!double.TryParse(value, NumberStyles.Float, CultureInfo.InvariantCulture, out number))
            {
                throw new FormatException("Regel " + line + " (" + field + ") is geen getal: '" + value + "'.");
            }
            if (double.IsNaN(number) || double.IsInfinity(number))
            {
                throw new FormatException("Regel " + line + " (" + field + ") is geen eindig getal: '" + value + "'.");
            }
            return number;
        }
    }

    internal sealed class GraphPreviewControl : UserControl
    {
        private GraphData _data;
        private string _error;
        private readonly double _margin = 28.0;
        private readonly double _dotRadius = 6.0;

        public GraphPreviewControl()
        {
            BackColor = Color.White;
            DoubleBuffered = true;
        }

        public void LoadGraph(GraphData data)
        {
            _data = data;
            _error = null;
            Invalidate();
        }

        public void ShowError(string error)
        {
            _data = null;
            _error = error;
            Invalidate();
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            base.OnPaint(e);
            e.Graphics.Clear(Color.White);
            e.Graphics.SmoothingMode = SmoothingMode.AntiAlias;
            if (!string.IsNullOrEmpty(_error))
            {
                DrawError(e.Graphics, _error);
                return;
            }
            if (_data == null)
            {
                DrawMessage(e.Graphics, "Geen graph geladen.");
                return;
            }
            DrawGraph(e.Graphics, _data);
        }

        private void DrawError(Graphics g, string text)
        {
            using (Font title = new Font("Segoe UI", 11f, FontStyle.Bold))
            using (Font body = new Font("Consolas", 9f))
            using (Brush titleBrush = new SolidBrush(Color.FromArgb(180, 0, 0)))
            using (Brush bodyBrush = new SolidBrush(Color.Black))
            {
                g.DrawString("Kan .graph/.opn niet previewen", title, titleBrush, new PointF(14, 14));
                g.DrawString(text, body, bodyBrush, new RectangleF(14, 44, Math.Max(20, Width - 28), Math.Max(20, Height - 58)));
            }
        }

        private void DrawMessage(Graphics g, string text)
        {
            using (Font f = new Font("Segoe UI", 10f))
            using (Brush b = new SolidBrush(Color.Black))
            {
                g.DrawString(text, f, b, new PointF(14, 14));
            }
        }

        private void DrawGraph(Graphics g, GraphData data)
        {
            g.Clear(Color.FromArgb(248, 250, 252));
            g.CompositingQuality = CompositingQuality.HighQuality;
            g.InterpolationMode = InterpolationMode.HighQualityBicubic;
            g.PixelOffsetMode = PixelOffsetMode.HighQuality;
            g.SmoothingMode = SmoothingMode.AntiAlias;

            double minX, minY, maxX, maxY;
            ComputeContentBounds(data, out minX, out minY, out maxX, out maxY);
            double contentW = Math.Max(1.0, maxX - minX);
            double contentH = Math.Max(1.0, maxY - minY);

            RectangleF card = new RectangleF(10, 34, Math.Max(20, Width - 20), Math.Max(20, Height - 44));
            using (Brush cardBrush = new SolidBrush(Color.FromArgb(249, 250, 251)))
            using (Pen cardPen = new Pen(Color.FromArgb(217, 223, 232), 1f))
            {
                g.FillRectangle(cardBrush, card);
                g.DrawRectangle(cardPen, card.X, card.Y, card.Width - 1, card.Height - 1);
            }

            double pad = Math.Max(10.0, Math.Min(card.Width, card.Height) * 0.08);
            double scale = Math.Min((card.Width - 2.0 * pad) / contentW, (card.Height - 2.0 * pad) / contentH);
            if (double.IsNaN(scale) || double.IsInfinity(scale) || scale <= 0.0) scale = 1.0;
            double drawW = contentW * scale;
            double drawH = contentH * scale;
            double ox = card.X + (card.Width - drawW) / 2.0;
            double oy = card.Y + (card.Height - drawH) / 2.0;

            Func<double, float> X = v => (float)(ox + (v - minX) * scale);
            Func<double, float> Y = v => (float)(oy + (v - minY) * scale);
            Func<double, float> S = v => (float)Math.Max(1.0, v * scale);

            using (Font headerFont = new Font("Segoe UI", 9f, FontStyle.Regular))
            using (Brush headerBrush = new SolidBrush(Color.FromArgb(71, 85, 105)))
            {
                string header = string.Format(CultureInfo.InvariantCulture, "{0}   dots {1}   edges {2}   grid {3}x{4}",
                    data.SourceName, data.Dots.Count, data.Edges.Count, data.GridXCount, data.GridYCount);
                g.DrawString(header, headerFont, headerBrush, new PointF(12, 9));
            }

            if (Width >= 420 || Height >= 320)
            {
                DrawCleanGrid(g, data, X, Y, minX, minY, maxX, maxY);
            }

            Dictionary<int, Dot> dotsById = new Dictionary<int, Dot>();
            foreach (Dot d in data.Dots) dotsById[d.Id] = d;

            float edgeWidth = Math.Max(1.4f, Math.Min(5.0f, (float)(Math.Min(card.Width, card.Height) / 150.0)));
            foreach (Edge edge in data.Edges)
            {
                Dot a, b;
                if (!dotsById.TryGetValue(edge.SourceId, out a)) continue;
                if (!dotsById.TryGetValue(edge.TargetId, out b)) continue;
                using (Pen shadow = new Pen(Color.FromArgb(42, 15, 23, 42), edgeWidth + 2f))
                using (Pen p = new Pen(ToCleanColor(edge.ColorValue, Color.FromArgb(37, 99, 235)), edgeWidth))
                {
                    shadow.StartCap = shadow.EndCap = LineCap.Round;
                    p.StartCap = p.EndCap = LineCap.Round;
                    g.DrawLine(shadow, X(a.X), Y(a.Y), X(b.X), Y(b.Y));
                    g.DrawLine(p, X(a.X), Y(a.Y), X(b.X), Y(b.Y));
                }
            }

            float radius = Math.Max(3.0f, Math.Min(8.5f, (float)(Math.Min(card.Width, card.Height) / 58.0)));
            if (data.Dots.Count > 60) radius *= 0.78f;
            if (data.Dots.Count > 120) radius *= 0.68f;

            bool drawLabels = data.Dots.Count <= 80 && Math.Min(card.Width, card.Height) > 320;
            using (Font labelFont = new Font("Segoe UI", Math.Max(8f, radius * 1.7f), FontStyle.Regular))
            using (Brush labelBrush = new SolidBrush(Color.FromArgb(15, 23, 42)))
            using (Brush labelBack = new SolidBrush(Color.FromArgb(220, 249, 250, 251)))
            using (Pen whiteOutline = new Pen(Color.FromArgb(245, 255, 255, 255), Math.Max(2f, radius * 0.55f)))
            using (Pen darkOutline = new Pen(Color.FromArgb(185, 15, 23, 42), 1f))
            {
                foreach (Dot d in data.Dots)
                {
                    float x = X(d.X);
                    float y = Y(d.Y);
                    RectangleF r = new RectangleF(x - radius, y - radius, radius * 2f, radius * 2f);
                    using (Brush fill = new SolidBrush(ToCleanColor(d.ColorValue, Color.FromArgb(37, 99, 235))))
                    {
                        g.FillEllipse(fill, r);
                        g.DrawEllipse(whiteOutline, r);
                        g.DrawEllipse(darkOutline, r);
                    }

                    if (drawLabels)
                    {
                        string label = string.IsNullOrWhiteSpace(d.Label) ? d.Id.ToString(CultureInfo.InvariantCulture) : d.Label;
                        SizeF sz = g.MeasureString(label, labelFont);
                        RectangleF lr = new RectangleF(x + radius + 3f, y - sz.Height / 2f, sz.Width + 5f, sz.Height);
                        g.FillRectangle(labelBack, lr);
                        g.DrawString(label, labelFont, labelBrush, lr.Location);
                    }
                }
            }
        }

        private static void DrawCleanGrid(Graphics g, GraphData data, Func<double, float> X, Func<double, float> Y, double minX, double minY, double maxX, double maxY)
        {
            if (data.GridXStep <= 0.0 || data.GridYStep <= 0.0) return;
            using (Pen minor = new Pen(Color.FromArgb(235, 239, 244), 1f))
            using (Pen major = new Pen(Color.FromArgb(218, 225, 234), 1f))
            {
                int firstX = (int)Math.Floor(minX / data.GridXStep);
                int lastX = (int)Math.Ceiling(maxX / data.GridXStep);
                for (int ix = firstX; ix <= lastX; ix++)
                {
                    double x = ix * data.GridXStep;
                    g.DrawLine(ix % 5 == 0 ? major : minor, X(x), Y(minY), X(x), Y(maxY));
                }
                int firstY = (int)Math.Floor(minY / data.GridYStep);
                int lastY = (int)Math.Ceiling(maxY / data.GridYStep);
                for (int iy = firstY; iy <= lastY; iy++)
                {
                    double y = iy * data.GridYStep;
                    g.DrawLine(iy % 5 == 0 ? major : minor, X(minX), Y(y), X(maxX), Y(y));
                }
            }
        }

        private static void ComputeContentBounds(GraphData data, out double minX, out double minY, out double maxX, out double maxY)
        {
            bool has = false;
            minX = minY = maxX = maxY = 0.0;
            foreach (Dot d in data.Dots) Include(d.X, d.Y, ref has, ref minX, ref minY, ref maxX, ref maxY);
            foreach (Edge e in data.Edges) Include(e.MidX, e.MidY, ref has, ref minX, ref minY, ref maxX, ref maxY);
            if (!has)
            {
                Include(0.0, 0.0, ref has, ref minX, ref minY, ref maxX, ref maxY);
                Include(data.Width, data.Height, ref has, ref minX, ref minY, ref maxX, ref maxY);
            }
            if (maxX <= minX) maxX = minX + 1.0;
            if (maxY <= minY) maxY = minY + 1.0;
        }

        private static void Include(double x, double y, ref bool has, ref double minX, ref double minY, ref double maxX, ref double maxY)
        {
            if (double.IsNaN(x) || double.IsInfinity(x) || double.IsNaN(y) || double.IsInfinity(y)) return;
            if (!has)
            {
                minX = maxX = x;
                minY = maxY = y;
                has = true;
                return;
            }
            minX = Math.Min(minX, x);
            minY = Math.Min(minY, y);
            maxX = Math.Max(maxX, x);
            maxY = Math.Max(maxY, y);
        }

        private static Color ToCleanColor(int value, Color fallback)
        {
            int rgb = value & 0xFFFFFF;
            if (rgb == 0) return fallback;
            int r = (rgb >> 16) & 255;
            int g = (rgb >> 8) & 255;
            int b = rgb & 255;
            if (r > 242 && g > 242 && b > 242) return fallback;
            return Color.FromArgb(r, g, b);
        }
    }

    [ComVisible(true)]
    [Guid(Guids.PreviewHandler)]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("OpenGraph.GraphPreviewHandler")]
    public sealed class GraphPreviewHandler : IPreviewHandler, IInitializeWithFile, IInitializeWithStream, IObjectWithSite, IOleWindow
    {
        private IntPtr _parentHwnd = IntPtr.Zero;
        private RECT _rect;
        private GraphPreviewControl _control;
        private string _filePath;
        private string _streamText;
        private object _site;

        public void Initialize(string pszFilePath, uint grfMode)
        {
            _filePath = pszFilePath;
            _streamText = null;
        }

        public void Initialize(IStream pstream, uint grfMode)
        {
            _streamText = ReadAllTextFromStream(pstream);
            _filePath = null;
        }

        public void SetWindow(IntPtr hwnd, ref RECT prc)
        {
            _parentHwnd = hwnd;
            _rect = prc;
            ApplyWindowBounds();
        }

        public void SetRect(ref RECT prc)
        {
            _rect = prc;
            ApplyWindowBounds();
        }

        public void DoPreview()
        {
            if (_parentHwnd == IntPtr.Zero)
            {
                throw new COMException("Preview parent window is not set.", NativeMethods.E_FAIL);
            }

            Unload();
            _control = new GraphPreviewControl();
            _control.CreateControl();
            NativeMethods.SetParent(_control.Handle, _parentHwnd);
            ApplyWindowBounds();

            try
            {
                GraphData data = _streamText != null
                    ? GraphParser.ParseText(_streamText, _filePath == null ? ".graph/.opn" : Path.GetFileName(_filePath))
                    : GraphParser.ParseFile(_filePath);
                _control.LoadGraph(data);
            }
            catch (Exception ex)
            {
                _control.ShowError(ex.Message);
            }

            _control.Show();
        }

        public void Unload()
        {
            if (_control != null)
            {
                try { _control.Dispose(); }
                catch { }
                _control = null;
            }
        }

        public void SetFocus()
        {
            if (_control != null) _control.Focus();
        }

        public void QueryFocus(out IntPtr phwnd)
        {
            phwnd = NativeMethods.GetFocus();
        }

        public int TranslateAccelerator(ref MSG pmsg)
        {
            return NativeMethods.E_NOTIMPL;
        }

        public void GetWindow(out IntPtr phwnd)
        {
            phwnd = _control != null ? _control.Handle : _parentHwnd;
        }

        public void ContextSensitiveHelp(bool fEnterMode)
        {
            throw new COMException("Not implemented", NativeMethods.E_NOTIMPL);
        }

        public void SetSite(object pUnkSite)
        {
            _site = pUnkSite;
        }

        public void GetSite(ref Guid riid, out IntPtr ppvSite)
        {
            ppvSite = IntPtr.Zero;
            if (_site == null)
            {
                throw new COMException("Site is not set.", NativeMethods.E_FAIL);
            }
            IntPtr punk = IntPtr.Zero;
            try
            {
                punk = Marshal.GetIUnknownForObject(_site);
                Marshal.QueryInterface(punk, ref riid, out ppvSite);
            }
            finally
            {
                if (punk != IntPtr.Zero) Marshal.Release(punk);
            }
        }

        private void ApplyWindowBounds()
        {
            if (_control == null || _control.IsDisposed) return;
            NativeMethods.SetWindowPos(_control.Handle, IntPtr.Zero, _rect.left, _rect.top, _rect.Width, _rect.Height,
                NativeMethods.SWP_NOZORDER | NativeMethods.SWP_NOACTIVATE);
        }

        private static string ReadAllTextFromStream(IStream stream)
        {
            if (stream == null) return string.Empty;
            using (MemoryStream ms = new MemoryStream())
            {
                byte[] buffer = new byte[8192];
                IntPtr pcbRead = Marshal.AllocHGlobal(sizeof(int));
                try
                {
                    while (true)
                    {
                        Marshal.WriteInt32(pcbRead, 0);
                        stream.Read(buffer, buffer.Length, pcbRead);
                        int read = Marshal.ReadInt32(pcbRead);
                        if (read <= 0) break;
                        ms.Write(buffer, 0, read);
                        if (read < buffer.Length) break;
                    }
                }
                finally
                {
                    Marshal.FreeHGlobal(pcbRead);
                }
                byte[] bytes = ms.ToArray();
                if (bytes.Length >= 3 && bytes[0] == 0xEF && bytes[1] == 0xBB && bytes[2] == 0xBF)
                {
                    return Encoding.UTF8.GetString(bytes, 3, bytes.Length - 3);
                }
                return Encoding.UTF8.GetString(bytes);
            }
        }
    }
}

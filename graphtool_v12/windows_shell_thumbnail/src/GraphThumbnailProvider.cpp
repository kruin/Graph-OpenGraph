// GraphThumbnailProvider.cpp
// Native C++ Windows Shell thumbnail provider for .graph/.opn files, including structured OPN.
// Builds as a 64-bit in-process COM DLL for Windows Explorer.
//
// Project: Mapping V4 / Graph tools
// Handler: .graph -> rendered thumbnail
// Interfaces: IThumbnailProvider + IInitializeWithStream

#ifndef NOMINMAX
#define NOMINMAX
#endif
#include <windows.h>
#include <shlwapi.h>
#include <shlobj.h>
#include <thumbcache.h>
#include <gdiplus.h>

#include <algorithm>
#include <cmath>
#include <cwchar>
#include <cwctype>
#include <limits>
#include <map>
#include <new>
#include <sstream>
#include <string>
#include <vector>

#pragma comment(lib, "gdiplus.lib")
#pragma comment(lib, "shlwapi.lib")
#pragma comment(lib, "ole32.lib")
#pragma comment(lib, "oleaut32.lib")
#pragma comment(lib, "uuid.lib")
#pragma comment(lib, "advapi32.lib")
#pragma comment(lib, "shell32.lib")
#pragma comment(lib, "user32.lib")
#pragma comment(lib, "gdi32.lib")

using namespace Gdiplus;

// {B86C773A-62BD-4F47-85D4-132380F52AE3}
static const CLSID CLSID_GraphThumbnailProvider =
{ 0xb86c773a, 0x62bd, 0x4f47, { 0x85, 0xd4, 0x13, 0x23, 0x80, 0xf5, 0x2a, 0xe3 } };

static const wchar_t* CLSID_GRAPH_THUMBNAIL_PROVIDER_STRING = L"{B86C773A-62BD-4F47-85D4-132380F52AE3}";
static const wchar_t* SHELLEX_THUMBNAIL_HANDLER_KEY = L"{E357FCCD-A995-4576-B01F-234630154E96}";
static const wchar_t* GRAPH_PROGID = L"GraphFile";

static HINSTANCE g_hInst = nullptr;
static long g_cDllRef = 0;
struct Dot
{
    int internalId = 0;
    double x = 0.0;
    double y = 0.0;
    std::wstring label;
    int colorValue = 0;
    std::wstring adjacency;
};

struct Edge
{
    int edgeId = 0;
    int sourceId = 0;
    int targetId = 0;
    std::wstring label;
    double midX = 0.0;
    double midY = 0.0;
    int colorValue = 0;
};

struct GraphData
{
    int gridXCount = 0;
    double gridXStep = 0.0;
    int gridYCount = 0;
    double gridYStep = 0.0;
    std::vector<Dot> dots;
    std::vector<Edge> edges;
};

static std::wstring Trim(const std::wstring& s)
{
    size_t begin = 0;
    while (begin < s.size() && iswspace(s[begin]))
    {
        ++begin;
    }

    size_t end = s.size();
    while (end > begin && iswspace(s[end - 1]))
    {
        --end;
    }

    std::wstring out = s.substr(begin, end - begin);
    if (!out.empty() && out[0] == 0xFEFF)
    {
        out.erase(out.begin());
    }
    return out;
}

static std::vector<std::wstring> SplitLines(const std::wstring& text)
{
    std::vector<std::wstring> lines;
    std::wstring current;
    for (wchar_t ch : text)
    {
        if (ch == L'\n')
        {
            if (!current.empty() && current.back() == L'\r')
            {
                current.pop_back();
            }
            lines.push_back(current);
            current.clear();
        }
        else
        {
            current.push_back(ch);
        }
    }

    if (!current.empty() || text.empty())
    {
        if (!current.empty() && current.back() == L'\r')
        {
            current.pop_back();
        }
        lines.push_back(current);
    }

    return lines;
}

static bool BytesToWideText(const std::string& bytes, std::wstring& out)
{
    if (bytes.empty())
    {
        out.clear();
        return true;
    }

    int needed = MultiByteToWideChar(CP_UTF8, MB_ERR_INVALID_CHARS, bytes.data(), static_cast<int>(bytes.size()), nullptr, 0);
    UINT codePage = CP_UTF8;
    DWORD flags = MB_ERR_INVALID_CHARS;

    if (needed <= 0)
    {
        codePage = CP_ACP;
        flags = 0;
        needed = MultiByteToWideChar(codePage, flags, bytes.data(), static_cast<int>(bytes.size()), nullptr, 0);
    }

    if (needed <= 0)
    {
        return false;
    }

    out.assign(static_cast<size_t>(needed), L'\0');
    int written = MultiByteToWideChar(codePage, flags, bytes.data(), static_cast<int>(bytes.size()), &out[0], needed);
    if (written <= 0)
    {
        out.clear();
        return false;
    }
    return true;
}

static bool ParseFloat(const std::wstring& s, double& value)
{
    std::wstring t = Trim(s);
    if (t.empty())
    {
        return false;
    }
    wchar_t* endPtr = nullptr;
    value = wcstod(t.c_str(), &endPtr);
    if (endPtr == t.c_str())
    {
        return false;
    }
    while (endPtr && *endPtr)
    {
        if (!iswspace(*endPtr))
        {
            return false;
        }
        ++endPtr;
    }
    return std::isfinite(value) != 0;
}

static bool ParseInt(const std::wstring& s, int& value)
{
    double d = 0.0;
    if (!ParseFloat(s, d))
    {
        return false;
    }
    double rounded = std::round(d);
    if (std::fabs(d - rounded) > 1e-9)
    {
        return false;
    }
    if (rounded < static_cast<double>(std::numeric_limits<int>::min()) ||
        rounded > static_cast<double>(std::numeric_limits<int>::max()))
    {
        return false;
    }
    value = static_cast<int>(rounded);
    return true;
}

static bool LineAt(const std::vector<std::wstring>& lines, size_t index, std::wstring& out)
{
    if (index >= lines.size())
    {
        out.clear();
        return false;
    }
    out = lines[index];
    return true;
}


static std::wstring ToLowerText(std::wstring s)
{
    std::transform(s.begin(), s.end(), s.begin(), [](wchar_t ch) { return static_cast<wchar_t>(towlower(ch)); });
    return s;
}

static std::wstring ToUpperText(std::wstring s)
{
    std::transform(s.begin(), s.end(), s.begin(), [](wchar_t ch) { return static_cast<wchar_t>(towupper(ch)); });
    return s;
}

static bool StartsWithText(const std::wstring& s, const std::wstring& prefix)
{
    return s.size() >= prefix.size() && s.compare(0, prefix.size(), prefix) == 0;
}

static std::wstring StripQuotes(const std::wstring& s)
{
    std::wstring value = Trim(s);
    if (value.size() >= 2)
    {
        wchar_t first = value.front();
        wchar_t last = value.back();
        if ((first == L'\"' && last == L'\"') || (first == L'\'' && last == L'\''))
        {
            return value.substr(1, value.size() - 2);
        }
    }
    return value;
}

static std::vector<std::wstring> SplitPipe(const std::wstring& line)
{
    std::vector<std::wstring> parts;
    std::wstring cur;
    for (wchar_t ch : line)
    {
        if (ch == L'|')
        {
            parts.push_back(Trim(cur));
            cur.clear();
        }
        else
        {
            cur.push_back(ch);
        }
    }
    parts.push_back(Trim(cur));
    return parts;
}

static int KindColor(const std::wstring& kind)
{
    std::wstring key = ToLowerText(Trim(kind));
    for (wchar_t& ch : key)
    {
        if (ch == L'_' || ch == L' ') ch = L'-';
    }
    if (key == L"lex") return 0x16a34a;
    if (key == L"verb-core" || key == L"v-center") return 0x7c3aed;
    if (key == L"syn") return 0xf97316;
    if (key == L"position") return 0x64748b;
    if (key == L"projection-label") return 0xdc2626;
    if (key == L"axis-label") return 0x475569;
    if (key == L"role" || key == L"role-position") return 0xf59e0b;
    if (key == L"thematic-state") return 0x0891b2;
    return 0x2563eb;
}

static std::wstring DictGet(const std::map<std::wstring, std::wstring>& values, const std::wstring& key)
{
    auto it = values.find(key);
    if (it == values.end()) return L"";
    return it->second;
}

static void AddKeyValue(std::map<std::wstring, std::wstring>& values, const std::wstring& line)
{
    size_t pos = line.find(L':');
    if (pos == std::wstring::npos) return;
    std::wstring key = ToLowerText(Trim(line.substr(0, pos)));
    std::wstring value = StripQuotes(line.substr(pos + 1));
    values[key] = value;
}

static bool LooksLikeStructuredOpn(const std::vector<std::wstring>& lines)
{
    std::wstring head;
    size_t limit = std::min<size_t>(lines.size(), 80);
    for (size_t i = 0; i < limit; ++i)
    {
        head += ToLowerText(Trim(lines[i]));
        head += L'\n';
    }
    return head.find(L"structure_nodes:") != std::wstring::npos ||
        (head.find(L"structure:") != std::wstring::npos && head.find(L"nodes:") != std::wstring::npos && head.find(L"edges:") != std::wstring::npos);
}

static bool BuildStructuredGraph(
    const std::vector<std::map<std::wstring, std::wstring>>& nodesRaw,
    const std::vector<std::map<std::wstring, std::wstring>>& edgesRaw,
    double scale,
    GraphData& graph)
{
    if (nodesRaw.empty())
    {
        return false;
    }

    graph.gridXCount = 0;
    graph.gridXStep = 40.0;
    graph.gridYCount = 0;
    graph.gridYStep = 40.0;
    graph.dots.clear();
    graph.edges.clear();

    std::map<std::wstring, int> idMap;
    for (const auto& raw : nodesRaw)
    {
        std::wstring nodeId = Trim(DictGet(raw, L"id"));
        if (nodeId.empty() || idMap.find(nodeId) != idMap.end())
        {
            return false;
        }
        int internalId = static_cast<int>(idMap.size()) + 1;
        idMap[nodeId] = internalId;

        double x = 0.0;
        double y = 0.0;
        if (!ParseFloat(DictGet(raw, L"x"), x)) return false;
        if (!ParseFloat(DictGet(raw, L"y"), y)) return false;

        Dot d;
        d.internalId = internalId;
        d.x = x * scale;
        d.y = y * scale;
        d.label = DictGet(raw, L"label").empty() ? nodeId : DictGet(raw, L"label");
        d.colorValue = KindColor(DictGet(raw, L"kind"));
        d.adjacency = DictGet(raw, L"kind");
        graph.dots.push_back(d);
    }

    for (const auto& raw : edgesRaw)
    {
        std::wstring src = Trim(DictGet(raw, L"from"));
        if (src.empty()) src = Trim(DictGet(raw, L"source"));
        std::wstring dst = Trim(DictGet(raw, L"to"));
        if (dst.empty()) dst = Trim(DictGet(raw, L"target"));
        if (src.empty() || dst.empty()) continue;
        auto ai = idMap.find(src);
        auto bi = idMap.find(dst);
        if (ai == idMap.end() || bi == idMap.end()) return false;
        const Dot& a = graph.dots[static_cast<size_t>(ai->second - 1)];
        const Dot& b = graph.dots[static_cast<size_t>(bi->second - 1)];

        Edge e;
        e.edgeId = static_cast<int>(graph.edges.size()) + 1;
        e.sourceId = ai->second;
        e.targetId = bi->second;
        e.label = L"";
        e.midX = (a.x + b.x) / 2.0;
        e.midY = (a.y + b.y) / 2.0;
        e.colorValue = 0x334155;
        graph.edges.push_back(e);
    }

    double maxX = 0.0;
    double maxY = 0.0;
    for (const Dot& d : graph.dots)
    {
        maxX = std::max(maxX, d.x);
        maxY = std::max(maxY, d.y);
    }
    graph.gridXCount = std::max(2, static_cast<int>(std::ceil(maxX / graph.gridXStep)) + 2);
    graph.gridYCount = std::max(2, static_cast<int>(std::ceil(maxY / graph.gridYStep)) + 2);
    return true;
}

static bool ParseStructuredPipe(const std::vector<std::wstring>& lines, GraphData& graph)
{
    std::vector<std::map<std::wstring, std::wstring>> nodesRaw;
    std::vector<std::map<std::wstring, std::wstring>> edgesRaw;
    std::wstring section;

    for (const std::wstring& raw : lines)
    {
        std::wstring line = Trim(raw);
        if (line.empty() || StartsWithText(line, L"#")) continue;
        std::wstring upper = ToUpperText(line);
        if (upper == L"STRUCTURE_NODES:") { section = L"nodes"; continue; }
        if (upper == L"STRUCTURE_EDGES:") { section = L"edges"; continue; }
        if (upper == L"PLACEMENT:" || upper == L"EXPECTED_UTTERANCE:" || upper == L"NOTES:" || upper == L"META:") { section.clear(); continue; }
        if (!line.empty() && line.back() == L':' && line.find(L'|') == std::wstring::npos && upper != L"OPN_VERSION:" && upper != L"STRUCTURE_TYPE:" && upper != L"TITLE:")
        {
            section.clear();
            continue;
        }

        if (section == L"nodes")
        {
            std::vector<std::wstring> parts = SplitPipe(line);
            if (parts.size() < 4) continue;
            std::map<std::wstring, std::wstring> n;
            n[L"id"] = parts[0];
            n[L"label"] = parts.size() > 1 ? parts[1] : parts[0];
            n[L"x"] = parts[2];
            n[L"y"] = parts[3];
            n[L"kind"] = parts.size() > 4 ? parts[4] : L"";
            nodesRaw.push_back(n);
        }
        else if (section == L"edges")
        {
            std::vector<std::wstring> parts = SplitPipe(line);
            if (parts.size() < 2) continue;
            std::map<std::wstring, std::wstring> e;
            e[L"from"] = parts[0];
            e[L"to"] = parts[1];
            edgesRaw.push_back(e);
        }
    }
    return BuildStructuredGraph(nodesRaw, edgesRaw, 40.0, graph);
}

static bool ParseStructuredYamlish(const std::vector<std::wstring>& lines, GraphData& graph)
{
    std::vector<std::map<std::wstring, std::wstring>> nodesRaw;
    std::vector<std::map<std::wstring, std::wstring>> edgesRaw;
    std::wstring section;
    std::map<std::wstring, std::wstring> current;
    bool hasCurrent = false;

    auto finishCurrent = [&]()
    {
        if (!hasCurrent) return;
        if (section == L"nodes") nodesRaw.push_back(current);
        else if (section == L"edges") edgesRaw.push_back(current);
        current.clear();
        hasCurrent = false;
    };

    for (const std::wstring& raw : lines)
    {
        std::wstring line = Trim(raw);
        if (line.empty() || StartsWithText(line, L"#")) continue;
        std::wstring lower = ToLowerText(line);
        if (lower == L"nodes:") { finishCurrent(); section = L"nodes"; continue; }
        if (lower == L"edges:") { finishCurrent(); section = L"edges"; continue; }
        if (lower == L"meta:" || lower == L"structure:" || lower == L"notes:") { finishCurrent(); if (lower == L"notes:") section.clear(); continue; }
        if (section != L"nodes" && section != L"edges") continue;

        if (StartsWithText(line, L"- "))
        {
            finishCurrent();
            current.clear();
            hasCurrent = true;
            AddKeyValue(current, Trim(line.substr(2)));
            continue;
        }
        if (hasCurrent)
        {
            AddKeyValue(current, line);
        }
    }
    finishCurrent();
    return BuildStructuredGraph(nodesRaw, edgesRaw, 1.0, graph);
}

static bool ParseStructuredOpnText(const std::vector<std::wstring>& lines, GraphData& graph)
{
    for (const std::wstring& raw : lines)
    {
        if (ToUpperText(Trim(raw)) == L"STRUCTURE_NODES:")
        {
            return ParseStructuredPipe(lines, graph);
        }
    }
    return ParseStructuredYamlish(lines, graph);
}

static bool ParseGraphText(const std::wstring& text, GraphData& graph)
{
    std::vector<std::wstring> lines = SplitLines(text);
    if (lines.empty())
    {
        return false;
    }

    if (LooksLikeStructuredOpn(lines))
    {
        return ParseStructuredOpnText(lines, graph);
    }

    size_t start = 0;
    if (!lines.empty() && Trim(lines[0]).empty())
    {
        start = 1;
    }

    if (lines.size() < start + 5)
    {
        return false;
    }

    int dotCount = 0;
    if (!ParseInt(lines[start + 0], graph.gridXCount)) return false;
    if (!ParseFloat(lines[start + 1], graph.gridXStep)) return false;
    if (!ParseInt(lines[start + 2], graph.gridYCount)) return false;
    if (!ParseFloat(lines[start + 3], graph.gridYStep)) return false;
    if (!ParseInt(lines[start + 4], dotCount)) return false;

    if (graph.gridXCount <= 0 || graph.gridYCount <= 0 || graph.gridXStep <= 0.0 || graph.gridYStep <= 0.0 || dotCount < 0)
    {
        return false;
    }

    graph.dots.clear();
    graph.edges.clear();

    size_t idx = start + 5;
    for (int i = 0; i < dotCount; ++i)
    {
        if (idx + 5 >= lines.size())
        {
            return false;
        }

        Dot d;
        if (!ParseInt(lines[idx + 0], d.internalId)) return false;
        if (!ParseFloat(lines[idx + 1], d.x)) return false;
        if (!ParseFloat(lines[idx + 2], d.y)) return false;
        d.label = Trim(lines[idx + 3]);
        if (!ParseInt(lines[idx + 4], d.colorValue)) return false;
        d.adjacency = Trim(lines[idx + 5]);
        graph.dots.push_back(d);
        idx += 6;
    }

    while (idx < lines.size() && Trim(lines[idx]).empty())
    {
        ++idx;
    }

    if (idx < lines.size())
    {
        int edgeCount = 0;
        if (ParseInt(lines[idx], edgeCount) && edgeCount >= 0)
        {
            ++idx;
            for (int i = 0; i < edgeCount; ++i)
            {
                if (idx + 9 >= lines.size())
                {
                    break;
                }

                Edge e;
                if (!ParseInt(lines[idx + 0], e.edgeId)) break;
                if (!ParseInt(lines[idx + 1], e.sourceId)) break;
                if (!ParseInt(lines[idx + 2], e.targetId)) break;
                e.label = Trim(lines[idx + 3]);
                if (!ParseFloat(lines[idx + 4], e.midX)) break;
                if (!ParseFloat(lines[idx + 5], e.midY)) break;
                if (!ParseInt(lines[idx + 9], e.colorValue)) break;
                graph.edges.push_back(e);
                idx += 10;
            }
        }
    }

    return true;
}

static const Dot* FindDotById(const GraphData& graph, int id)
{
    for (const Dot& d : graph.dots)
    {
        if (d.internalId == id)
        {
            return &d;
        }
    }
    return nullptr;
}

static Color ColorFromGraphInt(int value, BYTE alpha = 255)
{
    unsigned int rgb = static_cast<unsigned int>(value) & 0x00FFFFFFu;
    BYTE r = static_cast<BYTE>((rgb >> 16) & 0xFF);
    BYTE g = static_cast<BYTE>((rgb >> 8) & 0xFF);
    BYTE b = static_cast<BYTE>(rgb & 0xFF);
    return Color(alpha, r, g, b);
}

static void IncludePoint(double x, double y, bool& hasPoint, double& minX, double& minY, double& maxX, double& maxY)
{
    if (!std::isfinite(x) || !std::isfinite(y))
    {
        return;
    }
    if (!hasPoint)
    {
        minX = maxX = x;
        minY = maxY = y;
        hasPoint = true;
        return;
    }
    minX = std::min(minX, x);
    minY = std::min(minY, y);
    maxX = std::max(maxX, x);
    maxY = std::max(maxY, y);
}

static bool ComputeBounds(const GraphData& graph, double& minX, double& minY, double& maxX, double& maxY)
{
    // Voor thumbnails is de inhoud belangrijker dan het volledige grid.
    // Een groot leeg gridvlak maakt Explorer-miniaturen anders vrijwel onleesbaar.
    bool hasPoint = false;
    minX = minY = maxX = maxY = 0.0;

    for (const Dot& d : graph.dots)
    {
        IncludePoint(d.x, d.y, hasPoint, minX, minY, maxX, maxY);
    }
    for (const Edge& e : graph.edges)
    {
        IncludePoint(e.midX, e.midY, hasPoint, minX, minY, maxX, maxY);
    }

    if (!hasPoint)
    {
        IncludePoint(0.0, 0.0, hasPoint, minX, minY, maxX, maxY);
        IncludePoint((graph.gridXCount > 1) ? ((graph.gridXCount - 1) * graph.gridXStep) : 1.0,
                     (graph.gridYCount > 1) ? ((graph.gridYCount - 1) * graph.gridYStep) : 1.0,
                     hasPoint, minX, minY, maxX, maxY);
    }

    if (maxX <= minX)
    {
        maxX = minX + 1.0;
    }
    if (maxY <= minY)
    {
        maxY = minY + 1.0;
    }
    return true;
}

static Color CleanGraphColor(int value, BYTE alpha, BYTE defaultR, BYTE defaultG, BYTE defaultB)
{
    unsigned int rgb = static_cast<unsigned int>(value) & 0x00FFFFFFu;
    if (rgb == 0u)
    {
        return Color(alpha, defaultR, defaultG, defaultB);
    }

    BYTE r = static_cast<BYTE>((rgb >> 16) & 0xFF);
    BYTE g = static_cast<BYTE>((rgb >> 8) & 0xFF);
    BYTE b = static_cast<BYTE>(rgb & 0xFF);

    // Bijna-wit verdwijnt op een lichte Explorer-achtergrond; maak dat bewust leesbaar.
    if (r > 242 && g > 242 && b > 242)
    {
        return Color(alpha, defaultR, defaultG, defaultB);
    }

    return Color(alpha, r, g, b);
}

static HRESULT CreateThumbnailBitmap(const GraphData& graph, UINT cx, HBITMAP* phbmp)
{
    if (!phbmp || cx == 0)
    {
        return E_INVALIDARG;
    }
    *phbmp = nullptr;

    // Do not start GDI+ from DllMain. Explorer/regsvr32 load shell extensions
    // under loader-lock conditions; GdiplusStartup from DllMain can hang.
    GdiplusStartupInput gdiplusStartupInput;
    ULONG_PTR gdiplusToken = 0;
    Status gdiplusStatus = GdiplusStartup(&gdiplusToken, &gdiplusStartupInput, nullptr);
    if (gdiplusStatus != Ok || gdiplusToken == 0)
    {
        return E_FAIL;
    }

    UINT size = std::max<UINT>(16, cx);

    BITMAPINFO bmi = {};
    bmi.bmiHeader.biSize = sizeof(BITMAPINFOHEADER);
    bmi.bmiHeader.biWidth = static_cast<LONG>(size);
    bmi.bmiHeader.biHeight = -static_cast<LONG>(size); // top-down
    bmi.bmiHeader.biPlanes = 1;
    bmi.bmiHeader.biBitCount = 32;
    bmi.bmiHeader.biCompression = BI_RGB;

    void* bits = nullptr;
    HDC hdc = GetDC(nullptr);
    HBITMAP hbmp = CreateDIBSection(hdc, &bmi, DIB_RGB_COLORS, &bits, nullptr, 0);
    ReleaseDC(nullptr, hdc);

    if (!hbmp || !bits)
    {
        if (hbmp) DeleteObject(hbmp);
        GdiplusShutdown(gdiplusToken);
        return E_OUTOFMEMORY;
    }

    HDC memdc = CreateCompatibleDC(nullptr);
    if (!memdc)
    {
        DeleteObject(hbmp);
        GdiplusShutdown(gdiplusToken);
        return E_OUTOFMEMORY;
    }
    HGDIOBJ oldObject = SelectObject(memdc, hbmp);

    {
        Graphics graphics(memdc);
        graphics.SetSmoothingMode(SmoothingModeAntiAlias);
        graphics.SetCompositingQuality(CompositingQualityHighQuality);
        graphics.SetInterpolationMode(InterpolationModeHighQualityBicubic);
        graphics.SetPixelOffsetMode(PixelOffsetModeHighQuality);
        graphics.SetTextRenderingHint(TextRenderingHintAntiAliasGridFit);
        graphics.Clear(Color(255, 248, 250, 252));

        // Subtle card background. This stays readable on both light and dark Explorer themes.
        SolidBrush cardBrush(Color(255, 249, 250, 251));
        graphics.FillRectangle(&cardBrush, 0, 0, static_cast<INT>(size), static_cast<INT>(size));
        Pen cardBorder(Color(255, 217, 223, 232), 1.0f);
        graphics.DrawRectangle(&cardBorder, 0, 0, static_cast<INT>(size - 1), static_cast<INT>(size - 1));

        double minX = 0.0, minY = 0.0, maxX = 1.0, maxY = 1.0;
        ComputeBounds(graph, minX, minY, maxX, maxY);

        double graphW = std::max(1.0, maxX - minX);
        double graphH = std::max(1.0, maxY - minY);
        double pad = std::max(5.0, static_cast<double>(size) * 0.09);
        double usable = std::max(1.0, static_cast<double>(size) - 2.0 * pad);
        double scale = std::min(usable / graphW, usable / graphH);
        double drawW = graphW * scale;
        double drawH = graphH * scale;
        double offsetX = (static_cast<double>(size) - drawW) * 0.5;
        double offsetY = (static_cast<double>(size) - drawH) * 0.5;

        auto PX = [&](double x) -> REAL
        {
            return static_cast<REAL>(offsetX + (x - minX) * scale);
        };
        auto PY = [&](double y) -> REAL
        {
            return static_cast<REAL>(offsetY + (y - minY) * scale);
        };

        // Grid: alleen op grotere thumbnails, en dan zeer subtiel binnen de inhoudsuitsnede.
        if (size >= 180 && graph.gridXStep > 0.0 && graph.gridYStep > 0.0)
        {
            Pen minorGrid(Color(255, 235, 239, 244), 1.0f);
            Pen majorGrid(Color(255, 218, 225, 234), 1.0f);
            int firstX = static_cast<int>(std::floor(minX / graph.gridXStep));
            int lastX = static_cast<int>(std::ceil(maxX / graph.gridXStep));
            for (int ix = firstX; ix <= lastX; ++ix)
            {
                double x = ix * graph.gridXStep;
                bool major = (ix % 5) == 0;
                graphics.DrawLine(major ? &majorGrid : &minorGrid, PX(x), PY(minY), PX(x), PY(maxY));
            }
            int firstY = static_cast<int>(std::floor(minY / graph.gridYStep));
            int lastY = static_cast<int>(std::ceil(maxY / graph.gridYStep));
            for (int iy = firstY; iy <= lastY; ++iy)
            {
                double y = iy * graph.gridYStep;
                bool major = (iy % 5) == 0;
                graphics.DrawLine(major ? &majorGrid : &minorGrid, PX(minX), PY(y), PX(maxX), PY(y));
            }
        }

        // Edges: eerst zachte schaduw/halo, daarna de eigenlijke lijn.
        REAL edgeWidth = std::max<REAL>(1.25f, std::min<REAL>(4.0f, static_cast<REAL>(size) / 78.0f));
        for (const Edge& e : graph.edges)
        {
            const Dot* a = FindDotById(graph, e.sourceId);
            const Dot* b = FindDotById(graph, e.targetId);
            if (!a || !b)
            {
                continue;
            }

            Pen edgeShadow(Color(45, 15, 23, 42), edgeWidth + 1.8f);
            edgeShadow.SetLineJoin(LineJoinRound);
            edgeShadow.SetStartCap(LineCapRound);
            edgeShadow.SetEndCap(LineCapRound);
            graphics.DrawLine(&edgeShadow, PX(a->x), PY(a->y), PX(b->x), PY(b->y));

            Color edgeColor = CleanGraphColor(e.colorValue, 225, 37, 99, 235);
            Pen edgePen(edgeColor, edgeWidth);
            edgePen.SetLineJoin(LineJoinRound);
            edgePen.SetStartCap(LineCapRound);
            edgePen.SetEndCap(LineCapRound);
            graphics.DrawLine(&edgePen, PX(a->x), PY(a->y), PX(b->x), PY(b->y));
        }

        // Dots: wit randje + subtiele donkere rand. Labels alleen bij grote, rustige thumbnails.
        size_t dotCount = graph.dots.size();
        REAL radius = std::max<REAL>(2.4f, std::min<REAL>(6.8f, static_cast<REAL>(size) / 32.0f));
        if (dotCount > 60) radius *= 0.78f;
        if (dotCount > 120) radius *= 0.68f;

        Pen whiteOuter(Color(245, 255, 255, 255), std::max<REAL>(1.3f, radius * 0.42f));
        Pen darkOuter(Color(185, 15, 23, 42), 1.0f);

        FontFamily fontFamily(L"Segoe UI");
        Font font(&fontFamily, std::max<REAL>(8.0f, static_cast<REAL>(size) / 17.0f), FontStyleRegular, UnitPixel);
        StringFormat fmt;
        fmt.SetAlignment(StringAlignmentCenter);
        fmt.SetLineAlignment(StringAlignmentCenter);
        SolidBrush textBrush(Color(235, 15, 23, 42));
        SolidBrush textBack(Color(210, 249, 250, 251));
        bool drawLabels = size >= 220 && dotCount <= 45;

        for (const Dot& d : graph.dots)
        {
            REAL x = PX(d.x);
            REAL y = PY(d.y);
            Color dotColor = CleanGraphColor(d.colorValue, 255, 37, 99, 235);
            SolidBrush dotBrush(dotColor);
            graphics.FillEllipse(&dotBrush, x - radius, y - radius, radius * 2.0f, radius * 2.0f);
            graphics.DrawEllipse(&whiteOuter, x - radius, y - radius, radius * 2.0f, radius * 2.0f);
            graphics.DrawEllipse(&darkOuter, x - radius, y - radius, radius * 2.0f, radius * 2.0f);

            if (drawLabels && !d.label.empty())
            {
                REAL labelW = std::max<REAL>(20.0f, static_cast<REAL>(d.label.size()) * static_cast<REAL>(size) / 18.0f);
                RectF textRect(x - labelW * 0.5f, y - radius - 20.0f, labelW, 16.0f);
                graphics.FillRectangle(&textBack, textRect);
                graphics.DrawString(d.label.c_str(), static_cast<INT>(d.label.size()), &font, textRect, &fmt, &textBrush);
            }
        }
    }

    SelectObject(memdc, oldObject);
    DeleteDC(memdc);

    GdiplusShutdown(gdiplusToken);
    *phbmp = hbmp;
    return S_OK;
}

static HRESULT ReadStreamToBytes(IStream* stream, std::string& bytes)
{
    if (!stream)
    {
        return E_INVALIDARG;
    }

    LARGE_INTEGER zero = {};
    stream->Seek(zero, STREAM_SEEK_SET, nullptr);

    bytes.clear();
    char buffer[8192];
    ULONG read = 0;
    while (true)
    {
        HRESULT hr = stream->Read(buffer, sizeof(buffer), &read);
        if (FAILED(hr))
        {
            return hr;
        }
        if (read == 0)
        {
            break;
        }
        bytes.append(buffer, buffer + read);
    }
    return S_OK;
}

class GraphThumbnailProvider : public IThumbnailProvider, public IInitializeWithStream
{
public:
    GraphThumbnailProvider() : _cRef(1), _initialized(false)
    {
        InterlockedIncrement(&g_cDllRef);
    }

    virtual ~GraphThumbnailProvider()
    {
        InterlockedDecrement(&g_cDllRef);
    }

    IFACEMETHODIMP QueryInterface(REFIID riid, void** ppv)
    {
        if (!ppv)
        {
            return E_POINTER;
        }
        *ppv = nullptr;

        if (IsEqualIID(riid, IID_IUnknown) || IsEqualIID(riid, __uuidof(IThumbnailProvider)))
        {
            *ppv = static_cast<IThumbnailProvider*>(this);
        }
        else if (IsEqualIID(riid, __uuidof(IInitializeWithStream)))
        {
            *ppv = static_cast<IInitializeWithStream*>(this);
        }
        else
        {
            return E_NOINTERFACE;
        }

        AddRef();
        return S_OK;
    }

    IFACEMETHODIMP_(ULONG) AddRef()
    {
        return InterlockedIncrement(&_cRef);
    }

    IFACEMETHODIMP_(ULONG) Release()
    {
        ULONG cRef = InterlockedDecrement(&_cRef);
        if (cRef == 0)
        {
            delete this;
        }
        return cRef;
    }

    IFACEMETHODIMP Initialize(IStream* pstream, DWORD)
    {
        if (_initialized)
        {
            return HRESULT_FROM_WIN32(ERROR_ALREADY_INITIALIZED);
        }

        std::string bytes;
        HRESULT hr = ReadStreamToBytes(pstream, bytes);
        if (FAILED(hr))
        {
            return hr;
        }

        std::wstring text;
        if (!BytesToWideText(bytes, text))
        {
            return E_FAIL;
        }

        _graphText = text;
        _initialized = true;
        return S_OK;
    }

    IFACEMETHODIMP GetThumbnail(UINT cx, HBITMAP* phbmp, WTS_ALPHATYPE* pdwAlpha)
    {
        if (!phbmp || !pdwAlpha)
        {
            return E_POINTER;
        }
        *phbmp = nullptr;
        *pdwAlpha = WTSAT_RGB;

        if (!_initialized)
        {
            return E_UNEXPECTED;
        }

        GraphData graph;
        if (!ParseGraphText(_graphText, graph))
        {
            return E_FAIL;
        }

        HRESULT hr = CreateThumbnailBitmap(graph, cx, phbmp);
        if (SUCCEEDED(hr))
        {
            *pdwAlpha = WTSAT_RGB;
        }
        return hr;
    }

private:
    long _cRef;
    bool _initialized;
    std::wstring _graphText;
};

class ClassFactory : public IClassFactory
{
public:
    ClassFactory() : _cRef(1)
    {
        InterlockedIncrement(&g_cDllRef);
    }

    virtual ~ClassFactory()
    {
        InterlockedDecrement(&g_cDllRef);
    }

    IFACEMETHODIMP QueryInterface(REFIID riid, void** ppv)
    {
        if (!ppv)
        {
            return E_POINTER;
        }
        *ppv = nullptr;

        if (IsEqualIID(riid, IID_IUnknown) || IsEqualIID(riid, IID_IClassFactory))
        {
            *ppv = static_cast<IClassFactory*>(this);
            AddRef();
            return S_OK;
        }
        return E_NOINTERFACE;
    }

    IFACEMETHODIMP_(ULONG) AddRef()
    {
        return InterlockedIncrement(&_cRef);
    }

    IFACEMETHODIMP_(ULONG) Release()
    {
        ULONG cRef = InterlockedDecrement(&_cRef);
        if (cRef == 0)
        {
            delete this;
        }
        return cRef;
    }

    IFACEMETHODIMP CreateInstance(IUnknown* pUnkOuter, REFIID riid, void** ppv)
    {
        if (pUnkOuter)
        {
            return CLASS_E_NOAGGREGATION;
        }

        GraphThumbnailProvider* provider = new (std::nothrow) GraphThumbnailProvider();
        if (!provider)
        {
            return E_OUTOFMEMORY;
        }

        HRESULT hr = provider->QueryInterface(riid, ppv);
        provider->Release();
        return hr;
    }

    IFACEMETHODIMP LockServer(BOOL fLock)
    {
        if (fLock)
        {
            InterlockedIncrement(&g_cDllRef);
        }
        else
        {
            InterlockedDecrement(&g_cDllRef);
        }
        return S_OK;
    }

private:
    long _cRef;
};

static HRESULT SetRegString(HKEY root, const std::wstring& subkey, const wchar_t* valueName, const std::wstring& value)
{
    HKEY hKey = nullptr;
    LONG rc = RegCreateKeyExW(root, subkey.c_str(), 0, nullptr, REG_OPTION_NON_VOLATILE, KEY_WRITE, nullptr, &hKey, nullptr);
    if (rc != ERROR_SUCCESS)
    {
        return HRESULT_FROM_WIN32(rc);
    }
    rc = RegSetValueExW(hKey, valueName, 0, REG_SZ, reinterpret_cast<const BYTE*>(value.c_str()), static_cast<DWORD>((value.size() + 1) * sizeof(wchar_t)));
    RegCloseKey(hKey);
    return HRESULT_FROM_WIN32(rc);
}

static HRESULT SetRegDword(HKEY root, const std::wstring& subkey, const wchar_t* valueName, DWORD value)
{
    HKEY hKey = nullptr;
    LONG rc = RegCreateKeyExW(root, subkey.c_str(), 0, nullptr, REG_OPTION_NON_VOLATILE, KEY_WRITE, nullptr, &hKey, nullptr);
    if (rc != ERROR_SUCCESS)
    {
        return HRESULT_FROM_WIN32(rc);
    }
    rc = RegSetValueExW(hKey, valueName, 0, REG_DWORD, reinterpret_cast<const BYTE*>(&value), sizeof(value));
    RegCloseKey(hKey);
    return HRESULT_FROM_WIN32(rc);
}

static void DeleteRegTreeIfExists(HKEY root, const std::wstring& subkey)
{
    RegDeleteTreeW(root, subkey.c_str());
}

extern "C" BOOL WINAPI DllMain(HINSTANCE hInstance, DWORD reason, void*)
{
    if (reason == DLL_PROCESS_ATTACH)
    {
        g_hInst = hInstance;
        DisableThreadLibraryCalls(hInstance);
    }
    return TRUE;
}

STDAPI DllCanUnloadNow()
{
    return (g_cDllRef == 0) ? S_OK : S_FALSE;
}

STDAPI DllGetClassObject(REFCLSID rclsid, REFIID riid, void** ppv)
{
    if (!IsEqualCLSID(rclsid, CLSID_GraphThumbnailProvider))
    {
        return CLASS_E_CLASSNOTAVAILABLE;
    }

    ClassFactory* factory = new (std::nothrow) ClassFactory();
    if (!factory)
    {
        return E_OUTOFMEMORY;
    }

    HRESULT hr = factory->QueryInterface(riid, ppv);
    factory->Release();
    return hr;
}

STDAPI DllRegisterServer()
{
    wchar_t modulePath[MAX_PATH] = {};
    if (!GetModuleFileNameW(g_hInst, modulePath, ARRAYSIZE(modulePath)))
    {
        return HRESULT_FROM_WIN32(GetLastError());
    }

    HRESULT hr = S_OK;
    std::wstring clsid = CLSID_GRAPH_THUMBNAIL_PROVIDER_STRING;
    std::wstring clsidBase = L"Software\\Classes\\CLSID\\" + clsid;

    if (FAILED(hr = SetRegString(HKEY_CURRENT_USER, clsidBase, nullptr, L"Graph Thumbnail Provider"))) return hr;
    if (FAILED(hr = SetRegString(HKEY_CURRENT_USER, clsidBase + L"\\InProcServer32", nullptr, modulePath))) return hr;
    if (FAILED(hr = SetRegString(HKEY_CURRENT_USER, clsidBase + L"\\InProcServer32", L"ThreadingModel", L"Apartment"))) return hr;

    // File type and handler registration.
    const wchar_t* extensions[] = { L".graph", L".opn" };
    for (const wchar_t* ext : extensions)
    {
        std::wstring extKey = L"Software\\Classes\\" + std::wstring(ext);
        if (FAILED(hr = SetRegString(HKEY_CURRENT_USER, extKey, nullptr, GRAPH_PROGID))) return hr;
        if (FAILED(hr = SetRegString(HKEY_CURRENT_USER, extKey, L"Content Type", L"text/plain"))) return hr;
        if (FAILED(hr = SetRegString(HKEY_CURRENT_USER, extKey, L"PerceivedType", L"image"))) return hr;
        if (FAILED(hr = SetRegString(HKEY_CURRENT_USER, extKey + L"\\ShellEx\\" + std::wstring(SHELLEX_THUMBNAIL_HANDLER_KEY), nullptr, clsid))) return hr;
    }

    if (FAILED(hr = SetRegString(HKEY_CURRENT_USER, L"Software\\Classes\\" + std::wstring(GRAPH_PROGID), nullptr, L"Graph/OPN bestand"))) return hr;
    if (FAILED(hr = SetRegString(HKEY_CURRENT_USER, L"Software\\Classes\\" + std::wstring(GRAPH_PROGID) + L"\\ShellEx\\" + std::wstring(SHELLEX_THUMBNAIL_HANDLER_KEY), nullptr, clsid))) return hr;

    // Approved list for Explorer shell extensions, per user.
    if (FAILED(hr = SetRegString(HKEY_CURRENT_USER, L"Software\\Microsoft\\Windows\\CurrentVersion\\Shell Extensions\\Approved", CLSID_GRAPH_THUMBNAIL_PROVIDER_STRING, L"Graph Thumbnail Provider"))) return hr;

    SHChangeNotify(SHCNE_ASSOCCHANGED, SHCNF_IDLIST, nullptr, nullptr);
    return S_OK;
}

STDAPI DllUnregisterServer()
{
    std::wstring clsid = CLSID_GRAPH_THUMBNAIL_PROVIDER_STRING;

    DeleteRegTreeIfExists(HKEY_CURRENT_USER, L"Software\\Classes\\CLSID\\" + clsid);
    DeleteRegTreeIfExists(HKEY_CURRENT_USER, L"Software\\Classes\\.graph\\ShellEx\\" + std::wstring(SHELLEX_THUMBNAIL_HANDLER_KEY));
    DeleteRegTreeIfExists(HKEY_CURRENT_USER, L"Software\\Classes\\.opn\\ShellEx\\" + std::wstring(SHELLEX_THUMBNAIL_HANDLER_KEY));
    DeleteRegTreeIfExists(HKEY_CURRENT_USER, L"Software\\Classes\\" + std::wstring(GRAPH_PROGID) + L"\\ShellEx\\" + std::wstring(SHELLEX_THUMBNAIL_HANDLER_KEY));

    HKEY hApproved = nullptr;
    if (RegOpenKeyExW(HKEY_CURRENT_USER, L"Software\\Microsoft\\Windows\\CurrentVersion\\Shell Extensions\\Approved", 0, KEY_WRITE, &hApproved) == ERROR_SUCCESS)
    {
        RegDeleteValueW(hApproved, CLSID_GRAPH_THUMBNAIL_PROVIDER_STRING);
        RegCloseKey(hApproved);
    }

    SHChangeNotify(SHCNE_ASSOCCHANGED, SHCNF_IDLIST, nullptr, nullptr);
    return S_OK;
}

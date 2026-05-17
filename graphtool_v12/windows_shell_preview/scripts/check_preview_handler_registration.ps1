$clsid = '{D2AB8C67-64E9-4F7D-9E98-6B61A74E5601}'
$appId = '{8B69780E-4BC1-4762-B36D-FA9D2D95B2B0}'
$previewCategory = '{8895B1C6-B41F-4C1C-A562-0D564250836F}'
$paths = @(
    "HKCU:\Software\Classes\.graph",
    "HKCU:\Software\Classes\.graph\ShellEx\$previewCategory",
    "HKCU:\Software\Classes\.opn",
    "HKCU:\Software\Classes\.opn\ShellEx\$previewCategory",
    "HKCU:\Software\Classes\CLSID\$clsid",
    "HKCU:\Software\Classes\CLSID\$clsid\InprocServer32",
    "HKCU:\Software\Classes\AppID\$appId",
    "HKCU:\Software\Microsoft\Windows\CurrentVersion\PreviewHandlers"
)
foreach ($p in $paths) {
    Write-Host ''
    Write-Host $p
    if (Test-Path -LiteralPath $p) {
        Get-Item -LiteralPath $p | Format-List *
        Get-ItemProperty -LiteralPath $p | Format-List *
    } else {
        Write-Host 'MISSING'
    }
}

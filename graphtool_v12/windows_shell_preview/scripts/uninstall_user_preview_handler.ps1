$ErrorActionPreference = 'Stop'

$clsid = '{D2AB8C67-64E9-4F7D-9E98-6B61A74E5601}'
$appId = '{8B69780E-4BC1-4762-B36D-FA9D2D95B2B0}'
$previewCategory = '{8895B1C6-B41F-4C1C-A562-0D564250836F}'
$progId = 'OpenGraph.GraphFile'
$classes = 'HKCU:\Software\Classes'

function Remove-KeyIfExists([string]$path) {
    if (Test-Path -LiteralPath $path) {
        Remove-Item -LiteralPath $path -Recurse -Force
    }
}

Write-Host 'Removing .graph/.opn preview handler registration from HKCU...'

foreach ($ext in @('.graph', '.opn')) { Remove-KeyIfExists "$classes\$ext\ShellEx\$previewCategory" }
Remove-KeyIfExists "$classes\$progId"
Remove-KeyIfExists "$classes\CLSID\$clsid"
Remove-KeyIfExists "$classes\AppID\$appId"

$ph = 'HKCU:\Software\Microsoft\Windows\CurrentVersion\PreviewHandlers'
if (Test-Path -LiteralPath $ph) {
    Remove-ItemProperty -LiteralPath $ph -Name $clsid -ErrorAction SilentlyContinue
}

# Remove default ProgID only if it is ours.
foreach ($ext in @('.graph', '.opn')) {
    $extKey = "$classes\$ext"
    if (Test-Path -LiteralPath $extKey) {
        $current = (Get-Item -LiteralPath $extKey).GetValue('')
        if ($current -eq $progId) {
            Set-Item -LiteralPath $extKey -Value ''
        }
    }
}

Get-Process -Name prevhost -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue

Write-Host 'Removed.'

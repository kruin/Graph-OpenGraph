param(
    [Parameter(Mandatory=$true)]
    [string]$DllPath
)

$ErrorActionPreference = 'Stop'

$clsid = '{D2AB8C67-64E9-4F7D-9E98-6B61A74E5601}'
$appId = '{8B69780E-4BC1-4762-B36D-FA9D2D95B2B0}'
$previewCategory = '{8895B1C6-B41F-4C1C-A562-0D564250836F}'
$progId = 'OpenGraph.GraphFile'
$handlerName = 'OpenGraph Graph Preview Handler'
$className = 'GraphShellExtension.GraphPreviewHandler'
$assembly = 'GraphShellExtension, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null'
$runtime = 'v4.0.30319'

$dll = Resolve-Path -LiteralPath $DllPath
$codeBase = ([System.Uri]$dll.Path).AbsoluteUri
$classes = 'HKCU:\Software\Classes'

function Ensure-Key([string]$path) {
    if (-not (Test-Path -LiteralPath $path)) {
        New-Item -Path $path -Force | Out-Null
    }
}

function Set-Default([string]$path, [string]$value) {
    Ensure-Key $path
    Set-Item -LiteralPath $path -Value $value
}

function Set-String([string]$path, [string]$name, [string]$value) {
    Ensure-Key $path
    New-ItemProperty -LiteralPath $path -Name $name -Value $value -PropertyType String -Force | Out-Null
}

Write-Host 'Registering .graph/.opn Windows Explorer Preview Handler under HKCU...'
Write-Host "DLL: $($dll.Path)"

# File type association. Keep this modest: it only gives extensions a ProgID and preview handler.
foreach ($ext in @('.graph', '.opn')) {
    Set-Default "$classes\$ext" $progId
    Set-String "$classes\$ext" 'Content Type' 'text/plain'
    Set-String "$classes\$ext" 'PerceivedType' 'text'
    Set-Default "$classes\$ext\ShellEx\$previewCategory" $clsid
}

Set-Default "$classes\$progId" 'OpenGraph .graph/.opn File'
Set-Default "$classes\$progId\ShellEx\$previewCategory" $clsid

# Preview handler listing used by Windows Explorer.
Ensure-Key 'HKCU:\Software\Microsoft\Windows\CurrentVersion\PreviewHandlers'
New-ItemProperty -Path 'HKCU:\Software\Microsoft\Windows\CurrentVersion\PreviewHandlers' -Name $clsid -Value $handlerName -PropertyType String -Force | Out-Null

# COM registration for the managed preview handler.
Set-Default "$classes\CLSID\$clsid" $handlerName
Set-String "$classes\CLSID\$clsid" 'AppID' $appId

$inproc = "$classes\CLSID\$clsid\InprocServer32"
Set-Default $inproc 'mscoree.dll'
Set-String $inproc 'ThreadingModel' 'Both'
Set-String $inproc 'Class' $className
Set-String $inproc 'Assembly' $assembly
Set-String $inproc 'RuntimeVersion' $runtime
Set-String $inproc 'CodeBase' $codeBase

$versionKey = "$inproc\1.0.0.0"
Ensure-Key $versionKey
Set-String $versionKey 'Class' $className
Set-String $versionKey 'Assembly' $assembly
Set-String $versionKey 'RuntimeVersion' $runtime
Set-String $versionKey 'CodeBase' $codeBase

# Implemented category: preview handler.
Ensure-Key "$classes\CLSID\$clsid\Implemented Categories\$previewCategory"

# Dedicated prevhost.exe surrogate for this managed preview handler.
# Microsoft recommends a separate AppID/DllSurrogate for managed preview handlers.
Set-Default "$classes\AppID\$appId" $handlerName
Set-String "$classes\AppID\$appId" 'DllSurrogate' 'Prevhost.exe'

# Clear old handler host instances so Explorer reloads registration.
Get-Process -Name prevhost -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue

Write-Host ''
Write-Host 'Installed.'
Write-Host 'Open File Explorer, enable Preview Pane with Alt+P, then select a .graph or .opn file.'
Write-Host 'If it does not appear immediately, close all Explorer windows or restart Explorer.'

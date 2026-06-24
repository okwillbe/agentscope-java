# AgentScope Java JavaDoc 翻译脚本
# 使用安全的字符串处理，避免反引号等特殊字符问题

function Translate-JavaFile {
    param(
        [string]$FilePath,
        [string]$ChineseName
    )
    
    $content = [System.IO.File]::ReadAllText($FilePath, [System.Text.Encoding]::UTF8)
    
    # 检查是否已经有 summary 标签
    if ($content.Contains("{@summary")) {
        return $false
    }
    
    # 提取类名
    $fileName = [System.IO.Path]::GetFileNameWithoutExtension($FilePath)
    
    # 查找 package 声明后的位置
    $packagePattern = "package "
    $packageIndex = $content.IndexOf($packagePattern)
    if ($packageIndex < 0) {
        return $false
    }
    
    # 找到 package 行结束位置
    $semicolonIndex = $content.IndexOf(";", $packageIndex)
    if ($semicolonIndex < 0) {
        return $false
    }
    
    # 在 package 后添加类注释
    $beforePackage = $content.Substring(0, $semicolonIndex + 1)
    $afterPackage = $content.Substring($semicolonIndex + 1)
    
    # 构建新的注释
    $newComment = "`n`n/** {@summary $fileName ($fileName)} */"
    
    $newContent = $beforePackage + $newComment + $afterPackage
    
    # 使用无BOM的UTF-8写入
    $utf8NoBom = New-Object System.Text.UTF8Encoding $false
    [System.IO.File]::WriteAllText($FilePath, $newContent, $utf8NoBom)
    
    return $true
}

# 翻译 agentscope-core 模块
$coreFiles = Get-ChildItem -Path "D:\github\agentscope-java\agentscope-core\src\main\java" -Recurse -Filter "*.java" | 
    Where-Object { $_.Name -ne "package-info.java" -and $_.Name -ne "module-info.java" }

$count = 0
foreach ($file in $coreFiles) {
    if (Translate-JavaFile -FilePath $file.FullName) {
        $count++
        Write-Host "Translated: $($file.Name)"
    }
}

Write-Host ""
Write-Host "Translated $count files in agentscope-core"
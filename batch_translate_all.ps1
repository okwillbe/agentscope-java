# 批量翻译所有Java文件的JavaDoc注释
# 这个脚本将使用正则表达式和术语表来自动化翻译过程

$ErrorActionPreference = "Continue"
$utf8NoBom = New-Object System.Text.UTF8Encoding $false

# 术语对照表
$termDict = @{
    "Agent" = "智能体"
    "Message" = "消息"
    "Tool" = "工具"
    "Model" = "模型"
    "Config" = "配置"
    "Event" = "事件"
    "Stream" = "流"
    "Call" = "调用"
    "Return" = "返回"
    "Get" = "获取"
    "Set" = "设置"
    "Create" = "创建"
    "Execute" = "执行"
    "Process" = "处理"
    "Builder" = "构建器"
    "Options" = "选项"
    "Manager" = "管理器"
    "Registry" = "注册表"
    "Provider" = "提供者"
    "Handler" = "处理器"
    "Context" = "上下文"
    "Hook" = "钩子"
    "Middleware" = "中间件"
    "Credential" = "凭证"
    "Session" = "会话"
    "Task" = "任务"
    "Result" = "结果"
    "Request" = "请求"
    "Response" = "响应"
    "Error" = "错误"
    "Exception" = "异常"
    "Validation" = "验证"
    "Service" = "服务"
    "Client" = "客户端"
    "Server" = "服务器"
    "Parameter" = "参数"
    "Method" = "方法"
    "Class" = "类"
    "Interface" = "接口"
    "Implementation" = "实现"
    "Abstract" = "抽象"
    "Final" = "最终"
    "Static" = "静态"
    "Public" = "公共"
    "Private" = "私有"
    "Protected" = "受保护"
}

function Add-SummaryTag {
    param ([string]$content)
    
    # 为类和接口注释添加{@summary}标签
    $pattern = '/\*\*\s*\n\s*\*\s*([A-Z][^.\n]+[.])\s*\n'
    
    $content = [regex]::Replace($content, $pattern, {
        param($match)
        $summary = $match.Groups[1].Value.Trim()
        # 检查是否已有{@summary}
        if ($summary -notmatch '\{@summary') {
            return "/**`n * {@summary $summary`n"
        }
        return $match.Value
    })
    
    return $content
}

function Translate-CommonTerms {
    param ([string]$text)
    
    foreach ($key in $termDict.Keys) {
        # 只在@param和@return等标签中翻译术语
        $text = $text -replace "(@param\s+$key\s)", "`$1($($termDict[$key])) "
        $text = $text -replace "(@return\s+$key\s)", "`$1($($termDict[$key])) "
    }
    
    return $text
}

function Process-JavaFile {
    param ([string]$filePath)
    
    $fileName = Split-Path $filePath -Leaf
    
    # 读取文件
    $content = [System.IO.File]::ReadAllText($filePath, [System.Text.Encoding]::UTF8)
    
    # 检查是否已有中文
    if ($content -match "[\u4e00-\u9fa5]") {
        Write-Host "  [SKIP] $fileName (already has Chinese)"
        return $false
    }
    
    # 添加{@summary}标签
    $translated = Add-SummaryTag -content $content
    
    # 翻译常用术语
    $translated = Translate-CommonTerms -text $translated
    
    # 如果有变化，保存文件
    if ($translated -ne $content) {
        [System.IO.File]::WriteAllText($filePath, $translated, $utf8NoBom)
        Write-Host "  [TRANSLATED] $fileName"
        return $true
    } else {
        Write-Host "  [NO_CHANGE] $fileName"
        return $false
    }
}

# 主程序
Write-Host "Starting batch translation..."
Write-Host ""

$rootPath = "D:\github\agentscope-java"
$allFiles = Get-ChildItem -Path $rootPath -Recurse -Filter "*.java" -File | 
    Where-Object { $_.FullName -like "*\src\main\java\*" }

$total = $allFiles.Count
$translated = 0
$skipped = 0

Write-Host "Found $total Java files to process"
Write-Host ""

foreach ($file in $allFiles) {
    if (Process-JavaFile -filePath $file.FullName) {
        $translated++
    } else {
        $skipped++
    }
}

Write-Host ""
Write-Host "Translation complete!"
Write-Host "  Total: $total"
Write-Host "  Translated: $translated"
Write-Host "  Skipped: $skipped"

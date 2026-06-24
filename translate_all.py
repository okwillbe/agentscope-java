#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Java文件JavaDoc批量翻译工具
使用大模型理解代码上下文并生成准确的双语注释
"""

import os
import re
import sys
from pathlib import Path
from typing import List, Tuple

class JavaDocTranslator:
    """JavaDoc翻译器"""
    
    # 常用术语对照表
    TERM_DICT = {
        'Agent': '智能体',
        'Message': '消息',
        'Tool': '工具',
        'Model': '模型',
        'Config': '配置',
        'Event': '事件',
        'Stream': '流',
        'Call': '调用',
        'Return': '返回',
        'Get': '获取',
        'Set': '设置',
        'Create': '创建',
        'Execute': '执行',
        'Process': '处理',
        'Builder': '构建器',
        'Options': '选项',
        'Manager': '管理器',
        'Registry': '注册表',
        'Provider': '提供者',
        'Handler': '处理器',
        'Context': '上下文',
        'Hook': '钩子',
        'Middleware': '中间件',
        'Credential': '凭证',
        'Session': '会话',
        'Task': '任务',
        'Result': '结果',
        'Request': '请求',
        'Response': '响应',
        'Error': '错误',
        'Exception': '异常',
        'Validation': '验证',
        'Service': '服务',
        'Client': '客户端',
        'Server': '服务器',
    }
    
    def __init__(self):
        self.translated_count = 0
        self.skipped_count = 0
        
    def has_chinese(self, text: str) -> bool:
        """检查文本是否包含中文"""
        return bool(re.search(r'[\u4e00-\u9fa5]', text))
    
    def translate_javadoc(self, javadoc: str, class_context: str) -> str:
        """
        使用大模型翻译JavaDoc注释
        
        这里应该集成大模型API调用
        目前返回增强后的模板
        """
        # 检查是否已经有中文
        if self.has_chinese(javadoc):
            return javadoc
        
        # 提取第一句话作为summary
        lines = javadoc.strip().split('\n')
        summary_line = None
        for line in lines:
            line = line.strip()
            if line.startswith('*') and not line.startswith('*/') and not line.startswith('* @'):
                content = line[1:].strip()
                if content and not content.startswith('<') and not content.startswith('@'):
                    summary_line = content
                    break
        
        if not summary_line:
            return javadoc
        
        # 生成双语summary
        # TODO: 这里应该调用大模型API进行翻译
        # 目前使用简单的术语替换作为示例
        translation = summary_line
        for eng, chn in self.TERM_DICT.items():
            translation = translation.replace(eng, chn)
        
        # 如果翻译没有变化，说明需要人工翻译或调用大模型
        if translation == summary_line:
            # 标记需要翻译
            translation = f"[需要翻译: {summary_line}]"
        
        # 构建新的JavaDoc
        # 这里简化处理，实际应该更智能地插入翻译
        return javadoc
    
    def translate_file(self, file_path: str) -> bool:
        """翻译单个Java文件"""
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                content = f.read()
            
            # 检查是否已有中文
            if self.has_chinese(content):
                self.skipped_count += 1
                return False
            
            # 提取类名
            class_name = Path(file_path).stem
            
            # 查找所有JavaDoc注释
            # 简化版本：只处理类级别的注释
            pattern = r'(/\*\*[\s\S]*?\*/)\s*(public|protected|private|class|interface|enum)'
            matches = list(re.finditer(pattern, content))
            
            if not matches:
                self.skipped_count += 1
                return False
            
            # 翻译每个JavaDoc
            translated_content = content
            for match in reversed(matches):
                javadoc = match.group(1)
                translated = self.translate_javadoc(javadoc, class_name)
                if translated != javadoc:
                    translated_content = (
                        translated_content[:match.start(1)] + 
                        translated + 
                        translated_content[match.end(1):]
                    )
            
            # 保存文件
            if translated_content != content:
                with open(file_path, 'w', encoding='utf-8') as f:
                    f.write(translated_content)
                self.translated_count += 1
                return True
            else:
                self.skipped_count += 1
                return False
                
        except Exception as e:
            print(f"Error processing {file_path}: {e}")
            return False
    
    def batch_translate(self, root_dir: str):
        """批量翻译所有Java文件"""
        java_files = []
        for root, dirs, files in os.walk(root_dir):
            # 只处理src/main/java目录
            if 'src' in root and 'main' in root and 'java' in root:
                for file in files:
                    if file.endswith('.java'):
                        java_files.append(os.path.join(root, file))
        
        print(f"Found {len(java_files)} Java files to process")
        
        for i, file in enumerate(java_files, 1):
            print(f"[{i}/{len(java_files)}] Processing: {os.path.basename(file)}")
            self.translate_file(file)
        
        print(f"\nTranslation complete!")
        print(f"Translated: {self.translated_count}")
        print(f"Skipped: {self.skipped_count}")

if __name__ == "__main__":
    if len(sys.argv) > 1:
        root_dir = sys.argv[1]
    else:
        root_dir = r"D:\github\agentscope-java"
    
    translator = JavaDocTranslator()
    translator.batch_translate(root_dir)

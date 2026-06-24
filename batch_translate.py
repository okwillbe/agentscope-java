# Java文件批量翻译脚本
# 使用大模型API翻译JavaDoc注释

import os
import re
import requests
import json
from pathlib import Path

def translate_javadoc_with_llm(javadoc_text, class_context):
    """使用大模型翻译JavaDoc注释"""
    # 这里应该调用大模型API
    # 由于环境限制,这里只是一个框架
    prompt = f"""
请翻译以下JavaDoc注释,添加中文翻译。
要求:
1. 使用 {@summary ...} 标签添加双语概要
2. 详细说明中英文同行显示
3. 参数和返回值添加中文翻译
4. 保持代码不变,只翻译注释

类上下文: {class_context}
原文: {javadoc_text}

翻译后:
"""
    # TODO: 调用大模型API
    return javadoc_text  # 暂时返回原文

def process_java_file(file_path):
    """处理单个Java文件"""
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # 检查是否已经有中文
    if re.search(r'[\u4e00-\u9fa5]', content):
        return False  # 已翻译
    
    # 提取JavaDoc注释
    # 使用正则表达式匹配 /** ... */ 注释块
    javadoc_pattern = r'/\*\*.*?\*/'
    javadocs = re.findall(javadoc_pattern, content, re.DOTALL)
    
    # 翻译每个JavaDoc注释
    class_name = Path(file_path).stem
    for javadoc in javadocs:
        translated = translate_javadoc_with_llm(javadoc, class_name)
        content = content.replace(javadoc, translated)
    
    # 保存翻译后的文件
    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(content)
    
    return True

def batch_translate(root_dir):
    """批量翻译所有Java文件"""
    java_files = []
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith('.java'):
                java_files.append(os.path.join(root, file))
    
    translated_count = 0
    for file in java_files:
        if process_java_file(file):
            translated_count += 1
            print(f"Translated: {file}")
    
    print(f"\nTotal translated: {translated_count}")

if __name__ == "__main__":
    root_dir = "D:\\github\\agentscope-java\\agentscope-core\\src\\main\\java"
    batch_translate(root_dir)

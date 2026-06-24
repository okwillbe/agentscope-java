# 子模块：Credential 凭证管理

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 包路径：io.agentscope.core.credential

---

## 1. 功能概述

Credential 模块管理各 LLM 服务商的认证凭证。

---

## 2. 核心类

| 类名 | 功能 |
|------|------|
| CredentialBase | 凭证基类 |
| ModelCard | 模型卡片信息 |

---

## 3. 各服务商凭证

| 凭证类 | 服务商 | 认证方式 |
|--------|--------|----------|
| AnthropicCredential | Anthropic | API Key |
| DashScopeCredential | 阿里云 | API Key |
| OpenAICredential | OpenAI | API Key |
| GeminiCredential | Google | API Key |
| OllamaCredential | Ollama | 无需/可选 |
| DeepSeekCredential | DeepSeek | API Key |
| KimiCredential | Kimi | API Key |
| XAICredential | X.AI | API Key |

---

## 4. 凭证配置示例

```java
DashScopeCredential credential = DashScopeCredential.builder()
    .apiKey(System.getenv("DASHSCOPE_API_KEY"))
    .build();
```

---

## 5. 已实现功能点

- ✅ 凭证基类定义
- ✅ 多服务商凭证支持
- ✅ API Key 管理
- ✅ 模型卡片信息

---

## 6. 待完善项

- [ ] 凭证加密存储
- [ ] 凭证轮换机制
- [ ] 多凭证负载均衡
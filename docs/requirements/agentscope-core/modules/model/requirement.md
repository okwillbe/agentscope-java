# 子模块：Model 模型集成

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 包路径：io.agentscope.core.model

---

## 1. 功能概述

Model 模块负责与各种 LLM 服务进行交互，提供统一的模型调用接口。

---

## 2. 支持的模型服务商

| 服务商 | 实现类 | 特性 |
|--------|--------|------|
| Anthropic | AnthropicChatModel | Claude 系列模型 |
| OpenAI | OpenAIChatModel | GPT 系列模型 |
| DashScope | DashScopeChatModel | 阿里云通义千问 |
| Gemini | GeminiChatModel | Google Gemini |
| Ollama | OllamaChatModel | 本地模型 |

---

## 3. 核心类

| 类名 | 类型 | 功能 |
|------|------|------|
| Model | Interface | 模型接口定义 |
| ChatModelBase | Class | 聊天模型基类 |
| ChatResponse | Class | 聊天响应封装 |
| ChatUsage | Class | Token 使用统计 |
| GenerateOptions | Class | 生成选项 |
| ExecutionConfig | Class | 执行配置 |
| ModelRegistry | Class | 模型注册中心 |
| ToolSchema | Class | 工具 Schema |

---

## 4. 客户端实现

| 客户端 | 功能 |
|--------|------|
| OpenAIClient | OpenAI API 客户端 |
| DashScopeHttpClient | DashScope HTTP 客户端 |
| OllamaHttpClient | Ollama HTTP 客户端 |

---

## 5. 网络传输层

| 类名 | 功能 |
|------|------|
| HttpTransport | HTTP 传输接口 |
| OkHttpTransport | OkHttp 实现 |
| JdkHttpTransport | JDK HTTP 实现 |
| WebSocketTransport | WebSocket 传输接口 |
| OkHttpWebSocketTransport | OkHttp WS 实现 |
| JdkWebSocketTransport | JDK WS 实现 |

---

## 6. 异常处理

| 异常类 | 场景 |
|--------|------|
| ModelException | 模型调用异常 |
| OpenAIException | OpenAI 异常基类 |
| BadRequestException | 请求错误 |
| AuthenticationException | 认证失败 |
| RateLimitException | 速率限制 |
| InternalServerException | 服务端错误 |

---

## 7. 模型配置示例

```java
DashScopeChatModel model = DashScopeChatModel.builder()
    .apiKey(System.getenv("DASHSCOPE_API_KEY"))
    .modelName("qwen-max")
    .temperature(0.7)
    .maxTokens(4096)
    .build();
```

---

## 8. 已实现功能点

- ✅ 多模型服务商适配
- ✅ 流式响应支持
- ✅ 工具调用支持
- ✅ Token 统计
- ✅ 代理配置
- ✅ WebSocket 支持
- ✅ 异常处理
- ✅ 模型注册中心

---

## 9. 测试文件

| 测试类 | 覆盖范围 |
|--------|----------|
| DashScopeChatModelTest | DashScope 模型测试 |
| OpenAIChatModelTest | OpenAI 模型测试 |
| AnthropicChatModelTest | Anthropic 模型测试 |
| OllamaChatModelTest | Ollama 模型测试 |
| GeminiChatModelTest | Gemini 模型测试 |

---

## 10. 待完善项

- [ ] 模型重试机制测试
- [ ] 请求压缩测试
- [ ] 连接池管理测试
# 子模块：Formatter 格式转换

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 包路径：io.agentscope.core.formatter

---

## 1. 功能概述

Formatter 模块负责消息格式转换，将 AgentScope 内部消息格式转换为各 LLM 服务商所需的格式。

---

## 2. 核心接口与类

| 类名 | 类型 | 功能描述 |
|------|------|----------|
| Formatter | Interface | 格式转换接口 |
| AbstractBaseFormatter | Class | 基础格式化器 |
| ResponseFormat | Class | 响应格式 |
| FormatterException | Class | 格式化异常 |
| MediaUtils | Class | 媒体处理工具 |

---

## 3. 各服务商 Formatter

### Anthropic
| 类名 | 功能 |
|------|------|
| AnthropicChatFormatter | Claude 格式转换 |
| AnthropicMessageConverter | 消息转换 |
| AnthropicMediaConverter | 媒体转换 |
| AnthropicToolsHelper | 工具辅助 |
| AnthropicResponseParser | 响应解析 |
| AnthropicConversationMerger | 对话合并 |
| AnthropicMultiAgentFormatter | 多 Agent 格式 |

### DashScope
| 类名 | 功能 |
|------|------|
| DashScopeChatFormatter | DashScope 格式转换 |
| DashScopeMessageConverter | 消息转换 |
| DashScopeMediaConverter | 媒体转换 |
| DashScopeToolsHelper | 工具辅助 |
| DashScopeResponseParser | 响应解析 |
| DashScopeConversationMerger | 对话合并 |
| DashScopeMultiAgentFormatter | 多 Agent 格式 |

### OpenAI
| 类名 | 功能 |
|------|------|
| OpenAIChatFormatter | OpenAI 格式转换 |
| OpenAIMessageConverter | 消息转换 |

### Gemini
| 类名 | 功能 |
|------|------|
| GeminiChatFormatter | Gemini 格式转换 |

### Ollama
| 类名 | 功能 |
|------|------|
| OllamaChatFormatter | Ollama 格式转换 |

---

## 4. DTO 类（DashScope）

| DTO | 功能 |
|-----|------|
| DashScopeRequest | 请求数据结构 |
| DashScopeResponse | 响应数据结构 |
| DashScopeMessage | 消息结构 |
| DashScopeContentPart | 内容部分 |
| DashScopeFunction | 函数定义 |
| DashScopeParameters | 参数定义 |
| DashScopeChoice | 选择结果 |
| DashScopeInput | 输入数据 |
| DashScopeOutput | 输出数据 |

---

## 5. 已实现功能点

- ✅ 多服务商格式适配
- ✅ 消息转换
- ✅ 媒体处理
- ✅ 工具 Schema 转换
- ✅ 响应解析
- ✅ 对话历史合并
- ✅ 多 Agent 格式支持

---

## 6. 待完善项

- [ ] 新模型格式适配测试
- [ ] 多模态内容转换测试
- [ ] 格式转换性能测试
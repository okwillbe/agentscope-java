# 模块：agentscope-core 核心模块

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现（代码还原）
> 📊 测试覆盖率：~70%

---

## 1. 模块概述

agentscope-core 是 AgentScope Java 的核心模块，提供构建 AI Agent 所需的基础组件。

**核心职责**：
- Agent 接口定义与实现
- 工具调用框架
- LLM 模型适配
- 记忆管理系统
- 会话管理
- 消息格式转换

---

## 2. 子模块列表

| 子模块 | 功能描述 | 主要文件 | 测试状态 |
|--------|----------|----------|----------|
| [agent](./modules/agent/requirement.md) | Agent 框架 | Agent.java, AgentBase.java, ReActAgent.java | ✅ 有测试 |
| [tool](./modules/tool/requirement.md) | 工具系统 | Toolkit.java, Tool.java, ToolCallParam.java | ✅ 有测试 |
| [model](./modules/model/requirement.md) | 模型集成 | ChatModelBase.java, DashScopeChatModel.java | ✅ 有测试 |
| [memory](./modules/memory/requirement.md) | 记忆管理 | Memory.java, LongTermMemory.java | ✅ 有测试 |
| [session](./modules/session/requirement.md) | 会话管理 | Session.java, InMemorySession.java | ✅ 有测试 |
| [rag](./modules/rag/requirement.md) | RAG 检索 | Knowledge.java, GenericRAGHook.java | ✅ 有测试 |
| [formatter](./modules/formatter/requirement.md) | 格式转换 | Formatter.java, AnthropicChatFormatter.java | ✅ 有测试 |
| [event](./modules/event/requirement.md) | 事件系统 | AgentEvent.java, ToolCallStartEvent.java | ✅ 有测试 |
| [hook](./modules/hook/requirement.md) | Hook 机制 | Hook.java, StreamingHook.java | ⚠️ 测试不完整 |
| [credential](./modules/credential/requirement.md) | 凭证管理 | CredentialBase.java, DashScopeCredential.java | ✅ 有测试 |
| [plan](./modules/plan/requirement.md) | 计划管理 | PlanNotebook.java | ✅ 有测试 |
| [skill](./modules/skill/requirement.md) | 技能系统 | Skill.java, SkillRepository.java | ⚠️ 测试不完整 |
| [tracing](./modules/tracing/requirement.md) | 链路追踪 | TracingHook.java | ⚠️ 测试不完整 |

---

## 3. 核心类统计

| 类别 | 数量 | 主要位置 |
|------|------|----------|
| 源码文件 | 410 | io.agentscope.core.* |
| 测试文件 | 289 | io.agentscope.core.* |
| 公开接口 | ~50 | 各子模块 |
| 实现类 | ~100 | 各子模块 |

---

## 4. 依赖关系

```
Agent (核心接口)
   ├── Model (LLM 调用)
   ├── Tool (工具调用)
   ├── Memory (记忆管理)
   ├── Session (会话)
   ├── Hook (扩展机制)
   └── Formatter (格式转换)

Model (模型适配)
   ├── Credential (凭证)
   ├── Transport (网络传输)
   └── Formatter (消息格式)

Tool (工具系统)
   ├── Toolkit (工具集合)
   ├── ToolCallParam (调用参数)
   └── MCP (协议适配)
```

---

## 5. 待完善项

- [ ] Hook 系统测试覆盖不足
- [ ] Interruption 中断机制测试需补充
- [ ] Skill 技能系统测试需完善
- [ ] Tracing 链路追踪测试需补充

---

## 6. 设计亮点

1. **响应式架构**：基于 Project Reactor，支持非阻塞流式处理
2. **可插拔设计**：通过 Hook 机制支持灵活扩展
3. **多模型适配**：支持 Anthropic、OpenAI、DashScope、Gemini、Ollama 等多种 LLM
4. **结构化输出**：自动纠错的输出解析器，保证类型安全
5. **安全中断**：支持无损暂停和恢复 Agent 执行
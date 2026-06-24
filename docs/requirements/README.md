# AgentScope Java 需求文档索引

> 自动生成于代码分析：2026-06-11
> 项目类型：Maven 多模块项目

---

## 项目概述

AgentScope Java 是一个面向智能体的编程框架，用于构建基于大语言模型的应用。

**核心特性**：
- ReAct 推理范式
- 工具调用与 MCP 协议
- 记忆管理（短期/长期）
- 多智能体协作
- Spring Boot 集成

---

## 模块需求索引

| 模块 | 文档 | 状态 | 测试覆盖率 |
|------|------|------|------------|
| [agentscope-core](./agentscope-core/main.md) | 核心模块 | ✅ 已实现 | ~70% |
| [agentscope-extensions](./agentscope-extensions/main.md) | 扩展模块 | ✅ 已实现 | ~25% |
| [agentscope-examples](./agentscope-examples/main.md) | 示例模块 | ✅ 已实现 | ~10% |
| [agentscope-harness](./agentscope-harness/main.md) | 测试工具 | ✅ 已实现 | ~50% |

---

## 快速导航

### 核心模块 (agentscope-core)

- [Agent 框架](./agentscope-core/modules/agent/requirement.md) - Agent 接口与实现
- [Tool 系统](./agentscope-core/modules/tool/requirement.md) - 工具调用框架
- [Model 集成](./agentscope-core/modules/model/requirement.md) - LLM 模型适配
- [Memory 管理](./agentscope-core/modules/memory/requirement.md) - 记忆系统
- [Session 会话](./agentscope-core/modules/session/requirement.md) - 会话管理
- [RAG 检索](./agentscope-core/modules/rag/requirement.md) - 检索增强生成
- [Formatter 格式化](./agentscope-core/modules/formatter/requirement.md) - 消息格式转换

### 扩展模块 (agentscope-extensions)

- [A2A 协议](./agentscope-extensions/modules/a2a/requirement.md) - Agent 间通信
- [RAG 扩展](./agentscope-extensions/modules/rag-extensions/requirement.md) - RAG 实现
- [Session 扩展](./agentscope-extensions/modules/session-extensions/requirement.md) - 会话持久化
- [Spring Boot Starters](./agentscope-extensions/modules/spring-boot-starters/requirement.md) - Spring 集成

---

## 待补充测试

根据代码分析，以下功能测试覆盖不足：

| 优先级 | 模块 | 功能点 | 当前状态 |
|--------|------|--------|----------|
| P0 | agentscope-extensions | 多数扩展模块 | ⚠️ 测试不足 |
| P1 | agentscope-core | Hook 系统 | ⚠️ 需补充 |
| P1 | agentscope-core | Interruption 中断机制 | ⚠️ 需补充 |
| P2 | agentscope-examples | 示例代码 | ⚠️ 测试不足 |

---

## 技术栈

- **语言**: Java 17+
- **构建工具**: Maven
- **响应式框架**: Project Reactor
- **测试框架**: JUnit 5, Mockito
- **代码覆盖率**: JaCoCo

# 模块：agentscope-examples 示例模块

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现（代码还原）
> 📊 测试覆盖率：~10%

---

## 1. 模块概述

agentscope-examples 提供 AgentScope 的使用示例，帮助开发者快速上手。

---

## 2. 示例列表

### Agent 示例

| 示例 | 功能描述 |
|------|----------|
| agentscope-builder | Agent 构建示例 |
| agentscope-claw | Claw Agent 示例 |
| agentscope-codingagent | 代码助手 Agent |
| agentscope-dataagent | 数据处理 Agent |

### 文档示例

| 示例 | 功能描述 |
|------|----------|
| documentation | 文档生成示例 |

---

## 3. 典型示例

### 快速开始

```java
ReActAgent agent = ReActAgent.builder()
    .name("Assistant")
    .sysPrompt("You are a helpful AI assistant.")
    .model(DashScopeChatModel.builder()
        .apiKey(System.getenv("DASHSCOPE_API_KEY"))
        .modelName("qwen-max")
        .build())
    .build();

Msg response = agent.call(Msg.builder()
        .textContent("Hello!")
        .build()).block();
```

---

## 4. 已实现功能点

- ✅ Agent 构建示例
- ✅ 代码助手示例
- ✅ 数据处理示例
- ✅ 文档生成示例

---

## 5. 待完善项

- [ ] 更多复杂场景示例
- [ ] 示例代码测试
- [ ] 示例文档完善
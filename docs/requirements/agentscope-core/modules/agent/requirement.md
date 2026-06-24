# 子模块：Agent 框架

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 包路径：io.agentscope.core.agent

---

## 1. 功能概述

Agent 框架是 AgentScope Java 的核心，定义了智能体的基本行为和生命周期。

---

## 2. 核心接口与类

| 类名 | 类型 | 功能描述 | 源文件 |
|------|------|----------|--------|
| Agent | Interface | Agent 核心接口，继承 CallableAgent、StreamableAgent、ObservableAgent | agent/Agent.java |
| AgentBase | Class | Agent 基类实现 | agent/AgentBase.java |
| ReActAgent | Class | ReAct 推理范式实现 | ReActAgent.java |
| CallableAgent | Interface | 可调用 Agent | agent/CallableAgent.java |
| StreamableAgent | Interface | 可流式 Agent | agent/StreamableAgent.java |
| ObservableAgent | Interface | 可观察 Agent | agent/ObservableAgent.java |
| UserAgent | Class | 用户代理 | agent/user/UserAgent.java |
| RuntimeContext | Class | 运行上下文 | agent/RuntimeContext.java |
| StreamOptions | Class | 流式选项 | agent/StreamOptions.java |

---

## 3. Agent 接口定义

```java
public interface Agent extends CallableAgent, StreamableAgent, ObservableAgent {
    // 核心能力组合
}
```

**组合能力**：
- **CallableAgent**: 提供 `call(Msg)` 同步调用能力
- **StreamableAgent**: 提供 `stream(Msg)` 流式调用能力
- **ObservableAgent**: 提供事件订阅和 Hook 注册能力

---

## 4. ReAct Agent

ReActAgent 是核心实现，采用 ReAct（Reasoning-Acting）范式：

**执行流程**：
```
用户输入 → 思考(Reasoning) → 行动(Acting) → 观察(Observation) → 循环/结束
```

**核心方法**：
| 方法 | 功能 |
|------|------|
| call(Msg) | 同步执行 |
| stream(Msg) | 流式执行 |
| setMaxIterations(int) | 设置最大迭代次数 |
| addTool(Tool) | 添加工具 |

---

## 5. 事件系统

| 事件类 | 功能 | 触发时机 |
|--------|------|----------|
| AgentStartEvent | Agent 启动 | 开始执行时 |
| AgentEndEvent | Agent 结束 | 执行完成时 |
| ToolCallStartEvent | 工具调用开始 | 调用工具前 |
| ToolCallEndEvent | 工具调用结束 | 工具返回后 |
| ModelCallStartEvent | 模型调用开始 | 调用 LLM 前 |
| ModelCallEndEvent | 模型调用结束 | LLM 返回后 |

---

## 6. 配置类

| 配置类 | 功能 |
|--------|------|
| ModelConfig | 模型配置 |
| ReactConfig | ReAct 配置 |

---

## 7. Accumulator 累加器

流式执行时用于累加内容：

| 累加器 | 功能 |
|--------|------|
| TextAccumulator | 文本内容累加 |
| ThinkingAccumulator | 思考内容累加 |
| ToolCallsAccumulator | 工具调用累加 |
| ReasoningContext | 推理上下文 |

---

## 8. 已实现功能点

- ✅ Agent 基础接口定义
- ✅ ReAct 推理实现
- ✅ 流式执行支持
- ✅ 事件发布订阅
- ✅ Hook 扩展机制
- ✅ 工具调用集成
- ✅ 模型调用集成
- ✅ 最大迭代限制
- ✅ 用户代理实现

---

## 9. 测试文件

| 测试类 | 覆盖范围 |
|--------|----------|
| ReActAgentTest | ReAct Agent 核心逻辑 |
| AgentBaseTest | Agent 基类功能 |
| UserAgentTest | 用户代理功能 |

---

## 10. 待完善项

- [ ] StructuredOutputAgent 测试需补充
- [ ] 多 Agent 协作场景测试
- [ ] Agent 异常处理测试
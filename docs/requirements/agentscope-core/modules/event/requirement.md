# 子模块：Event 事件系统

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 包路径：io.agentscope.core.event

---

## 1. 功能概述

Event 模块定义了 Agent 执行过程中的各种事件，用于通知外部系统 Agent 的状态变化。

---

## 2. 事件类型

### Agent 生命周期事件
| 事件类 | 触发时机 |
|--------|----------|
| AgentStartEvent | Agent 开始执行 |
| AgentEndEvent | Agent 执行结束 |

### 模型调用事件
| 事件类 | 触发时机 |
|--------|----------|
| ModelCallStartEvent | 开始调用 LLM |
| ModelCallEndEvent | LLM 返回结果 |

### 工具调用事件
| 事件类 | 触发时机 |
|--------|----------|
| ToolCallStartEvent | 开始调用工具 |
| ToolCallEndEvent | 工具返回结果 |
| ToolCallDeltaEvent | 工具调用增量 |

### 数据块事件
| 事件类 | 触发时机 |
|--------|----------|
| DataBlockStartEvent | 数据块开始 |
| DataBlockDeltaEvent | 数据块增量 |
| DataBlockEndEvent | 数据块结束 |

### 文本事件
| 事件类 | 触发时机 |
|--------|----------|
| TextBlockStartEvent | 文本块开始 |
| TextBlockDeltaEvent | 文本增量 |
| TextBlockEndEvent | 文本块结束 |

### 思考事件
| 事件类 | 触发时机 |
|--------|----------|
| ThinkingBlockStartEvent | 思考块开始 |
| ThinkingBlockDeltaEvent | 思考增量 |
| ThinkingBlockEndEvent | 思考块结束 |

### 工具结果事件
| 事件类 | 触发时机 |
|--------|----------|
| ToolResultStartEvent | 工具结果开始 |
| ToolResultDeltaEvent | 工具结果增量 |
| ToolResultEndEvent | 工具结果结束 |

### 用户交互事件
| 事件类 | 触发时机 |
|--------|----------|
| RequireUserConfirmEvent | 需要用户确认 |
| UserConfirmResultEvent | 用户确认结果 |
| RequestStopEvent | 请求停止 |
| ExceedMaxItersEvent | 超过最大迭代 |

### 外部执行事件
| 事件类 | 触发时机 |
|--------|----------|
| RequireExternalExecutionEvent | 需要外部执行 |
| ExternalExecutionResultEvent | 外部执行结果 |

---

## 3. 确认结果

| 类名 | 功能 |
|------|------|
| ConfirmResult | 确认结果封装 |

---

## 4. 已实现功能点

- ✅ 完整的事件类型定义
- ✅ Agent 生命周期事件
- ✅ 模型调用事件
- ✅ 工具调用事件
- ✅ 流式数据块事件
- ✅ 用户交互事件
- ✅ 外部执行事件

---

## 5. 待完善项

- [ ] 事件序列化测试
- [ ] 事件订阅性能测试
- [ ] 事件丢失处理测试
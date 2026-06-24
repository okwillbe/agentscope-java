# 子模块：Tracing 链路追踪

> 📂 来源：代码分析
> 🔄 状态：⚠️ 测试不完整
> 📍 包路径：io.agentscope.core.tracing

---

## 1. 功能概述

Tracing 模块提供 Agent 执行过程的链路追踪能力，支持 OpenTelemetry 集成。

---

## 2. 核心类

| 类名 | 类型 | 功能描述 |
|------|------|----------|
| TracingHook | Class | 追踪 Hook |
| SpanBuilder | Class | Span 构建器 |
| TraceContext | Class | 追踪上下文 |

---

## 3. 追踪能力

- Agent 执行追踪
- 模型调用追踪
- 工具调用追踪
- 事件传播追踪

---

## 4. OpenTelemetry 集成

AgentScope 原生支持 OpenTelemetry：
- Span 自动创建
- 属性自动注入
- 上下文传播

---

## 5. 已实现功能点

- ✅ 链路追踪 Hook
- ✅ Span 管理
- ✅ OpenTelemetry 集成
- ⚠️ 追踪数据导出（测试不完整）

---

## 6. 待完善项

- [ ] 追踪采样策略
- [ ] 追踪数据导出测试
- [ ] 性能影响评估
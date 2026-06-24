# 子模块：Hook 扩展机制

> 📂 来源：代码分析
> 🔄 状态：⚠️ 测试不完整
> 📍 包路径：io.agentscope.core.hook

---

## 1. 功能概述

Hook 模块提供 Agent 执行过程的扩展点，允许在关键节点插入自定义逻辑。

---

## 2. 核心接口与类

| 类名 | 类型 | 功能描述 |
|------|------|----------|
| Hook | Interface | Hook 接口定义 |
| HookRegistry | Class | Hook 注册表 |
| StreamingHook | Class | 流式 Hook 实现 |
| HookContext | Class | Hook 上下文 |

---

## 3. Hook 执行点

| 执行点 | 触发时机 |
|--------|----------|
| PRE_CALL | Agent 调用前 |
| POST_CALL | Agent 调用后 |
| PRE_TOOL_CALL | 工具调用前 |
| POST_TOOL_CALL | 工具调用后 |
| PRE_MODEL_CALL | 模型调用前 |
| POST_MODEL_CALL | 模型调用后 |
| ON_EVENT | 事件触发时 |

---

## 4. 内置 Hook

| Hook | 功能 |
|------|------|
| StreamingHook | 流式输出处理 |
| StructuredOutputHook | 结构化输出处理 |
| StaticLongTermMemoryHook | 长期记忆保存 |
| GenericRAGHook | RAG 检索注入 |

---

## 5. Hook 注册方式

```java
agent.addHook(new MyHook());
agent.addHook(hook, HookPoint.PRE_CALL);
```

---

## 6. 已实现功能点

- ✅ Hook 接口定义
- ✅ Hook 注册管理
- ✅ 多执行点支持
- ✅ 流式 Hook 实现
- ✅ 结构化输出 Hook
- ⚠️ Hook 执行顺序（测试不完整）

---

## 7. 待完善项

- [ ] Hook 执行顺序测试
- [ ] Hook 异常处理测试
- [ ] Hook 链式调用测试
- [ ] Hook 性能影响测试
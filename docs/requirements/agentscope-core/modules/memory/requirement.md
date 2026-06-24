# 子模块：Memory 记忆管理

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 包路径：io.agentscope.core.memory

---

## 1. 功能概述

Memory 模块提供 Agent 的记忆管理能力，支持短期记忆和长期记忆。

---

## 2. 核心接口与类

| 类名 | 类型 | 功能描述 |
|------|------|----------|
| Memory | Interface | 记忆接口 |
| InMemoryMemory | Class | 内存记忆实现 |
| StateBackedMemory | Class | 状态-backed 记忆 |
| LongTermMemory | Interface | 长期记忆接口 |
| LongTermMemoryTools | Class | 长期记忆工具 |
| StaticLongTermMemoryHook | Class | 长期记忆 Hook |

---

## 3. 记忆类型

### 短期记忆 (Memory)
- 存储当前会话的对话历史
- 基于内存实现
- 自动管理对话轮次

### 长期记忆 (LongTermMemory)
- 跨会话持久化
- 语义搜索能力
- 支持多租户隔离

---

## 4. 记忆操作

| 操作 | 功能 |
|------|------|
| add(Msg) | 添加消息到记忆 |
| getAll() | 获取所有记忆 |
| clear() | 清空记忆 |
| search(query) | 语义搜索（长期记忆） |

---

## 5. LongTermMemory 工具

提供以下工具供 Agent 使用：

| 工具 | 功能 |
|------|------|
| save_memory | 保存记忆到长期存储 |
| search_memory | 搜索历史记忆 |
| list_memories | 列出所有记忆 |

---

## 6. Hook 集成

StaticLongTermMemoryHook 可自动将对话保存到长期记忆：

```java
agent.addHook(new StaticLongTermMemoryHook(longTermMemory));
```

---

## 7. 已实现功能点

- ✅ 内存记忆实现
- ✅ 状态-backed 记忆
- ✅ 长期记忆接口
- ✅ 长期记忆工具
- ✅ Hook 自动保存
- ✅ 对话历史管理

---

## 8. 扩展实现（在 extensions 模块）

| 实现 | 模块 |
|------|------|
| ReMeLongTermMemory | agentscope-extensions-reme |
| Mem0 集成 | agentscope-extensions-mem0 |
| 百炼记忆 | agentscope-extensions-memory-bailian |

---

## 9. 待完善项

- [ ] 记忆压缩策略
- [ ] 记忆优先级排序
- [ ] 多租户隔离测试
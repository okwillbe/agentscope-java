# 子模块：Session 会话管理

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 包路径：io.agentscope.core.session

---

## 1. 功能概述

Session 模块管理 Agent 的会话状态，支持会话持久化和恢复。

---

## 2. 核心接口与类

| 类名 | 类型 | 功能描述 |
|------|------|----------|
| Session | Interface | 会话接口 |
| SessionInfo | Class | 会话信息 |
| InMemorySession | Class | 内存会话实现 |
| JsonSession | Class | JSON 文件会话实现 |

---

## 3. Session 接口定义

```java
public interface Session {
    String getSessionId();
    void save(String key, Object value);
    <T> T load(String key, Class<T> type);
    void clear();
    void delete();
}
```

---

## 4. 会话存储方式

| 实现 | 存储方式 | 适用场景 |
|------|----------|----------|
| InMemorySession | 内存 | 测试、临时会话 |
| JsonSession | JSON 文件 | 本地持久化 |
| RedisSession | Redis（扩展模块） | 分布式部署 |
| MysqlSession | MySQL（扩展模块） | 企业级持久化 |

---

## 5. 会话信息

SessionInfo 包含：
- sessionId：会话唯一标识
- userId：用户标识
- createdAt：创建时间
- updatedAt：更新时间
- metadata：元数据

---

## 6. 已实现功能点

- ✅ 会话接口定义
- ✅ 内存会话实现
- ✅ JSON 文件会话
- ✅ 会话信息管理
- ✅ 状态保存与恢复

---

## 7. 扩展实现（在 extensions 模块）

| 实现 | 模块 |
|------|------|
| RedisSession | agentscope-extensions-session-redis |
| MysqlSession | agentscope-extensions-session-mysql |

---

## 8. 待完善项

- [ ] 会话过期清理机制
- [ ] 会话迁移测试
- [ ] 会话并发访问测试
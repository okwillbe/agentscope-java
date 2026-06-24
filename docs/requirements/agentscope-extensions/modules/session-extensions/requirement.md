# 子模块：Session 扩展

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 模块：agentscope-extensions-session-*

---

## 1. 功能概述

Session 扩展模块提供会话的持久化存储能力。

---

## 2. Redis Session

**模块**: agentscope-extensions-session-redis

| 类名 | 功能 |
|------|------|
| RedisSession | Redis 会话实现 |
| RedisClientAdapter | Redis 客户端适配器 |
| JedisSession | Jedis 客户端实现 |
| LettuceSession | Lettuce 客户端实现 |
| RedissonSession | Redisson 客户端实现 |

**支持的 Redis 客户端**：
- Jedis
- Lettuce
- Redisson

---

## 3. MySQL Session

**模块**: agentscope-extensions-session-mysql

| 类名 | 功能 |
|------|------|
| MysqlSession | MySQL 会话实现 |

**特性**：
- 关系型数据库持久化
- 支持 JSON 格式存储
- 支持会话查询

---

## 4. 已实现功能点

- ✅ Redis 多客户端支持
- ✅ MySQL 持久化
- ✅ 会话序列化

---

## 5. 待完善项

- [ ] 会话压缩存储
- [ ] 会话加密
- [ ] 分布式锁支持
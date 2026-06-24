# 子模块：A2A 协议

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 模块：agentscope-extensions-a2a

---

## 1. 功能概述

A2A (Agent-to-Agent) 协议实现 Agent 之间的通信和协作。

---

## 2. 子模块结构

| 子模块 | 功能 |
|--------|------|
| agentscope-extensions-a2a-client | A2A 客户端 |
| agentscope-extensions-a2a-server | A2A 服务端 |

---

## 3. 核心功能

- Agent 能力注册
- Agent 发现与调用
- 消息传递
- 流式响应

---

## 4. Nacos 集成

通过 agentscope-extensions-nacos-a2a 实现：
- 服务注册与发现
- 配置管理
- 健康检查

---

## 5. Spring Boot Starter

agentscope-a2a-spring-boot-starter 提供：
- 自动配置
- 便捷注入
- 健康检查集成

---

## 6. 已实现功能点

- ✅ A2A 协议实现
- ✅ 客户端/服务端分离
- ✅ Nacos 服务发现
- ✅ Spring Boot 集成

---

## 7. 待完善项

- [ ] A2A 安全认证
- [ ] 消息压缩
- [ ] 断路器支持
# 子模块：AgentScope Studio

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 模块：agentscope-extensions-studio

---

## 1. 功能概述

AgentScope Studio 提供可视化的 Agent 开发和调试界面。

---

## 2. 核心类

| 类名 | 功能 |
|------|------|
| StudioClient | Studio 客户端 |
| StudioConfig | Studio 配置 |
| StudioManager | Studio 管理器 |
| StudioMessageHook | 消息 Hook |
| StudioUserAgent | 用户 Agent |
| StudioWebSocketClient | WebSocket 客户端 |

---

## 3. 功能特性

- 实时消息推送
- Agent 执行可视化
- 调试支持
- 用户输入处理

---

## 4. POJO 类

| 类名 | 功能 |
|------|------|
| PushMessageRequest | 推送消息请求 |
| RegisterRunRequest | 注册运行请求 |
| RequestUserInputRequest | 用户输入请求 |
| UserInputMetadata | 用户输入元数据 |

---

## 5. 已实现功能点

- ✅ WebSocket 连接管理
- ✅ 消息推送机制
- ✅ 用户交互处理
- ✅ Agent 执行监控

---

## 6. 待完善项

- [ ] 断线重连机制
- [ ] 消息压缩
- [ ] 多用户会话支持
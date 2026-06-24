# 模块：agentscope-extensions 扩展模块

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现（代码还原）
> 📊 测试覆盖率：~25%

---

## 1. 模块概述

agentscope-extensions 提供各种企业级扩展能力，包括协议支持、持久化、调度等。

---

## 2. 扩展模块列表

| 模块 | 功能描述 | 测试状态 |
|------|----------|----------|
| agentscope-extensions-a2a | A2A 协议（Agent 间通信） | ⚠️ 测试不足 |
| agentscope-extensions-agent-protocol | Agent 协议 | ⚠️ 测试不足 |
| agentscope-extensions-agui | AGUI 协议 | ⚠️ 测试不足 |
| agentscope-extensions-chat-completions-web | Chat Completions Web API | ⚠️ 测试不足 |
| agentscope-extensions-higress | Higress 网关集成 | ⚠️ 测试不足 |
| agentscope-extensions-kotlin | Kotlin DSL 支持 | ⚠️ 测试不足 |
| agentscope-extensions-mem0 | Mem0 记忆服务 | ⚠️ 测试不足 |
| agentscope-extensions-memory-bailian | 百炼记忆服务 | ⚠️ 测试不足 |
| agentscope-extensions-nacos | Nacos 服务发现 | ⚠️ 测试不足 |
| agentscope-extensions-rag-bailian | 百炼 RAG | ⚠️ 测试不足 |
| agentscope-extensions-rag-dify | Dify RAG | ⚠️ 测试不足 |
| agentscope-extensions-rag-haystack | Haystack RAG | ⚠️ 测试不足 |
| agentscope-extensions-rag-ragflow | RagFlow RAG | ⚠️ 测试不足 |
| agentscope-extensions-rag-simple | 简单 RAG 实现 | ⚠️ 测试不足 |
| agentscope-extensions-reme | ReMe 记忆服务 | ⚠️ 测试不足 |
| agentscope-extensions-rocketmq | RocketMQ 集成 | ⚠️ 测试不足 |
| agentscope-extensions-scheduler | 调度器 | ⚠️ 测试不足 |
| agentscope-extensions-session-mysql | MySQL 会话存储 | ⚠️ 测试不足 |
| agentscope-extensions-session-redis | Redis 会话存储 | ⚠️ 测试不足 |
| agentscope-extensions-skill-git-repository | Git 技能仓库 | ⚠️ 测试不足 |
| agentscope-extensions-skill-mysql-repository | MySQL 技能仓库 | ⚠️ 测试不足 |
| agentscope-extensions-studio | AgentScope Studio | ⚠️ 测试不足 |
| agentscope-extensions-training | 训练相关 | ⚠️ 测试不足 |
| agentscope-spring-boot-starters | Spring Boot Starters | ⚠️ 测试不足 |

---

## 3. 分类

### 协议扩展
- A2A (Agent-to-Agent)
- AGUI
- Agent Protocol

### RAG 扩展
- 百炼 RAG
- Dify RAG
- Haystack RAG
- RagFlow RAG
- 简单 RAG

### 记忆扩展
- Mem0
- 百炼记忆
- ReMe

### 会话扩展
- MySQL Session
- Redis Session

### 调度扩展
- Quartz 调度器
- XXL-Job 调度器

### Spring Boot 集成
- agentscope-spring-boot-starter
- agentscope-a2a-spring-boot-starter
- agentscope-admin-spring-boot-starter
- agentscope-agui-spring-boot-starter
- agentscope-chat-completions-web-starter
- agentscope-nacos-spring-boot-starter

---

## 4. 待完善项

- [ ] 大部分扩展模块测试覆盖不足
- [ ] 需要补充集成测试
- [ ] 需要补充文档
# 子模块：Spring Boot Starters

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 模块：agentscope-spring-boot-starters

---

## 1. 功能概述

Spring Boot Starters 提供 AgentScope 的 Spring Boot 自动配置支持。

---

## 2. Starter 列表

| Starter | 功能 |
|---------|------|
| agentscope-spring-boot-starter | 核心 Starter |
| agentscope-a2a-spring-boot-starter | A2A 协议 |
| agentscope-admin-spring-boot-starter | 管理界面 |
| agentscope-agui-spring-boot-starter | AGUI 协议 |
| agentscope-chat-completions-web-starter | Chat Completions Web API |
| agentscope-nacos-spring-boot-starter | Nacos 集成 |

---

## 3. 核心 Starter 功能

**agentscope-spring-boot-starter**：

| 配置类 | 功能 |
|--------|------|
| AgentscopeAutoConfiguration | 自动配置 |
| AgentProperties | Agent 配置 |
| ModelProperties | 模型配置 |

**支持的模型配置**：
- Anthropic (Claude)
- DashScope (通义千问)
- OpenAI (GPT)
- Gemini
- OpenAI

---

## 4. Nacos Starter 功能

**agentscope-nacos-spring-boot-starter**：

| 配置类 | 功能 |
|--------|------|
| AgentscopeA2aNacosAutoConfiguration | A2A Nacos 配置 |
| AgentscopeNacosPromptAutoConfiguration | Nacos Prompt 配置 |
| AgentscopeNacosReActAgentAutoConfiguration | ReAct Agent 配置 |

---

## 5. 已实现功能点

- ✅ 自动配置支持
- ✅ 多模型配置
- ✅ A2A Spring 集成
- ✅ Nacos Spring 集成

---

## 6. 待完善项

- [ ] 配置验证
- [ ] 健康检查完善
- [ ] Metrics 集成
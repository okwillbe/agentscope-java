# 子模块：Scheduler 调度器

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 模块：agentscope-extensions-scheduler

---

## 1. 功能概述

Scheduler 模块提供 Agent 的定时调度能力。

---

## 2. 子模块结构

| 子模块 | 功能 |
|--------|------|
| agentscope-extensions-scheduler-common | 调度公共组件 |
| agentscope-extensions-scheduler-quartz | Quartz 实现 |
| agentscope-extensions-scheduler-xxl-job | XXL-Job 实现 |

---

## 3. 核心类

| 类名 | 功能 |
|------|------|
| AgentScheduler | 调度器接口 |
| ScheduleAgentTask | 调度任务 |
| BaseScheduleAgentTask | 任务基类 |

---

## 4. 调度配置

| 配置类 | 功能 |
|--------|------|
| AgentConfig | Agent 配置 |
| ModelConfig | 模型配置 |
| ScheduleConfig | 调度配置 |

---

## 5. Quartz 实现

| 类名 | 功能 |
|------|------|
| AgentQuartzJob | Quartz Job 实现 |
| QuartzAgentScheduler | Quartz 调度器 |

---

## 6. XXL-Job 实现

| 类名 | 功能 |
|------|------|
| XxlJobAgentScheduler | XXL-Job 调度器 |

---

## 7. 已实现功能点

- ✅ 调度器接口定义
- ✅ Quartz 集成
- ✅ XXL-Job 集成
- ✅ 任务配置管理

---

## 8. 待完善项

- [ ] 任务执行日志
- [ ] 任务失败重试
- [ ] 任务依赖管理
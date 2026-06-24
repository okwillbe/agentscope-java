# 子模块：Plan 计划管理

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 包路径：io.agentscope.core.plan

---

## 1. 功能概述

Plan 模块提供结构化的任务管理能力，让 Agent 能够分解和追踪复杂任务。

---

## 2. 核心类

| 类名 | 类型 | 功能描述 |
|------|------|----------|
| PlanNotebook | Class | 计划笔记本 |
| Plan | Class | 计划实体 |
| PlanStep | Class | 计划步骤 |
| PlanStatus | Enum | 计划状态 |
| PlanStorage | Interface | 计划存储 |

---

## 3. 计划状态

| 状态 | 含义 |
|------|------|
| PENDING | 待执行 |
| IN_PROGRESS | 执行中 |
| COMPLETED | 已完成 |
| FAILED | 失败 |
| PAUSED | 已暂停 |

---

## 4. PlanNotebook 功能

- 创建多个计划
- 添加/删除步骤
- 更新步骤状态
- 查询计划进度
- 暂停/恢复计划

---

## 5. 计划存储

| 实现 | 存储方式 |
|------|----------|
| InMemoryPlanStorage | 内存存储 |
| FilePlanStorage | 文件存储（扩展模块） |

---

## 6. 已实现功能点

- ✅ 计划创建与管理
- ✅ 步骤状态追踪
- ✅ 多计划并发
- ✅ 计划暂停/恢复
- ✅ 计划存储接口

---

## 7. 待完善项

- [ ] 计划依赖关系
- [ ] 计划模板
- [ ] 计划可视化
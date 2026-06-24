# 子模块：Skill 技能系统

> 📂 来源：代码分析
> 🔄 状态：⚠️ 测试不完整
> 📍 包路径：io.agentscope.core.skill

---

## 1. 功能概述

Skill 模块提供可复用的技能管理能力，支持技能的定义、存储和调用。

---

## 2. 核心类

| 类名 | 类型 | 功能描述 |
|------|------|----------|
| Skill | Class | 技能定义 |
| SkillRepository | Interface | 技能仓库接口 |
| SkillToolGroup | Class | 技能工具组 |
| SkillUtils | Class | 技能工具类 |

---

## 3. Skill 结构

一个 Skill 包含：
- name：技能名称
- description：技能描述
- tools：工具列表
- prompt：技能提示词
- metadata：元数据

---

## 4. SkillRepository 接口

```java
public interface SkillRepository {
    void save(Skill skill);
    Skill findByName(String name);
    List<Skill> findAll();
    void delete(String name);
}
```

---

## 5. 技能使用方式

```java
Skill skill = Skill.builder()
    .name("weather")
    .description("天气查询技能")
    .tools(weatherTools)
    .build();

agent.addSkill(skill);
```

---

## 6. 已实现功能点

- ✅ 技能定义结构
- ✅ 技能仓库接口
- ✅ 技能工具组
- ⚠️ 技能加载机制（测试不完整）

---

## 7. 扩展实现（在 extensions 模块）

| 实现 | 模块 |
|------|------|
| GitSkillRepository | agentscope-extensions-skill-git-repository |
| MysqlSkillRepository | agentscope-extensions-skill-mysql-repository |

---

## 8. 待完善项

- [ ] 技能版本管理
- [ ] 技能依赖解析
- [ ] 技能热加载测试
# 子模块：Tool 工具系统

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 包路径：io.agentscope.core.tool

---

## 1. 功能概述

Tool 系统提供了 Agent 与外部世界交互的能力，支持自定义工具、MCP 协议工具、子 Agent 工具等。

---

## 2. 核心接口与类

| 类名 | 类型 | 功能描述 | 源文件 |
|------|------|----------|--------|
| Toolkit | Class | 工具集合管理 | tool/Toolkit.java |
| ToolkitConfig | Class | 工具集合配置 | tool/ToolkitConfig.java |
| ToolGroup | Class | 工具分组 | tool/ToolGroup.java |
| ToolCallParam | Class | 工具调用参数 | tool/ToolCallParam.java |
| ToolExecutionContext | Class | 工具执行上下文 | tool/ToolExecutionContext.java |
| ToolResultConverter | Interface | 工具结果转换器 | tool/ToolResultConverter.java |
| ToolEmitter | Interface | 工具发射器 | tool/ToolEmitter.java |
| AgentTool | Interface | Agent 工具接口 | tool/AgentTool.java |
| SchemaOnlyTool | Class | 仅 Schema 工具 | tool/SchemaOnlyTool.java |

---

## 3. MCP 工具集成

| 类名 | 功能 |
|------|------|
| McpClientBuilder | MCP 客户端构建器 |
| McpTool | MCP 工具包装 |
| McpSyncClientWrapper | 同步 MCP 客户端 |
| McpAsyncClientWrapper | 异步 MCP 客户端 |
| McpContentConverter | MCP 内容转换 |

---

## 4. 内置工具

### 文件操作工具
| 工具 | 功能 |
|------|------|
| ReadFileTool | 读取文件 |
| WriteFileTool | 写入文件 |
| FileToolUtils | 文件工具辅助 |

### 命令执行工具
| 工具 | 功能 |
|------|------|
| ShellCommandTool | Shell 命令执行 |
| CommandValidator | 命令验证器 |
| WindowsCommandValidator | Windows 命令验证 |
| UnixCommandValidator | Unix 命令验证 |

### 多模态工具
| 工具 | 功能 |
|------|------|
| DashScopeMultiModalTool | DashScope 多模态 |
| OpenAIMultiModalTool | OpenAI 多模态 |

### 其他内置工具
| 工具 | 功能 |
|------|------|
| TodoTools | 任务管理工具 |
| SubAgentTool | 子 Agent 工具 |
| SkillToolGroup | 技能工具组 |

---

## 5. 工具调用流程

```
Agent 决策调用工具
      ↓
  ToolCallParam 构建
      ↓
  ToolExecutionContext 提供上下文
      ↓
  工具执行
      ↓
  ToolResultConverter 转换结果
      ↓
  返回给 Agent
```

---

## 6. 工具注册方式

**注解方式**：
```java
public class MyTools {
    @Tool(name = "get_weather", description = "获取天气")
    public String getWeather(String city) {
        // ...
    }
}
```

**编程方式**：
```java
Toolkit toolkit = Toolkit.builder()
    .addTool(new MyTool())
    .addToolGroup(new SkillToolGroup())
    .build();
```

---

## 7. 子 Agent 工具

| 类名 | 功能 |
|------|------|
| SubAgentTool | 子 Agent 工具实现 |
| SubAgentProvider | 子 Agent 提供者 |
| SubAgentConfig | 子 Agent 配置 |

允许将一个 Agent 作为另一个 Agent 的工具使用。

---

## 8. 已实现功能点

- ✅ 工具注册与管理
- ✅ MCP 协议集成
- ✅ 工具调用参数解析
- ✅ 工具结果转换
- ✅ 文件操作工具
- ✅ Shell 命令工具
- ✅ 多模态工具
- ✅ 子 Agent 工具
- ✅ 工具上下文注入

---

## 9. 测试文件

| 测试类 | 覆盖范围 |
|--------|----------|
| ToolkitTest | 工具集合管理 |
| ToolCallParamTest | 参数解析 |
| McpToolTest | MCP 工具集成 |
| ShellCommandToolTest | 命令执行 |

---

## 10. 待完善项

- [ ] 工具沙箱安全机制测试
- [ ] MCP 工具超时处理测试
- [ ] 工具并发执行测试
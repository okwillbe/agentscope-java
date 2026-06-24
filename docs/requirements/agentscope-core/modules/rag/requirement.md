# 子模块：RAG 检索增强生成

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 包路径：io.agentscope.core.rag

---

## 1. 功能概述

RAG 模块提供检索增强生成能力，让 Agent 能够基于知识库回答问题。

---

## 2. 核心接口与类

| 类名 | 类型 | 功能描述 |
|------|------|----------|
| Knowledge | Interface | 知识库接口 |
| GenericRAGHook | Class | RAG Hook 实现 |
| KnowledgeRetrievalTools | Class | 知识检索工具 |
| Document | Class | 文档数据结构 |
| DocumentMetadata | Class | 文档元数据 |
| RetrieveConfig | Class | 检索配置 |

---

## 3. Knowledge 接口

```java
public interface Knowledge {
    List<Document> retrieve(String query, RetrieveConfig config);
    void addDocument(Document document);
    void removeDocument(String documentId);
}
```

---

## 4. RAG 工作流程

```
用户提问
    ↓
GenericRAGHook 拦截
    ↓
Knowledge 检索相关文档
    ↓
将文档注入上下文
    ↓
Agent 基于知识回答
```

---

## 5. 知识检索工具

| 工具 | 功能 |
|------|------|
| retrieve_knowledge | 检索知识库 |
| list_documents | 列出所有文档 |
| add_document | 添加文档到知识库 |

---

## 6. 文档结构

Document 包含：
- id：文档唯一标识
- content：文档内容
- metadata：元数据（来源、作者、时间等）
- embedding：向量嵌入

---

## 7. 检索配置

RetrieveConfig：
- topK：返回文档数量
- threshold：相似度阈值
- filters：过滤条件

---

## 8. 已实现功能点

- ✅ 知识库接口定义
- ✅ RAG Hook 实现
- ✅ 知识检索工具
- ✅ 文档数据结构
- ✅ 检索配置

---

## 9. 扩展实现（在 extensions 模块）

| 实现 | 模块 |
|------|------|
| SimpleKnowledge | agentscope-extensions-rag-simple |
| 百炼 RAG | agentscope-extensions-rag-bailian |
| Dify RAG | agentscope-extensions-rag-dify |
| Haystack RAG | agentscope-extensions-rag-haystack |
| RagFlow RAG | agentscope-extensions-rag-ragflow |

向量存储支持：
- InMemoryStore
- ElasticsearchStore
- MilvusStore
- PgVectorStore
- QdrantStore

---

## 10. 待完善项

- [ ] 文档分块策略测试
- [ ] 检索结果排序测试
- [ ] 多知识库融合测试
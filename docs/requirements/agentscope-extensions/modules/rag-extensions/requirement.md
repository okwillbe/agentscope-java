# 子模块：RAG 扩展

> 📂 来源：代码分析
> 🔄 状态：✅ 已实现
> 📍 模块：agentscope-extensions-rag-*

---

## 1. 功能概述

RAG 扩展模块提供多种检索增强生成的实现。

---

## 2. RAG 实现列表

### agentscope-extensions-rag-simple

提供基础的 RAG 实现：

| 组件 | 功能 |
|------|------|
| SimpleKnowledge | 简单知识库实现 |
| Reader | 文档读取器 |
| TextReader | 文本读取 |
| PDFReader | PDF 读取 |
| WordReader | Word 文档读取 |
| ImageReader | 图片读取 |
| TikaReader | Tika 通用读取 |

### 向量存储

| 存储 | 功能 |
|------|------|
| InMemoryStore | 内存向量存储 |
| ElasticsearchStore | Elasticsearch 存储 |
| MilvusStore | Milvus 存储 |
| PgVectorStore | PostgreSQL 向量存储 |
| QdrantStore | Qdrant 存储 |

### Embedding 服务

| 服务 | 功能 |
|------|------|
| DashScopeTextEmbedding | 阿里云 Embedding |
| OpenAITextEmbedding | OpenAI Embedding |
| OllamaTextEmbedding | Ollama Embedding |

---

## 3. 其他 RAG 实现

| 模块 | 功能 |
|------|------|
| agentscope-extensions-rag-bailian | 阿里云百炼 RAG |
| agentscope-extensions-rag-dify | Dify RAG |
| agentscope-extensions-rag-haystack | Haystack RAG |
| agentscope-extensions-rag-ragflow | RagFlow RAG |

---

## 4. 已实现功能点

- ✅ 多种文档读取器
- ✅ 多种向量存储
- ✅ 多种 Embedding 服务
- ✅ 多平台 RAG 集成

---

## 5. 待完善项

- [ ] 文档分块策略优化
- [ ] 向量索引性能优化
- [ ] 多语言支持
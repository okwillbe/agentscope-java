/*
 * Copyright 2024-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.agentscope.extensions.higress;

import io.agentscope.core.tool.mcp.McpClientWrapper;
import io.modelcontextprotocol.spec.McpSchema;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * {@summary MCP client wrapper for Higress AI Gateway (Higress AI网关的MCP客户端包装器)}
 *
 * <p>This wrapper extends {@link McpClientWrapper} to connect to Higress's unified MCP service
 * endpoint (union-tools-search). When semantic search is enabled in Higress, the gateway provides
 * a special tool {@code x_higress_tool_search} that can automatically select the most suitable
 * MCP tools based on user requests.
 * <p>此包装器扩展{@link McpClientWrapper}以连接到Higress的统一MCP服务端点(union-tools-search)。
 * 当在Higress中启用语义搜索时，网关提供一个特殊工具{@code x_higress_tool_search},
 * 可以根据用户请求自动选择最合适的MCP工具。
 *
 * <p>The wrapper delegates all MCP operations to an underlying {@link McpClientWrapper} instance
 * (either async or sync), providing a consistent interface for Higress-specific functionality.
 * <p>此包装器将所有MCP操作委托给底层的{@link McpClientWrapper}实例(异步或同步),
 * 为Higress特定功能提供一致的接口。
 *
 * <p>Example usage (使用示例):
 * <pre>{@code
 * HigressMcpClientWrapper client = HigressMcpClientBuilder
 *     .create("higress-mcp")
 *     .sseEndpoint("http://higress-gateway/mcp-servers/union-tools-search/sse")
 *     .build();
 *
 * // Initialize and list tools (初始化并列出工具)
 * client.initialize().block();
 * List<McpSchema.Tool> tools = client.listTools().block();
 *
 * // Call x_higress_tool_search to get recommended tools (调用x_higress_tool_search获取推荐工具)
 * McpSchema.CallToolResult result = client.callTool("x_higress_tool_search",
 *     Map.of("query", "search query")).block();
 * }</pre>
 *
 * @see HigressMcpClientBuilder
 * @see McpClientWrapper
 */
public class HigressMcpClientWrapper extends McpClientWrapper {

    private static final Logger logger = LoggerFactory.getLogger(HigressMcpClientWrapper.class);

    /**
     * {@summary The name of the Higress tool search tool (Higress工具搜索工具的名称)}
     */
    public static final String TOOL_SEARCH_NAME = "x_higress_tool_search";

    /**
     * {@summary The underlying MCP client that handles actual MCP protocol communication (处理实际MCP协议通信的底层MCP客户端)}
     */
    private final McpClientWrapper delegateClient;

    /**
     * {@summary Whether x_higress_tool_search is enabled (是否启用x_higress_tool_search)}
     */
    private final boolean enableToolSearch;

    /**
     * {@summary The query for x_higress_tool_search (x_higress_tool_search的查询)}
     */
    private final String toolSearchQuery;

    /**
     * {@summary The maximum number of tools to return from x_higress_tool_search (x_higress_tool_search返回的最大工具数)}
     */
    private final int toolSearchTopK;

    /**
     * {@summary Constructs a new HigressMcpClientWrapper (构造新的HigressMcpClientWrapper)}
     *
     * @param name the unique name for this client (此客户端的唯一名称)
     * @param delegateClient the underlying MCP client wrapper to delegate operations to (委托操作的底层MCP客户端包装器)
     * @param enableToolSearch whether to enable x_higress_tool_search tool (是否启用x_higress_tool_search工具)
     * @param toolSearchQuery the query for x_higress_tool_search (x_higress_tool_search的查询)
     * @param toolSearchTopK the maximum number of tools to return (返回的最大工具数)
     */
    HigressMcpClientWrapper(
            String name,
            McpClientWrapper delegateClient,
            boolean enableToolSearch,
            String toolSearchQuery,
            int toolSearchTopK) {
        super(name);
        this.delegateClient = delegateClient;
        this.enableToolSearch = enableToolSearch;
        this.toolSearchQuery = toolSearchQuery;
        this.toolSearchTopK = toolSearchTopK;
    }

    /**
     * {@summary Initializes the Higress MCP client connection (初始化Higress MCP客户端连接)}
     *
     * <p>This method delegates to the underlying MCP client to establish connection
     * with the Higress gateway and discover available tools.
     * <p>此方法委托给底层MCP客户端以建立与Higress网关的连接并发现可用工具。
     *
     * @return a Mono that completes when initialization is finished (初始化完成时完成的Mono)
     */
    @Override
    public Mono<Void> initialize() {
        if (isInitialized()) {
            logger.debug("Higress MCP client '{}' already initialized", name);
            return Mono.empty();
        }

        logger.info("Initializing Higress MCP client: {}", name);

        return delegateClient
                .initialize()
                .doOnSuccess(
                        unused -> {
                            this.initialized = true;
                            logger.info("Higress MCP client '{}' initialized successfully", name);
                        })
                .doOnError(
                        error ->
                                logger.error(
                                        "Failed to initialize Higress MCP client '{}': {}",
                                        name,
                                        error.getMessage(),
                                        error));
    }

    /**
     * {@summary Lists available tools from the Higress gateway (列出Higress网关的可用工具)}
     *
     * <p>When tool search is enabled, this method calls {@code x_higress_tool_search} to get
     * semantically relevant tools. Otherwise, it lists all available tools.
     * <p>当启用工具搜索时，此方法调用{@code x_higress_tool_search}获取语义相关的工具。
     * 否则，它列出所有可用工具。
     *
     * @return a Mono emitting the list of available tools (发出可用工具列表的Mono)
     */
    @Override
    public Mono<List<McpSchema.Tool>> listTools() {
        if (!enableToolSearch) {
            return delegateClient.listTools();
        }

        return callTool(TOOL_SEARCH_NAME, Map.of("query", toolSearchQuery, "topK", toolSearchTopK))
                .map(result -> parseToolsFromSearchResult(result));
    }

    private List<McpSchema.Tool> parseToolsFromSearchResult(McpSchema.CallToolResult result) {
        if (Boolean.TRUE.equals(result.isError())) {
            logger.warn("Tool search returned error, falling back to delegateClient.listTools()");
            return delegateClient.listTools().block();
        }

        List<Object> content = result.content();
        if (content == null || content.isEmpty()) {
            logger.warn("Tool search returned empty content, falling back to listTools()");
            return delegateClient.listTools().block();
        }

        List<HigressToolSearchResult.ToolInfo> toolInfos =
                HigressToolSearchResult.parse(result).tools();

        return toolInfos.stream().map(this::convertToMcpTool).toList();
    }

    private McpSchema.Tool convertToMcpTool(HigressToolSearchResult.ToolInfo toolInfo) {
        McpSchema.JsonSchema inputSchema = null;
        if (toolInfo.inputSchema() != null) {
            Map<String, Object> inputSchemaMap = toolInfo.inputSchema();
            Map<String, Object> properties = (Map<String, Object>) inputSchemaMap.get("properties");
            List<String> required =
                    inputSchemaMap.containsKey("required")
                            ? ((List<Object>) inputSchemaMap.get("required"))
                                    .stream()
                                            .filter(item -> item instanceof String)
                                            .map(item -> (String) item)
                                            .toList()
                            : null;
            inputSchema =
                    new McpSchema.JsonSchema(
                            (String) inputSchemaMap.get("type"),
                            properties,
                            required,
                            null, // additionalProperties
                            null, // definitions
                            null // defs
                            );
        }

        return new McpSchema.Tool(
                toolInfo.name(),
                toolInfo.title(), // title
                toolInfo.description(),
                inputSchema,
                null, // outputSchema
                null, // annotations
                null // extensions
                );
    }

    /**
     * {@summary Invokes a tool on the Higress gateway (调用Higress网关上的工具)}
     *
     * <p>This method can be used to call any tool available on the gateway, including
     * the {@code x_higress_tool_search} tool for intelligent tool selection.
     * <p>此方法可用于调用网关上的任何可用工具，包括用于智能工具选择的{@code x_higress_tool_search}工具。
     *
     * @param toolName the name of the tool to call (要调用的工具名称)
     * @param arguments the arguments to pass to the tool (传递给工具的参数)
     * @return a Mono emitting the tool call result (发出工具调用结果的Mono)
     */
    @Override
    public Mono<McpSchema.CallToolResult> callTool(String toolName, Map<String, Object> arguments) {
        return callTool(toolName, arguments, null);
    }

    @Override
    public Mono<McpSchema.CallToolResult> callTool(
            String toolName, Map<String, Object> arguments, Map<String, Object> meta) {
        logger.debug(
                "Calling tool '{}' on Higress MCP client '{}' with arguments: {}",
                toolName,
                name,
                arguments);

        return delegateClient
                .callTool(toolName, arguments, meta)
                .doOnSuccess(
                        result -> {
                            if (Boolean.TRUE.equals(result.isError())) {
                                logger.warn(
                                        "Higress tool '{}' returned error: {}",
                                        toolName,
                                        result.content());
                            } else {
                                logger.debug("Higress tool '{}' completed successfully", toolName);
                            }
                        })
                .doOnError(
                        error ->
                                logger.error(
                                        "Failed to call Higress tool '{}': {}",
                                        toolName,
                                        error.getMessage()));
    }

    /**
     * {@summary Closes the Higress MCP client and releases all resources (关闭Higress MCP客户端并释放所有资源)}
     *
     * <p>This method closes the underlying delegate client and clears all cached data.
     * <p>此方法关闭底层委托客户端并清除所有缓存数据。
     */
    @Override
    public void close() {
        logger.info("Closing Higress MCP client: {}", name);

        if (delegateClient != null) {
            try {
                delegateClient.close();
                logger.debug("Higress MCP client '{}' closed successfully", name);
            } catch (Exception e) {
                logger.error("Error closing Higress MCP client '{}': {}", name, e.getMessage(), e);
            }
        }

        this.initialized = false;
        this.cachedTools.clear();
    }

    /**
     * {@summary Checks if x_higress_tool_search mode is enabled in this client (检查此客户端是否启用了x_higress_tool_search模式)}
     *
     * <p>When enabled, listTools() will call x_higress_tool_search to get
     * semantically relevant tools instead of returning all tools.
     * <p>启用后，listTools()将调用x_higress_tool_search获取语义相关的工具，而不是返回所有工具。
     *
     * @return true if tool search mode is enabled (如果工具搜索模式已启用则返回true)
     */
    public boolean isToolSearchEnabled() {
        return enableToolSearch;
    }

    /**
     * {@summary Searches for tools matching the query and returns parsed result (搜索匹配查询的工具并返回解析结果)}
     *
     * <p>This method calls x_higress_tool_search and parses the response into
     * a structured {@link HigressToolSearchResult}.
     * <p>此方法调用x_higress_tool_search并将响应解析为结构化的{@link HigressToolSearchResult}。
     *
     * @param query the user query to find matching tools (required) (查找匹配工具的用户查询(必需))
     * @return a Mono emitting the parsed tool search result (发出解析的工具搜索结果的Mono)
     */
    public Mono<HigressToolSearchResult> searchTools(String query) {
        return callTool(TOOL_SEARCH_NAME, Map.of("query", query))
                .map(HigressToolSearchResult::parse);
    }

    /**
     * {@summary Searches for tools matching the query with topK limit (搜索匹配查询的工具并限制topK)}
     *
     * @param query the user query to find matching tools (required) (查找匹配工具的用户查询(必需))
     * @param topK the maximum number of tools to return (返回的最大工具数)
     * @return a Mono emitting the parsed tool search result (发出解析的工具搜索结果的Mono)
     */
    public Mono<HigressToolSearchResult> searchTools(String query, int topK) {
        return callTool(TOOL_SEARCH_NAME, Map.of("query", query, "topK", topK))
                .map(HigressToolSearchResult::parse);
    }
}

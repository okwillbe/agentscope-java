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

import io.agentscope.core.tool.mcp.McpClientBuilder;
import io.agentscope.core.tool.mcp.McpClientWrapper;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import reactor.core.publisher.Mono;

/**
 * {@summary Builder for creating HigressMcpClientWrapper instances (用于创建HigressMcpClientWrapper实例的构建器)}
 *
 * <p>This builder follows the same pattern as {@link McpClientBuilder} in agentscope-core,
 * providing a fluent API for configuring and building Higress MCP clients.
 * <p>此构建器遵循与agentscope-core中的{@link McpClientBuilder}相同的模式，
 * 提供流式API用于配置和构建Higress MCP客户端。
 *
 * <p>Supports two transport types (支持两种传输类型):
 * <ul>
 *   <li><b>SSE (Server-Sent Events)</b> - for stateful connections with server push
 *   <p>用于需要服务器推送的有状态连接</li>
 *   <li><b>StreamableHTTP</b> - for stateless HTTP streaming
 *   <p>用于无状态的HTTP流式传输</li>
 * </ul>
 *
 * <p>Example usage with SSE transport (使用SSE传输的示例):
 * <pre>{@code
 * HigressMcpClientWrapper client = HigressMcpClientBuilder
 *     .create("higress-mcp")
 *     .sseEndpoint("http://higress-gateway/mcp-servers/union-tools-search/sse")
 *     .build();
 * }</pre>
 *
 * <p>Example usage with StreamableHTTP transport (使用StreamableHTTP传输的示例):
 * <pre>{@code
 * HigressMcpClientWrapper client = HigressMcpClientBuilder
 *     .create("higress-mcp")
 *     .streamableHttpEndpoint("http://higress-gateway/mcp-servers/union-tools-search")
 *     .build();
 * }</pre>
 *
 * <p>Example with authentication (带认证的示例):
 * <pre>{@code
 * HigressMcpClientWrapper client = HigressMcpClientBuilder
 *     .create("higress-mcp")
 *     .sseEndpoint("http://higress-gateway/mcp-servers/union-tools-search/sse")
 *     .header("Authorization", "Bearer " + token)
 *     .header("X-Api-Key", apiKey)
 *     .timeout(Duration.ofSeconds(60))
 *     .buildAsync()
 *     .block();
 * }</pre>
 *
 * @see HigressMcpClientWrapper
 * @see McpClientBuilder
 */
public class HigressMcpClientBuilder {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(120);
    private static final Duration DEFAULT_INIT_TIMEOUT = Duration.ofSeconds(30);

    private final String clientName;
    private String endpoint;
    private TransportType transportType;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> queryParams = new HashMap<>();
    private Duration timeout = DEFAULT_TIMEOUT;
    private Duration initializationTimeout = DEFAULT_INIT_TIMEOUT;
    private boolean enableToolSearch = false;
    private String toolSearchQuery;
    private int toolSearchTopK = 10;

    /**
     * {@summary Private constructor. Use static factory methods to create instances (私有构造函数，使用静态工厂方法创建实例)}
     *
     * @param clientName the unique name for the MCP client (MCP客户端的唯一名称)
     */
    private HigressMcpClientBuilder(String clientName) {
        this.clientName = clientName;
    }

    /**
     * {@summary Creates a new builder with the specified client name (使用指定的客户端名称创建新的构建器)}
     *
     * @param clientName unique identifier for the MCP client (MCP客户端的唯一标识符)
     * @return new builder instance (新的构建器实例)
     * @throws IllegalArgumentException if clientName is null or empty (如果clientName为null或空)
     */
    public static HigressMcpClientBuilder create(String clientName) {
        if (clientName == null || clientName.trim().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be null or empty");
        }
        return new HigressMcpClientBuilder(clientName);
    }

    /**
     * {@summary Configures SSE (Server-Sent Events) transport endpoint (配置SSE传输端点)}
     *
     * <p>SSE transport is recommended for scenarios requiring real-time server push
     * and stateful connections.
     * <p>SSE传输推荐用于需要实时服务器推送和有状态连接的场景。
     *
     * @param endpoint the SSE endpoint URL (SSE端点URL)
     *                 (e.g., "http://higress-gateway/mcp-servers/union-tools-search/sse")
     * @return this builder for method chaining (此构建器用于方法链式调用)
     */
    public HigressMcpClientBuilder sseEndpoint(String endpoint) {
        this.endpoint = endpoint;
        this.transportType = TransportType.SSE;
        return this;
    }

    /**
     * {@summary Configures StreamableHTTP transport endpoint (配置StreamableHTTP传输端点)}
     *
     * <p>StreamableHTTP transport is suitable for stateless HTTP streaming scenarios.
     * <p>StreamableHTTP传输适用于无状态的HTTP流式传输场景。
     *
     * @param endpoint the StreamableHTTP endpoint URL (StreamableHTTP端点URL)
     *                 (e.g., "http://higress-gateway/mcp-servers/union-tools-search")
     * @return this builder for method chaining (此构建器用于方法链式调用)
     */
    public HigressMcpClientBuilder streamableHttpEndpoint(String endpoint) {
        this.endpoint = endpoint;
        this.transportType = TransportType.STREAMABLE_HTTP;
        return this;
    }

    /**
     * {@summary Adds an HTTP header to be sent with each request (添加每次请求时要发送的HTTP头)}
     *
     * @param name header name (头部名称)
     * @param value header value (头部值)
     * @return this builder for method chaining (此构建器用于方法链式调用)
     */
    public HigressMcpClientBuilder header(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    /**
     * {@summary Adds multiple HTTP headers (添加多个HTTP头)}
     *
     * @param headers map of headers to add (要添加的头部映射)
     * @return this builder for method chaining (此构建器用于方法链式调用)
     */
    public HigressMcpClientBuilder headers(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    /**
     * {@summary Adds a query parameter to be sent with each request (添加每次请求时要发送的查询参数)}
     *
     * @param name query parameter name (查询参数名称)
     * @param value query parameter value (查询参数值)
     * @return this builder for method chaining (此构建器用于方法链式调用)
     */
    public HigressMcpClientBuilder queryParam(String name, String value) {
        this.queryParams.put(name, value);
        return this;
    }

    /**
     * {@summary Adds multiple query parameters (添加多个查询参数)}
     *
     * @param queryParams map of query parameters to add (要添加的查询参数映射)
     * @return this builder for method chaining (此构建器用于方法链式调用)
     */
    public HigressMcpClientBuilder queryParams(Map<String, String> queryParams) {
        this.queryParams.putAll(queryParams);
        return this;
    }

    /**
     * {@summary Sets the timeout for MCP operations (设置MCP操作的超时时间)}
     *
     * @param timeout timeout duration (超时时长)
     * @return this builder for method chaining (此构建器用于方法链式调用)
     */
    public HigressMcpClientBuilder timeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * {@summary Sets the timeout for client initialization (设置客户端初始化的超时时间)}
     *
     * @param timeout initialization timeout duration (初始化超时时长)
     * @return this builder for method chaining (此构建器用于方法链式调用)
     */
    public HigressMcpClientBuilder initializationTimeout(Duration timeout) {
        this.initializationTimeout = timeout;
        return this;
    }

    /**
     * {@summary Enables tool search functionality (启用工具搜索功能)}
     *
     * <p>When enabled, the client will search for relevant tools based on the query
     * instead of listing all available tools.
     * <p>启用后，客户端将根据查询搜索相关工具，而不是列出所有可用工具。
     *
     * @param query search query for filtering tools (用于过滤工具的搜索查询)
     * @return this builder for method chaining (此构建器用于方法链式调用)
     */
    public HigressMcpClientBuilder toolSearch(String query) {
        this.enableToolSearch = true;
        this.toolSearchQuery = query;
        return this;
    }

    /**
     * {@summary Sets the maximum number of tools to return in search results (设置搜索结果中返回的最大工具数)}
     *
     * @param topK maximum number of tools to return (返回的最大工具数)
     * @return this builder for method chaining (此构建器用于方法链式调用)
     */
    public HigressMcpClientBuilder toolSearchTopK(int topK) {
        this.toolSearchTopK = topK;
        return this;
    }

    /**
     * {@summary Builds an asynchronous HigressMcpClientWrapper instance (构建异步的HigressMcpClientWrapper实例)}
     *
     * <p>This method (此方法):
     * <ol>
     *   <li>Validates the configuration (验证配置)</li>
     *   <li>Creates the underlying MCP client using {@link McpClientBuilder}
     *       (使用{@link McpClientBuilder}创建底层MCP客户端)</li>
     *   <li>Wraps it in a {@link HigressMcpClientWrapper}
     *       (将其包装在{@link HigressMcpClientWrapper}中)</li>
     *   <li>Initializes the client (初始化客户端)</li>
     * </ol>
     *
     * @return Mono emitting the configured HigressMcpClientWrapper instance
     *         (发出已配置的HigressMcpClientWrapper实例的Mono)
     * @throws IllegalArgumentException if endpoint is not configured (如果端点未配置)
     * @throws IllegalStateException if transport type is not configured (如果传输类型未配置)
     */
    public Mono<HigressMcpClientWrapper> buildAsync() {
        // Validate configuration
        validateConfiguration();

        // Build the underlying MCP client using agentscope's McpClientBuilder
        McpClientBuilder mcpClientBuilder = createMcpClientBuilder();

        // Build async delegate client, wrap it and initialize
        return mcpClientBuilder
                .buildAsync()
                .flatMap(
                        delegateClient -> {
                            HigressMcpClientWrapper wrapper =
                                    new HigressMcpClientWrapper(
                                            clientName,
                                            delegateClient,
                                            enableToolSearch,
                                            toolSearchQuery,
                                            toolSearchTopK);
                            return wrapper.initialize().thenReturn(wrapper);
                        });
    }

    /**
     * {@summary Builds a synchronous HigressMcpClientWrapper instance (构建同步的HigressMcpClientWrapper实例)}
     *
     * <p>This method (此方法):
     * <ol>
     *   <li>Validates the configuration (验证配置)</li>
     *   <li>Creates the underlying synchronous MCP client using {@link McpClientBuilder}
     *       (使用{@link McpClientBuilder}创建底层同步MCP客户端)</li>
     *   <li>Wraps it in a {@link HigressMcpClientWrapper}
     *       (将其包装在{@link HigressMcpClientWrapper}中)</li>
     *   <li>Initializes the client (初始化客户端)</li>
     * </ol>
     *
     * <p>Example usage (使用示例):
     * <pre>{@code
     * HigressMcpClientWrapper client = HigressMcpClientBuilder
     *     .create("higress-mcp")
     *     .streamableHttpEndpoint(endpoint)
     *     .buildSync();
     * }</pre>
     *
     * @return configured and initialized HigressMcpClientWrapper instance
     *         (已配置并初始化的HigressMcpClientWrapper实例)
     * @throws IllegalArgumentException if endpoint is not configured (如果端点未配置)
     * @throws IllegalStateException if transport type is not configured (如果传输类型未配置)
     */
    public HigressMcpClientWrapper buildSync() {
        // Validate configuration
        validateConfiguration();

        // Build the underlying MCP client using agentscope's McpClientBuilder
        McpClientBuilder mcpClientBuilder = createMcpClientBuilder();

        // Build sync delegate client
        McpClientWrapper delegateClient = mcpClientBuilder.buildSync();

        // Create the Higress wrapper and initialize
        HigressMcpClientWrapper wrapper =
                new HigressMcpClientWrapper(
                        clientName,
                        delegateClient,
                        enableToolSearch,
                        toolSearchQuery,
                        toolSearchTopK);

        wrapper.initialize().block();

        return wrapper;
    }

    /**
     * {@summary Validates the builder configuration (验证构建器配置)}
     *
     * @throws IllegalArgumentException if endpoint is not configured or tool search query is missing
     *         (如果端点未配置或工具搜索查询缺失)
     * @throws IllegalStateException if transport type is not configured (如果传输类型未配置)
     */
    private void validateConfiguration() {
        if (endpoint == null || endpoint.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Endpoint must be configured via sseEndpoint() or streamableHttpEndpoint()");
        }
        if (transportType == null) {
            throw new IllegalStateException(
                    "Transport type must be configured via sseEndpoint() or"
                            + " streamableHttpEndpoint()");
        }
        if (enableToolSearch && (toolSearchQuery == null || toolSearchQuery.trim().isEmpty())) {
            throw new IllegalArgumentException(
                    "Query is required for tool search. Use toolSearch(query) to configure.");
        }
    }

    /**
     * {@summary Creates and configures the underlying McpClientBuilder (创建并配置底层McpClientBuilder)}
     *
     * @return configured McpClientBuilder instance (已配置的McpClientBuilder实例)
     */
    private McpClientBuilder createMcpClientBuilder() {
        McpClientBuilder mcpClientBuilder = McpClientBuilder.create(clientName);

        // Configure transport based on type
        switch (transportType) {
            case SSE -> mcpClientBuilder.sseTransport(endpoint);
            case STREAMABLE_HTTP -> mcpClientBuilder.streamableHttpTransport(endpoint);
        }

        // Configure headers
        if (!headers.isEmpty()) {
            mcpClientBuilder.headers(headers);
        }

        // Configure query parameters
        if (!queryParams.isEmpty()) {
            mcpClientBuilder.queryParams(queryParams);
        }

        // Configure timeouts
        mcpClientBuilder.timeout(timeout);
        mcpClientBuilder.initializationTimeout(initializationTimeout);

        return mcpClientBuilder;
    }

    /**
     * {@summary Transport type enumeration (传输类型枚举)}
     */
    private enum TransportType {
        /**
         * Server-Sent Events transport for stateful connections.
         * 用于有状态连接的服务器发送事件传输。
         */
        SSE,

        /**
         * Streamable HTTP transport for stateless connections.
         * 用于无状态连接的可流式HTTP传输。
         */
        STREAMABLE_HTTP
    }
}

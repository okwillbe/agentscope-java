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

import io.agentscope.core.tool.Toolkit;
import io.agentscope.core.tool.ToolkitConfig;
import io.agentscope.core.tool.mcp.McpClientWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * {@summary Toolkit extension for Higress AI Gateway integration (Higress AI网关集成的工具包扩展)}
 *
 * <p>This toolkit extends {@link Toolkit} to provide Higress-specific functionality
 * while inheriting all standard toolkit features.
 * <p>此工具包扩展{@link Toolkit}以提供Higress特定功能，同时继承所有标准工具包特性。
 *
 * <h2>Example Usage (使用示例)</h2>
 * <pre>{@code
 * // Create Higress MCP client (创建Higress MCP客户端)
 * HigressMcpClientWrapper client = HigressMcpClientBuilder
 *     .create("higress")
 *     .streamableHttpEndpoint("http://gateway/mcp-servers/union-tools-search")
 *     .build();
 *
 * // Register with toolkit (注册到工具包)
 * HigressToolkit toolkit = new HigressToolkit();
 * toolkit.registerMcpClient(client).block();
 *
 * // Or use fluent API for more options (或使用流式API获得更多选项)
 * toolkit.registration()
 *     .mcpClient(client)
 *     .enableTools(List.of("tool1", "tool2"))
 *     .group("myGroup")
 *     .apply();
 * }</pre>
 *
 * @see Toolkit
 * @see HigressMcpClientWrapper
 * @see HigressMcpClientBuilder
 */
public class HigressToolkit extends Toolkit {

    private static final Logger logger = LoggerFactory.getLogger(HigressToolkit.class);

    /**
     * {@summary Reference to the registered Higress MCP client (已注册的Higress MCP客户端的引用)}
     */
    private HigressMcpClientWrapper higressMcpClient;

    /**
     * {@summary Creates a new HigressToolkit with default configuration (使用默认配置创建新的HigressToolkit)}
     */
    public HigressToolkit() {
        super();
    }

    /**
     * {@summary Creates a new HigressToolkit with custom configuration (使用自定义配置创建新的HigressToolkit)}
     *
     * @param config the toolkit configuration (工具包配置)
     */
    public HigressToolkit(ToolkitConfig config) {
        super(config);
    }

    /**
     * {@summary Registers an MCP client with the toolkit (向工具包注册MCP客户端)}
     *
     * <p>If the client is a {@link HigressMcpClientWrapper}, it will be cached
     * for later access via {@link #getHigressMcpClient()}.
     * <p>如果客户端是{@link HigressMcpClientWrapper}，它将被缓存以供后续通过{@link #getHigressMcpClient()}访问。
     *
     * <p>For advanced registration options (filtering, groups), use the fluent API
     * (对于高级注册选项(过滤、分组)，请使用流式API):
     * <pre>{@code
     * toolkit.registration()
     *     .mcpClient(client)
     *     .enableTools(List.of("tool1"))
     *     .group("myGroup")
     *     .apply();
     * }</pre>
     *
     * @param mcpClientWrapper the MCP client wrapper to register (要注册的MCP客户端包装器)
     * @return Mono that completes when registration is finished (注册完成时完成的Mono)
     */
    @Override
    public Mono<Void> registerMcpClient(McpClientWrapper mcpClientWrapper) {
        return super.registerMcpClient(mcpClientWrapper)
                .doOnSuccess(unused -> cacheHigressClient(mcpClientWrapper));
    }

    private void cacheHigressClient(McpClientWrapper mcpClientWrapper) {
        if (mcpClientWrapper instanceof HigressMcpClientWrapper higressClient) {
            this.higressMcpClient = higressClient;
            logger.info(
                    "Higress MCP client '{}' registered successfully", mcpClientWrapper.getName());
        }
    }

    /**
     * {@summary Gets the registered Higress MCP client (获取已注册的Higress MCP客户端)}
     *
     * @return the Higress MCP client, or null if not registered (Higress MCP客户端，如果未注册则为null)
     */
    public HigressMcpClientWrapper getHigressMcpClient() {
        return higressMcpClient;
    }
}

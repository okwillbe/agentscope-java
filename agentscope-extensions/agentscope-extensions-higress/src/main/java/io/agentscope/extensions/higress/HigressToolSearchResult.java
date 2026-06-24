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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.agentscope.core.util.JsonException;
import io.agentscope.core.util.JsonUtils;
import io.modelcontextprotocol.spec.McpSchema;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@summary Represents the result of calling the x_higress_tool_search tool (表示调用x_higress_tool_search工具的结果)}
 *
 * <p>The x_higress_tool_search tool returns a list of recommended tools based on
 * semantic similarity to the query. This class provides methods to parse and access
 * the search results.
 * <p>x_higress_tool_search工具根据查询的语义相似度返回推荐工具列表。此类提供解析和访问搜索结果的方法。
 *
 * <p>Example response structure from Higress (Higress响应结构示例):
 * <pre>{@code
 * {
 *   "content": [
 *     {
 *       "type": "text",
 *       "text": "{\"tools\":[...]}"
 *     }
 *   ],
 *   "structuredContent": {
 *     "tools": [
 *       {
 *         "name": "map___maps_weather",
 *         "description": "query weather for a specified city",
 *         "title": "maps_weather",
 *         "inputSchema": {...},
 *         "outputSchema": {}
 *       }
 *     ]
 *   }
 * }
 * }</pre>
 *
 */
public class HigressToolSearchResult {

    private static final Logger logger = LoggerFactory.getLogger(HigressToolSearchResult.class);

    private final List<ToolInfo> tools;
    private final boolean success;
    private final String errorMessage;

    private HigressToolSearchResult(List<ToolInfo> tools, boolean success, String errorMessage) {
        this.tools = tools != null ? tools : Collections.emptyList();
        this.success = success;
        this.errorMessage = errorMessage;
    }

    /**
     * {@summary Parses the tool search result from MCP CallToolResult (从MCP CallToolResult解析工具搜索结果)}
     *
     * @param callToolResult the result from calling x_higress_tool_search (调用x_higress_tool_search的结果)
     * @return parsed HigressToolSearchResult (解析后的HigressToolSearchResult)
     */
    public static HigressToolSearchResult parse(McpSchema.CallToolResult callToolResult) {
        if (callToolResult == null) {
            return error("CallToolResult is null");
        }

        if (Boolean.TRUE.equals(callToolResult.isError())) {
            return error("Tool call returned error");
        }

        // Try to parse from structuredContent first
        Object structuredContent = callToolResult.structuredContent();
        if (structuredContent instanceof Map<?, ?> structuredMap) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> typedMap = (Map<String, Object>) structuredMap;
                return parseFromStructuredContent(typedMap);
            } catch (Exception e) {
                logger.debug(
                        "Failed to parse structuredContent, trying content: {}", e.getMessage());
            }
        }

        // Fall back to parsing from content
        if (callToolResult.content() != null && !callToolResult.content().isEmpty()) {
            try {
                return parseFromContent(callToolResult.content());
            } catch (Exception e) {
                logger.error("Failed to parse content: {}", e.getMessage(), e);
                return error("Failed to parse tool search result: " + e.getMessage());
            }
        }

        return error("No content in tool search result");
    }

    /**
     * {@summary Parses from structuredContent map (从structuredContent映射解析)}
     */
    @SuppressWarnings("unchecked")
    private static HigressToolSearchResult parseFromStructuredContent(
            Map<String, Object> structuredContent) {
        Object toolsObj = structuredContent.get("tools");
        if (toolsObj instanceof List<?> toolsList) {
            List<ToolInfo> tools =
                    toolsList.stream()
                            .filter(item -> item instanceof Map)
                            .map(item -> parseToolInfo((Map<String, Object>) item))
                            .toList();
            return success(tools);
        }
        throw new IllegalArgumentException("Invalid structuredContent format");
    }

    /**
     * {@summary Parses from content list (text content) (从内容列表解析(文本内容))}
     */
    private static HigressToolSearchResult parseFromContent(List<McpSchema.Content> contentList)
            throws JsonException {
        for (McpSchema.Content content : contentList) {
            if (content instanceof McpSchema.TextContent textContent) {
                String text = textContent.text();
                if (text != null && !text.isEmpty()) {
                    ToolSearchResponse response =
                            JsonUtils.getJsonCodec().fromJson(text, ToolSearchResponse.class);
                    if (response.tools != null) {
                        return success(response.tools);
                    }
                }
            }
        }
        throw new IllegalArgumentException("No valid text content found");
    }

    /**
     * {@summary Parses a single tool info from a map (从映射解析单个工具信息)}
     */
    @SuppressWarnings("unchecked")
    private static ToolInfo parseToolInfo(Map<String, Object> map) {
        return new ToolInfo(
                (String) map.get("name"),
                (String) map.get("description"),
                (String) map.get("title"),
                (Map<String, Object>) map.get("inputSchema"),
                (Map<String, Object>) map.get("outputSchema"));
    }

    private static HigressToolSearchResult success(List<ToolInfo> tools) {
        return new HigressToolSearchResult(tools, true, null);
    }

    private static HigressToolSearchResult error(String message) {
        return new HigressToolSearchResult(Collections.emptyList(), false, message);
    }

    /**
     * {@summary Returns whether the search was successful (返回搜索是否成功)}
     *
     * @return true if successful (如果成功则返回true)
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * {@summary Returns the error message if the search failed (如果搜索失败则返回错误消息)}
     *
     * @return error message, or null if successful (错误消息，如果成功则为null)
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * {@summary Returns the list of recommended tools (返回推荐工具列表)}
     *
     * @return list of tools, never null but may be empty (工具列表，永不为null但可能为空)
     */
    public List<ToolInfo> getTools() {
        return tools;
    }

    /**
     * {@summary Returns the names of all found tools (返回所有找到的工具名称)}
     *
     * @return list of tool names (工具名称列表)
     */
    public List<String> getToolNames() {
        return tools.stream().map(ToolInfo::name).toList();
    }

    @Override
    public String toString() {
        if (!success) {
            return "HigressToolSearchResult{error='" + errorMessage + "'}";
        }
        return "HigressToolSearchResult{tools=" + getToolNames() + "}";
    }

    /**
     * {@summary Represents a single tool returned from the search (表示搜索返回的单个工具)}
     *
     * @param name the tool name (e.g., "map___maps_weather") (工具名称)
     * @param description the tool description (工具描述)
     * @param title the tool title (e.g., "maps_weather") (工具标题)
     * @param inputSchema the input parameter JSON schema (输入参数JSON模式)
     * @param outputSchema the output parameter JSON schema (输出参数JSON模式)
     */
    public record ToolInfo(
            String name,
            String description,
            String title,
            Map<String, Object> inputSchema,
            Map<String, Object> outputSchema) {}

    /**
     * {@summary Internal class for JSON deserialization (用于JSON反序列化的内部类)}
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ToolSearchResponse {
        @JsonProperty("tools")
        List<ToolInfo> tools;
    }
}

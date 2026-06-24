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
package io.agentscope.core.agui.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
 * Represents a message in the AG-UI protocol.
 *
 * <p>Messages are the primary communication unit in the AG-UI protocol.
 * They contain content, role information, and optionally tool calls or tool call IDs.
 *
 * <p>Message roles:
 * <ul>
 *   <li>user - Messages from the user</li>
 *   <li>assistant - Messages from the AI assistant</li>
 *   <li>system - System instructions</li>
 *   <li>tool - Tool execution results</li>
 * </ul>
 */
public class AguiMessage {

    private final String id;
    private final String role;
    private final String content;
    private final List<AguiToolCall> toolCalls;
    private final String toolCallId;

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Creates a new AguiMessage.
     *
     * @param id The unique message ID
     * @param role The message role (user, assistant, system, tool)
     * @param content The message content
     * @param toolCalls Tool calls for assistant messages (optional)
     * @param toolCallId Tool call ID for tool messages (optional)
     */
    @JsonCreator
    public AguiMessage(
            @JsonProperty("id") String id,
            @JsonProperty("role") String role,
            @JsonProperty("content") String content,
            @JsonProperty("toolCalls") List<AguiToolCall> toolCalls,
            @JsonProperty("toolCallId") String toolCallId) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.role = Objects.requireNonNull(role, "role cannot be null");
        this.content = content;
        this.toolCalls =
                toolCalls != null
                        ? Collections.unmodifiableList(toolCalls)
                        : Collections.emptyList();
        this.toolCallId = toolCallId;
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Creates a simple user message.
     *
     * @param id The message ID
     * @param content The message content
     * @return A new user message
     */
    public static AguiMessage userMessage(String id, String content) {
        return new AguiMessage(id, "user", content, null, null);
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Creates a simple assistant message.
     *
     * @param id The message ID
     * @param content The message content
     * @return A new assistant message
     */
    public static AguiMessage assistantMessage(String id, String content) {
        return new AguiMessage(id, "assistant", content, null, null);
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Creates a system message.
     *
     * @param id The message ID
     * @param content The message content
     * @return A new system message
     */
    public static AguiMessage systemMessage(String id, String content) {
        return new AguiMessage(id, "system", content, null, null);
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Creates a tool result message.
     *
     * @param id The message ID
     * @param toolCallId The ID of the tool call this is responding to
     * @param content The tool result content
     * @return A new tool message
     */
    public static AguiMessage toolMessage(String id, String toolCallId, String content) {
        return new AguiMessage(id, "tool", content, null, toolCallId);
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Get the message ID.
     *
     * @return The message ID
     */
    public String getId() {
        return id;
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Get the message role.
     *
     * @return The role (user, assistant, system, tool)
     */
    public String getRole() {
        return role;
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Get the message content.
     *
     * @return The content, may be null
     */
    public String getContent() {
        return content;
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Get the tool calls (for assistant messages).
     *
     * @return The tool calls as an immutable list, empty if none
     */
    public List<AguiToolCall> getToolCalls() {
        return toolCalls;
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Get the tool call ID (for tool messages).
     *
     * @return The tool call ID, or null if not a tool message
     */
    public String getToolCallId() {
        return toolCallId;
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Check if this is a user message.
     *
     * @return true if role is "user"
     */
    public boolean isUserMessage() {
        return "user".equals(role);
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Check if this is an assistant message.
     *
     * @return true if role is "assistant"
     */
    public boolean isAssistantMessage() {
        return "assistant".equals(role);
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Check if this is a system message.
     *
     * @return true if role is "system"
     */
    public boolean isSystemMessage() {
        return "system".equals(role);
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Check if this is a tool message.
     *
     * @return true if role is "tool"
     */
    public boolean isToolMessage() {
        return "tool".equals(role);
    }

    /**
     * {@summary Represents a message in the AG-UI protocol. (Represents a message in the AG-UI protocol.)}
     * Check if this message has tool calls.
     *
     * @return true if tool calls are present
     */
    public boolean hasToolCalls() {
        return toolCalls != null && !toolCalls.isEmpty();
    }

    @Override
    public String toString() {
        return "AguiMessage{id='"
                + id
                + "', role='"
                + role
                + "', content='"
                + content
                + "', toolCalls="
                + toolCalls
                + ", toolCallId='"
                + toolCallId
                + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AguiMessage that = (AguiMessage) o;
        return Objects.equals(id, that.id)
                && Objects.equals(role, that.role)
                && Objects.equals(content, that.content)
                && Objects.equals(toolCalls, that.toolCalls)
                && Objects.equals(toolCallId, that.toolCallId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, content, toolCalls, toolCallId);
    }
}

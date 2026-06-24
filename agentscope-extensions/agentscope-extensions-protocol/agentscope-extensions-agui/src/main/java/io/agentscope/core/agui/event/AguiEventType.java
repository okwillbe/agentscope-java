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
package io.agentscope.core.agui.event;

/**
 * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
 * Enumeration of all AG-UI protocol event types.
 */
public enum AguiEventType {
    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Indicates that an agent run has started.
     */
    RUN_STARTED,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Indicates that an agent run has finished.
     */
    RUN_FINISHED,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Indicates the start of a text message.
     */
    TEXT_MESSAGE_START,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Contains text content for a message.
     */
    TEXT_MESSAGE_CONTENT,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Indicates the end of a text message.
     */
    TEXT_MESSAGE_END,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Indicates the start of a tool call.
     */
    TOOL_CALL_START,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Contains arguments for a tool call.
     */
    TOOL_CALL_ARGS,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Indicates the end of a tool call.
     */
    TOOL_CALL_END,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Contains the result of a tool call.
     */
    TOOL_CALL_RESULT,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Contains a snapshot of the current state.
     */
    STATE_SNAPSHOT,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Contains a delta update to the state.
     */
    STATE_DELTA,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * A raw event with custom data.
     */
    RAW,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * A custom event with structured data.
     */
    CUSTOM,
    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Indicates the start of a reasoning/thinking phase.
     */
    REASONING_START,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Signals the start of a reasoning message.
     */
    REASONING_MESSAGE_START,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Contains a chunk of content in a streaming reasoning message.
     */
    REASONING_MESSAGE_CONTENT,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Signals the end of a reasoning message.
     */
    REASONING_MESSAGE_END,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * A convenience event to auto start/close reasoning messages.
     */
    REASONING_MESSAGE_CHUNK,

    /**
     * {@summary Enumeration of all AG-UI protocol event types. (Enumeration of all AG-UI protocol event types.)}
     * Indicates the end of a reasoning/thinking phase.
     */
    REASONING_END
}

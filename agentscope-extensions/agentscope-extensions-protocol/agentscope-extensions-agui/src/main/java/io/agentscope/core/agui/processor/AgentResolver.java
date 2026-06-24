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
package io.agentscope.core.agui.processor;

import io.agentscope.core.agent.Agent;
import io.agentscope.core.agui.AguiException;

/**
 * {@summary Interface for resolving agents from various sources. (Interface for resolving agents from various sources.)}
 * Interface for resolving agents from various sources.
 *
 * <p>This interface abstracts the agent resolution logic, allowing different
 * implementations for different scenarios (e.g., simple registry lookup,
 * session-based resolution with memory management).
 */
public interface AgentResolver {

    /**
     * {@summary Interface for resolving agents from various sources. (Interface for resolving agents from various sources.)}
     * Resolve an agent by its ID and thread ID.
     *
     * @param agentId The agent ID to resolve
     * @param threadId The thread ID for session management
     * @return The resolved agent
     * @throws AguiException.AgentNotFoundException if the agent is not found
     */
    Agent resolveAgent(String agentId, String threadId);

    /**
     * {@summary Interface for resolving agents from various sources. (Interface for resolving agents from various sources.)}
     * Check if a thread has existing memory/conversation history.
     *
     * <p>This is used to determine whether to use frontend-provided history
     * or rely on server-side memory.
     *
     * @param threadId The thread ID to check
     * @return true if the thread has existing memory
     */
    boolean hasMemory(String threadId);
}

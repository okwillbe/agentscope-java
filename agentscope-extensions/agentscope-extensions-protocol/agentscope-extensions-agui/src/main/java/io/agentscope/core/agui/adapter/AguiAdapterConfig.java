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
package io.agentscope.core.agui.adapter;

import io.agentscope.core.agui.model.ToolMergeMode;
import java.time.Duration;

/**
 * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
 * Configuration for the AG-UI agent adapter.
 *
 * <p>This class provides configuration options for how the adapter handles
 * tool merging, state events, and other behaviors.
 */
public class AguiAdapterConfig {

    private final ToolMergeMode toolMergeMode;
    private final boolean emitStateEvents;
    private final boolean emitToolCallArgs;
    private final boolean enableReasoning;
    private final Duration runTimeout;
    private final String defaultAgentId;

    private AguiAdapterConfig(Builder builder) {
        this.toolMergeMode = builder.toolMergeMode;
        this.emitStateEvents = builder.emitStateEvents;
        this.emitToolCallArgs = builder.emitToolCallArgs;
        this.enableReasoning = builder.enableReasoning;
        this.runTimeout = builder.runTimeout;
        this.defaultAgentId = builder.defaultAgentId;
    }

    /**
     * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
     * Get the tool merge mode.
     *
     * @return The tool merge mode
     */
    public ToolMergeMode getToolMergeMode() {
        return toolMergeMode;
    }

    /**
     * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
     * Check if state events should be emitted.
     *
     * @return true if state events should be emitted
     */
    public boolean isEmitStateEvents() {
        return emitStateEvents;
    }

    /**
     * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
     * Check if tool call arguments should be streamed.
     *
     * @return true if tool call args events should be emitted
     */
    public boolean isEmitToolCallArgs() {
        return emitToolCallArgs;
    }

    /**
     * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
     * Check if reasoning/thinking content should be emitted.
     *
     * <p>When enabled, ThinkingBlock content will be converted to REASONING_* events
     * according to the AG-UI Reasoning draft specification. When disabled (default),
     * ThinkingBlock content is ignored and no reasoning events are emitted.
     *
     * @return true if reasoning events should be emitted
     */
    public boolean isEnableReasoning() {
        return enableReasoning;
    }

    /**
     * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
     * Get the run timeout duration.
     *
     * @return The run timeout
     */
    public Duration getRunTimeout() {
        return runTimeout;
    }

    /**
     * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
     * Get the default agent ID.
     *
     * @return The default agent ID, or null if not set
     */
    public String getDefaultAgentId() {
        return defaultAgentId;
    }

    /**
     * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
     * Creates a new builder for AguiAdapterConfig.
     *
     * @return A new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
     * Creates a default configuration.
     *
     * @return Default configuration
     */
    public static AguiAdapterConfig defaultConfig() {
        return builder().build();
    }

    /**
     * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
     * Builder for AguiAdapterConfig.
     */
    public static class Builder {

        private ToolMergeMode toolMergeMode = ToolMergeMode.MERGE_FRONTEND_PRIORITY;
        private boolean emitStateEvents = true;
        private boolean emitToolCallArgs = true;
        private boolean enableReasoning = false;
        private Duration runTimeout = Duration.ofMinutes(10);
        private String defaultAgentId;

        /**
         * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
         * Set the tool merge mode.
         *
         * @param toolMergeMode The tool merge mode
         * @return This builder
         */
        public Builder toolMergeMode(ToolMergeMode toolMergeMode) {
            this.toolMergeMode = toolMergeMode;
            return this;
        }

        /**
         * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
         * Set whether to emit state events.
         *
         * @param emitStateEvents true to emit state events
         * @return This builder
         */
        public Builder emitStateEvents(boolean emitStateEvents) {
            this.emitStateEvents = emitStateEvents;
            return this;
        }

        /**
         * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
         * Set whether to emit tool call argument events.
         *
         * @param emitToolCallArgs true to emit tool call args events
         * @return This builder
         */
        public Builder emitToolCallArgs(boolean emitToolCallArgs) {
            this.emitToolCallArgs = emitToolCallArgs;
            return this;
        }

        /**
         * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
         * Set whether to enable reasoning/thinking content output.
         *
         * <p>When enabled, ThinkingBlock content will be converted to REASONING_* events
         * according to the AG-UI Reasoning draft specification. Default is false to ensure
         * backward compatibility and privacy compliance.
         *
         * @param enableReasoning true to enable reasoning events
         * @return This builder
         */
        public Builder enableReasoning(boolean enableReasoning) {
            this.enableReasoning = enableReasoning;
            return this;
        }

        /**
         * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
         * Set the run timeout duration.
         *
         * @param runTimeout The timeout duration
         * @return This builder
         */
        public Builder runTimeout(Duration runTimeout) {
            this.runTimeout = runTimeout;
            return this;
        }

        /**
         * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
         * Set the default agent ID.
         *
         * @param defaultAgentId The default agent ID
         * @return This builder
         */
        public Builder defaultAgentId(String defaultAgentId) {
            this.defaultAgentId = defaultAgentId;
            return this;
        }

        /**
         * {@summary Configuration for the AG-UI agent adapter. (Configuration for the AG-UI agent adapter.)}
         * Build the configuration.
         *
         * @return The built configuration
         */
        public AguiAdapterConfig build() {
            return new AguiAdapterConfig(this);
        }
    }
}

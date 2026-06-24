/*
 * Copyright 2024-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.agentscope.core.agent;

import io.agentscope.core.message.Msg;
import io.agentscope.core.tool.Toolkit;

/**
 * {@summary Complete agent interface combining all capabilities (完整的智能体接口，整合所有能力)}
 *
 * <p>This interface defines the core contract for agents, combining:
 * <p>此接口定义了智能体的核心契约，整合了：
 * <ul>
 *   <li>{@link CallableAgent} - Process messages and generate responses (处理消息并生成响应)</li>
 *   <li>{@link StreamableAgent} - Stream events during execution (执行期间流式传输事件)</li>
 *   <li>{@link ObservableAgent} - Observe messages without responding (观察消息但不响应)</li>
 * </ul>
 *
 * <p>Design Philosophy (设计哲学):
 * <ul>
 *   <li>Memory management is NOT part of the core Agent interface - it's the responsibility
 *       of specific agent implementations (e.g., ReActAgent)
 *   <p>内存管理不是核心Agent接口的一部分 - 它是特定智能体实现（如ReActAgent）的责任</li>
 *   <li>Structured output is a specialized capability provided by specific agents
 *   <p>结构化输出是特定智能体提供的专门能力</li>
 *   <li>Observe pattern allows agents to receive messages without generating a reply,
 *       enabling multi-agent collaboration
 *   <p>Observe模式允许智能体接收消息而不生成回复，从而支持多智能体协作</li>
 * </ul>
 *
 * <p>All agents in the AgentScope framework should implement this interface.
 * <p>AgentScope框架中的所有智能体都应实现此接口。
 *
 * <p><b>Reply contract (回复契约):</b> a single {@code call(...)} invocation produces exactly one
 * terminal {@link Msg}. Streaming variants (see {@link StreamableAgent}) may emit
 * many events but resolve to a single terminal Msg. This is enforced by the
 * {@code Mono<Msg>} return type on the call methods.
 * <p>单次{@code call(...)}调用生成且仅生成一条终结态的{@link Msg}。流式变体（见{@link StreamableAgent}）
 * 可能发出多个事件，但最终解析为一条终结态消息。这通过call方法上的{@code Mono<Msg>}返回类型强制保证。
 */
public interface Agent extends CallableAgent, StreamableAgent, ObservableAgent {

    /**
     * {@summary Get the unique identifier for this agent (获取此智能体的唯一标识符)}
     *
     * @return Agent ID (智能体ID)
     */
    String getAgentId();

    /**
     * {@summary Get the name of this agent (获取此智能体的名称)}
     *
     * @return Agent name (智能体名称)
     */
    String getName();

    /**
     * {@summary Get the description of this agent (获取此智能体的描述)}
     *
     * @return Agent description (智能体描述)
     */
    default String getDescription() {
        return "Agent(" + getAgentId() + ") " + getName();
    }

    /**
     * {@summary Interrupt the current agent execution (中断当前智能体执行)}
     *
     * <p>This method sets an interrupt flag that will be checked by the agent at appropriate
     * checkpoints during execution. The interruption is cooperative and may not take effect
     * immediately.
     * <p>此方法设置一个中断标志，智能体将在执行过程中的适当检查点检查该标志。
     * 中断是协作式的，可能不会立即生效。
     */
    void interrupt();

    /**
     * {@summary Interrupt the current agent execution with a user message (使用用户消息中断当前智能体执行)}
     *
     * <p>This method sets an interrupt flag and associates a user message with the interruption.
     * The interruption is cooperative and may not take effect immediately.
     * <p>此方法设置中断标志并将用户消息与中断关联。中断是协作式的，可能不会立即生效。
     *
     * @param msg User message associated with the interruption (与中断相关的用户消息)
     */
    void interrupt(Msg msg);

    /**
     * {@summary Returns the agent's runtime AgentState, or null if this agent type does not maintain one (返回智能体的运行时AgentState，如果此智能体类型不维护状态则返回null)}
     *
     * <p>This is the canonical access point used by tool methods declared with
     * {@code @Tool(stateInjected=true)}: the framework binds the live state to the
     * {@code AgentState} parameter at invocation time.
     * <p>这是使用{@code @Tool(stateInjected=true)}声明的工具方法的规范访问点：
     * 框架在调用时将活跃状态绑定到{@code AgentState}参数。
     */
    default io.agentscope.core.state.AgentState getAgentState() {
        return null;
    }

    /**
     * {@summary Returns the agent's live Toolkit, or null if this agent type does not maintain one (返回智能体的活跃Toolkit，如果此智能体类型不维护工具包则返回null)}
     *
     * <p>This is the <em>runtime</em> toolkit 閳?the same instance the agent uses when listing
     * available tools for the model and dispatching tool calls. Middleware that needs to register
     * tools dynamically (e.g., skill loaders) must use this accessor rather than any toolkit
     * reference captured at build time, because agents may deep-copy the toolkit during
     * construction.
     * <p>这是<em>运行时</em>工具包——智能体在为模型列出可用工具和分派工具调用时使用的同一实例。
     * 需要动态注册工具的中间件（例如技能加载器）必须使用此访问器，而不是在构建时捕获的任何工具包引用，
     * 因为智能体可能在构造过程中深度复制工具包。
     */
    default Toolkit getToolkit() {
        return null;
    }
}

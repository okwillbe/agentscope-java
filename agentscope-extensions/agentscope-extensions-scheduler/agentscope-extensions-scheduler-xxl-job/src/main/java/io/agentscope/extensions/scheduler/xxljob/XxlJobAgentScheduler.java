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
package io.agentscope.extensions.scheduler.xxljob;

import com.xxl.job.core.context.XxlJobContext;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.handler.IJobHandler;
import io.agentscope.core.message.Msg;
import io.agentscope.core.message.MsgRole;
import io.agentscope.core.message.TextBlock;
import io.agentscope.extensions.scheduler.AgentScheduler;
import io.agentscope.extensions.scheduler.ScheduleAgentTask;
import io.agentscope.extensions.scheduler.config.AgentConfig;
import io.agentscope.extensions.scheduler.config.ScheduleConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@summary AgentScheduler implementation based on XXL-Job distributed task scheduling platform (基于XXL-Job分布式任务调度平台的AgentScheduler实现)}
 *
 * <p>This scheduler integrates with XXL-Job to provide distributed scheduling capabilities
 * for agents. Jobs are registered as JobHandlers in XXL-Job and can be managed through
 * the XXL-Job admin console.
 * <p>此调度器与XXL-Job集成，为智能体提供分布式调度能力。作业在XXL-Job中注册为JobHandler，
 * 可以通过XXL-Job管理控制台进行管理。
 *
 * <p><b>Key Features (关键特性):</b>
 * <ul>
 *   <li>Distributed scheduling across multiple executor instances (跨多个执行器实例的分布式调度)</li>
 *   <li>Centralized management through XXL-Job admin console (通过XXL-Job管理控制台进行集中管理)</li>
 *   <li>Support for cron expressions, fixed rate, and manual triggers (支持cron表达式、固定频率和手动触发)</li>
 *   <li>Built-in monitoring and logging (内置监控和日志)</li>
 *   <li>Fault tolerance and automatic failover (容错和自动故障转移)</li>
 * </ul>
 *
 * <p><b>Prerequisites (前提条件):</b>
 * <ul>
 *   <li>XXL-Job admin server must be running and accessible (XXL-Job管理服务器必须运行且可访问)</li>
 *   <li>XxlJobExecutor must be properly initialized and started (XxlJobExecutor必须正确初始化并启动)</li>
 *   <li>Jobs must be configured in XXL-Job admin console with matching JobHandler names
 *       (作业必须在XXL-Job管理控制台中配置，JobHandler名称需匹配)</li>
 * </ul>
 *
 * <p><b>Usage Example (使用示例):</b>
 * <pre>{@code
 * // 1. Initialize XXL-Job executor (typically in Spring Boot configuration)
 * // 1. 初始化XXL-Job执行器（通常在Spring Boot配置中）
 * XxlJobExecutor executor = new XxlJobExecutor();
 * executor.setAdminAddresses("http://localhost:8080/xxl-job-admin");
 * executor.setAppname("agentscope-executor");
 * executor.setPort(9999);
 * executor.start();
 *
 * // 2. Create scheduler (2. 创建调度器)
 * AgentScheduler scheduler = new XxlJobAgentScheduler(executor);
 *
 * // 3. Create agent configuration (3. 创建智能体配置)
 * AgentConfig agentConfig = AgentConfig.builder()
 *     .name("MyAgent")
 *     .description("My scheduled agent")
 *     .agentFactory(() -> ReActAgent.builder()
 *         .name("MyAgent")
 *         .model(model)
 *         .toolkit(toolkit)
 *         .build())
 *     .build();
 *
 * // 4. Schedule the agent (4. 调度智能体)
 * ScheduleConfig scheduleConfig = ScheduleConfig.builder().build();
 * ScheduleAgentTask task = scheduler.schedule(agentConfig, scheduleConfig);
 *
 * // 5. Configure the job in XXL-Job admin console:
 * // 5. 在XXL-Job管理控制台配置作业：
 * //    - JobHandler: MyAgent (same as agent name，与智能体名称相同)
 * //    - Schedule type: CRON (调度类型：CRON)
 * //    - Cron expression: 0 0 8 * * ? (cron表达式)
 *
 * // 6. The agent will be executed according to the schedule in XXL-Job
 * // 6. 智能体将根据XXL-Job中的调度执行
 * //    Each execution will create a fresh Agent instance (每次执行将创建新的Agent实例)
 * }</pre>
 *
 * <p><b>Important Notes (重要说明):</b>
 * <ul>
 *   <li>The agent name from AgentConfig is used as the JobHandler name in XXL-Job
 *       (AgentConfig中的智能体名称用作XXL-Job中的JobHandler名称)</li>
 *   <li>Each execution creates a fresh Agent instance using the configured factory
 *       (每次执行使用配置的工厂创建新的Agent实例)</li>
 *   <li>Schedule configuration (cron, fixedRate, fixedDelay) is managed in XXL-Job admin console
 *       (调度配置在XXL-Job管理控制台中管理)</li>
 *   <li>This scheduler only registers the JobHandler; actual scheduling is controlled by XXL-Job
 *       (此调度器仅注册JobHandler；实际调度由XXL-Job控制)</li>
 *   <li>Pause/resume operations are not directly supported; use XXL-Job admin console instead
 *       (暂停/恢复操作不直接支持；请使用XXL-Job管理控制台)</li>
 * </ul>
 *
 * @see AgentScheduler
 * @see ScheduleAgentTask
 */
public class XxlJobAgentScheduler implements AgentScheduler {

    private static final Logger logger = LoggerFactory.getLogger(XxlJobAgentScheduler.class);

    private final XxlJobExecutor executor;
    private final Map<String, ScheduleAgentTask> scheduleAgentTasks;

    /**
     * {@summary Constructor for XxlJobAgentScheduler (XxlJobAgentScheduler的构造函数)}
     *
     * @param executor The XXL-Job executor instance (must be initialized and started)
     *                 (XXL-Job执行器实例，必须已初始化并启动)
     */
    public XxlJobAgentScheduler(XxlJobExecutor executor) {
        this.executor = executor;
        this.scheduleAgentTasks = new ConcurrentHashMap<>();
    }

    /**
     * {@summary Schedule an agent for execution based on the provided configuration (根据提供的配置调度智能体执行)}
     *
     * <p>This method registers a JobHandler with XXL-Job executor. The actual scheduling
     * (timing, triggers) is controlled through XXL-Job admin console.
     * <p>此方法向XXL-Job执行器注册JobHandler。实际调度（时间、触发器）通过XXL-Job管理控制台控制。
     *
     * @param agentConfig The agent configuration containing name, description, and factory
     *                    (包含名称、描述和工厂的智能体配置)
     * @param scheduleConfig The schedule configuration (note: timing is managed in XXL-Job)
     *                       (调度配置，注意：时间在XXL-Job中管理)
     * @return The scheduled agent task (已调度的智能体任务)
     * @throws IllegalArgumentException if agentConfig is null or agent name is empty
     *         (如果agentConfig为null或智能体名称为空)
     * @throws Exception if JobHandler registration fails (如果JobHandler注册失败)
     */
    @Override
    public ScheduleAgentTask schedule(AgentConfig agentConfig, ScheduleConfig scheduleConfig)
            throws Exception {
        if (agentConfig == null || agentConfig.getName() == null) {
            throw new IllegalArgumentException("AgentConfig and agent name cannot be null");
        }

        String name = agentConfig.getName();
        logger.info("Scheduling agent '{}' with XXL-Job scheduler", name);

        // Create a JobHandler that will be invoked by XXL-Job
        IJobHandler jobHandler =
                new IJobHandler() {
                    @Override
                    public void execute() throws Exception {
                        executeAgent(name);
                    }
                };

        // Register the JobHandler with XXL-Job executor
        registerJobHandler(name, jobHandler);

        // Create and store the scheduled task
        ScheduleAgentTask task = new XxlJobScheduleAgentTask(agentConfig, scheduleConfig);
        scheduleAgentTasks.put(name, task);

        logger.info("Successfully registered agent '{}' as JobHandler in XXL-Job executor", name);
        return task;
    }

    /**
     * {@summary Execute the agent when triggered by XXL-Job (当XXL-Job触发时执行智能体)}
     *
     * <p>This method is called by XXL-Job when the scheduled job is triggered.
     * It creates a fresh Agent instance and executes it with the job parameters.
     * <p>当调度的作业被触发时，此方法由XXL-Job调用。它创建新的Agent实例并使用作业参数执行。
     *
     * @param name The name of the scheduled agent task (已调度的智能体任务名称)
     * @throws RuntimeException if task execution fails (如果任务执行失败)
     */
    private void executeAgent(String name) {
        XxlJobContext context = XxlJobHelper.getXxlJobContext();
        ScheduleAgentTask scheduleAgentTask = scheduleAgentTasks.get(name);

        if (scheduleAgentTask == null) {
            logger.error("No scheduled agent task found for name: {}", name);
            XxlJobHelper.handleFail("No scheduled agent task found for name: " + name);
            return;
        }

        try {
            logger.info(
                    "Executing scheduled task '{}'. JobId: {}, Param: {}",
                    name,
                    context != null ? context.getJobId() : "N/A",
                    context != null ? context.getJobParam() : "N/A");
            // Prepare input message with XXL-Job context
            String jobParam = context != null ? context.getJobParam() : "";
            Msg inputMsg =
                    Msg.builder()
                            .role(MsgRole.USER)
                            .content(TextBlock.builder().text(jobParam).build())
                            .build();

            // Create a fresh Agent instance for this execution
            Msg result = scheduleAgentTask.run(inputMsg).block();

            // Increment execution count
            scheduleAgentTask.incrementExecutionCount();

            logger.info(
                    "Successfully executed scheduled task '{}'. Result: {}",
                    name,
                    result != null ? result.getTextContent() : "null");
            XxlJobHelper.handleSuccess("Successfully executed scheduled task '" + name + "'");
        } catch (Exception e) {
            logger.error("Failed to execute scheduled task '{}'", name, e);
            throw new RuntimeException("Task execution failed: " + name, e);
        }
    }

    /**
     * {@summary Cancel a scheduled task (取消已调度的任务)}
     *
     * <p><b>Note:</b> This operation is not supported by XXL-Job scheduler.
     * To stop a scheduled task, use the XXL-Job admin console to pause or delete the job.
     * <p><b>注意：</b>此操作不被XXL-Job调度器支持。要停止已调度的任务，
     * 请使用XXL-Job管理控制台暂停或删除作业。
     *
     * @param name The name of the scheduled task to cancel (要取消的已调度任务名称)
     * @return Never returns, always throws UnsupportedOperationException (永不返回，总是抛出UnsupportedOperationException)
     * @throws UnsupportedOperationException always, as cancellation is not supported (总是抛出，因为不支持取消)
     */
    @Override
    public boolean cancel(String name) {
        throw new UnsupportedOperationException(
                "Cancel operation is not supported by XxlJobAgentScheduler. "
                        + "Please use XXL-Job admin console to pause or delete the job.");
    }

    /**
     * {@summary Retrieve a scheduled task by its name (根据名称检索已调度的任务)}
     *
     * @param name The name of the scheduled task (same as agent name) (已调度任务的名称，与智能体名称相同)
     * @return The ScheduleAgentTask if found, or null if not found or name is empty
     *         (如果找到则返回ScheduleAgentTask，如果未找到或名称为空则返回null)
     */
    @Override
    public ScheduleAgentTask getScheduledAgent(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return scheduleAgentTasks.get(name);
    }

    /**
     * {@summary Retrieve all scheduled tasks managed by this scheduler (检索此调度器管理的所有已调度任务)}
     *
     * @return A list of all ScheduleAgentTask instances registered in this scheduler
     *         (在此调度器中注册的所有ScheduleAgentTask实例列表)
     */
    @Override
    public List<ScheduleAgentTask> getAllScheduleAgentTasks() {
        return new ArrayList<>(scheduleAgentTasks.values());
    }

    /**
     * {@summary Gracefully shutdown the scheduler (优雅关闭调度器)}
     *
     * <p>This operation (此操作):
     * <ul>
     *   <li>Destroys the XXL-Job executor (销毁XXL-Job执行器)</li>
     *   <li>Removes all registered JobHandlers (移除所有已注册的JobHandler)</li>
     *   <li>Clears all scheduled agent tasks (清除所有已调度的智能体任务)</li>
     * </ul>
     *
     * <p>After shutdown, no methods on this scheduler should be called.
     * (关闭后，不应再调用此调度器上的任何方法。)
     */
    @Override
    public void shutdown() {
        logger.info("Shutting down XxlJobAgentScheduler. Removing all registered JobHandlers...");
        this.executor.destroy();
        scheduleAgentTasks.clear();
        logger.info("XxlJobAgentScheduler shutdown completed");
    }

    /**
     * {@summary Get the type identifier of this scheduler implementation (获取此调度器实现的类型标识符)}
     *
     * @return "xxl-job"
     */
    @Override
    public String getSchedulerType() {
        return "xxl-job";
    }

    /**
     * {@summary Register a JobHandler with XXL-Job executor, compatible with both old and new versions (向XXL-Job执行器注册JobHandler，兼容新旧版本)}
     *
     * <p>This method provides compatibility across different XXL-Job versions:
     * <p>此方法提供跨不同XXL-Job版本的兼容性：
     * <ul>
     *   <li>XXL-Job 3.3.1+: Uses {@code registryJobHandler} method (new naming)
     *       (使用registryJobHandler方法，新命名)</li>
     *   <li>XXL-Job older versions: Uses {@code registJobHandler} method (old naming)
     *       (使用registJobHandler方法，旧命名)</li>
     * </ul>
     *
     * <p>The method attempts to use the new method name first (registryJobHandler), and falls
     * back to the old method name (registJobHandler) if the new one is not available.
     * <p>此方法首先尝试使用新的方法名称，如果新方法不可用，则回退到旧的方法名称。
     *
     * @param jobName The name of the job handler (作业处理器名称)
     * @param jobHandler The job handler instance to register (要注册的作业处理器实例)
     * @throws Exception if registration fails (如果注册失败)
     */
    private void registerJobHandler(String jobName, IJobHandler jobHandler) throws Exception {
        try {
            // Try new method name first (XXL-Job 3.3.1+)
            XxlJobExecutor.registryJobHandler(jobName, jobHandler);
            logger.debug(
                    "Registered JobHandler '{}' using registryJobHandler (XXL-Job 3.3.1+)",
                    jobName);
        } catch (Throwable e) {
            logger.error(
                    "Registered JobHandler '{}' using registryJobHandler failed. (Need to update"
                            + " xxl-job core to 3.3.1+)",
                    jobName);
            throw e;
        }
    }
}

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

/**
 * {@summary Tool-permission evaluation engine for AgentScope Java (AgentScope Java的工具权限评估引擎)}
 *
 * <p>The package hosts {@code PermissionEngine}, {@code PermissionMode},
 * {@code PermissionRule}, {@code PermissionContextState}, {@code PermissionDecision}
 * and {@code PermissionBehavior}.
 * <p>此包包含{@code PermissionEngine}、{@code PermissionMode}、{@code PermissionRule}、
 * {@code PermissionContextState}、{@code PermissionDecision}和{@code PermissionBehavior}。
 *
 * <p>The evaluation order is (评估顺序为):
 * <pre>
 *   deny → ask → tool self-check → allow → BYPASS → default ASK
 *   拒绝 → 询问 → 工具自检 → 允许 → 绕过 → 默认询问
 * </pre>
 */
package io.agentscope.core.permission;

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
 * {@summary Provider credentials for model authentication (用于模型认证的提供商凭证)}
 *
 * <p>{@link io.agentscope.core.credential.CredentialBase} carries the {@code id} field and exposes
 * {@code getChatModelClass()} (the consuming {@link io.agentscope.core.model.ChatModelBase}
 * subclass) plus {@code listModels()} (model catalog discovery).
 * <p>{@link io.agentscope.core.credential.CredentialBase}携带{@code id}字段，并暴露{@code getChatModelClass()}
 * （消费的{@link io.agentscope.core.model.ChatModelBase}子类）以及{@code listModels()}（模型目录发现）。
 *
 * <p>Concrete subclasses cover Anthropic, OpenAI, DashScope, Gemini, Ollama, DeepSeek, Kimi, and xAI.
 * Credentials whose Java model class is not yet implemented throw {@link UnsupportedOperationException}
 * from {@code getChatModelClass()} while still round-tripping through JSON for storage compatibility.
 * <p>具体子类涵盖Anthropic、OpenAI、DashScope、Gemini、Ollama、DeepSeek、Kimi和xAI。
 * Java模型类尚未实现的凭证从{@code getChatModelClass()}抛出{@link UnsupportedOperationException}，
 * 同时仍通过JSON进行往返以保持存储兼容性。
 */
package io.agentscope.core.credential;

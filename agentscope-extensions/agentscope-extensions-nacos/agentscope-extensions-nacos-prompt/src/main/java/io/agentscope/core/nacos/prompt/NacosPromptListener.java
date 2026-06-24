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

package io.agentscope.core.nacos.prompt;

import com.alibaba.nacos.api.ai.AiService;
import com.alibaba.nacos.api.ai.listener.AbstractNacosPromptListener;
import com.alibaba.nacos.api.ai.listener.NacosPromptEvent;
import com.alibaba.nacos.api.ai.model.prompt.Prompt;
import com.alibaba.nacos.api.exception.NacosException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@summary Nacos prompt listener for managing prompt templates (用于管理提示模板的Nacos提示监听器)}
 *
 * <p>This class provides functionality to subscribe to and retrieve prompt templates
 * from Nacos AI service, with automatic updates when prompts change.
 * <p>此类提供订阅和从Nacos AI服务获取提示模板的功能，当提示更改时自动更新。
 */
public class NacosPromptListener {

    private static final Logger log = LoggerFactory.getLogger(NacosPromptListener.class);

    private static final Prompt EMPTY_SENTINEL = new Prompt("", "", "");

    private final AiService aiService;

    private final Map<String, Prompt> prompts;

    private final AbstractNacosPromptListener internalListener =
            new AbstractNacosPromptListener() {
                @Override
                public void onEvent(NacosPromptEvent event) {
                    if (event == null) {
                        return;
                    }
                    String key = event.getPromptKey();
                    Prompt prompt = event.getPrompt();
                    if (key == null || key.isEmpty()) {
                        log.warn("Received prompt event with null or empty promptKey");
                        return;
                    }
                    if (prompt != null && prompt.getTemplate() != null) {
                        prompts.put(key, prompt);
                        log.info(
                                "Prompt updated for key: {}, version: {}",
                                key,
                                prompt.getVersion());
                    } else {
                        log.warn(
                                "Received prompt event with null prompt or template for key: {}",
                                key);
                    }
                }
            };

    /**
     * {@summary Constructs a new NacosPromptListener (构造新的NacosPromptListener)}
     *
     * @param aiService the Nacos AI service instance (Nacos AI服务实例)
     */
    public NacosPromptListener(AiService aiService) {
        this.aiService = aiService;
        this.prompts = new ConcurrentHashMap<>(10);
    }

    /**
     * {@summary Get prompt template by key (根据键获取提示模板)}
     *
     * @param promptKey the prompt key (提示键)
     * @return rendered prompt string (渲染后的提示字符串)
     * @throws NacosException if Nacos service error occurs (如果Nacos服务出错)
     */
    public String getPrompt(String promptKey) throws NacosException {
        return getPrompt(promptKey, null);
    }

    /**
     * {@summary Get prompt template with arguments (带参数获取提示模板)}
     *
     * @param promptKey the prompt key (提示键)
     * @param args the template variables for rendering (用于渲染的模板变量)
     * @return rendered prompt string (渲染后的提示字符串)
     * @throws NacosException if Nacos service error occurs (如果Nacos服务出错)
     */
    public String getPrompt(String promptKey, Map<String, String> args) throws NacosException {
        return getPrompt(promptKey, args, null);
    }

    /**
     * {@summary Get prompt template with optional default value (带可选默认值获取提示模板)}
     *
     * @param promptKey the prompt key (提示键)
     * @param args the template variables for rendering (用于渲染的模板变量)
     * @param defaultValue the default value to use if prompt not found in Nacos (如果在Nacos中找不到提示时使用的默认值)
     * @return rendered prompt string or default value (渲染后的提示字符串或默认值)
     * @throws NacosException if Nacos service error occurs (如果Nacos服务出错)
     */
    public String getPrompt(String promptKey, Map<String, String> args, String defaultValue)
            throws NacosException {
        return getPrompt(promptKey, null, null, args, defaultValue);
    }

    /**
     * {@summary Get prompt template with version/label targeting and optional default value (带版本/标签定位和可选默认值获取提示模板)}
     *
     * @param promptKey the prompt key (提示键)
     * @param version target prompt version (e.g. "1.0.0"), mutually exclusive with label (目标提示版本，与标签互斥)
     * @param label target prompt label (e.g. "prod"), mutually exclusive with version (目标提示标签，与版本互斥)
     * @param args the template variables for rendering (用于渲染的模板变量)
     * @param defaultValue the default value to use if prompt not found in Nacos (如果在Nacos中找不到提示时使用的默认值)
     * @return rendered prompt string or default value (渲染后的提示字符串或默认值)
     * @throws NacosException if Nacos service error occurs (如果Nacos服务出错)
     */
    public String getPrompt(
            String promptKey,
            String version,
            String label,
            Map<String, String> args,
            String defaultValue)
            throws NacosException {

        Prompt prompt = prompts.get(promptKey);
        if (prompt == null) {
            try {
                prompt = subscribeAndLoad(promptKey, version, label);
            } catch (NacosException e) {
                log.error("Failed to load prompt from Nacos for key: {}", promptKey, e);
                if (defaultValue != null) {
                    log.info("Using default value for prompt key: {}", promptKey);
                    return renderDefault(defaultValue, args);
                }
                throw e;
            }
            prompts.putIfAbsent(promptKey, prompt);
            prompt = prompts.get(promptKey);
        }

        if (prompt == EMPTY_SENTINEL
                || prompt.getTemplate() == null
                || prompt.getTemplate().isEmpty()) {
            if (defaultValue != null) {
                log.info("Using default value for prompt key: {}", promptKey);
                return renderDefault(defaultValue, args);
            }
            return "";
        }

        return prompt.render(args);
    }

    private Prompt subscribeAndLoad(String promptKey, String version, String label)
            throws NacosException {
        Prompt prompt = aiService.subscribePrompt(promptKey, version, label, internalListener);
        if (prompt != null) {
            log.info("Loaded prompt for key: {}, version: {}", promptKey, prompt.getVersion());
            return prompt;
        }
        log.warn("Prompt not found in Nacos for key: {}", promptKey);
        return EMPTY_SENTINEL;
    }

    private String renderDefault(String defaultValue, Map<String, String> args) {
        if (args == null || args.isEmpty()) {
            return defaultValue;
        }
        Prompt fallback = new Prompt(null, null, defaultValue);
        return fallback.render(args);
    }
}

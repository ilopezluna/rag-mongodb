package com.example.rag.mongodb;

import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.ollama.OllamaContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ValidatorAgentConfiguration {

    @Bean
    public ValidatorAgent validator(OllamaContainer ollamaContainer) {
        return AiServices.builder(ValidatorAgent.class)
                .chatLanguageModel(OllamaChatModel.builder()
                        .baseUrl(ollamaContainer.getEndpoint())
                        .modelName("llama3.1:8b")
                        .build())
                .build();
    }
}

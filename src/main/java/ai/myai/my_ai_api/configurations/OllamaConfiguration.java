package ai.myai.my_ai_api.configurations;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@EnableConfigurationProperties(OllamaConfiguration.OllamaProperties.class)
@RequiredArgsConstructor
@Slf4j
public class OllamaConfiguration {

    final OllamaProperties properties;

    @Bean
    @Qualifier("ollama")
    public ChatLanguageModel ollamaChatLanguageModel() {
        return OllamaChatModel.builder()
                .baseUrl(properties.baseUrl())
                .modelName("llama3.1:8b")
                .build();
    }

    @Validated
    @ConfigurationProperties(prefix = "ollama")
    public record OllamaProperties(String baseUrl, String model) {}
}

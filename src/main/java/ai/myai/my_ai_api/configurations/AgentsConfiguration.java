package ai.myai.my_ai_api.configurations;

import ai.myai.my_ai_api.agents.ChatAgent;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AgentsConfiguration {

    @Bean(name = "straight")
    ChatAgent straight(ChatLanguageModel chatLanguageModel) {
        return AiServices.builder(ChatAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .build();
    }

    @Bean(name = "ragged")
    ChatAgent ragged(ChatLanguageModel chatLanguageModel, EmbeddingStore<TextSegment> embeddingStore) {
        return AiServices.builder(ChatAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .build();
    }
}
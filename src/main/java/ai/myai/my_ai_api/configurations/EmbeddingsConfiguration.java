package ai.myai.my_ai_api.configurations;

import com.mongodb.client.MongoClient;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2q.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.mongodb.IndexMapping;
import dev.langchain4j.store.embedding.mongodb.MongoDbEmbeddingStore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@Slf4j
public class EmbeddingsConfiguration {

    @Bean
    EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2QuantizedEmbeddingModel();
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(MongoClient client) {
        return MongoDbEmbeddingStore.builder()
                .fromClient(client)
                .databaseName("test_database")
                .collectionName("test_collection")
                .indexName("test_index")
                .indexMapping(IndexMapping.builder()
                        .dimension(384)
                        .metadataFieldNames(Sets.newHashSet("test-key"))
                        .build())
                .createIndex(true)
                .build();
    }
}

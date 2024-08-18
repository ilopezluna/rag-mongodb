package ai.myai.my_ai_api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.ollama.OllamaContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    public MongoDBAtlasContainer mongoDBAtlasContainer(DynamicPropertyRegistry registry) {
        MongoDBAtlasContainer mongoDBAtlasContainer = new MongoDBAtlasContainer().withReuse(true);
        registry.add("mongodb.url", mongoDBAtlasContainer::getConnectionString);
        registry.add("mongodb.username", () -> "root");
        registry.add("mongodb.password", () -> "root");
        registry.add("mongodb.database", () -> "admin");
        return mongoDBAtlasContainer;
    }

    @Bean
    public OllamaContainer ollama(DynamicPropertyRegistry registry) {
        var ollamaContainer =
                new OllamaContainer(DockerImageName.parse("ilopezluna/ollama-llama3.1:0.3.6-8b")
                        .asCompatibleSubstituteFor("ollama/ollama"))
                        .withLabel("com.testcontainers.desktop.service", "ollama")
                        .withReuse(true);
        registry.add("ollama.baseUrl", () -> "http://%s:%d".formatted(ollamaContainer.getHost(), ollamaContainer.getFirstMappedPort()));
        return ollamaContainer;
    }
}

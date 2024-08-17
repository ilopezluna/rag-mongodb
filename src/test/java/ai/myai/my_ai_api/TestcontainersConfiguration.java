package ai.myai.my_ai_api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ollama.OllamaContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        var image = DockerImageName.parse("pgvector/pgvector:pg16").asCompatibleSubstituteFor("postgres");
        return new PostgreSQLContainer<>(image).withLabel("com.testcontainers.desktop.service", "vector")
                .withReuse(true);
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

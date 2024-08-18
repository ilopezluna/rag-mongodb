package ai.myai.my_ai_api.configurations;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@EnableConfigurationProperties(MongoDBConfiguration.MongoDBProperties.class)
@RequiredArgsConstructor
@Slf4j
public class MongoDBConfiguration {

    final MongoDBProperties properties;

    @Bean
    public MongoClient mongoClient() {
        MongoCredential credential = MongoCredential.createCredential(properties.username, properties.database, properties.password.toCharArray());
        return MongoClients.create(
                MongoClientSettings.builder()
                        .credential(credential)
                        .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
                        .applyConnectionString(new ConnectionString(properties.url))
                        .build());
    }

    @Validated
    @ConfigurationProperties(prefix = "mongodb")
    public record MongoDBProperties(String url, String database, String username, String password) {
    }
}

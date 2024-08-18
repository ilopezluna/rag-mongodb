package com.example.rag.mongodb.api;

import com.example.rag.mongodb.Application;
import com.example.rag.mongodb.TestcontainersConfiguration;
import com.example.rag.mongodb.ValidatorAgent;
import com.example.rag.mongodb.ValidatorAgentConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = {
                Application.class,
                TestcontainersConfiguration.class,
                ValidatorAgentConfiguration.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ChatControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    protected ValidatorAgent validatorAgent;

    static String question = "what is a bifidus?";
    static String reference = """
            - Answer must indicate that bifidus is an another way to say buddy.
            """;

    @Test
    void straight() {
        String answer  = webTestClient.get().uri("/chat/straight?question=%s".formatted(question)).exchange().expectBody(ChatController.ChatResponse.class).returnResult().getResponseBody().message();
        ValidatorAgent.ValidatorResponse validate = validatorAgent.validate(question, answer, reference);
        assertThat(validate.response()).isEqualTo("no");
    }

    @Test
    void ragged() {
        String answer  = webTestClient.get().uri("/chat/ragged?question=%s".formatted(question)).exchange().expectBody(ChatController.ChatResponse.class).returnResult().getResponseBody().message();
        ValidatorAgent.ValidatorResponse validate = validatorAgent.validate(question, answer, reference);
        assertThat(validate.response()).isEqualTo("yes");
    }
}
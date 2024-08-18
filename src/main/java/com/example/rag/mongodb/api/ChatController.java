package com.example.rag.mongodb.api;

import com.example.rag.mongodb.agents.ChatAgent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@Slf4j
@AllArgsConstructor
public class ChatController {

    final ChatAgent straight;

    final ChatAgent ragged;

    @GetMapping("/straight")
    ChatResponse straight(@RequestParam String question) {
        return new ChatResponse(straight.chat(question));
    }

    @GetMapping("/ragged")
    ChatResponse ragged(@RequestParam String question) {
        return new ChatResponse(ragged.chat(question));
    }


    public record ChatResponse(String message) {}
}

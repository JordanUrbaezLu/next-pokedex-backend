package com.authapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ChatGPTService {
    private final RestTemplate rest = new RestTemplate();
    private final String apiKey;

    public ChatGPTService(@Value("${openai.api.key}") String apiKey) {
        this.apiKey = apiKey;
    }

    private HttpHeaders headers() {
        HttpHeaders h = new HttpHeaders();
        h.setBearerAuth(apiKey);
        h.setContentType(MediaType.APPLICATION_JSON);
        return h;
    }

    /** Returns true if ChatGPT answers “YES” to whether this is Pokémon-related. */
    public boolean isPokemonRelated(String question) {
        Map<String,Object> payload = Map.of(
            "model", "gpt-3.5-turbo",
            "messages", List.of(
                Map.of("role", "system", "content", "You are a strict classifier. Your only job is to determine if the user's message is related to Pokémon.\n\nIf the user’s message sounds like they are describing a Pokémon, referencing Pokémon characters, abilities, or anything clearly related to the Pokémon world — respond with only:\n\nYES\n\nIf the message is unrelated, vague, or clearly about something outside the Pokémon universe (e.g. food, emotions, general questions) — respond with only:\n\nNO\n\nDo not explain. Only respond with YES or NO."), 
                Map.of("role","user","content", question)
            ),
            "max_tokens", 1,
            "temperature", 0.0
        );

        ResponseEntity<ChatResponse> resp = rest.postForEntity(
            "https://api.openai.com/v1/chat/completions",
            new HttpEntity<>(payload, headers()),
            ChatResponse.class
        );

        String reply = resp.getBody()
                           .choices.get(0)
                           .message
                           .content
                           .trim()
                           .toUpperCase();
        return reply.startsWith("Y");
    }

    // --- DTO for OpenAI response ---
    public static class ChatResponse {
        public List<Choice> choices;
        public static class Choice {
            public Message message;
        }
        public static class Message {
            public String role;
            public String content;
        }
    }
}

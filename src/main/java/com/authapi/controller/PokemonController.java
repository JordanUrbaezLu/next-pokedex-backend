package com.authapi.controller;

import com.authapi.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// DTOs
record QuestionRequest(String question) {}
record RelatedResponse(boolean related) {}

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    @Autowired
    private ChatGPTService chatGPT;

    /** 
     * POST /api/pokemon/related 
     * Body: { "question": "I am thinking of a pokemon..." }
     * Response: { "related": true }
     */
    @PostMapping("/related")
    public ResponseEntity<RelatedResponse> related(@RequestBody QuestionRequest req) {
        boolean isRelated = chatGPT.isPokemonRelated(req.question());
        return ResponseEntity.ok(new RelatedResponse(isRelated));
    }
}

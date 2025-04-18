package com.authapi.controller;

import com.authapi.service.ChatGPTService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// DTOs
record QuestionRequest(String question) {}
record RelatedResponse(boolean related) {}
record FindRequest(
  String question,
  List<String> pastDescriptions,
  List<String> previousGuesses
) {}
record AnswerResponse(String answer) {}

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

    /**
     * POST /api/pokemon/find
     * Request:  {
            "question": "It’s small and blue",
            "pastDescriptions": [
                "It has spikes on its back",
                "It breathes fire"
            ],
            "previousGuesses": [
                "Charmander",
                "Squirtle"
            ]
        }
     * Response: { "answer": "Sonnle" }
     */
    @PostMapping("/find")
    public ResponseEntity<AnswerResponse> find(@RequestBody FindRequest req) {
        String pokemon = chatGPT.findPokemon(
          req.question(),
          req.pastDescriptions(),
          req.previousGuesses()
        );
        return ResponseEntity.ok(new AnswerResponse(pokemon));
    }
}

package com.springarena.controller;

import com.springarena.dto.ChallengeDto;
import com.springarena.service.ChallengeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping
    public ResponseEntity<List<ChallengeDto>> listChallenges() {
        return ResponseEntity.ok(challengeService.listChallenges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDto> getChallenge(@PathVariable String id) {
        return challengeService.getChallenge(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

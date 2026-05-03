package com.springarena.controller;

import com.springarena.dto.RunRequest;
import com.springarena.dto.RunResponse;
import com.springarena.service.RunnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/challenges")
public class RunController {

    private final RunnerService runnerService;

    public RunController(RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @PostMapping("/{id}/run")
    public ResponseEntity<RunResponse> runChallenge(@PathVariable String id, @RequestBody RunRequest request) {
        RunResponse response = runnerService.runChallenge(id, request);
        return ResponseEntity.ok(response);
    }
}

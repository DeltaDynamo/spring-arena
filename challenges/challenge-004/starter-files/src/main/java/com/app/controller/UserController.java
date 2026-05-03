package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final List<Map<String, String>> users = List.of(
            Map.of("id", "1", "name", "Alice"),
            Map.of("id", "2", "name", "Bob"),
            Map.of("id", "3", "name", "Charlie"),
            Map.of("id", "4", "name", "Diana")
    );

    @GetMapping
    public ResponseEntity<List<Map<String, String>>> searchUsers(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // Implement filtering and pagination here.
        return ResponseEntity.status(501).body(List.of());
    }
}

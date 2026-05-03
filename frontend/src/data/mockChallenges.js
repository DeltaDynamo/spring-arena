export const mockChallenges = [
  {
    id: 'challenge-001',
    title: 'Build GET /users/{id}',
    description: 'Implement a simple user retrieval endpoint with 404 handling.',
    difficulty: 'easy',
    status: 'open',
    prompt:
      'Create a REST endpoint GET /users/{id} that returns user details for id 1 and a 404 response for any other id.',
    files: [
      {
        path: 'src/main/java/com/app/controller/UserController.java',
        content: `package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable String id) {
        if ("1".equals(id)) {
            return ResponseEntity.ok("{\"id\": \"1\", \"name\": \"Alice\"}");
        }
        return ResponseEntity.status(404).body("User not found");
    }
}
`,
      },
    ],
  },
  {
    id: 'challenge-002',
    title: 'POST /users validation',
    description: 'Add validation for a new user creation endpoint.',
    difficulty: 'medium',
    status: 'open',
    prompt:
      'Build POST /users with request validation. Reject invalid payloads with a 400 response and accept valid user data.',
    files: [
      {
        path: 'src/main/java/com/app/controller/UserController.java',
        content: `package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody String requestBody) {
        if (requestBody == null || requestBody.isBlank()) {
            return ResponseEntity.badRequest().body("Request body is required");
        }
        return ResponseEntity.ok("User created");
    }
}
`,
      },
    ],
  },
  {
    id: 'challenge-003',
    title: 'Global exception handler',
    description: 'Display a friendly error response using controller advice.',
    difficulty: 'hard',
    status: 'open',
    prompt:
      'Implement a global exception handler that returns a structured error payload for uncaught runtime exceptions.',
    files: [
      {
        path: 'src/main/java/com/app/exception/GlobalExceptionHandler.java',
        content: `package com.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(500).body("Internal server error: " + ex.getMessage());
    }
}
`,
      },
    ],
  },
];

export function findChallengeById(id) {
  return mockChallenges.find((challenge) => challenge.id === id) || null;
}

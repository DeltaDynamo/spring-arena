package main.java.com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/errors")
public class ExceptionController {

    @GetMapping("/bad-request")
    public String throwBadRequest() {
        throw new IllegalArgumentException("Invalid request payload");
    }

    @GetMapping("/server-error")
    public String throwServerError() {
        throw new RuntimeException("Unexpected failure");
    }
}

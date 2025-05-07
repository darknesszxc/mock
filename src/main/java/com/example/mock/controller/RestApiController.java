package com.example.mock.controller;

import com.example.mock.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private void sleepRandomTime() {
        try {
            TimeUnit.SECONDS.sleep((long)(Math.random() * 2 + 1));
        } catch (InterruptedException e) {
            System.err.println("Sleep interrupted: " + e.getMessage());
        }
    }

    @GetMapping("/getUserInfo")
    public String getUserInfo() {
        sleepRandomTime();
        return "{\"login\":\"Login1\",\"status\":\"ok\"}";
    }

    @PostMapping(value = "/authenticate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> authenticate( @Valid @RequestBody User inputUser) {
        sleepRandomTime();
        User userWithDate = new User(inputUser.getLogin(), inputUser.getPassword());
        System.out.println("Returning user: " + userWithDate);
        return ResponseEntity.ok(userWithDate);
    }
}

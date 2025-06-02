package com.example.mock.controller;

import com.example.mock.worker.FileWorker;


import com.example.mock.worker.DataBaseWorker;
import com.example.mock.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private final DataBaseWorker dbWorker = new DataBaseWorker();
    private final FileWorker fileWorker = new FileWorker();

    private void sleepRandomTime() {
        try {
            TimeUnit.SECONDS.sleep((long)(Math.random() * 2 + 1));
        } catch (InterruptedException e) {
            System.err.println("Sleep interrupted: " + e.getMessage());
        }
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<?> getUserInfo(@RequestParam String login) {
        sleepRandomTime();
        try {
            User user = dbWorker.getUserByLogin(login);
            String json = String.format(
                    "{\"login\":\"%s\", \"password\":\"%s\", \"date\":\"%s\", \"email\":\"%s\"}",
                    user.getLogin(),
                    user.getPassword(),
                    user.getDate(),
                    user.getEmail()
            );
            fileWorker.writeToFile(json);
            return ResponseEntity.ok(user);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/randomUserFromFile")
    public ResponseEntity<String> randomUserFromFile() {
        String randomLine = fileWorker.readRandomLine();
        return ResponseEntity.ok(randomLine);
    }

    @PostMapping(value = "/authenticate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> authenticate(@Valid @RequestBody User inputUser) {
        sleepRandomTime();
        try {
            inputUser.setDate(Date.valueOf(LocalDateTime.now().toLocalDate()));
            int inserted = dbWorker.insertUser(inputUser);
            return ResponseEntity.ok("Добавлена сущность: " + inserted);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @ExceptionHandler({org.springframework.http.converter.HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleInvalidJson(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Некорректный JSON: " + ex.getMessage());
    }


}

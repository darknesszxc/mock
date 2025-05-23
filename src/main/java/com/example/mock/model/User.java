package com.example.mock.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    @NotBlank(message = "Login cannot be blank")
    private String login;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String date;

    public void setLogin(String login) {
        this.login = login;
        setDateIfNull();
    }

    public void setPassword(String password) {
        this.password = password;
        setDateIfNull();
    }

    private void setDateIfNull() {
        if (this.date == null) {
            this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }
}


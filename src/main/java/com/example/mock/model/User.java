package com.example.mock.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.sql.Date;

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

    private Date date;
    private String email;

    public User(String login, String password, Date date, String email) {
        this.login = login;
        this.password = password;
        this.date = date;
        this.email = email;
    }



}


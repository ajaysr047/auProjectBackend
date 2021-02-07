package com.au.main.request;

import lombok.Getter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class Credentials {
    @NotBlank(message = "Email cannot be empty!")
    @Email
    private String email;

    @NotBlank(message = "Password cannot be empty!")
    private String password;
}

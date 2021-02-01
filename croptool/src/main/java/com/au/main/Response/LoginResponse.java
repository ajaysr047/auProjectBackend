package com.au.main.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private Boolean isValid;

    private String role;

    private String message;

    public LoginResponse(Boolean isValid, String role, String message) {
        this.isValid = isValid;
        this.role = role;
        this.message = message;
    }

    public LoginResponse() {
    }
}

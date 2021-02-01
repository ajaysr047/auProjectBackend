package com.au.main.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private Boolean isValid;

    private String role;

    private String message;

    private Integer employeeId;

    public LoginResponse(Integer employeeId, Boolean isValid, String role, String message) {
        this.isValid = isValid;
        this.role = role;
        this.message = message;
        this.employeeId = employeeId;
    }

    public LoginResponse() {
    }
}

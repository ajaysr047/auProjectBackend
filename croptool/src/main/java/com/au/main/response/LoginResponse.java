package com.au.main.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private boolean isValid;

    private String role;

    private String message;

    private Integer employeeId;

    private String employeeName;

    private String email;

    public LoginResponse(Integer employeeId, Boolean isValid, String role, String message, String employeeName, String email) {
        this.isValid = isValid;
        this.role = role;
        this.message = message;
        this.employeeId = employeeId;
        this.employeeName =employeeName;
        this.email =email;
    }

    public LoginResponse() {
    }
}

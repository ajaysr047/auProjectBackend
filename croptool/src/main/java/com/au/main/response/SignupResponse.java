package com.au.main.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponse {
    private Integer employeeId;

    private boolean isSignedUp;

    private String message;

    public SignupResponse() {
    }

    public SignupResponse(Integer employeeId, Boolean isSignedUp, String message) {
        this.employeeId = employeeId;
        this.isSignedUp = isSignedUp;
        this.message = message;
    }
}

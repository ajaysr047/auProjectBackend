package com.au.main.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeSignUp {

    private String employeeName;

    private String email;

    private String password;

    private String role;
}

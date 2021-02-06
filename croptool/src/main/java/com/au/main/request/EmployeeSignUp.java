package com.au.main.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class EmployeeSignUp {

    @NotEmpty(message = "Employee name cannot be empty!")
    private String employeeName;

    @NotBlank(message = "Employee email cannot be empty!")
    @Email
    private String email;

    @NotBlank(message = "Employee password cannot be empty!")
    private String password;

    @NotBlank(message = "Employee role cannot be empty!")
    private String role;
}

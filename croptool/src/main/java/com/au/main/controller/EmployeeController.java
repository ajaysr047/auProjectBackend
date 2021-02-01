package com.au.main.controller;


import com.au.main.request.Credentials;
import com.au.main.entity.Employee;
import com.au.main.response.LoginResponse;
import com.au.main.response.SignupResponse;
import com.au.main.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> employeeSignup(@RequestBody Employee employee){

        File file = new File("src/main/resources/d.jpg");
        try {
            employee.setEditedImage(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Employee response = employeeService.addEmployee(employee);
        if(response != null)
            return new ResponseEntity<>(new SignupResponse(employee.getEmployeeId(), Boolean.TRUE, "Sign up successful!"), HttpStatus.CREATED);
        return new ResponseEntity<>(new SignupResponse(-1, Boolean.FALSE, "Email is duplicate"), HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> employeeLogin(@RequestBody Credentials credentials){
        Employee employee = employeeService.login(credentials);

        if(employee != null)
            return ResponseEntity.ok(new LoginResponse(employee.getEmployeeId(), Boolean.TRUE, employee.getRole(), "Login Success!"));
        return new ResponseEntity<>(new LoginResponse(-1, Boolean.FALSE, "", "Login failed!, Invalid credentials"), HttpStatus.BAD_REQUEST);
    }

}

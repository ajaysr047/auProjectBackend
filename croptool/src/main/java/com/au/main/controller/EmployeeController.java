package com.au.main.controller;

import com.au.main.request.Credentials;
import com.au.main.request.EmployeeSignUp;
import com.au.main.response.LoginResponse;
import com.au.main.response.SignupResponse;
import com.au.main.response.SubordinatesResponse;
import com.au.main.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> employeeSignup(@Valid @RequestBody EmployeeSignUp employeeSignUp) throws IOException, NoSuchAlgorithmException{
        SignupResponse response = employeeService.addEmployee(employeeSignUp);
        if(response.isSignedUp())
            return ResponseEntity.ok(response);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> employeeLogin(@Valid @RequestBody Credentials credentials){
        LoginResponse response = employeeService.login(credentials);
        if(response.isValid())
            return ResponseEntity.ok(response);
        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping("/getSubordinates/{managerId}")
    public ResponseEntity<Object> getSubordinates(@PathVariable("managerId") Integer managerId){
        SubordinatesResponse response = employeeService.getSubordinates(managerId);
        if(response.isSuccess())
            return ResponseEntity.ok(response);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}

package com.au.main.Controller;


import com.au.main.Request.Credentials;
import com.au.main.Entity.Employee;
import com.au.main.Response.LoginResponse;
import com.au.main.Response.SignupResponse;
import com.au.main.Service.EmployeeService;
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
    ResponseEntity<SignupResponse> employeeSignup(@RequestBody Employee employee){

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
    ResponseEntity<LoginResponse> employeeLogin(@RequestBody Credentials credentials){
        Employee employee = employeeService.login(credentials);

        if(employee != null)
            return ResponseEntity.ok(new LoginResponse(Boolean.TRUE, employee.getRole(), "Login Success!"));
        return new ResponseEntity<>(new LoginResponse(Boolean.FALSE, "", "Login failed!, Invalid credentials"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/editImage/{employeeId}")
    ResponseEntity<Object> editImage(@RequestParam("image")MultipartFile image, @PathVariable("employeeId") Integer employeeId){

        boolean isSaved = employeeService.saveToDB(employeeId, image);

        if(isSaved)
            return new ResponseEntity<>("Saved", HttpStatus.OK);
        return new ResponseEntity<>("couldn't save!", HttpStatus.BAD_REQUEST);
    }
    
}

package com.au.main.controller;


import com.au.main.request.Credentials;
import com.au.main.entity.Employee;
import com.au.main.request.EmployeeSignUp;
import com.au.main.request.ImageWrapper;
import com.au.main.response.LoginResponse;
import com.au.main.response.SignupResponse;
import com.au.main.response.SubordinatesResponse;
import com.au.main.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private static final String[] EDITED_IMAGE_FILE_NAMES = {"dummy1.jpg", "dummy2.jpg", "dummy3.jpg", "dummy4.jpg", "dummy5.jpg" };

    private static final String EDITED_IMAGE_FOLDER = "/dummyResponseImages/";

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> employeeSignup(@RequestBody EmployeeSignUp employeeSignUp){
        Random random = new Random();
        Employee persistentEmployee = new Employee();
        try {
            int randomIndex = random.nextInt(4) + 1;
            System.out.println(EDITED_IMAGE_FILE_NAMES[randomIndex]);
            InputStream inputStream = new ClassPathResource(EDITED_IMAGE_FOLDER + EDITED_IMAGE_FILE_NAMES[randomIndex]).getInputStream();

            persistentEmployee.setEmployeeName(employeeSignUp.getEmployeeName());
            persistentEmployee.setEmail(employeeSignUp.getEmail());
            persistentEmployee.setPassword(employeeSignUp.getPassword());
            persistentEmployee.setRole(employeeSignUp.getRole());
            persistentEmployee.setEditedImage(inputStream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new SignupResponse(-1, Boolean.FALSE, "Error while signing up!!"), HttpStatus.CONFLICT);
        }
        Employee response = employeeService.addEmployee(persistentEmployee);
        if(response != null)
            return new ResponseEntity<>(new SignupResponse(response.getEmployeeId(), Boolean.TRUE, "Sign up successful!"), HttpStatus.CREATED);
        return new ResponseEntity<>(new SignupResponse(-1, Boolean.FALSE, "Email is duplicate"), HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> employeeLogin(@RequestBody Credentials credentials){
        Employee employee = employeeService.login(credentials);

        if(employee != null)
            return ResponseEntity.ok(new LoginResponse(employee.getEmployeeId(), Boolean.TRUE, employee.getRole(), "Login Success!"));
        return new ResponseEntity<>(new LoginResponse(-1, Boolean.FALSE, "", "Login failed!, Invalid credentials"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getSubordinates/{managerId}")
    public ResponseEntity<Object> getSubordinates(@PathVariable("managerId") Integer managerId){
        Set<Employee> responseList = employeeService.getSubordinated(managerId);
        SubordinatesResponse subordinates = new SubordinatesResponse();
        subordinates.setManagerId(managerId);
        if(!responseList.isEmpty()){

            subordinates.setIsSuccess(Boolean.TRUE);
            subordinates.setMessage("Subordinates fetch success!");

            responseList.forEach(employee -> subordinates.getSubordinateList().add(employee));
            return ResponseEntity.ok(subordinates);
        }

        subordinates.setIsSuccess(Boolean.FALSE);
        subordinates.setMessage("Subordinates fetch failed!");

        return new ResponseEntity<>(subordinates, HttpStatus.NOT_FOUND);
    }


}

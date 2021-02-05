package com.au.main.controller;


import com.au.main.constants.Constants;
import com.au.main.request.Credentials;
import com.au.main.entity.Employee;
import com.au.main.request.EmployeeSignUp;
import com.au.main.response.LoginResponse;
import com.au.main.response.SignupResponse;
import com.au.main.response.SubordinatesResponse;
import com.au.main.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {



    @Autowired
    EmployeeService employeeService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> employeeSignup(@RequestBody EmployeeSignUp employeeSignUp) throws IOException, NoSuchAlgorithmException{

        Employee response = employeeService.addEmployee(employeeSignUp);
        if(response != null)
            return new ResponseEntity<>(new SignupResponse(response.getEmployeeId(), Boolean.TRUE, Constants.SIGNUP_SUCCESS_MESSAGE), HttpStatus.CREATED);

        return new ResponseEntity<>(new SignupResponse(Constants.FAILURE_EMPLOYEE_ID, Boolean.FALSE, Constants.SIGNUP_EMAIL_DUPLICATE_MESSAGE), HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> employeeLogin(@RequestBody Credentials credentials){
        Employee employee = employeeService.login(credentials);

        if(employee != null)
            return ResponseEntity.ok(new LoginResponse(employee.getEmployeeId(), Boolean.TRUE, employee.getRole(), Constants.LOGIN_SUCCESS_MESSAGE));
        return new ResponseEntity<>(new LoginResponse(Constants.FAILURE_EMPLOYEE_ID, Boolean.FALSE, Constants.LOGIN_FAILED_ROLE, Constants.LOGIN_FAILED_MESSAGE), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getSubordinates/{managerId}")
    public ResponseEntity<Object> getSubordinates(@PathVariable("managerId") Integer managerId){
        Set<Employee> responseList = employeeService.getSubordinates(managerId);
        SubordinatesResponse subordinates = new SubordinatesResponse();
        subordinates.setManagerId(managerId);
        if(!responseList.isEmpty()){

            subordinates.setIsSuccess(Boolean.TRUE);
            subordinates.setMessage(Constants.SUBORDINATE_SUCCESS_MESSAGE);

            responseList.forEach(employee -> subordinates.getSubordinateList().add(employee));
            return ResponseEntity.ok(subordinates);
        }

        subordinates.setIsSuccess(Boolean.FALSE);
        subordinates.setMessage(Constants.SUBORDINATE_FAILURE_MESSAGE);

        return new ResponseEntity<>(subordinates, HttpStatus.NOT_FOUND);
    }


}

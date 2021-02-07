package com.au.main.controller;

import com.au.main.entity.Employee;
import com.au.main.request.Credentials;
import com.au.main.request.EmployeeSignUp;
import com.au.main.response.LoginResponse;
import com.au.main.response.SignupResponse;
import com.au.main.response.SubordinatesResponse;
import com.au.main.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeService employeeService;

    @Test
    void employeeSignupTest() throws Exception{
//        Successfully created
        Employee dummyEmployee = new Employee();

        Mockito.when(employeeService.addEmployee(Mockito.any(EmployeeSignUp.class))).thenReturn(dummyEmployee);

        EmployeeSignUp employeeSignUp = new EmployeeSignUp();
        employeeSignUp.setEmployeeName("Test 1");
        employeeSignUp.setEmail("test@gmail.com");
        employeeSignUp.setRole("manager");
        employeeSignUp.setPassword("123");

        ResponseEntity<SignupResponse> successResponse = employeeController.employeeSignup(employeeSignUp);

        Assertions.assertEquals(Boolean.TRUE, Objects.requireNonNull(successResponse.getBody()).getIsSignedUp());

//        Employee signup failed
        Mockito.when(employeeService.addEmployee(Mockito.any(EmployeeSignUp.class))).thenReturn(null);
        ResponseEntity<SignupResponse> nullResponse = employeeController.employeeSignup(employeeSignUp);

        Assertions.assertEquals(Boolean.FALSE, Objects.requireNonNull(nullResponse.getBody()).getIsSignedUp());

    }

    @Test
    void employeeSignupIOExceptionTest() throws IOException, NoSuchAlgorithmException {
        EmployeeSignUp employeeSignUp = new EmployeeSignUp();
        Mockito.when(employeeService.addEmployee(employeeSignUp)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            employeeController.employeeSignup(employeeSignUp);
        });
    }

    @Test
    void employeeSignupNoSuchAlgorithmExceptionTest() throws IOException, NoSuchAlgorithmException {
        EmployeeSignUp employeeSignUp = new EmployeeSignUp();
        Mockito.when(employeeService.addEmployee(employeeSignUp)).thenThrow(new NoSuchAlgorithmException());

        Assertions.assertThrows(NoSuchAlgorithmException.class, () -> {
            employeeController.employeeSignup(employeeSignUp);
        });
    }

    @Test
    void employeeLoginTest() {
        Credentials dummyCredentials = new Credentials();
        LoginResponse response = new LoginResponse();
        response.setValid(true);
//        Successful Login
        Mockito.when(employeeService.login(Mockito.any(Credentials.class))).thenReturn(response);

        ResponseEntity<LoginResponse> successResponse = employeeController.employeeLogin(dummyCredentials);

        Assertions.assertTrue(Objects.requireNonNull(successResponse.getBody()).isValid());
//        Login failure
        response.setValid(false);
        Mockito.when(employeeService.login(Mockito.any(Credentials.class))).thenReturn(response);

        ResponseEntity<LoginResponse> failureResponse = employeeController.employeeLogin(dummyCredentials);

        Assertions.assertFalse(Objects.requireNonNull(failureResponse.getBody()).isValid());
    }

    @Test
    void getSubordinatesTest() {
        SubordinatesResponse response = new SubordinatesResponse();
        response.setSuccess(true);
//        Successful retrieval
        Mockito.when(employeeService.getSubordinates(Mockito.any(Integer.class))).thenReturn(response);

        ResponseEntity<Object> successResponse = employeeController.getSubordinates(1);

        Assertions.assertEquals(HttpStatus.OK, successResponse.getStatusCode());
//        Retrieval failed
        response.setSuccess(false);
        Mockito.when(employeeService.getSubordinates(Mockito.any(Integer.class))).thenReturn(response);

        ResponseEntity<Object> failureResponse = employeeController.getSubordinates(1);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, failureResponse.getStatusCode());

    }
}
package com.au.main.service.implementation;

import com.au.main.entity.Employee;
import com.au.main.repository.EmployeeRepository;
import com.au.main.request.Credentials;
import com.au.main.request.EmployeeSignUp;
import com.au.main.request.ImageWrapper;
import com.au.main.response.LoginResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    @Spy
    EmployeeServiceImpl employeeServiceMock;

    @Mock
    EmployeeRepository employeeRepository;

    EmployeeSignUp employeeSignUp = new EmployeeSignUp();
    Employee employee = new Employee();

    void setup(){
        this.employeeSignUp.setRole("employee");
        this.employeeSignUp.setEmail("demo@gmail.com");
        this.employeeSignUp.setPassword("123");
    }

    @Test
    void addEmployeeTest() throws NoSuchAlgorithmException, IOException {
        setup();
        Mockito.when(employeeRepository.findByEmail(employeeSignUp.getEmail())).thenReturn(Optional.empty());
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);
        Assertions.assertTrue(employeeServiceMock.addEmployee(employeeSignUp).isSignedUp());

        employee.setEmail("demo@gmail.com");
        Mockito.when(employeeRepository.findByEmail(employeeSignUp.getEmail())).thenReturn(Optional.of(employee));
        Assertions.assertFalse(employeeServiceMock.addEmployee(employeeSignUp).isSignedUp());

    }

    @Test
    void addEmployeeNoSuchAlgorithmTest() throws NoSuchAlgorithmException {
        setup();
        employee.setEmployeeId(1);
        Mockito.when(employeeServiceMock.getRandom()).thenThrow(new NoSuchAlgorithmException());
        Assertions.assertThrows(NoSuchAlgorithmException.class, () -> {
            employeeServiceMock.getRandom();
        });
    }

    @Test
    void addEmployeeIOExceptionTest() throws IOException {
        setup();
        employee.setEmployeeId(1);
        Mockito.when(employeeServiceMock.getInputStream(Mockito.anyInt())).thenThrow(new IOException());
        Assertions.assertThrows(IOException.class, () -> {
            employeeServiceMock.getInputStream(1);
        });
    }

    @Test
    void loginTest() {
        Credentials credentials = new Credentials();
        credentials.setEmail("demo@gmail.com");
        credentials.setPassword("123");
        LoginResponse response = new LoginResponse();
        response.setValid(true);
        setup();
        Mockito.when(employeeRepository.findByEmail(employeeSignUp.getEmail())).thenReturn(Optional.of(employee));
        Assertions.assertFalse(employeeServiceMock.login(credentials).isValid());

        employee.setPassword(new BCryptPasswordEncoder().encode("123"));
        Mockito.when(employeeRepository.findByEmail(employeeSignUp.getEmail())).thenReturn(Optional.of(employee));
        Assertions.assertTrue(employeeServiceMock.login(credentials).isValid());
    }

    @Test
    void getEditedImageTest() {
        ImageWrapper imageWrapper = new ImageWrapper();
        imageWrapper.setEmployeeId(1);
        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.of(employee));
        Assertions.assertTrue(employeeServiceMock.getEditedImage(imageWrapper).isEdited());

        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertFalse(employeeServiceMock.getEditedImage(imageWrapper).isEdited());
    }

    @Test
    void getSubordinatesTest() {
        Employee manager = new Employee();
        manager.setRole("manager");
        manager.setEmail("manager@demo.com");
        manager.setPassword("12234");
        Mockito.when(employeeRepository.findByRoleAndEmployeeId(Mockito.any(), Mockito.any())).thenReturn(Optional.of(manager));
        Assertions.assertFalse(employeeServiceMock.getSubordinates(manager.getEmployeeId()).isSuccess());

        manager.getSubordinateEmployees().add(employee);
        Assertions.assertTrue(employeeServiceMock.getSubordinates(manager.getEmployeeId()).isSuccess());
    }
}
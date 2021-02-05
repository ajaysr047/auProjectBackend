package com.au.main.service;

import com.au.main.request.Credentials;
import com.au.main.entity.Employee;
import com.au.main.request.EmployeeSignUp;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

public interface EmployeeService {
    Employee addEmployee(EmployeeSignUp employee) throws IOException, NoSuchAlgorithmException;
    Employee login(Credentials credentials);
    Employee getEditedImage(Integer employeeId);
    Set<Employee> getSubordinates(Integer managerId);
}

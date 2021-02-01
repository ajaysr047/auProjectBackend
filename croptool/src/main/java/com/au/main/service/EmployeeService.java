package com.au.main.service;

import com.au.main.request.Credentials;
import com.au.main.entity.Employee;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    Employee login(Credentials credentials);
    Employee getEditedImage(Integer employeeId);
}

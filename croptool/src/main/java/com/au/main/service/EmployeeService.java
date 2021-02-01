package com.au.main.service;

import com.au.main.request.Credentials;
import com.au.main.entity.Employee;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    Employee login(Credentials credentials);
}

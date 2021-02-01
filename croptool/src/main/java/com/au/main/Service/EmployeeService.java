package com.au.main.Service;

import com.au.main.Request.Credentials;
import com.au.main.Entity.Employee;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    Employee login(Credentials credentials);
    boolean saveToDB(Integer employeeId, MultipartFile imageFile);
}

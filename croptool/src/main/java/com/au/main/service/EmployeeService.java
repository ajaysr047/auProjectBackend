package com.au.main.service;

import com.au.main.request.Credentials;
import com.au.main.entity.Employee;
import java.util.Set;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    Employee login(Credentials credentials);
    Employee getEditedImage(Integer employeeId);
    Set<Employee> getSubordinated(Integer managerId);
}

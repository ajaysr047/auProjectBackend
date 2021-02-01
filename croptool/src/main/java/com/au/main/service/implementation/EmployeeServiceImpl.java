package com.au.main.service.implementation;

import com.au.main.repository.ImageRepository;
import com.au.main.request.Credentials;
import com.au.main.entity.Employee;
import com.au.main.repository.EmployeeRepository;
import com.au.main.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public Employee addEmployee(Employee employee) {
       if(employeeRepository.findByEmail(employee.getEmail()).isEmpty()){
           String plainPassword = employee.getPassword();
           employee.setPassword(encryptPassword(plainPassword));

           return employeeRepository.save(employee);
       }
       return null;
    }

    @Override
    public Employee login(Credentials credentials) {
        Optional<Employee> employee = employeeRepository.findByEmail(credentials.getEmail());
        if(employee.isPresent() && isPasswordValid(credentials.getPassword(), employee.get().getPassword()))
                return employee.get();
        return null;
    }

    @Override
    public Employee getEditedImage(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        return employee.orElse(null);
    }


    private String encryptPassword(String plainPassword) {
        return new BCryptPasswordEncoder().encode(plainPassword);
    }

    private boolean isPasswordValid(String inputPassword, String storedPassword){
        return new BCryptPasswordEncoder().matches(inputPassword, storedPassword);
    }
}

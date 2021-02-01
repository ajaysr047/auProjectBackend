package com.au.main.service.implementation;

import com.au.main.entity.Image;
import com.au.main.repository.ImageRepository;
import com.au.main.request.Credentials;
import com.au.main.entity.Employee;
import com.au.main.repository.EmployeeRepository;
import com.au.main.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public Employee addEmployee(Employee employee) {
       if(employeeRepository.findByEmail(employee.getEmail()) == null){
           String plainPassword = employee.getPassword();
           employee.setPassword(encryptPassword(plainPassword));

           return employeeRepository.save(employee);
       }
       return null;
    }

    @Override
    public Employee login(Credentials credentials) {
        Employee employee = employeeRepository.findByEmail(credentials.getEmail());
        if(employee != null && isPasswordValid(credentials.getPassword(), employee.getPassword()))
                return employee;
        return null;
    }


    private String encryptPassword(String plainPassword) {
        return new BCryptPasswordEncoder().encode(plainPassword);
    }

    private boolean isPasswordValid(String inputPassword, String storedPassword){
        return new BCryptPasswordEncoder().matches(inputPassword, storedPassword);
    }
}

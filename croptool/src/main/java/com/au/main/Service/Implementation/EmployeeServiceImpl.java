package com.au.main.Service.Implementation;

import com.au.main.Entity.Image;
import com.au.main.Request.Credentials;
import com.au.main.Entity.Employee;
import com.au.main.Repository.EmployeeRepository;
import com.au.main.Service.EmployeeService;
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

    @Override
    public Employee addEmployee(Employee employee) {
       if(employeeRepository.findByEmail(employee.getEmail()) == null){
           String plainPassword = employee.getPassword();
           employee.setPassword(encryptPassword(plainPassword));
           Employee savedEmployee = employeeRepository.save(employee);

           return savedEmployee;
       }
       return null;
    }

    @Override
    public Employee login(Credentials credentials) {
        Optional<Employee> employee = employeeRepository.findById(credentials.getEmployeeId());
        if(employee.isPresent() && isPasswordValid(credentials.getPassword(), employee.get().getPassword()))
                return employee.get();
        return null;
    }

    @Override
    public boolean saveToDB(Integer employeeId,MultipartFile imageFile) {

        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if(employee.isPresent()){
            try {

                Image image = new Image();
                image.setEmployeeId(employeeId);
                image.setImageFileData(imageFile.getBytes());
                image.setImageFileName(imageFile.getOriginalFilename());
                image.setImageFileType(imageFile.getContentType());
                employee.get().getNewImages().add(image);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private String encryptPassword(String plainPassword) {
        return new BCryptPasswordEncoder().encode(plainPassword);
    }

    private boolean isPasswordValid(String inputPassword, String storedPassword){
        return new BCryptPasswordEncoder().matches(inputPassword, storedPassword);
    }
}

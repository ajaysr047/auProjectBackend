package com.au.main.service.implementation;

import com.au.main.constants.Constants;
import com.au.main.request.Credentials;
import com.au.main.entity.Employee;
import com.au.main.repository.EmployeeRepository;
import com.au.main.request.EmployeeSignUp;
import com.au.main.request.ImageWrapper;
import com.au.main.response.EditedImage;
import com.au.main.response.SubordinatesResponse;
import com.au.main.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);


    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee addEmployee(EmployeeSignUp employeeSignUp) {
        try {
            Random random = SecureRandom.getInstanceStrong();
            int randomIndex = random.nextInt(Constants.BOUND_FOR_DUMMY_IMAGES) + Constants.BOUND_OFFSET;
            InputStream inputStream = new ClassPathResource(Constants.EDITED_IMAGE_FOLDER + Constants.getEditedImageFileNames()[randomIndex]).getInputStream();

            Employee persistentEmployee = new Employee(employeeSignUp.getEmployeeName(), employeeSignUp.getEmail(), employeeSignUp.getPassword(), employeeSignUp.getRole().toLowerCase(), inputStream.readAllBytes());

            if(employeeRepository.findByEmail(persistentEmployee.getEmail()).isEmpty()){
                String plainPassword = persistentEmployee.getPassword();
                persistentEmployee.setPassword(encryptPassword(plainPassword));
                logger.info("Employee signup success!");
                return employeeRepository.save(persistentEmployee);
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            logger.error(String.format("Employee signup failed with error: %s" , e.toString()));
        }

       return null;
    }

    @Override
    public Employee login(Credentials credentials) {
        Optional<Employee> employee = employeeRepository.findByEmail(credentials.getEmail());
        if(employee.isPresent() && isPasswordValid(credentials.getPassword(), employee.get().getPassword())){
            logger.info("Logged in successfully");
            return employee.get();
        }
        logger.warn("Login failed!");
        return null;
    }

    @Override
    public EditedImage getEditedImage(ImageWrapper imageWrapper) {
        Optional<Employee> employee = employeeRepository.findById(imageWrapper.getEmployeeId());
        EditedImage editedImage = new EditedImage();
        if(employee.isPresent()){
            editedImage.setEmployeeId(imageWrapper.getEmployeeId());
            editedImage.setImageFileData(employee.get().getEditedImage());
            editedImage.setEdited(true);
            editedImage.setMessage(Constants.IMAGE_SAVED_MESSAGE);
        }
        else{
            editedImage.setEdited(false);
            editedImage.setMessage(Constants.INVALID_USER_MESSAGE);
        }
        return editedImage;
    }

    @Override
    public SubordinatesResponse getSubordinates(Integer managerId) {

        SubordinatesResponse subordinates = new SubordinatesResponse();
        Optional<Employee> manager = employeeRepository.findByRoleAndEmployeeId(Constants.MANAGER_ROLE, managerId);
        if(manager.isPresent()){
            subordinates.setManagerId(managerId);
            Set<Employee> responseList = manager.get().getSubordinateEmployees();
            if(!responseList.isEmpty()){
                subordinates.setSuccess(true);
                subordinates.setMessage(Constants.SUBORDINATE_SUCCESS_MESSAGE);

                responseList.forEach(employee -> subordinates.getSubordinateList().add(employee));
                logger.info("Subordinates found!");
                return subordinates;
            }
        }
        subordinates.setSuccess(false);
        subordinates.setMessage(Constants.SUBORDINATE_FAILURE_MESSAGE);
        logger.warn("Subordinates not found!");
        return subordinates;
    }

    private String encryptPassword(String plainPassword) {
        return new BCryptPasswordEncoder().encode(plainPassword);
    }

    private boolean isPasswordValid(String inputPassword, String storedPassword){
        return new BCryptPasswordEncoder().matches(inputPassword, storedPassword);
    }
}

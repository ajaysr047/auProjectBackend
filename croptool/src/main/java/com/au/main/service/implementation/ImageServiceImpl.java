package com.au.main.service.implementation;

import com.au.main.entity.Employee;
import com.au.main.entity.Image;
import com.au.main.repository.EmployeeRepository;
import com.au.main.repository.ImageRepository;
import com.au.main.request.ImageWrapper;
import com.au.main.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public boolean saveToDB(Integer employeeId, ImageWrapper imageWrapper) {

        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if(employee.isPresent()){
            Image image = new Image();
            image.setEmployeeId(employeeId);
            image.setImageFileData(imageWrapper.getImageFileData());
            image.setImageFileName(imageWrapper.getImageFileName());
            image.setImageFileType(imageWrapper.getImageFileType());

            imageRepository.save(image);
            return true;

        }
        return false;
    }
}

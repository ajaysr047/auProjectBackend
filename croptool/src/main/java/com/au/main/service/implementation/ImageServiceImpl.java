package com.au.main.service.implementation;

import com.au.main.entity.Employee;
import com.au.main.entity.Image;
import com.au.main.repository.EmployeeRepository;
import com.au.main.repository.ImageRepository;
import com.au.main.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public boolean saveToDB(Integer employeeId, MultipartFile imageFile) {

        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if(employee.isPresent()){
            try {

                Image image = new Image();
                image.setEmployeeId(employeeId);
                image.setImageFileData(imageFile.getBytes());
                image.setImageFileName(imageFile.getOriginalFilename());
                image.setImageFileType(imageFile.getContentType());

                imageRepository.save(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}

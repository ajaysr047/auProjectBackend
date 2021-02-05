package com.au.main.service.implementation;

import com.au.main.constants.Constants;
import com.au.main.entity.Employee;
import com.au.main.entity.Image;
import com.au.main.repository.EmployeeRepository;
import com.au.main.repository.ImageRepository;
import com.au.main.request.ImageWrapper;
import com.au.main.response.BulkImageResponse;
import com.au.main.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService {



    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public boolean saveToDB(ImageWrapper imageWrapper) {

        Optional<Employee> employee = employeeRepository.findById(imageWrapper.getEmployeeId());

        if(employee.isPresent()){
            Image image = new Image();
            image.setEmployeeId(imageWrapper.getEmployeeId());
            image.setImageFileData(imageWrapper.getImageFileData());
            image.setImageFileName(imageWrapper.getImageFileName());
            image.setImageFileType(imageWrapper.getImageFileType());

            imageRepository.save(image);
            return true;
        }
        return false;
    }

    @Override
    public BulkImageResponse bulkEditSave(Set<ImageWrapper> imageWrapperSet) {
        BulkImageResponse response = new BulkImageResponse();

        for(ImageWrapper imageWrapper : imageWrapperSet){

            Optional<Employee> employee = employeeRepository.findById(imageWrapper.getEmployeeId());
            if(saveToDB(imageWrapper) && employee.isPresent()){

                ImageWrapper responseEditedImage = new ImageWrapper();
                responseEditedImage.setEmployeeId(imageWrapper.getEmployeeId());
                responseEditedImage.setImageFileData(employee.get().getEditedImage());
                responseEditedImage.setImageFileName(employee.get().getEmployeeName());
                responseEditedImage.setImageFileType(Constants.DUMMY_IMAGE_TYPE);

                response.getResponseEditedImages().add(responseEditedImage);
            }
            else{
                response.setIsSuccess(Boolean.FALSE);
                response.setMessage(Constants.BULK_IMAGE_FAILURE_MESSAGE);
                return response;
            }
        }
        response.setIsSuccess(Boolean.TRUE);
        response.setMessage(Constants.BULK_IMAGE_SUCCESS_MESSAGE);
        return response;
    }
}

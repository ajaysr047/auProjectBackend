package com.au.main.service.implementation;

import com.au.main.constants.Constants;
import com.au.main.entity.Employee;
import com.au.main.entity.Image;
import com.au.main.repository.EmployeeRepository;
import com.au.main.repository.ImageRepository;
import com.au.main.request.ImageWrapper;
import com.au.main.response.BulkImageResponse;
import com.au.main.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;
import java.util.Optional;


@Service
public class ImageServiceImpl implements ImageService {

    private Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

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
            logger.info("Image edit and saved successfully!");
            return true;
        }
        logger.warn("Image failed to be saved!");
        return false;
    }

    @Override
    public BulkImageResponse bulkEditSave(LinkedHashSet<ImageWrapper> imageWrapperSet) {
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
                response.setSuccess(false);
                response.setMessage(Constants.BULK_IMAGE_FAILURE_MESSAGE);
                logger.warn("Bulk edit failed!");
                return response;
            }
        }
        response.setSuccess(true);
        response.setMessage(Constants.BULK_IMAGE_SUCCESS_MESSAGE);
        logger.info("Bulk edit success!");
        return response;
    }
}

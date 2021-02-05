package com.au.main.controller;

import com.au.main.constants.Constants;
import com.au.main.entity.Employee;
import com.au.main.request.ImageWrapper;
import com.au.main.response.BulkImageResponse;
import com.au.main.response.EditedImage;
import com.au.main.service.EmployeeService;
import com.au.main.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping(value = "/editImage")
    public ResponseEntity<Object> editImage(@RequestBody ImageWrapper imageWrapper){

        boolean isSaved = imageService.saveToDB(imageWrapper);
        EditedImage editedImage = new EditedImage();
        if(isSaved){
            Employee employee = employeeService.getEditedImage(imageWrapper.getEmployeeId());


            if(employee != null){
                editedImage.setEmployeeId(imageWrapper.getEmployeeId());
                editedImage.setImageFileData(employee.getEditedImage());
                editedImage.setIsEdited(Boolean.TRUE);
                editedImage.setMessage(Constants.IMAGE_SAVED_MESSAGE);
                return new ResponseEntity<>(editedImage, HttpStatus.OK);
            }
            else{
                editedImage.setIsEdited(Boolean.FALSE);
                editedImage.setMessage(Constants.INVALID_USER_MESSAGE);
                return new ResponseEntity<>(editedImage, HttpStatus.NOT_FOUND);
            }
        }
        editedImage.setIsEdited(Boolean.FALSE);
        editedImage.setMessage(Constants.IMAGE_EDIT_FAILED_MESSAGE);
        return new ResponseEntity<>(editedImage, HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping("/bulkEdit")
    public ResponseEntity<BulkImageResponse> bulkEdit(@RequestBody Set<ImageWrapper> bulkImages){

        BulkImageResponse response = imageService.bulkEditSave(bulkImages);
        return ResponseEntity.ok(response);
    }
}

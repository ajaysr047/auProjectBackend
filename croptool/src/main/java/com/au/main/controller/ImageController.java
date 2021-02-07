package com.au.main.controller;

import com.au.main.constants.Constants;
import com.au.main.request.BulkImageWrapper;
import com.au.main.request.ImageWrapper;
import com.au.main.response.BulkImageResponse;
import com.au.main.response.EditedImage;
import com.au.main.service.EmployeeService;
import com.au.main.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping(value = "/editImage")
    public ResponseEntity<EditedImage> editImage(@Valid @RequestBody ImageWrapper imageWrapper){

        boolean isSaved = imageService.saveToDB(imageWrapper);
        EditedImage editedImage = new EditedImage();
        if(isSaved){
            editedImage = employeeService.getEditedImage(imageWrapper);
            return ResponseEntity.ok(editedImage);
        }
        editedImage.setEdited(false);
        editedImage.setMessage(Constants.IMAGE_EDIT_FAILED_MESSAGE);
        return new ResponseEntity<>(editedImage, HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping("/bulkEdit")
    public ResponseEntity<BulkImageResponse> bulkEdit(@Valid @RequestBody BulkImageWrapper bulkImageWrapper){

        BulkImageResponse response = imageService.bulkEditSave(bulkImageWrapper.getImageWrapperSet());
        return ResponseEntity.ok(response);
    }
}

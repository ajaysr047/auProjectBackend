package com.au.main.controller;

import com.au.main.entity.Employee;
import com.au.main.response.EditedImage;
import com.au.main.service.EmployeeService;
import com.au.main.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping(value = "/editImage/{employeeId}")
    public ResponseEntity<Object> editImage(@RequestParam("image") MultipartFile image, @PathVariable("employeeId") Integer employeeId){

        boolean isSaved = imageService.saveToDB(employeeId, image);
        EditedImage editedImage = new EditedImage();
        if(isSaved){
            Employee employee = employeeService.getEditedImage(employeeId);


            if(employee != null){
                editedImage.setEmployeeId(employeeId);
                editedImage.setImageFileData(employee.getEditedImage());
                editedImage.setIsEdited(Boolean.TRUE);
                editedImage.setMessage("Received image saved!");
                //            To be added
                //            editedImage.setDownloadURI();
                return new ResponseEntity<>(editedImage, HttpStatus.OK);
            }
            else{
                editedImage.setIsEdited(Boolean.FALSE);
                editedImage.setMessage("User invalid!");
                return new ResponseEntity<>(editedImage, HttpStatus.NOT_FOUND);
            }
        }
        editedImage.setIsEdited(Boolean.FALSE);
        editedImage.setMessage("Couldn't edit image");
        return new ResponseEntity<>(editedImage, HttpStatus.EXPECTATION_FAILED);
    }
}

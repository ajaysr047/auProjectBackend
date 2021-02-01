package com.au.main.controller;

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

    @PostMapping(value = "/editImage/{employeeId}")
    public ResponseEntity<Object> editImage(@RequestParam("image") MultipartFile image, @PathVariable("employeeId") Integer employeeId){

        boolean isSaved = imageService.saveToDB(employeeId, image);

        if(isSaved)
            return new ResponseEntity<>("Saved", HttpStatus.OK);
        return new ResponseEntity<>("couldn't save!", HttpStatus.BAD_REQUEST);
    }
}

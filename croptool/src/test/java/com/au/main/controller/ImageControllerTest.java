package com.au.main.controller;

import com.au.main.entity.Employee;
import com.au.main.entity.Image;
import com.au.main.request.ImageWrapper;
import com.au.main.response.BulkImageResponse;
import com.au.main.service.EmployeeService;
import com.au.main.service.ImageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @InjectMocks
    ImageController imageController;

    @Mock
    ImageService imageService;

    @Mock
    EmployeeService employeeService;

    @Test
    void editImage() {
        Employee dummyEmployee = new Employee();
        ImageWrapper dummyImageWrapper = new ImageWrapper();
        dummyImageWrapper.setEmployeeId(1);
//        Success
        Mockito.when(imageService.saveToDB(Mockito.any(ImageWrapper.class))).thenReturn(Boolean.TRUE);
        Mockito.when(employeeService.getEditedImage(1)).thenReturn(dummyEmployee);
        ResponseEntity<Object> successResponse = imageController.editImage(dummyImageWrapper);
        Assertions.assertEquals(HttpStatus.OK, successResponse.getStatusCode());

//        Employee Null
        Mockito.when(employeeService.getEditedImage(1)).thenReturn(null);
        ResponseEntity<Object> employeeNullResponse = imageController.editImage(dummyImageWrapper);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, employeeNullResponse.getStatusCode());

//        Failure
        Mockito.when(imageService.saveToDB(Mockito.any(ImageWrapper.class))).thenReturn(Boolean.FALSE);
        ResponseEntity<Object> failureResponse = imageController.editImage(dummyImageWrapper);
        Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED, failureResponse.getStatusCode());
    }

    @Test
    void bulkEdit() {
        Set<ImageWrapper> imageWrapperSet = new HashSet<>();
        ImageWrapper dummyImageWrapper = new ImageWrapper();
        dummyImageWrapper.setEmployeeId(1);

        BulkImageResponse serviceResponse = new BulkImageResponse();
        serviceResponse.setIsSuccess(Boolean.TRUE);
//        Success
        Mockito.when(imageService.bulkEditSave(Mockito.any())).thenReturn(serviceResponse);
        ResponseEntity<BulkImageResponse> response = imageController.bulkEdit(imageWrapperSet);
        Assertions.assertEquals(Boolean.TRUE, Objects.requireNonNull(response.getBody()).getIsSuccess());
//        Failure
        serviceResponse.setIsSuccess(Boolean.FALSE);
        Mockito.when(imageService.bulkEditSave(Mockito.any())).thenReturn(serviceResponse);
        response = imageController.bulkEdit(imageWrapperSet);

        Assertions.assertEquals(Boolean.FALSE, Objects.requireNonNull(response.getBody()).getIsSuccess());

    }
}
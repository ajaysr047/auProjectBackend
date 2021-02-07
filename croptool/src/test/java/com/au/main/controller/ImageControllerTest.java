package com.au.main.controller;

import com.au.main.entity.Employee;
import com.au.main.entity.Image;
import com.au.main.request.BulkImageWrapper;
import com.au.main.request.ImageWrapper;
import com.au.main.response.BulkImageResponse;
import com.au.main.response.EditedImage;
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
import java.util.LinkedHashSet;
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
        EditedImage editedImage = new EditedImage();
        editedImage.setEdited(true);
        ImageWrapper dummyImageWrapper = new ImageWrapper();
        dummyImageWrapper.setEmployeeId(1);

//        Success
        Mockito.when(imageService.saveToDB(Mockito.any(ImageWrapper.class))).thenReturn(Boolean.TRUE);
        Mockito.when(employeeService.getEditedImage(dummyImageWrapper)).thenReturn(editedImage);
        ResponseEntity<EditedImage> successResponse = imageController.editImage(dummyImageWrapper);
        Assertions.assertEquals(HttpStatus.OK, successResponse.getStatusCode());

//        Employee Null
        editedImage.setEdited(false);
        Mockito.when(employeeService.getEditedImage(dummyImageWrapper)).thenReturn(editedImage);
        ResponseEntity<EditedImage> employeeNullResponse = imageController.editImage(dummyImageWrapper);
        assertFalse(Objects.requireNonNull(employeeNullResponse.getBody()).isEdited());

//        Failure
        Mockito.when(imageService.saveToDB(Mockito.any(ImageWrapper.class))).thenReturn(Boolean.FALSE);
        ResponseEntity<EditedImage> failureResponse = imageController.editImage(dummyImageWrapper);
        Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED, failureResponse.getStatusCode());
    }

    @Test
    void bulkEdit() {
        LinkedHashSet<ImageWrapper> imageWrapperSet = new LinkedHashSet<>();
        ImageWrapper dummyImageWrapper = new ImageWrapper();
        dummyImageWrapper.setEmployeeId(1);
        BulkImageWrapper dummyBulkImageWrapper = new BulkImageWrapper();
        dummyBulkImageWrapper.setImageWrapperSet(imageWrapperSet);

        BulkImageResponse serviceResponse = new BulkImageResponse();
        serviceResponse.setIsSuccess(Boolean.TRUE);
//        Success
        Mockito.when(imageService.bulkEditSave(Mockito.any())).thenReturn(serviceResponse);
        ResponseEntity<BulkImageResponse> response = imageController.bulkEdit(dummyBulkImageWrapper);
        Assertions.assertEquals(Boolean.TRUE, Objects.requireNonNull(response.getBody()).getIsSuccess());
//        Failure
        serviceResponse.setIsSuccess(Boolean.FALSE);
        Mockito.when(imageService.bulkEditSave(Mockito.any())).thenReturn(serviceResponse);
        response = imageController.bulkEdit(dummyBulkImageWrapper);

        Assertions.assertEquals(Boolean.FALSE, Objects.requireNonNull(response.getBody()).getIsSuccess());

    }
}
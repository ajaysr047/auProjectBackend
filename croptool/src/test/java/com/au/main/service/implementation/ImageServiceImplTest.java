package com.au.main.service.implementation;

import com.au.main.entity.Employee;
import com.au.main.entity.Image;
import com.au.main.repository.EmployeeRepository;
import com.au.main.repository.ImageRepository;
import com.au.main.request.ImageWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @InjectMocks
    @Spy
    ImageServiceImpl imageService;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    ImageRepository imageRepository;

    @Test
    void saveToDBTest() {
        ImageWrapper imageWrapper = new ImageWrapper();
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.of(employee));
        Assertions.assertTrue(imageService.saveToDB(imageWrapper));

        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertFalse(imageService.saveToDB(imageWrapper));
    }

    @Test
    void bulkEditSaveTest() {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        LinkedHashSet<ImageWrapper> imageWrapperSet = new LinkedHashSet<>();
        ImageWrapper imageWrapper = new ImageWrapper();
        imageWrapper.setEmployeeId(1);
        imageWrapperSet.add(imageWrapper);

        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.of(employee));
        Assertions.assertTrue(imageService.bulkEditSave(imageWrapperSet).isSuccess());

        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertFalse(imageService.bulkEditSave(imageWrapperSet).isSuccess());
    }
}
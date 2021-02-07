package com.au.main.controller;

import com.au.main.request.AssignManager;
import com.au.main.response.AdminAllEmployeeDataResponse;
import com.au.main.response.AssignManagerResponse;
import com.au.main.service.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @InjectMocks
    AdminController adminController;

    @Mock
    AdminService adminService;

    @Test
    void getAllEmployeeData() {
        AdminAllEmployeeDataResponse response = new AdminAllEmployeeDataResponse();
        response.setSuccess(true);

        Mockito.when(adminService.getAllData()).thenReturn(response);
        Assertions.assertTrue(Objects.requireNonNull(adminController.getAllEmployeeData().getBody()).isSuccess());

        response.setSuccess(false);
        Assertions.assertFalse(Objects.requireNonNull(adminController.getAllEmployeeData().getBody()).isSuccess());
    }

    @Test
    void assignManager() {
        AssignManagerResponse response = new AssignManagerResponse();
        response.setSuccess(true);
        AssignManager assignManager = new AssignManager();
        assignManager.setManagerId(1);
        assignManager.setEmployeeId(2);

        Mockito.when(adminService.assignManager(Mockito.any(), Mockito.any())).thenReturn(response);
        Assertions.assertTrue(Objects.requireNonNull(adminController.assignManager(assignManager).getBody()).isSuccess());

        response.setSuccess(false);
        Assertions.assertFalse(Objects.requireNonNull(adminController.assignManager(assignManager).getBody()).isSuccess());
    }
}
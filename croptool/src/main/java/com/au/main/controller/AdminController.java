package com.au.main.controller;

import com.au.main.request.AssignManager;
import com.au.main.response.AdminAllEmployeeDataResponse;
import com.au.main.response.AssignManagerResponse;
import com.au.main.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/getAllData")
    public ResponseEntity<AdminAllEmployeeDataResponse> getAllEmployeeData(){

        AdminAllEmployeeDataResponse response = adminService.getAllData();
        if(response.isSuccess())
            return ResponseEntity.ok(response);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/assignManager")
    public ResponseEntity<AssignManagerResponse> assignManager(@Valid @RequestBody AssignManager assignManager){
        AssignManagerResponse response = adminService.assignManager(assignManager.getEmployeeId(), assignManager.getManagerId());

        if(response.isSuccess())
            return ResponseEntity.ok(response);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}

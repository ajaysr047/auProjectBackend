package com.au.main.service.implementation;

import com.au.main.entity.Employee;
import com.au.main.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @InjectMocks
    AdminServiceImpl adminService;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void getAllDataTest() {
        List<Employee> employeeList = new ArrayList<>();
        Employee dummyManager = new Employee("Dummy", "dummy@dummy.com", "123", "manager", null);
        Employee dummyEmployeeWithManager = new Employee("Dummy", "dummy1@dummy.com", "123", "employee", null);
        dummyEmployeeWithManager.setManager(dummyManager);
        Employee dummyEmployeeWithoutManager = new Employee("Dummy", "dummy2@dummy.com", "123", "employee", null);
        dummyEmployeeWithoutManager.setManager(null);

        employeeList.add(dummyManager);
        employeeList.add(dummyEmployeeWithManager);
        employeeList.add(dummyEmployeeWithoutManager);

        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        Assertions.assertTrue(adminService.getAllData().isSuccess());

        employeeList.clear();
        Assertions.assertFalse(adminService.getAllData().isSuccess());
    }

    @Test
    void assignManagerTest() {

        Employee employee = new Employee();
        Mockito.when(employeeRepository.findByRoleAndEmployeeId(Mockito.any(), Mockito.any())).thenReturn(Optional.of(employee));
        Assertions.assertTrue(adminService.assignManager(1, 1).isSuccess());

        Mockito.when(employeeRepository.findByRoleAndEmployeeId(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertFalse(adminService.assignManager(1, 1).isSuccess());
    }
}
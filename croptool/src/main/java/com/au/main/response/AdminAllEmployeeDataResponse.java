package com.au.main.response;

import com.au.main.entity.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AdminAllEmployeeDataResponse {

    private boolean isSuccess;

    private String message;

    private Set<Employee> employeeSetWithManager = new HashSet<>();

    private Set<Employee> employeeSetWithoutManager = new HashSet<>();

    private Set<Employee> managerSet = new HashSet<>();
}

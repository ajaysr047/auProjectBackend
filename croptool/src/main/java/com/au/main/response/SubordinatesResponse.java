package com.au.main.response;

import com.au.main.entity.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SubordinatesResponse {
    private Integer managerId;

    private boolean isSuccess;

    private String message;

    private List<Employee> subordinateList = new ArrayList<>();
}

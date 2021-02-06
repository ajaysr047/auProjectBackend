package com.au.main.service;

import com.au.main.response.AdminAllEmployeeDataResponse;
import com.au.main.response.AssignManagerResponse;

public interface AdminService {
    AdminAllEmployeeDataResponse getAllData();
    AssignManagerResponse assignManager(Integer employeeId, Integer managerId);
}

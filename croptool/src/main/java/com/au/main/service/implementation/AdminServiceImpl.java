package com.au.main.service.implementation;

import com.au.main.constants.Constants;
import com.au.main.entity.Employee;
import com.au.main.repository.EmployeeRepository;
import com.au.main.response.AdminAllEmployeeDataResponse;
import com.au.main.response.AssignManagerResponse;
import com.au.main.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public AdminAllEmployeeDataResponse getAllData() {
        AdminAllEmployeeDataResponse response = new AdminAllEmployeeDataResponse();

        List<Employee> employeeList = employeeRepository.findAll();

        if(!employeeList.isEmpty()){
            for(Employee employee : employeeList){
                if(employee.getRole().equals(Constants.MANAGER_ROLE)){
                    response.getManagerSet().add(employee);
                }else if(employee.getManager() == null){
                    response.getEmployeeSetWithoutManager().add(employee);
                }else {
                    response.getEmployeeSetWithManager().add(employee);
                }
            }
            response.setSuccess(true);
            response.setMessage(Constants.ADMIN_GET_ALL_DATA_SUCCESS_MESSAGE);
            return response;
        }
        response.setSuccess(false);
        response.setMessage(Constants.ADMIN_GET_ALL_DATA_FAILURE_MESSAGE);
        return response;
    }

    @Override
    public AssignManagerResponse assignManager(Integer employeeId, Integer managerId) {
        Optional<Employee> manager = employeeRepository.findByRoleAndEmployeeId(Constants.MANAGER_ROLE, managerId);
        Optional<Employee> employee = employeeRepository.findByRoleAndEmployeeId(Constants.EMPLOYEE_ROLE, employeeId);
        AssignManagerResponse response = new AssignManagerResponse();
        if(manager.isPresent() && employee.isPresent()){
            employee.get().setManager(manager.get());
            employeeRepository.save(employee.get());
            response.setSuccess(true);
            response.setMessage(Constants.ASSIGN_MANAGER_SUCCESS_MESSAGE);
            return response;
        }
        response.setSuccess(false);
        response.setMessage(Constants.ASSIGN_MANAGER_FAILURE_MESSAGE);
        return response;
    }
}

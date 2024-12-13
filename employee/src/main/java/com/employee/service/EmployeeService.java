package com.employee.service;

import com.employee.entities.EmployeeEntity;
import com.employee.entities.GenericResponse;

public interface EmployeeService {

    GenericResponse addEmployee(EmployeeEntity empEntity);
    GenericResponse getEmployeeDetails(int empId);
    GenericResponse getEmployeeList();

}

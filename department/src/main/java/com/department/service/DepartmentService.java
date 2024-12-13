package com.department.service;

import com.department.entities.DepartmentEntity;
import com.department.entities.GenericResponse;

public interface DepartmentService {

    GenericResponse addDepartment(DepartmentEntity depart);

    GenericResponse getDepartmentDetails(int depId);

    GenericResponse getDepartmentList();

    GenericResponse getSpecificDepDetails(int depId);
}

package com.employee.service.impl;

import com.employee.entities.EmployeeEntity;
import com.employee.entities.GenericResponse;
import com.employee.repositories.EmployeeRepository;
import com.employee.service.EmployeeService;
import com.employee.utils.DataManagementUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.employee.utils.EmployeeConstant.*;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private DataManagementUtil dataManagementUtil;

    @Autowired
    private EmployeeRepository empRepository;

    @Override
    public GenericResponse addEmployee(EmployeeEntity empEntity) {
        GenericResponse responseBody = new GenericResponse();
        int insertCount = empRepository.addEmployeeDetails(empEntity.getEmpId(), empEntity.getEmpName(),
                empEntity.getEmpMobile(), empEntity.getDesginationId());
        if(insertCount > 0){
            responseBody = dataManagementUtil.response(SUCCESS_CODE,
                    ADD_EMPLOYEE_SUCCESS ,insertCount);
        }
        else
            responseBody = dataManagementUtil.response(INTERNAL_SERVER_ERROR_CODE,
                    ADD_EMPLOYEE_FAILED, insertCount);
        return responseBody;
    }

    @Override
    public GenericResponse getEmployeeDetails(int empId) {
        GenericResponse responseBody = new GenericResponse();
        Optional<EmployeeEntity> mappingDetails = empRepository.getMappingDetailsById(empId);
        if(mappingDetails == null){
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DATABASE_ERROR_MSG);
            responseBody.setData(new HashMap<>());
        } else if (mappingDetails.isEmpty()) {
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(EMPLOYEE_DATA_NOT_FOUND);
            responseBody.setData(new HashMap<>());
        } else {
            responseBody.setStatus(SUCCESS_CODE);
            responseBody.setMessage(EMPLOYEE_DATA_FOUND);
            responseBody.setData(mappingDetails);
        }
        return responseBody;
    }

    @Override
    public GenericResponse getEmployeeList() {
        GenericResponse responseBody = new GenericResponse();
        List<EmployeeEntity> mappingList = empRepository.getEmployeeDetails();
        if(mappingList == null){
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DATABASE_ERROR_MSG);
            responseBody.setData(new ArrayList<>());
        } else if (mappingList.isEmpty()) {
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(EMPLOYEE_LIST_DATA_NOT_FOUND);
            responseBody.setData(new ArrayList<>());
        } else {
            responseBody.setStatus(SUCCESS_CODE);
            responseBody.setMessage(EMPLOYEE_LIST_DATA_FOUND);
            responseBody.setData(mappingList);
        }
        return responseBody;
    }
}

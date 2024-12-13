package com.department.service.impl;

import com.department.entities.DepartmentEntity;
import com.department.entities.GenericResponse;
import com.department.entities.StudentListResponse;
import com.department.repositories.DepartmentRepository;
import com.department.service.DepartmentService;
import com.department.service.StudentFeignClient;
import com.department.utils.DataManagementUtil;
import com.department.utils.DepartmentConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.department.utils.DepartmentConstant.*;

@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DataManagementUtil dataManagementUtil;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private StudentFeignClient studFeginClient;

    @Override
    public GenericResponse addDepartment(DepartmentEntity depart) {
        GenericResponse responseBody = new GenericResponse();
        int insertCount = departmentRepository.addDepartmentDetails(depart.getDepId(), depart.getDepName(),depart.getDescription(),depart.getHodId());
        if(insertCount > 0){
            responseBody = dataManagementUtil.response(SUCCESS_CODE,
                    DepartmentConstant.ADD_DEPARTMENT_SUCCESS ,insertCount);
        }
        else
            responseBody = dataManagementUtil.response(INTERNAL_SERVER_ERROR_CODE,
                    DepartmentConstant.ADD_DEPARTMENT_FAILED, insertCount);
        return responseBody;
    }

    @Override
    public GenericResponse getDepartmentDetails(int depId) {
        GenericResponse responseBody = new GenericResponse();
        Optional<DepartmentEntity> mappingDetails = departmentRepository.getMappingDetailsById(depId);
        if(mappingDetails == null){
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DATABASE_ERROR_MSG);
            responseBody.setData(new HashMap<>());
        } else if (mappingDetails.isEmpty()) {
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DEPARTMENT_DATA_NOT_FOUND);
            responseBody.setData(new HashMap<>());
        } else {
            responseBody.setStatus(SUCCESS_CODE);
            responseBody.setMessage(DEPARTMENT_DATA_FOUND);
            responseBody.setData(mappingDetails);
        }
        return responseBody;
    }

    @Override
    public GenericResponse getDepartmentList() {
        GenericResponse responseBody = new GenericResponse();
        List<DepartmentEntity> mappingList = departmentRepository.getDepartmentDetails();
        if(mappingList == null){
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DATABASE_ERROR_MSG);
            responseBody.setData(new ArrayList<>());
        } else if (mappingList.isEmpty()) {
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DEPARTMENT_LIST_DATA_NOT_FOUND);
            responseBody.setData(new ArrayList<>());
        } else {
            responseBody.setStatus(SUCCESS_CODE);
            responseBody.setMessage(DEPARTMENT_LIST_DATA_FOUND);
            responseBody.setData(mappingList);
        }
        return responseBody;
    }


    @Override
    public GenericResponse getSpecificDepDetails(int depId) {
        GenericResponse responseBody = new GenericResponse();
        System.out.println("getSpecificDepDetails invoked..... "+depId);
        Optional<DepartmentEntity> mappingDetails = departmentRepository.getMappingDetailsById(depId);

        if(mappingDetails == null){
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DATABASE_ERROR_MSG);
            responseBody.setData(new HashMap<>());
        } else if (mappingDetails.isEmpty()) {
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DEPARTMENT_DATA_NOT_FOUND);
            responseBody.setData(new HashMap<>());
        } else {
            StudentListResponse res = new StudentListResponse();
            List<Object> studentData= new ArrayList<>();
            try{
                mappingDetails.stream().forEach( data ->{
                    System.out.println(data.getDepId());
                    studentData.add(studFeginClient.getStudentListById(data.getDepId()).getData());
                });
            }catch (Exception e){
                e.printStackTrace();
            }

            res.setDepId(mappingDetails.get().getDepId());
            res.setDepName(mappingDetails.get().getDepName());
            res.setDescription(mappingDetails.get().getDescription());
            res.setHodId(mappingDetails.get().getHodId());
            res.setStudData(studentData);
            responseBody.setStatus(SUCCESS_CODE);
            responseBody.setMessage(DEPARTMENT_DATA_FOUND);
            responseBody.setData(res);

        }
        return responseBody;
    }
}

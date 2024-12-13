package com.student.service.impl;

import com.student.entities.GenericResponse;
import com.student.entities.StudentEntity;
import com.student.repositories.StudentRepository;
import com.student.service.StudentService;
import com.student.utils.DataManagementUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.student.utils.StudentConstant.*;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private DataManagementUtil dataManagementUtil;

    @Autowired
    private StudentRepository studRepository;

    @Override
    public GenericResponse addStudent(StudentEntity studEntity) {
        GenericResponse responseBody = new GenericResponse();
        int insertCount = studRepository.addStudentDetails(studEntity.getStudId(), studEntity.getStudName(),
                studEntity.getGender(), studEntity.getDepId());
        if(insertCount > 0){
            responseBody = dataManagementUtil.response(SUCCESS_CODE,
                    ADD_STUDENT_SUCCESS ,insertCount);
        }
        else
            responseBody = dataManagementUtil.response(INTERNAL_SERVER_ERROR_CODE,
                    ADD_STUDENT_FAILED, insertCount);
        return responseBody;
    }

    @Override
    public GenericResponse getStudentList() {
        GenericResponse responseBody = new GenericResponse();
        List<StudentEntity> mappingList = studRepository.getStudentDetails();
        if(mappingList == null){
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DATABASE_ERROR_MSG);
            responseBody.setData(new ArrayList<>());
        } else if (mappingList.isEmpty()) {
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(STUDENT_LIST_DATA_NOT_FOUND);
            responseBody.setData(new ArrayList<>());
        } else {
            responseBody.setStatus(SUCCESS_CODE);
            responseBody.setMessage(STUDENT_LIST_DATA_FOUND);
            responseBody.setData(mappingList);
        }
        return responseBody;
    }


    @Override
    public GenericResponse getStudentDetails(int studId) {
        GenericResponse responseBody = new GenericResponse();
        Optional<StudentEntity> mappingDetails = studRepository.getMappingDetailsById(studId);
        if(mappingDetails == null){
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DATABASE_ERROR_MSG);
            responseBody.setData(new HashMap<>());
        } else if (mappingDetails.isEmpty()) {
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(STUDENT_DATA_NOT_FOUND);
            responseBody.setData(new HashMap<>());
        } else {
            responseBody.setStatus(SUCCESS_CODE);
            responseBody.setMessage(STUDENT_DATA_FOUND);
            responseBody.setData(mappingDetails);
        }
        return responseBody;
    }

    @Override
    public GenericResponse getStudDeprtDetails(int depId) {
        GenericResponse responseBody = new GenericResponse();
        List<StudentEntity> mappingList = studRepository.getStudentDetailsByDepId(depId);
        if(mappingList == null){
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DATABASE_ERROR_MSG);
            responseBody.setData(new ArrayList<>());
        } else if (mappingList.isEmpty()) {
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(STUDENT_LIST_DATA_NOT_FOUND);
            responseBody.setData(new ArrayList<>());
        } else {
            responseBody.setStatus(SUCCESS_CODE);
            responseBody.setMessage(STUDENT_LIST_DATA_FOUND);
            responseBody.setData(mappingList);
        }
        return responseBody;
    }

}

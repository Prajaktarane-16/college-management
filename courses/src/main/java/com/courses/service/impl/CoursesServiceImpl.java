package com.courses.service.impl;


import com.courses.entities.CoursesEntity;
import com.courses.entities.GenericResponse;
import com.courses.repositories.CourseRepository;
import com.courses.service.CoursesService;
import com.courses.utils.DataManagementUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.courses.utils.CourseConstant.*;

@Slf4j
@Service
public class CoursesServiceImpl implements CoursesService {

    @Autowired
    private DataManagementUtil dataManagementUtil;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public GenericResponse addCourse(CoursesEntity course) {
        GenericResponse responseBody = new GenericResponse();
        int insertCount = courseRepository.addCourseDetails(course.getCourse_id(), course.getCourse_name(),course.getDescription(),
                course.getDep_id(),course.getProfessor_id());
        if(insertCount > 0){
            responseBody = dataManagementUtil.response(SUCCESS_CODE,
                    ADD_COURSE_SUCCESS ,insertCount);
        }
        else
            responseBody = dataManagementUtil.response(INTERNAL_SERVER_ERROR_CODE,
                    ADD_COURSE_FAILED, insertCount);
        return responseBody;
    }

    @Override
    public GenericResponse getCourseDetails(int courseId) {
        GenericResponse responseBody = new GenericResponse();
        Optional<CoursesEntity> mappingDetails = courseRepository.getMappingDetailsById(courseId);
        if(mappingDetails == null){
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DATABASE_ERROR_MSG);
            responseBody.setData(new HashMap<>());
        } else if (mappingDetails.isEmpty()) {
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(COURSE_DATA_NOT_FOUND);
            responseBody.setData(new HashMap<>());
        } else {
            responseBody.setStatus(SUCCESS_CODE);
            responseBody.setMessage(COURSE_DATA_FOUND);
            responseBody.setData(mappingDetails);
        }
        return responseBody;
    }

    @Override
    public GenericResponse getCourseList() {
        GenericResponse responseBody = new GenericResponse();
        List<CoursesEntity> mappingList = courseRepository.getCourseDetails();
        if(mappingList == null){
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DATABASE_ERROR_MSG);
            responseBody.setData(new ArrayList<>());
        } else if (mappingList.isEmpty()) {
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(COURSE_LIST_DATA_NOT_FOUND);
            responseBody.setData(new ArrayList<>());
        } else {
            responseBody.setStatus(SUCCESS_CODE);
            responseBody.setMessage(COURSE_LIST_DATA_FOUND);
            responseBody.setData(mappingList);
        }
        return responseBody;
    }


}

package com.courses.service;

import com.courses.entities.CoursesEntity;
import com.courses.entities.GenericResponse;

public interface CoursesService {

    GenericResponse addCourse(CoursesEntity coursesEntity);

    GenericResponse getCourseDetails(int courseId);

    GenericResponse getCourseList();
}

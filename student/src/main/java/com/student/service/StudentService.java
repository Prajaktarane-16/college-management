package com.student.service;

import com.student.entities.GenericResponse;
import com.student.entities.StudentEntity;

public interface StudentService {

    GenericResponse addStudent(StudentEntity studEntity);

    GenericResponse getStudentList();

    GenericResponse getStudentDetails(int studentId);

    GenericResponse getStudDeprtDetails(int depId);
}

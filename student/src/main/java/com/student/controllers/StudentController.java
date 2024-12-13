package com.student.controllers;

import com.student.entities.GenericResponse;
import com.student.entities.StudentEntity;
import com.student.service.StudentService;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/addStudent")
    public ResponseEntity addStudent(@RequestBody StudentEntity studEntity){
        log.info("Entered the /student/addStudent API..",studEntity);
        GenericResponse res = studentService.addStudent(studEntity);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getListOfStudent")
    public ResponseEntity getListOfStudent(){
        log.info("Entered the /student/getListOfStudent API");
        GenericResponse res = studentService.getStudentList();
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getStudentByIds/{studentId}")
    public ResponseEntity getStudentById(@PathVariable("studentId") int studentId){
        log.info("Entered the /student/getStudentByIds API");
        GenericResponse res = studentService.getStudentDetails(studentId);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/department/getSpecificDepartmentData/{depId}")
    public ResponseEntity getSpecificDepartmentData(@PathVariable("depId") int depId){
        log.info("Entered the /department/getSpecificDepartmentData API");
        GenericResponse res = studentService.getStudDeprtDetails(depId);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


}

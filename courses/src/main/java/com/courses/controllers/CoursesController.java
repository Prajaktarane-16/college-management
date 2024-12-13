package com.courses.controllers;

import com.courses.entities.CoursesEntity;
import com.courses.entities.GenericResponse;
import com.courses.service.CoursesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@Slf4j
public class CoursesController {


    @Autowired
    private CoursesService coursesService;

    @PostMapping("/addCourses")
    public ResponseEntity addCourses(@RequestBody CoursesEntity courseEntity){
        log.info("Entered the /courses/addCourses API..",courseEntity);
        GenericResponse res = coursesService.addCourse(courseEntity);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getCourseByIds/{courseId}")
    public ResponseEntity getCourseById(@PathVariable("courseId") int courseId){
        log.info("Entered the /courses/getCourseByIds API");
        GenericResponse res = coursesService.getCourseDetails(courseId);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getListOfCourse")
    public ResponseEntity getListOfCourse(){
        log.info("Entered the /courses/getListOfCourse API");
        GenericResponse res = coursesService.getCourseList();
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}

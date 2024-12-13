package com.department.controllers;

import com.department.entities.DepartmentEntity;
import com.department.entities.GenericResponse;
import com.department.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/addDepartment")
    public ResponseEntity addDepartment(@RequestBody DepartmentEntity departEntity){
        log.info("Entered the /department/addDepartment API..",departEntity);
        GenericResponse res = departmentService.addDepartment(departEntity);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getDepartmentByIds/{depId}")
    public ResponseEntity getDepartmentById(@PathVariable("depId") int depId){
        log.info("Entered the /department/getDepartmentByIds API");
        GenericResponse res = departmentService.getDepartmentDetails(depId);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getListOfDepartments")
    public ResponseEntity getListOfDepartments(){
        log.info("Entered the /department/getListOfDepartments API");
        GenericResponse res = departmentService.getDepartmentList();
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getSpecificDepData/{depId}")
    public ResponseEntity getSpecificDepData(@PathVariable int depId ){
        log.info("Entered the /department/getSpecificDepData API");
        GenericResponse res = departmentService.getSpecificDepDetails(depId);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}

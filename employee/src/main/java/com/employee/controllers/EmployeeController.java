package com.employee.controllers;

import com.employee.entities.EmployeeEntity;
import com.employee.entities.GenericResponse;
import com.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService empService;

    @PostMapping("/addEmployee")
    public ResponseEntity addEmployee(@RequestBody EmployeeEntity empEntity){
        log.info("Entered the /employee/addEmployee API..",empEntity);
        GenericResponse res = empService.addEmployee(empEntity);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getEmpByIds/{empId}")
    public ResponseEntity getEmployeeById(@PathVariable("empId") int empId){
        log.info("Entered the /employee/getEmpByIds API");
        GenericResponse res = empService.getEmployeeDetails(empId);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getListOfEmployee")
    public ResponseEntity getListOfEmployee(){
        log.info("Entered the /employee/getListOfEmployee API");
        GenericResponse res = empService.getEmployeeList();
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}

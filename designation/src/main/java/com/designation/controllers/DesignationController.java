package com.designation.controllers;

import com.designation.entities.DesignationEntity;
import com.designation.entities.GenericResponse;
import com.designation.service.DesignationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/designation")
@Slf4j
public class DesignationController {

    @Autowired
    private DesignationService designService;

    @PostMapping("/addDesignation")
    public ResponseEntity addDesignation(@RequestBody DesignationEntity designEntity){
        log.info("Entered the /designation/addDesignation API..",designEntity);
        GenericResponse res = designService.addDesignation(designEntity);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/getDesignationByIds/{designId}")
    public ResponseEntity getDesignationById(@PathVariable("designId") int designId){
        log.info("Entered the /designation/getDesignationByIds API");
        GenericResponse res = designService.getDesignationDetails(designId);
        log.info("Response Body : {}", res);
        log.info("Getting responseBody");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


}

package com.department.service;

import com.department.entities.GenericResponse;
import com.department.service.impl.DepartmentServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//with docker container
@FeignClient(name = "Student-service" ,  url = "${student-service-base-url}" , fallback = DepartmentServiceImpl.class)
// with local
//@FeignClient(name = "Student" ,  url = "http://localhost:6068")
public interface StudentFeignClient {

    @GetMapping("/student/department/getSpecificDepartmentData/{depId}")
    public GenericResponse getStudentListById(@PathVariable("depId") int depId);
}

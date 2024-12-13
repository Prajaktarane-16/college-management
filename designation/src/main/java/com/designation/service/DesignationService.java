package com.designation.service;

import com.designation.entities.DesignationEntity;
import com.designation.entities.GenericResponse;

public interface DesignationService {

    GenericResponse addDesignation(DesignationEntity desgEntity);

    GenericResponse getDesignationDetails(int courseId);
}

package com.designation.service.impl;

import com.designation.entities.DesignationEntity;
import com.designation.entities.GenericResponse;
import com.designation.repositories.DesignationRepository;
import com.designation.service.DesignationService;
import com.designation.utils.DataManagementUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

import static com.designation.utils.DesignationConstant.*;

@Slf4j
@Service
public class DesignationServiceImpl implements DesignationService {

    @Autowired
    private DataManagementUtil dataManagementUtil;

    @Autowired
    private DesignationRepository designRepository;

    @Override
    public GenericResponse addDesignation(DesignationEntity desgEntity) {
        GenericResponse responseBody = new GenericResponse();
        int insertCount = designRepository.addDesignationDetails(desgEntity.getDesgId(), desgEntity.getDesgName());
        if(insertCount > 0){
            responseBody = dataManagementUtil.response(SUCCESS_CODE,
                    ADD_DESIGNATION_SUCCESS ,insertCount);
        }
        else
            responseBody = dataManagementUtil.response(INTERNAL_SERVER_ERROR_CODE,
                    ADD_DESIGNATION_FAILED, insertCount);
        return responseBody;
    }

    @Override
    public GenericResponse getDesignationDetails(int designId) {
        GenericResponse responseBody = new GenericResponse();
        Optional<DesignationEntity> mappingDetails = designRepository.getMappingDetailsById(designId);
        if(mappingDetails == null){
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DATABASE_ERROR_MSG);
            responseBody.setData(new HashMap<>());
        } else if (mappingDetails.isEmpty()) {
            responseBody.setStatus(INTERNAL_SERVER_ERROR_CODE);
            responseBody.setMessage(DESIGNATION_DATA_NOT_FOUND);
            responseBody.setData(new HashMap<>());
        } else {
            responseBody.setStatus(SUCCESS_CODE);
            responseBody.setMessage(DESIGNATION_DATA_FOUND);
            responseBody.setData(mappingDetails);
        }
        return responseBody;
    }
}

package com.designation.utils;


import com.designation.entities.GenericResponse;
import org.springframework.stereotype.Component;

@Component
public class DataManagementUtil {

    public GenericResponse response (int status, String message, Object data) {
        GenericResponse responseBody = new GenericResponse();
        responseBody.setStatus(status);
        responseBody.setMessage(message);
        responseBody.setData(data);
        return responseBody;
    }
}

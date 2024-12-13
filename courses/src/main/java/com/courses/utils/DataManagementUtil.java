package com.courses.utils;

import com.courses.entities.GenericResponse;
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

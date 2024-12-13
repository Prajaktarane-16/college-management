package com.department.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenericResponse<T> implements Serializable {

    private int status;

    private String message;

    private T data;
}

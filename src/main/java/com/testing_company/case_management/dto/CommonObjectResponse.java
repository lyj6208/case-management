package com.testing_company.case_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonObjectResponse<T> {
    private boolean success;
    private String message;
    private T data;
    public static<T>CommonObjectResponse<T>success (T data, String message){
        return new CommonObjectResponse<>(true, message, data);
    }
    public static<T>CommonObjectResponse<T>error(String message){
        return new CommonObjectResponse<>(false, message, null);
    }
}

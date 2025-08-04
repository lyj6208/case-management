package com.testing_company.case_management.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class MyErrorResponse {
    private int status;
    private String message;
    private String path;
    private String errorCode;
    private LocalDateTime timestamp;

    public MyErrorResponse(HttpStatus status, String message, String path, String errorCode){
        this.status=status.value();
        this.message=message;
        this.path=path;
        this.errorCode=errorCode;
        this.timestamp=LocalDateTime.now();
    }
}

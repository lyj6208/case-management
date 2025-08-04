package com.testing_company.case_management.exception;

public class NotFoundException extends RuntimeException{
    private String errorCode;

    public NotFoundException(String message){
        super(message);
        this.errorCode="NOT_FOUND";
    }
    public NotFoundException(String message, String errorCode){
        super(message);
        this.errorCode=errorCode;
    }
    public String getErrorCode(){
        return errorCode;
    }
}

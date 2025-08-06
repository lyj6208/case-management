package com.testing_company.case_management.exception;

import com.testing_company.case_management.dto.MyErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jdk.jfr.StackTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //輸入錯誤的欄位type
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MyErrorResponse>handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request){
        MyErrorResponse errorResponse=new MyErrorResponse(
                HttpStatus.BAD_REQUEST,
                "請求參數格式錯誤，無法處理：" + ex.getValue(),
                request.getRequestURI(),
                ex.getErrorCode());
        log.warn("請求參數格式錯誤，無法處理：{}",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    //Controller驗證model條件
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>>handleValidation(MethodArgumentNotValidException ex){

        Map<String, Object>errorsResponse=new LinkedHashMap<>();
        errorsResponse.put("time", LocalDateTime.now());
        errorsResponse.put("error type", ex.getClass()+ " by Jakarta Bean Validation at Controller");


        List<Map<String, Object>>errorsList=new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error->{
            Map<String, Object>errorsMap=new LinkedHashMap<>();

            errorsMap.put("field",error.getField());
            errorsMap.put("message",error.getDefaultMessage());
            errorsList.add(errorsMap);
        });errorsResponse.put("errors",errorsList);
        log.warn("發生參數驗證錯誤：{},原始訊息：{}",errorsList,ex.getMessage());
        return ResponseEntity.badRequest().body(errorsResponse);
    }
    //Entity驗證model條件
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>>handelJpaValidation(ConstraintViolationException ex){
        Map<String, Object>errorsResponse=new LinkedHashMap<>();
        errorsResponse.put("time", LocalDateTime.now());
        errorsResponse.put("error type",ex.getClass()+" by Hibernate Bean Validator at Entity");
        List<Map<String, Object>> errorsList=new ArrayList<>();
        ex.getConstraintViolations().forEach(violation->{
            Map<String, Object>errorsMap=new LinkedHashMap<>();
            errorsMap.put("model",violation.getRootBeanClass().toString());
            errorsMap.put("filed",violation.getPropertyPath().toString());
            errorsMap.put("message", violation.getMessage());
            errorsList.add(errorsMap);
        });errorsResponse.put("errors",errorsList);
        log.warn("約束違反異常：{},原始訊息：{}",errorsList,ex.getMessage());
        return ResponseEntity.badRequest().body(errorsResponse);
    }
    //未找到資料
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MyErrorResponse>handleNotFoundException(NotFoundException ex, HttpServletRequest request){
        MyErrorResponse myErrorResponse=new MyErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI(),
                ex.getErrorCode()
        );
        log.warn("找不到資料：{}",ex.getMessage());
        return new ResponseEntity<>(myErrorResponse, HttpStatus.NOT_FOUND);
    }
    //驗證資料庫
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MyErrorResponse>handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
        String message=ex.getRootCause()!=null
                ?ex.getRootCause().getMessage():ex.getMessage();
        String userMessage;
        if(message.contains("Duplicate")||message.contains("UNIQUE")){
            userMessage="資料重複，請使用其他名稱";
        }else if(message.contains("a foreign key constraint fails")){
            userMessage="關聯資料錯誤，請確認相關欄位是否正確存在";
        }else if(message.contains("cannot be null")){
            userMessage="有必要欄位為空，請確認資料完整性";
        }else if(message.contains("客製化訊息")){
            userMessage= ex.getMessage().substring(7);
        }else {
            userMessage="資料儲存失敗，請確認輸入內容";
        }
        MyErrorResponse myErrorResponse=new MyErrorResponse(
                HttpStatus.CONFLICT,
                userMessage,
                request.getRequestURL().toString(),
                HttpStatus.CONFLICT.getReasonPhrase());
        log.warn("資料庫錯誤：{}",ex.getMessage());
        return new ResponseEntity<>(myErrorResponse, HttpStatus.CONFLICT);
    }

    //enum格式錯誤
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?>handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request){
        MyErrorResponse myErrorResponse=new MyErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.getReasonPhrase().toString());
        log.warn("資料格式錯誤：{}",ex.getMessage());
        return new ResponseEntity<>(myErrorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?>handleDuplicateException(DuplicateException ex, HttpServletRequest request){
        MyErrorResponse myErrorResponse=new MyErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
        log.warn("資料重複：{}",ex.getMessage());
        return new ResponseEntity<>(myErrorResponse, HttpStatus.CONFLICT);
    }
}

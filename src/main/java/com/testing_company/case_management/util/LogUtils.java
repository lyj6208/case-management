package com.testing_company.case_management.util;

import org.slf4j.Logger;

import java.util.Arrays;
import java.util.stream.Stream;

public class LogUtils {
    public static void logRequest(Logger log, Object context, String message, Object... args){
        String className=context.getClass().getSimpleName();
        String methodName=Thread.currentThread().getStackTrace()[2].getMethodName();
        String fullMessage="[收到請求]"+message+"\t[來自]類別：{}，方法：{}";
        Object[] fullArgs= Stream.concat(Arrays.stream(args),Stream.of(className, methodName)).toArray();
        log.info(fullMessage, fullArgs);
    }

    public static void logResponse(Logger log, Object context, String message, Object... args){
        String className=context.getClass().getSimpleName();
        String methodName=Thread.currentThread().getStackTrace()[2].getMethodName();
        String fullMessage="[回覆請求]"+message+"\t[來自]類別：{}，方法：{}";
        Object[] fullArgs=Stream.concat(Arrays.stream(args), Stream.of(className, methodName)).toArray();
        log.info(fullMessage, fullArgs);
    }
}

package com.itbsky.exception;

/**
 * 自定义异常：终止已经不符合业务逻辑的操作
 */
public class MyException extends RuntimeException {
    private final static Long serialVersionUID = 1127151684998116941L;
    public MyException(String message){
        super(message);
    }
}
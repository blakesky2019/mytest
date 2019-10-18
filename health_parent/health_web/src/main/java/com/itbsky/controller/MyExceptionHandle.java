package com.itbsky.controller;


import com.itbsky.entity.Result;
import com.itbsky.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 包名:com.itbsky.controller
 * 作者:龙在江湖
 * 日期:2019/9/22 16:13
 */
@RestControllerAdvice
public class MyExceptionHandle {

    //记录日志
    private final static Logger log=LoggerFactory.getLogger(MyExceptionHandle.class);


    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException myException){
        //log.error("验证失败",myException);
        myException.printStackTrace();
        return new Result(false,"发生了异常");
    }


    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException myException){

        //log.error("RuntimeException",myException);
        myException.printStackTrace();
        return new Result(false,"发生了异常");
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception exception){
       //log.error("出错了",exception);
        exception.printStackTrace();
        return new Result(false,"发生了异常");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result handleAccessDeniedException(AccessDeniedException exception){
        //log.error("出错了",exception);
        exception.printStackTrace();
        return new Result(false,"您没有权限!");
    }

}

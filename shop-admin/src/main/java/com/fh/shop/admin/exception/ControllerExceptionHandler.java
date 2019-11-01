package com.fh.shop.admin.exception;

import com.fh.shop.admin.common.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//加上此注解才能生效
@ControllerAdvice
public class ControllerExceptionHandler {

    //异常生效类型
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServerResponse handleException(Exception ex) {
        ex.printStackTrace();
        return ServerResponse.error();
    }
}

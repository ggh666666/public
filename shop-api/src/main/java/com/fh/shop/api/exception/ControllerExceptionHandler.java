package com.fh.shop.api.exception;

import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(GlobalException.class)//捕获的异常类型
    @ResponseBody
    public ServerResponse handleException(GlobalException globalException) {
        return ServerResponse.error(globalException.getResponseEnum());
    }
}

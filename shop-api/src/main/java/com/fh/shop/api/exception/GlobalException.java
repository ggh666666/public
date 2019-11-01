package com.fh.shop.api.exception;

import com.fh.shop.api.common.ResponseEnum;

/**
 * 自定义异常类
 */
public class GlobalException extends RuntimeException {
    private ResponseEnum responseEnum;

    public GlobalException(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }

    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }
}

package com.fh.shop.admin.common;

import java.io.Serializable;

public class ServerResponse implements Serializable {
    //暂时存放 日志打印
    public static String printLog(Class clazz, String method) {
        String clazzName = clazz.getName();
        //String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
        return "------ log print ---- 访问 class : " + clazzName + " 的 " + method + " Method 成功------";
    }

    private int code;

    private String msg;

    private Object data;

    private ServerResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    static public ServerResponse success() {
        return new ServerResponse(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), null);
    }

    public static ServerResponse success(Object data) {
        return new ServerResponse(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), data);
    }

    public static ServerResponse error() {
        return new ServerResponse(ResponseEnum.ERROR.getCode(), ResponseEnum.ERROR.getMsg(), null);
    }

    public static ServerResponse error(ResponseEnum responseEnum) {
        return new ServerResponse(responseEnum.getCode(), responseEnum.getMsg(), null);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}

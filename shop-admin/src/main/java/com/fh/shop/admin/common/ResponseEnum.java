package com.fh.shop.admin.common;

public enum ResponseEnum {
    //逗号隔开 必须写在最上面  调用:ResponseEnum.ERROR.getCode()
    ERROR(-1, "error"),
    HAVE_NO_RIGHT(-10000, "权限不足"),
    USERNAME_PASSWORD_IS_EMPTY(1000, "用户名或密码为空"),
    USERNAME_IS_ERROR(1001, "用户名错误"),
    PASSWORD_IS_ERROR(1002, "密码错误"),
    USERNAME_IS_EXIST(1003, "用户名已存在"),
    USER_IS_LOCK(1004, "用户已被锁定"),
    EMAIL_IS_NOT(1005, "邮箱与用户名不匹配"),
    USER_PASSWORD_IS_NULL(1006, "原密码或新密码或确认密码不能为空"),
    NEWPASSWORD_CONPASSWORD_IS_ERROR(1007, "新密码和确认密码不一致"),
    OLDPASSWORD_IS_ERROR(1008, "原密码不正确,请重新输入"),
    USER_NOT_EXIST(1009, "用户不存在"),
    EMAIL_IS_ERROR(1010, "邮箱错误"),
    CODE_IS_NULL(7000, "验证码为空"),
    CODE_IS_ERROR(7001, "验证码错误"),
    SUCCESS(200, "ok");

    private int code;

    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

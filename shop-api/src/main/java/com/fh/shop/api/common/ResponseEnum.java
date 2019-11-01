package com.fh.shop.api.common;

public enum ResponseEnum {
    //逗号隔开 必须写在最上面  调用:ResponseEnum.ERROR.getCode()
    ERROR(-1, "error"),
    MOBILE_IS_NULL(1001, "请输入手机号"),
    CODE_IS_ERROR(1002, "验证码错误"),
    CODE_SEND_ERROR(1003, "验证码发送失败"),
    CODE_NOT_SEND_OR_LOSE(1004, "验证码已失效或未发送"),
    CODE_IS_NULL(1005, "请输入验证码"),
    USERNAME_IS_NULL(1006, "请输入账号"),
    PASSWORD_IS_NULL(1007, "请输入密码"),
    PASSWORD0_IS_UNLIKE(1008, "俩次密码不一致"),
    NAME_IS_NULL(1009, "请输入名字"),
    MOBILE_IS_EXISTS(1010, "手机号已注册"),
    USERNAME_IS_EXISTS(1011, "账号已注册"),
    MOBILE_IS_ERROR(1012, "请输入正确的手机号"),
    USERNAME_IS_ERROR(1013, "请输入正确账号"),
    PASSWORD_IS_ERROR(1014, "请输入正确的密码"),
    HEADER_IS_MISS(1015, "头部信息丢失"),
    SIGN_IS_MISS(1016, "签名信息缺失"),
    SIGN_IS_ERROR(1017, "签名信息错误"),
    LOGIN_IS_TIMEOUT(1018, "登录超时"),
    PRODUCT_NOT_EXISTS(1019, "商品不存在"),
    PRODUCT_IS_DOWN(1020, "商品已下架"),
    MOBILE_IS_NOT_REGISTER(1021, "手机未注册"),
    HANDLER_IS_REPET(1022, "重复操作"),
    CART_IS_NULL(1023, "购物车为空"),
    STOCK_IS_SHORTAGE(1024, "购物车中没有库存足够的商品"),
    PAY_IS_TIMEOUT(1025, "订单支付超时"),
    PAY_IS_SUCCESS(200, "订单支付成功"),
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

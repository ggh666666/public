package com.weixin;

/**
 * 微信支付配置文件
 *
 * @author chenp
 */
public class WeChatConfig {

    /**
     * 微信服务号APPID
     */
    public static String APPID = "wxa1e44e130a9a8eee";
    /**
     * 微信支付的商户号
     */
    public static String MCHID = "1507758211";
    /**
     * 微信支付的API密钥
     */
    public static String APIKEY = "feihujiaoyu12345678yuxiaoyang123";
    /**
     * 微信支付成功之后的回调地址【注意：当前回调地址必须是公网能够访问的地址】
     */
    public static String WECHAT_NOTIFY_URL_PC = "www.baidu.com";
    /**
     * 微信统一下单API地址mch_id
     */
    public static String UFDODER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * true为使用真实金额支付，false为使用测试金额支付（1分）
     */
    public static String WXPAY = "1";

}

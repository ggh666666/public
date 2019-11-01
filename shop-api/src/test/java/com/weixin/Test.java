package com.weixin;

public class Test {

    public static void main(String[] args) {
        WeChatParams ps = new WeChatParams();
        ps.setBody("测试商品3");
        ps.setTotal_fee("1");
        ps.setOut_trade_no("3456789");
        ps.setAttach("xiner");
        ps.setMemberid("888");
        String urlCode = null;
        try {
            urlCode = WeixinPay.getCodeUrl(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("======");
        System.out.println(urlCode);
        System.out.println("======");

    }
}

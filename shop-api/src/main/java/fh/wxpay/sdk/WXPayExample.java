package fh.wxpay.sdk;

import java.util.HashMap;
import java.util.Map;

public class WXPayExample {
    public static void main(String[] args) throws Exception {

        MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", "2016090910595900111111");

        try {
            Map<String, String> resp = wxpay.orderQuery(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        MyConfig config = new MyConfig();
//        WXPay wxpay = new WXPay(config);
//
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("body", "ggh-测试");
//        data.put("out_trade_no", "2016090910595900111111");
//        data.put("total_fee", "1");
//        data.put("spbill_create_ip", "123.12.12.123");
//        data.put("notify_url", "http://www.example.com/wxpay/notify");
//        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
////        data.put("product_id", "12");
//
//        try {
//            Map<String, String> resp = wxpay.unifiedOrder(data);
//            System.out.println(resp);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}

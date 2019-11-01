package com.fh.shop.api.pay.biz;

import com.alibaba.fastjson.JSON;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.order.mapper.IOrderMapper;
import com.fh.shop.api.order.po.Order;
import com.fh.shop.api.paylog.mapper.IPaylogMapper;
import com.fh.shop.api.paylog.po.Paylog;
import com.fh.shop.api.util.BigDecimalUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import fh.wxpay.sdk.MyConfig;
import fh.wxpay.sdk.WXPay;
import fh.wxpay.sdk.WXPayConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("payService")
public class PayService {

    @Autowired
    private IOrderMapper orderMapper;

    @Autowired
    private IPaylogMapper paylogMapper;

    public ServerResponse CreateQRCode(Long id) {
        //
        String paylogJson = RedisUtil.get(KeyUtil.buildPaylogKey(id));
        if (StringUtils.isEmpty(paylogJson)) {
            return ServerResponse.error();
        }

        //调用微信统一下单
        Paylog paylog = JSON.parseObject(paylogJson, Paylog.class);
        Map map = new HashMap();
        String qrCode = null;
        try {
            MyConfig config = new MyConfig();
            WXPay wxpay = new WXPay(config);

            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "ggh-测试");
            data.put("out_trade_no", paylog.getOrderId());
            data.put("total_fee", BigDecimalUtil.multiply(paylog.getPayMoney().toString(), "100").intValue() + "");
            data.put("spbill_create_ip", "123.12.12.123");
            SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
            Date now = new Date();
            data.put("time_start", yyyyMMddHHmmss.format(now));
            data.put("time_expire", yyyyMMddHHmmss.format(DateUtils.addMinutes(now, 1)));
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            //        data.put("product_id", "12");

            Map<String, String> resp = wxpay.unifiedOrder(data);
            //return_code
            if (!resp.get("return_code").equalsIgnoreCase(WXPayConstants.SUCCESS)) {
                return ServerResponse.error(2000, resp.get("return_msg"));
            }
            //result_code
            if (!resp.get("result_code").equalsIgnoreCase(WXPayConstants.SUCCESS)) {
                return ServerResponse.error(2001, resp.get("return_msg"));
            }
            //code_url
            if (StringUtils.isEmpty(resp.get("code_url"))) {
                return ServerResponse.error(2002, "二维码生成失败");
            }
            qrCode = resp.get("code_url");
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("qrCode", qrCode);
        map.put("payMoney", paylog.getPayMoney());
        map.put("orderId", paylog.getOrderId());

        return ServerResponse.success(map);
    }

    public ServerResponse payStatus(Long id) {
        //
        String paylogJson = RedisUtil.get(KeyUtil.buildPaylogKey(id));
        if (StringUtils.isEmpty(paylogJson)) {
            return ServerResponse.error();
        }

        //调用微信查询订单
        try {
            Paylog paylog = JSON.parseObject(paylogJson, Paylog.class);

            MyConfig config = new MyConfig();
            WXPay wxpay = new WXPay(config);

            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", paylog.getOrderId());
            int count = 0;
            while (true) {
                Map<String, String> resp = wxpay.orderQuery(data);

                //return_code
                if (!resp.get("return_code").equalsIgnoreCase(WXPayConstants.SUCCESS)) {
                    return ServerResponse.error(2000, resp.get("return_msg"));
                }
                //result_code
                if (!resp.get("result_code").equalsIgnoreCase(WXPayConstants.SUCCESS)) {
                    return ServerResponse.error(Integer.valueOf(resp.get("err_code")), resp.get("err_code_des"));
                }
                //trade_state
                if (resp.get("trade_state").equalsIgnoreCase(WXPayConstants.SUCCESS)) {
                    //修改订单状态
                    Order order = orderMapper.selectById(paylog.getOrderId());
                    order.setStatus(1);
                    order.setStatusDesc("已支付");
                    orderMapper.updateById(order);
                    //修改支付日志
                    paylog.setPayStatus(1);
                    paylog.setPayTime(new Date());
                    paylog.setTransactionId(resp.get("transaction_id "));
                    paylogMapper.updateById(paylog);
                    //清空redis
                    RedisUtil.del(KeyUtil.buildPaylogKey(id));
                    return ServerResponse.success(paylog.getPayMoney());
                }
                System.out.println(resp);
                Thread.sleep(1000 * 3);
                count++;
                if (count >= 20) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.error(ResponseEnum.PAY_IS_TIMEOUT);
    }
}

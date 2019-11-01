package com.fh.shop.api.util.note;

import com.alibaba.fastjson.JSON;
import com.fh.shop.api.util.HttpUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;

public class NoteUtil {
    //发送验证码的请求路径URL
    private static final String
            SERVER_URL = "https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    private static final String
//            APP_KEY="854154723a80cdf85fc757801c477c82";
            APP_KEY = "b6da9d1b1c7d4770a2f30f1382c9fd79";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
//    private static final String APP_SECRET="11ed9411d835";
    private static final String APP_SECRET = "8fe3f820d125";
    //随机数
    private static final String NONCE = "123456";
    //短信模板ID    不加则是短信
//    private static final String TEMPLATEID="14825218";
    //手机号
//    private static final String MOBILE="15001359482";
    private static final String MOBILE = "15910933097";
    //验证码长度，范围4～10，默认为4
    private static final String CODELEN = "4";


    public static String sendCode(String mobile) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(SERVER_URL);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        /*
         * 参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
         */
        String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);

        // 设置请求的header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        /*
         * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
         * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
         * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
         */
//        nvps.add(new BasicNameValuePair("templateid", TEMPLATEID));
        nvps.add(new BasicNameValuePair("mobile", mobile));
        nvps.add(new BasicNameValuePair("codeLen", CODELEN));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        /*
         * 1.打印执行结果，打印结果一般会200、315、403、404、413、414、500
         * 2.具体的code有问题的可以参考官网的Code状态表
         */
        System.out.println();
        String responseJson = EntityUtils.toString(response.getEntity(), "utf-8");
        NoteResponse noteResponse = JSON.parseObject(responseJson, NoteResponse.class);
        if (200 == noteResponse.getCode())
            return noteResponse.getObj();
        return "";
    }

    /**
     * httpClient 请求网易云接口发送短信
     *
     * @param mobile
     * @return
     */
    public static String sendCodeHttpClient(String mobile) {

        //头信息
        Map<String, String> headers = new HashMap<>();
        headers.put("AppKey", APP_KEY);
        String nonce = UUID.randomUUID().toString();
        headers.put("Nonce", nonce);
        String curTime = System.currentTimeMillis() + "";
        headers.put("CurTime", curTime);
        headers.put("CheckSum", CheckSumBuilder.getCheckSum(APP_SECRET, nonce, curTime));

        //参数
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);

        //将响应的json字符串转换成javaBean
        NoteResponse noteResponse = JSON.parseObject(HttpUtil.httpPost(SERVER_URL, headers, params), NoteResponse.class);
        if (200 == noteResponse.getCode())
            return noteResponse.getObj();
        return "";
    }
}

package com.fh.shop.api.util;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    public static String httpPost(String url, Map<String, String> headers, Map<String, String> params) {
        CloseableHttpClient client = null;

        HttpPost httpPost = null;

        CloseableHttpResponse response = null;
        try {
            client = HttpClientBuilder.create().build();

            httpPost = new HttpPost(url);

            //添加头部信息
            if (headers != null && headers.size() > 0) {
                Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> next = iterator.next();
                    httpPost.addHeader(next.getKey(), next.getValue());
                }

            }

            //添加参数
            if (params != null && params.size() > 0) {
                Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
                List list = new ArrayList();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> next = iterator.next();
                    list.add(new BasicNameValuePair(next.getKey(), next.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
            }

            //发送请求 并 接收响应信息(Json字符串)
            response = client.execute(httpPost);
            String s = EntityUtils.toString(response.getEntity(), "utf-8");
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {//    关闭/释放 连接
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpPost != null)
                httpPost.releaseConnection();
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}

package com.fh.shop.api.util;

import java.security.MessageDigest;
import java.util.UUID;

public class Md5Util {

    /**
     * 获取签名
     *
     * @param s
     * @return
     */
    public final static String sign(String s) {
        return md5(s + SystemConst.SECORET_KEY);
    }

    /**
     * 双重md5+salt
     *
     * @param s
     * @return
     */
    public final static String salt_md5_2(String s, String salt) {

        return md5(md5(s) + salt);
    }

    /**
     * 将明文处理成salt+md5组成的数组
     *
     * @param s
     * @return ss[0] = salt    ss[1] = md5
     */
    public final static String[] salt_md5_2(String s) {
        String[] ss = new String[2];
        String salt = UUID.randomUUID().toString();
        String md5 = md5(md5(s) + salt);
        ss[0] = salt;
        ss[1] = md5;
        return ss;
    }

    public final static String md5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            // 使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                // 将没个数(int)b进行双字节加密
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(md5("123"));
        String[] ss = salt_md5_2("ggh");
        System.out.println(ss[0]);
        System.out.println(ss[1]);
        System.out.println(salt_md5_2("ggh", ss[0]));
    }
}

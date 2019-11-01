package com.fh.shop.api.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class DistributedSession {
    public static String getSessionId(HttpServletRequest req, HttpServletResponse resp) {
        String sessionId = CookieUtil.readCookie(SystemConst.SESSION_ID, req);
        if (StringUtils.isEmpty(sessionId)) {
            sessionId = UUID.randomUUID().toString();
            CookieUtil.writeCookie(SystemConst.SESSION_ID, sessionId, "shop.admin.com", resp);
        }
        return sessionId;
    }

    public static void invalidate(String sessionId) {
        String[] keys =
                {
//                        KeyUtil.buildCodeKey(sessionId),//验证码在登陆后就会删除
                        KeyUtil.buildCurrUserKey(sessionId),
                        KeyUtil.buildCurrUserMenuInfoKey(sessionId),
                        KeyUtil.buildCurrUserMenuUrls(sessionId),
                        KeyUtil.buildLastLoginTimeKey(sessionId),
                        KeyUtil.buildMenuAllInfo(sessionId)
                };

        //删除多个key
        RedisUtil.del(keys);
    }

    public static void reExpire(String sessionId) {//重置session过期时间
        String[] keys =
                {
                        //KeyUtil.buildCodeKey(sessionId) ,//验证码不用刷新
                        KeyUtil.buildCurrUserKey(sessionId),
                        KeyUtil.buildCurrUserMenuInfoKey(sessionId),
                        KeyUtil.buildCurrUserMenuUrls(sessionId),
                        KeyUtil.buildLastLoginTimeKey(sessionId),
                        KeyUtil.buildMenuAllInfo(sessionId)
                };

        for (String key : keys) {
            RedisUtil.expire(key, SystemConst.EXPIRE);
        }
    }
}

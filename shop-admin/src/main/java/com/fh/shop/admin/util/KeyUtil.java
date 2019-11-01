package com.fh.shop.admin.util;

public class KeyUtil {
    public static String buildCodeKey(String sessionId) {
        return "code" + ":" + sessionId;
    }

    public static String buildCurrUserKey(String sessionId) {
        return SystemConst.CURR_USER + ":" + sessionId;
    }

    public static String buildLastLoginTimeKey(String sessionId) {
        return "lastLoginTime" + ":" + sessionId;
    }

    public static String buildCurrUserMenuInfoKey(String sessionId) {
        return SystemConst.CURR_USER_MENU_INFO + ":" + sessionId;
    }

    public static String buildMenuAllInfo(String sessionId) {
        return SystemConst.MENU_ALL_INFO + ":" + sessionId;
    }

    public static String buildCurrUserMenuUrls(String sessionId) {
        return SystemConst.CURR_USER_MENU_URLS + ":" + sessionId;
    }
}

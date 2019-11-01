package com.fh.shop.api.util;

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

    public static String buildMobileKey(String sessionId) {
        return "mobile" + ":" + sessionId;
    }

    public static String buildMobileCodeKey(String mobile) {
        return "mobile_code" + ":" + mobile;
    }

    public static String buildMember(String username, String uuid) {
        return "member:" + username + "uuid:" + uuid;
    }

    public static String buildCartField(Long memberId) {
        return "member:" + memberId;
    }

    public static String buildProductField(Long productId) {
        return "product:" + productId;
    }

    public static String buildMemberField(Long memberId) {
        return "member:" + memberId;
    }

    public static String buildPaylogKey(Long memberId) {
        return "paylog:" + memberId;
    }
}

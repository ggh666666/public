package com.fh.shop.admin.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void writeCookie(String name, String value, String domain, HttpServletResponse resp) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath("/");
//        cookie.setMaxAge(5 * 60);
        resp.addCookie(cookie);
    }

    public static String readCookie(String name, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name))
                return cookie.getValue();
        }

        return null;
    }
}

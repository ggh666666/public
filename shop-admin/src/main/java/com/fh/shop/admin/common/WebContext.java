package com.fh.shop.admin.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebContext {

    public static ThreadLocal<HttpServletRequest> threadLocal = new ThreadLocal<>();
    public static ThreadLocal<HttpServletResponse> threadLocal2 = new ThreadLocal<>();

    public static void setRequest(HttpServletRequest request) {
        threadLocal.set(request);
    }

    public static HttpServletRequest getRequest() {
        return threadLocal.get();
    }

    public static void removeRequest() {
        threadLocal.remove();
    }

    public static void setResponse(HttpServletResponse response) {
        threadLocal2.set(response);
    }

    public static HttpServletResponse getResponse() {
        return threadLocal2.get();
    }

    public static void removeResponse() {
        threadLocal2.remove();
    }
}

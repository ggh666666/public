package com.fh.shop.admin.interceptor;

import com.fh.shop.admin.util.DistributedSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReExpireInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //分布式session    cookie + redis
        //获取sessionId(自定义的)
        String sessionId = DistributedSession.getSessionId(request, response);

        //重置session生命周期
        DistributedSession.reExpire(sessionId);

        return true;
    }
}

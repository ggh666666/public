package com.fh.shop.admin.interceptor;

import com.alibaba.fastjson.JSON;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.util.DistributedSession;
import com.fh.shop.admin.util.KeyUtil;
import com.fh.shop.admin.util.RedisUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        User user = (User) request.getSession().getAttribute(SystemConst.CURR_USER);

        //分布式session    cookie + redis
        String sessionId = DistributedSession.getSessionId(request, response);
        String userJson = RedisUtil.get(KeyUtil.buildCurrUserKey(sessionId));
        User user = JSON.parseObject(userJson, User.class);


        if (user == null) {
            response.sendRedirect("/");//欢迎页面为登录页面 /login.jsp
            return false;
        }

        //分布式session    cookie + redis
        //获取sessionId(自定义的)
        //String sessionId = DistributedSession.getSessionId(request, response);

        //重置session生命周期
        DistributedSession.reExpire(sessionId);

        return true;
    }
}

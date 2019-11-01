package com.fh.shop.admin.interceptor;

import com.fh.shop.admin.util.DistributedSession;
import com.fh.shop.admin.util.KeyUtil;
import com.fh.shop.admin.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class MenuInterceptor2 extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();//用于将uri后面多余部分去掉 只留下模块路径
        if ("/index.jhtml".equals(uri))
            return true;
        //判断用户是否有访问权限 防止用户通过url直接访问
//        List<String> menuUrls = (List<String>) request.getSession().getAttribute(SystemConst.CURR_USER_MENU_URLS);

        //分布式session    cookie + redis
        String sessionId = DistributedSession.getSessionId(request, response);
        String menuUrlsStr = RedisUtil.get(KeyUtil.buildCurrUserMenuUrls(sessionId));
        List<String> menuUrls = Arrays.asList(menuUrlsStr.split(","));


        for (int i = 0; i < menuUrls.size(); i++) {//将request的uri与数据库查出来的路径做对比 相同则通过
            String url = menuUrls.get(i);//用于将url后面多余部分去掉 只留下模块路径   如:/user/toList.jhtml /user就是模块路径
            if (!StringUtils.isEmpty(url) && !url.equals("#") && !uri.equals("#")) {//uri有时为#
                int urlIndex = url.lastIndexOf("/");
                int uriIndex = uri.lastIndexOf("/");
                if (uriIndex != -1 && urlIndex != -1) {//uri为#时会报下标越界异常
                    if (uri.substring(0, uriIndex).equals(url.substring(0, urlIndex))) {
                        return true;
                    }
                }
            }
        }
        response.sendRedirect("/index.jhtml");//访问没有权限的路径会跳转到导航页面
        return false;
    }
}

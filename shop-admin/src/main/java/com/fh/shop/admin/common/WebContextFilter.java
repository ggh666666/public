package com.fh.shop.admin.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        WebContext.setRequest((HttpServletRequest) request);
        //
        WebContext.setResponse((HttpServletResponse) response);

        //这个类就是过滤器 相当于咱们的拦截器
        try {
            //继续执行原来的核心业务
            chain.doFilter(request, response);
        } finally {
            WebContext.removeRequest();
            //
            WebContext.removeResponse();
        }
    }

    @Override
    public void destroy() {

    }
}

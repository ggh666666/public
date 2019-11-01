package com.fh.shop.api.interceptor;

import com.fh.shop.api.common.ApiIdempotent;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class IdempotentInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        boolean annotationPresent = method.isAnnotationPresent(ApiIdempotent.class);
        if (!annotationPresent) {
            return true;
        }
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new GlobalException(ResponseEnum.HEADER_IS_MISS);
        }
        boolean exists = RedisUtil.exists(token);
        if (!exists) {
            throw new GlobalException(ResponseEnum.HANDLER_IS_REPET);
        }
        Long del = RedisUtil.del(token);
        if (del <= 0) {
            throw new GlobalException(ResponseEnum.HANDLER_IS_REPET);
        }
        return true;
    }
}

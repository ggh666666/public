package com.fh.shop.api.interceptor;

import com.alibaba.fastjson.JSON;
import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.Md5Util;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Base64;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //跨域
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:8082");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "x-auth,token");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE");

        //处理options请求
        String requestMethod = request.getMethod();
        if (requestMethod.equalsIgnoreCase("options")) {
            return false;
        }

        //公共资源放行
        HandlerMethod handler1 = (HandlerMethod) handler;
        Method method = handler1.getMethod();
        boolean annotationPresent = method.isAnnotationPresent(Check.class);
        if (!annotationPresent) {
            return true;
        }

        //头部信息非空判断
        String authHeader = request.getHeader("x-auth");
        if (StringUtils.isEmpty(authHeader)) {
            throw new GlobalException(ResponseEnum.HEADER_IS_MISS);
        }
        //验签
        String[] split = authHeader.split("\\.");//  .在正则中是特殊符号 需转义
        if (split.length != 2) {
            throw new GlobalException(ResponseEnum.SIGN_IS_MISS);
        }

        String memberJson = null;
        try {
            String memberBase64 = split[0];
            String signBase64 = split[1];

            byte[] memberDecode = new byte[0];
            byte[] signDecode = new byte[0];
            memberDecode = Base64.getDecoder().decode(memberBase64);
            signDecode = Base64.getDecoder().decode(signBase64);
            memberJson = StringUtils.toEncodedString(memberDecode, null);
            String sign = Md5Util.sign(memberJson);
            if (!sign.equals(new String(signDecode))) {
                throw new GlobalException(ResponseEnum.SIGN_IS_ERROR);
            }
        } catch (GlobalException e) {
            e.printStackTrace();
            throw new GlobalException(ResponseEnum.SIGN_IS_ERROR);
        }

        //登录超时验证
        MemberVo memberVo = JSON.parseObject(memberJson, MemberVo.class);
        String memberKey = KeyUtil.buildMember(memberVo.getUsername(), memberVo.getUuid());
        boolean exists = RedisUtil.exists(memberKey);
        if (!exists) {
            throw new GlobalException(ResponseEnum.LOGIN_IS_TIMEOUT);
        }

        //续命
        RedisUtil.expire(memberKey, SystemConst.EXPIRE);

        //将会员信息存到request中 方便controller层获取
        request.setAttribute("member", memberVo);
        return true;
    }
}

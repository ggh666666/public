package com.fh.shop.api.sms.biz;

import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.biz.MemberService;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Service("smsService")
public class SMSService {

    public ServerResponse validateCode(String mobile, String code2, HttpServletRequest request, MemberService memberService) {
        //根据flag判断什么时候发送的验证码 若 flag不为空 则 是登录时发送的
        String flag = request.getParameter("flag");
        if (StringUtils.isNotEmpty(flag)) {
            //验证手机号注册情况
            Member one = memberService.findOneByMobile(mobile);
            if (one == null) {
                return ServerResponse.error(ResponseEnum.MOBILE_IS_NOT_REGISTER);
            }
        }

        //验证手机号格式
        String pattern = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
        boolean res = Pattern.matches(pattern, mobile);
        if (!res)
            return ServerResponse.error(ResponseEnum.MOBILE_IS_ERROR);
        //分布式session    redis + cookie
//        String sessionId = DistributedSession.getSessionId(request, response);
        //根据手机号 从redis中获取 验证码
        String code = RedisUtil.get(KeyUtil.buildMobileCodeKey(mobile));
        if (StringUtils.isEmpty(code))
            return ServerResponse.error(ResponseEnum.CODE_IS_ERROR);
        if (!code.equals(code2))//前台输入的验证码 与 手机收到的做对比
            return ServerResponse.error(ResponseEnum.CODE_NOT_SEND_OR_LOSE);
        return ServerResponse.success();
    }
}

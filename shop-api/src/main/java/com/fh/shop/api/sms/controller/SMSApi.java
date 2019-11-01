package com.fh.shop.api.sms.controller;

import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.biz.MemberService;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.sms.biz.SMSService;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemConst;
import com.fh.shop.api.util.note.NoteUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/sms")
@CrossOrigin("*")
public class SMSApi {

    @Autowired
    private HttpServletRequest request;

    @Resource(name = "smsService")
    private SMSService smsService;

    @Resource(name = "memberService")
    private MemberService memberService;

    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ServerResponse sendCode(String mobile) {
        //分布式session    redis + cookie
//        String sessionId = DistributedSession.getSessionId(request, response);

        if (StringUtils.isEmpty(mobile))
            return ServerResponse.error(ResponseEnum.MOBILE_IS_NULL);
        //验证手机号格式
        String pattern = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
        boolean res = Pattern.matches(pattern, mobile);
        if (!res)
            return ServerResponse.error(ResponseEnum.MOBILE_IS_ERROR);

        String code;
        try {
            //发送验证码后 返回 验证码
//            code = NoteUtil.sendCode(mobile);
            code = NoteUtil.sendCodeHttpClient(mobile);
            if (StringUtils.isEmpty(code))
                return ServerResponse.error(ResponseEnum.CODE_SEND_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.CODE_SEND_ERROR);
        }
        //用手机号代替sessionId 将 验证码 存入redis   即使换个页面(电脑) 也不用再次发送
        RedisUtil.setEx(KeyUtil.buildMobileCodeKey(mobile), code, SystemConst.MOBILE_CODE_EXPIRE);
        return ServerResponse.success();
    }

    @GetMapping(value = "/{mobile}/{code2}")
    public ServerResponse validateCode(@PathVariable String mobile, @PathVariable String code2, HttpServletRequest request) {
        return smsService.validateCode(mobile, code2, request, memberService);
    }
}

package com.fh.shop.api.member.controller;

import com.alibaba.fastjson.JSON;
import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.biz.MemberService;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.sms.biz.SMSService;
import com.fh.shop.api.sms.controller.SMSApi;
import com.fh.shop.api.util.*;
import com.fh.shop.api.util.note.NoteUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/member")
@CrossOrigin(value = "*")//域名
public class MemberController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Resource(name = "memberService")
    private MemberService memberService;
    @Resource(name = "smsService")
    private SMSService smsService;

    /**
     * 会员注册
     *
     * @param member
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ServerResponse register(Member member) {
        //注册验证
        ServerResponse response = memberService.register(member);
        if (response != null)
            return response;

        //添加到DB
        memberService.add(member);

        return ServerResponse.success();
    }

    /**
     * 登录
     *
     * @param member
     * @return
     */
    @PostMapping(value = "/login")
    public ServerResponse login(Member member) {
        //根据flag判断登录方式  有值 则 为免密登录
        String flag = request.getParameter("flag");
        Member one = null;
        if (StringUtils.isNotEmpty(flag)) {
            String mobile = request.getParameter("mobile");
            String code = request.getParameter("code");
            //非空验证
            if (StringUtils.isEmpty(mobile)) {
                return ServerResponse.error(ResponseEnum.MOBILE_IS_NULL);
            }
            if (StringUtils.isEmpty(code)) {
                return ServerResponse.error(ResponseEnum.CODE_IS_NULL);
            }
            one = memberService.findOneByMobile(mobile);
            //验证手机号 和 验证码
            ServerResponse serverResponse = smsService.validateCode(mobile, code, request, memberService);
            if (serverResponse.getCode() != 200) {
                return serverResponse;
            }
        } else {
            //非空验证
            if (StringUtils.isEmpty(member.getUsername())) {
                return ServerResponse.error(ResponseEnum.USERNAME_IS_NULL);
            }
            if (StringUtils.isEmpty(member.getPassword())) {
                return ServerResponse.error(ResponseEnum.PASSWORD_IS_NULL);
            }
            //验证账号
            one = memberService.findOneByUsername(member.getUsername());
            if (one == null) {
                return ServerResponse.error(ResponseEnum.USERNAME_IS_ERROR);
            }
            //验证密码
            if (!Md5Util.salt_md5_2(member.getPassword(), one.getSalt()).equals(one.getPassword())) {
                return ServerResponse.error(ResponseEnum.PASSWORD_IS_ERROR);
            }
        }

        //签名
        MemberVo vo = new MemberVo();
        vo.setId(one.getId());
        vo.setUsername(one.getUsername());
        vo.setRealName(one.getRealName());
        String voJson = JSON.toJSONString(vo);

        //设置过期时间
        RedisUtil.setEx(KeyUtil.buildMember(vo.getUsername(), vo.getUuid()), "1", SystemConst.EXPIRE);

        String sign = Md5Util.sign(voJson);

        String voBase64 = null;
        String signBase64 = null;
        try {
            voBase64 = Base64.getEncoder().encodeToString(voJson.getBytes("utf-8"));
            signBase64 = Base64.getEncoder().encodeToString(sign.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //测试解码
        byte[] decode = Base64.getDecoder().decode(voBase64);
        String s = StringUtils.toEncodedString(decode, null);
        System.out.println(s);
        Map map = new HashMap<String, String>();
        map.put("data", voBase64 + "." + signBase64);
        return ServerResponse.success(voBase64 + "." + signBase64);
    }

    /**
     * 注销
     * 退出登录
     *
     * @return
     */
    @DeleteMapping(value = "/logout")
    @Check
    public ServerResponse logout() {
        MemberVo member = (MemberVo) request.getAttribute("member");
        RedisUtil.del(KeyUtil.buildMember(member.getUsername(), member.getUuid()));
        return ServerResponse.success();
    }

    /**
     * 判断用户唯一
     *
     * @param username
     * @return
     */
    @GetMapping()
    public ServerResponse usernameSole(String username) {
        Member one = memberService.findOneByUsername(username);
        if (one != null)
            return ServerResponse.error(ResponseEnum.USERNAME_IS_EXISTS);
        return ServerResponse.success();
    }

    /**
     * 判断手机号唯一
     *
     * @param mobile
     * @return
     */
    @GetMapping(value = "/{mobile}")
    public ServerResponse mobileSole(@PathVariable String mobile) {
        Member one = memberService.findOneByMobile(mobile);
        if (one != null)
            return ServerResponse.error(ResponseEnum.MOBILE_IS_EXISTS);
        return ServerResponse.success();
    }

    @GetMapping(value = "/memberInfo")
    @Check
    public ServerResponse memberInfo() {
        MemberVo member = (MemberVo) request.getAttribute("member");
        return ServerResponse.success(member);
    }

//    /**
//     * 发送短信验证码
//     * @param mobile
//     * @return
//     */
    /*@RequestMapping(method = RequestMethod.GET)
    public ServerResponse sendCode(String mobile){
        //分布式session    redis + cookie
//        String sessionId = DistributedSession.getSessionId(request, response);

        if(StringUtils.isEmpty(mobile))
            return ServerResponse.error(ResponseEnum.MOBILE_IS_NULL);
        //验证手机号格式
        String pattern = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
        boolean res = Pattern.matches(pattern, mobile);
        if(!res)
            return ServerResponse.error(ResponseEnum.MOBILE_IS_ERROR);

        String code;
        try {
            //发送验证码后 返回 验证码
//            code = NoteUtil.sendCode(mobile);
            code = NoteUtil.sendCodeHttpClient(mobile);
            if(StringUtils.isEmpty(code))
                return ServerResponse.error(ResponseEnum.CODE_SEND_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.CODE_SEND_ERROR);
        }
        //用手机号代替sessionId 将 验证码 存入redis   即使换个页面(电脑) 也不用再次发送
        RedisUtil.set(KeyUtil.buildMobileCodeKey(mobile), code);
        return ServerResponse.success();
    }*/
}

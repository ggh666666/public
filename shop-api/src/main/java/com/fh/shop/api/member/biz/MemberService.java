package com.fh.shop.api.member.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.mapper.IMemberMapper;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.Md5Util;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service(value = "memberService")
public class MemberService {
    @Autowired
    private IMemberMapper memberMapper;

    public void add(Member member) {
        //加密
        String password = member.getPassword();
        String[] s = Md5Util.salt_md5_2(password);
        member.setSalt(s[0]);
        member.setPassword(s[1]);

        memberMapper.insert(member);
    }

    public ServerResponse register(Member member) {
        //非空验证
        if (StringUtils.isEmpty(member.getUsername()))
            return ServerResponse.error(ResponseEnum.USERNAME_IS_NULL);
        if (StringUtils.isEmpty(member.getPassword()))
            return ServerResponse.error(ResponseEnum.PASSWORD_IS_NULL);
        if (!member.getPassword().equals(member.getPassword2()))
            return ServerResponse.error(ResponseEnum.PASSWORD0_IS_UNLIKE);
        if (StringUtils.isEmpty(member.getRealName()))
            return ServerResponse.error(ResponseEnum.NAME_IS_NULL);

        if (StringUtils.isEmpty(member.getCode()))
            return ServerResponse.error(ResponseEnum.CODE_IS_NULL);

        //验证手机号格式
        String pattern = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
        boolean res = Pattern.matches(pattern, member.getMobile());
        if (!res)
            return ServerResponse.error(ResponseEnum.MOBILE_IS_ERROR);
        //分布式session    redis + cookie
//        String sessionId = DistributedSession.getSessionId(request, response);
        //根据手机号 从redis中获取 验证码
        String code = RedisUtil.get(KeyUtil.buildMobileCodeKey(member.getMobile()));
        if (StringUtils.isEmpty(code))
            return ServerResponse.error(ResponseEnum.CODE_IS_ERROR);
        if (!code.equals(member.getCode()))//前台输入的验证码 与 手机收到的做对比
            return ServerResponse.error(ResponseEnum.CODE_NOT_SEND_OR_LOSE);


        //验证手机号唯一
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", member.getMobile());
        Member one = memberMapper.selectOne(queryWrapper);
        if (one != null)
            return ServerResponse.error(ResponseEnum.MOBILE_IS_EXISTS);
        //验证账号唯一
        one = findOneByUsername(member.getUsername());
        if (one != null)
            return ServerResponse.error(ResponseEnum.USERNAME_IS_EXISTS);
        return null;
    }

    public Member findOneByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return memberMapper.selectOne(queryWrapper);
    }

    public Member findOneByMobile(String mobile) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        return memberMapper.selectOne(queryWrapper);
    }
}

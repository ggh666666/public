package com.fh.shop.api.pay.controller;

import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.pay.biz.PayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/pays")
public class PayApi {

    @Resource(name = "payService")
    private PayService payService;

    @GetMapping(value = "/getCode")
    @Check
    public ServerResponse createQRCode(MemberVo memberVo) {
        return payService.CreateQRCode(memberVo.getId());
    }

    @GetMapping(value = "/payStatus")
    @Check
    public ServerResponse payStatus(MemberVo memberVo) {
        return payService.payStatus(memberVo.getId());
    }
}

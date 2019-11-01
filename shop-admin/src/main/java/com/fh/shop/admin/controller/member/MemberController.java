package com.fh.shop.admin.controller.member;

import com.fh.shop.admin.biz.member.IMemberService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.member.MemberSearchParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @Resource(name = "memberService")
    private IMemberService memberService;

    @RequestMapping(value = "/toList")
    public String toList() {
        return "member/list";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public ServerResponse list(MemberSearchParam param) {
        DataTableResult dataTableResult = memberService.list(param);
        return ServerResponse.success(dataTableResult);
    }
}

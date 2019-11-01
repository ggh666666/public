package com.fh.shop.api.order.controller;

import com.fh.shop.api.common.ApiIdempotent;
import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
//import com.fh.shop.api.order.biz.OrderService;
import com.fh.shop.api.order.biz.OrderService;
import com.fh.shop.api.order.biz.OrderService2;
import com.fh.shop.api.order.param.OrderParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/orders")
public class OrderApi {
    @Autowired
    private HttpServletRequest request;

    @Resource(name = "orderService")
    private OrderService orderService;

    @PostMapping
    @Check
//    @ApiIdempotent
    public ServerResponse submitOrder(OrderParam param, MemberVo memberVo) {

        return orderService.addOrder(param, memberVo.getId());
    }
}

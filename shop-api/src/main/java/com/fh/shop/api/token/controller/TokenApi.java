package com.fh.shop.api.token.controller;

import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.token.biz.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/tokens")
public class TokenApi {
    @Resource(name = "tokenService")
    private TokenService tokenService;

    @GetMapping
    @Check
    public ServerResponse getToken() {
        return tokenService.getToken();
    }
}

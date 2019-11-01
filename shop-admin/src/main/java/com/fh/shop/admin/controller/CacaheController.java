package com.fh.shop.admin.controller;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/cache")
public class CacaheController {

    @RequestMapping(value = "/delCache")
    @ResponseBody
    public ServerResponse delCache(String key) {
        RedisUtil.del(key);
        return ServerResponse.success();
    }
}

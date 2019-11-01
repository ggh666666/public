package com.fh.shop.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class IndexController {
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }


    @RequestMapping(value = "/error")
    public String error() {
        return "error";
    }
}

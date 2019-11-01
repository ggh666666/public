package com.fh.shop.api.brand.controller;

import com.fh.shop.api.brand.biz.BrandService;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController//@Controller + ResponseBody
@RequestMapping(value = "/brand")
public class BrandController {
    @Resource(name = "brandService")
    private BrandService brandService;

    /**
     * 查询热销品牌
     *
     * @return
     */
    @RequestMapping(value = "/hotList")
    @CrossOrigin("http://localhost:8082")//解决ajax跨域 参数为*或指定域名
    @Check
    public ServerResponse hotList() {
        List<Brand> list = brandService.hotList();
        return ServerResponse.success(list);
    }
}

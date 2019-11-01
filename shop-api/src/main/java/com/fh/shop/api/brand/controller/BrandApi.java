package com.fh.shop.api.brand.controller;

import com.fh.shop.api.brand.biz.BrandService;
import com.fh.shop.api.brand.param.BrandSearchParam;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.DataTableResult;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/brands")
@CrossOrigin("*")
public class BrandApi {
    @Resource(name = "brandService")
    private BrandService brandService;


    //@RequestMapping(method = RequestMethod.GET)
    public ServerResponse findAll() {
        List list = brandService.findAll();
        return ServerResponse.success(list);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ServerResponse add(Brand brand) {
        brandService.add(brand);
        return ServerResponse.success();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ServerResponse delete(@PathVariable Integer id) {
        brandService.delete(id);
        return ServerResponse.success();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ServerResponse findOne(@PathVariable Integer id) {
        Brand brand = brandService.findOne(id);
        return ServerResponse.success(brand);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ServerResponse update(@RequestBody Brand brand) {
        brandService.update(brand);
        return ServerResponse.success();
    }

    @RequestMapping(method = RequestMethod.GET)
    @Check
//    public ServerResponse findPage(@RequestBody BrandSearchParam param){
    public ServerResponse findPage(BrandSearchParam param) {
        DataTableResult data = brandService.findPage(param);
        return ServerResponse.success(data);
    }
}

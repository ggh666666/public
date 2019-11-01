package com.fh.shop.api.area.controller;

import com.fh.shop.api.area.biz.AreaService;
import com.fh.shop.api.area.po.Area;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/area")
@CrossOrigin(value = "*")
public class AreaApi {

    @Resource(name = "areaService")
    private AreaService areaService;

    @RequestMapping(method = RequestMethod.GET)
    public ServerResponse findChildAreaList(Integer id) {
        List<Area> childAreaList = areaService.findChildAreaList(id);
        return ServerResponse.success(childAreaList);
    }
}

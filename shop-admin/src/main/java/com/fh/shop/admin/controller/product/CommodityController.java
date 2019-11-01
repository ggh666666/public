package com.fh.shop.admin.controller.product;

import com.fh.shop.admin.biz.product.ICommodityService;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.product.CommodityParam;
import com.fh.shop.admin.po.product.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/commodity")
public class CommodityController {
    @Autowired
    private ICommodityService commodityService;

    /**
     * 去列表展示页面
     *
     * @return
     */
    @RequestMapping(value = "/toList")
    public String toList() {
        return "product/commodityList";
    }

    /**
     * 添加
     *
     * @param commodity
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public ServerResponse add(Commodity commodity) {
        commodityService.insert(commodity);
        return ServerResponse.success(commodity.getId());
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ServerResponse delete(Integer id) {
        commodityService.deleteById(id);
        return ServerResponse.success();
    }

    /**
     * 回显
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findOne")
    @ResponseBody
    public ServerResponse findOne(Integer id) {
        Commodity commodity = commodityService.selectById(id);
        return ServerResponse.success(commodity);
    }

    /**
     * 修改
     *
     * @param commodity
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public ServerResponse update(Commodity commodity) {
        commodityService.updateById(commodity);
        return ServerResponse.success();
    }

    /**
     * 查询
     *
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ServerResponse list(CommodityParam commodityParam) {
        List<Commodity> commodities = commodityService.selectList(commodityParam);
        return ServerResponse.success(commodities);
    }
}

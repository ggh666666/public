package com.fh.shop.admin.controller.product;

import com.fh.shop.admin.biz.product.ICommodityService;
import com.fh.shop.admin.po.product.Commodity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-common.xml"})
public class TestCommodityContrroller {
    @Autowired
    private ICommodityService commodityService;

    /**
     * 添加
     *
     * @return
     */
    @Test
    public void add() {
        Commodity commodity = new Commodity();
        commodity.setName("ceshi add");
        commodity.setPrice(new BigDecimal(1.1));
        commodityService.insert(commodity);
    }

    /**
     * 删除
     *
     * @return
     */
    @Test
    public void delete() {
        commodityService.deleteById(1);
    }

    /**
     * 回显
     *
     * @return
     */
    @Test
    public void findOne() {
        Commodity commodity = commodityService.selectById(1);
    }

    /**
     * 修改
     *
     * @return
     */
    @Test
    public void update() {
        Commodity commodity = new Commodity();
        commodity.setId(12);
        commodity.setName("ceshi update");
        commodity.setPrice(new BigDecimal(222.22));
        commodityService.updateById(commodity);
    }

    /**
     * 查询
     *
     * @return
     */
    @Test
    public void list() {
        commodityService.selectList(null);
    }
}

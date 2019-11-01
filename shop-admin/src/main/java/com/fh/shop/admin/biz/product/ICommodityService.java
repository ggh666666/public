package com.fh.shop.admin.biz.product;

import com.fh.shop.admin.param.product.CommodityParam;
import com.fh.shop.admin.po.product.Commodity;

import java.util.List;

public interface ICommodityService {
    public void insert(Commodity commodity);

    public void deleteById(Integer id);

    public Commodity selectById(Integer id);

    public void updateById(Commodity commodity);

    public List<Commodity> selectList(CommodityParam commodityParam);
}

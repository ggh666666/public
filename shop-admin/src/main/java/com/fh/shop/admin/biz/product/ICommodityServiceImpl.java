package com.fh.shop.admin.biz.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.mapper.product.ICommodityMapper;
import com.fh.shop.admin.param.product.CommodityParam;
import com.fh.shop.admin.po.product.Commodity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commodityService")
public class ICommodityServiceImpl implements ICommodityService {
    @Autowired
    private ICommodityMapper commodityMapper;

    //添加
    public void insert(Commodity commodity) {
        commodityMapper.insert(commodity);
    }

    //通过id删除
    public void deleteById(Integer id) {
        commodityMapper.deleteById(id);
    }

    //通过id查询
    public Commodity selectById(Integer id) {
        return commodityMapper.selectById(id);
    }

    //修改
    public void updateById(Commodity commodity) {
        commodityMapper.updateById(commodity);
    }

    //查询
    public List<Commodity> selectList(CommodityParam commodityParam) {
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper();
        String name = commodityParam.getName();
        Double minPrice = commodityParam.getMinPrice();
        Double maxPrice = commodityParam.getMaxPrice();
        queryWrapper
                .like(!StringUtils.isEmpty(name), "name", name)
                .ge(minPrice != null, "price", minPrice)
                .le(maxPrice != null, "price", maxPrice)
        ;
        return commodityMapper.selectList(queryWrapper);
    }


}

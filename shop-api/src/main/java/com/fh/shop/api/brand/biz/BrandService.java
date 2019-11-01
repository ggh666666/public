package com.fh.shop.api.brand.biz;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.shop.api.brand.mapper.IBrandMapper;
import com.fh.shop.api.brand.param.BrandSearchParam;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.DataTableResult;
import com.fh.shop.api.util.RedisPool;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service(value = "brandService")
public class BrandService {
    @Autowired
    private IBrandMapper brandMapper;


    public List<Brand> hotList() {
        String hotListJson = RedisUtil.get("hotList");
        if (StringUtils.isNotEmpty(hotListJson)) {
            List<Brand> hotList = JSON.parseArray(hotListJson, Brand.class);
            return hotList;
        }


        QueryWrapper<Brand> brandQueryWrapper = new QueryWrapper<>();
        brandQueryWrapper.eq("sellState", 1)
                .orderByAsc("sort");
        List<Brand> brandListDb = brandMapper.selectList(brandQueryWrapper);

        //
        hotListJson = JSON.toJSONString(brandListDb);
        RedisUtil.set("hotList", hotListJson);
        Jedis jedis = RedisPool.getResource();
        jedis.expire("hotList", 60);
//        jedis.setex("hotList", 20, hotListJson);
        jedis.close();
        return brandListDb;
    }

    public List findAll() {
        return brandMapper.selectList(null);
    }

    public void add(Brand brand) {
        brandMapper.insert(brand);
    }

    public void delete(Integer id) {
        brandMapper.deleteById(id);
    }

    public Brand findOne(Integer id) {
        return brandMapper.selectById(id);
    }

    public void update(Brand brand) {
        brandMapper.updateById(brand);
    }

    public DataTableResult findPage(BrandSearchParam param) {
        QueryWrapper<Brand> brandQueryWrapper = new QueryWrapper<>();
        brandQueryWrapper.orderByDesc("sellState");
        String brandName = param.getBrandName();
        if (StringUtils.isNotEmpty(brandName))
            brandQueryWrapper.like("brandName", brandName);
        //
        Integer start = param.getStart();
        Integer length = param.getLength();
        Page<Brand> page = new Page<>((start / length) + 1, length);//页数从1开始 记得+1
        page.setSearchCount(true);
        IPage<Brand> page1 = brandMapper.selectPage(page, brandQueryWrapper);

        long total = page1.getTotal();
        DataTableResult dataTableResult = new DataTableResult(param.getDraw(), total, total, page1.getRecords());
        return dataTableResult;
    }
}

package com.fh.shop.admin.mapper.brand;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.common.Page;
import com.fh.shop.admin.po.brand.Brand;

import java.util.List;

public interface IBrandMapper extends BaseMapper<Brand> {

    void add(Brand brand);

    Long getRecordsTotal();

    List<Brand> list(Page page);

    void delete(Integer id);

    Brand findBrand(Integer id);

    void update(Brand brand);
}

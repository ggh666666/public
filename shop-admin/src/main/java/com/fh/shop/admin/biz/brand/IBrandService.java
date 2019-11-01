package com.fh.shop.admin.biz.brand;

import com.fh.shop.admin.common.Page;
import com.fh.shop.admin.po.brand.Brand;

import java.util.List;

public interface IBrandService {
    void add(Brand brand);

    Long getRecordsTotal();

    List<Brand> list(Page page);

    void delete(Integer id);

    Brand findBrand(Integer id);

    void update(Brand brand);

    void updateBrand(Brand brand);
}

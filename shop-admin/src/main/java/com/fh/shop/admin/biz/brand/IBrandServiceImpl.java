package com.fh.shop.admin.biz.brand;

import com.fh.shop.admin.common.Page;
import com.fh.shop.admin.mapper.brand.IBrandMapper;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.util.OssUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "brandService")
public class IBrandServiceImpl implements IBrandService {
    @Autowired
    private IBrandMapper brandMapper;

    @Override
    public void add(Brand brand) {
        brandMapper.add(brand);
    }

    @Override
    public Long getRecordsTotal() {
        return brandMapper.getRecordsTotal();
    }

    @Override
    public List<Brand> list(Page page) {
        return brandMapper.list(page);
    }

    @Override
    public void delete(Integer id) {
        Brand brand = brandMapper.selectById(id);
        String brandImg = brand.getBrandImg();
        if (StringUtils.isNotEmpty(brandImg))
            OssUtil.delete(brandImg);
        brandMapper.delete(id);
    }

    @Override
    public Brand findBrand(Integer id) {
        return brandMapper.findBrand(id);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.update(brand);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateById(brand);
    }

}

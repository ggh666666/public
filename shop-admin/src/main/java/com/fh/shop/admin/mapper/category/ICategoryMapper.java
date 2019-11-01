package com.fh.shop.admin.mapper.category;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.po.category.Category;

import java.util.List;

public interface ICategoryMapper extends BaseMapper<Category> {
    List<Category> findCategoryList();

    void add(Category category);

    List<Category> findChildCategoryList(Integer id);
}

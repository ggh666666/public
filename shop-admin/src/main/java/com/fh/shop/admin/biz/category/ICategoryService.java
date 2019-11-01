package com.fh.shop.admin.biz.category;

import com.fh.shop.admin.po.category.Category;
import com.fh.shop.admin.vo.category.CategoryVo;

import java.util.List;

public interface ICategoryService {
    List<CategoryVo> findCategoryList();

    void add(Category category);

    void updateCategory(Category category);

    void deleteCategory(Integer[] ids);

    List<Category> findChildCategoryList(Integer id);
}

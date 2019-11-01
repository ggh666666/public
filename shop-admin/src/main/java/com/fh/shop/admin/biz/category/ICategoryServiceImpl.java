package com.fh.shop.admin.biz.category;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.mapper.category.ICategoryMapper;
import com.fh.shop.admin.po.category.Category;
import com.fh.shop.admin.vo.category.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class ICategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryMapper categoryMapper;

    @Override
    public List<CategoryVo> findCategoryList() {
        List<Category> list = categoryMapper.findCategoryList();
        //po转vo
        List<CategoryVo> voList = buildCategoryVo(list);
        return voList;
    }

    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateById(category);
    }

    @Override
    public void deleteCategory(Integer[] ids) {
        categoryMapper.deleteById(ids);
    }

    @Override
    public List<Category> findChildCategoryList(Integer id) {
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("fatherId", id);
        List<Category> childCategoryList = categoryMapper.selectList(categoryQueryWrapper);
        return childCategoryList;
    }

    private List<CategoryVo> buildCategoryVo(List<Category> list) {
        List<CategoryVo> voList = new ArrayList<>();
        for (Category c : list) {
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(c.getId());
            categoryVo.setName(c.getCategoryName());
            categoryVo.setpId(c.getFatherId());
            voList.add(categoryVo);
        }
        return voList;
    }
}

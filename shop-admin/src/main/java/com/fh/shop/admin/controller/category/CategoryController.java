package com.fh.shop.admin.controller.category;

import com.fh.shop.admin.biz.category.ICategoryService;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.category.Category;
import com.fh.shop.admin.vo.category.CategoryVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Resource(name = "categoryService")
    private ICategoryService categoryService;

    @RequestMapping(value = "/findChildCategoryList")
    @ResponseBody
    public ServerResponse findChildCategoryList(Integer id) {
        List<Category> childCategoryList = categoryService.findChildCategoryList(id);
        return ServerResponse.success(childCategoryList);
    }

    @RequestMapping(value = "/toList")
    public String toList() {
        return "/category/categoryList";
    }

    /**
     * 查询
     *
     * @return
     */
    @RequestMapping(value = "/findCategoryList")
    @ResponseBody
    public ServerResponse findCategoryList() {
        List<CategoryVo> list = categoryService.findCategoryList();
        return ServerResponse.success(list);
    }

    /**
     * 添加
     *
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public ServerResponse add(Category category) {
        categoryService.add(category);
        return ServerResponse.success(category.getId());
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "/updateCategory")
    @ResponseBody
    public ServerResponse updateCategory(Category category) {
        categoryService.updateCategory(category);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/deleteCategory")
    @ResponseBody
    public ServerResponse deleteCategory(Integer[] ids) {
        categoryService.deleteCategory(ids);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }
}

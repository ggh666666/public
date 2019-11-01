package com.fh.shop.admin.controller.brand;

import com.fh.shop.admin.biz.brand.IBrandService;
import com.fh.shop.admin.common.Log;
import com.fh.shop.admin.common.Page;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.brand.Brand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/brand")
public class BrandController {
    @Resource(name = "brandService")
    private IBrandService brandService;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandController.class);

    /**
     * ajax添加
     *
     * @param brand
     * @return
     */
    @RequestMapping(value = "/addAjax")
    @ResponseBody
    @Log("添加品牌")
    public String addAjax(Brand brand) {
        brandService.add(brand);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return "1";
    }

    /**
     * 去列表展示页面
     *
     * @return
     */
    @RequestMapping(value = "/toList")
    public String toList() {
        return "brand/list";
    }

    /**
     * 查询list数据
     *
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map list(Page page) {
        Map<String, Object> map = new HashMap<>();
        Long recordsTotal = brandService.getRecordsTotal();
        List<Brand> list = brandService.list(page);
        map.put("draw", page.getDraw());
        map.put("recordsTotal", recordsTotal);
        map.put("recordsFiltered", recordsTotal);
        map.put("data", list);
        return map;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    @Log("删除品牌")
    public Map delete(Integer id) {
        Map map = new HashMap();
        try {
            brandService.delete(id);
            map.put("code", 200);
            map.put("msg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("msg", "删除失败 ");
        }
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return map;
    }

    /**
     * 回显
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "findBrand")
    @ResponseBody
    public Map findBrand(Integer id) {
        Map map = new HashMap();
        try {
            Brand brand = brandService.findBrand(id);
            map.put("code", 200);
            map.put("msg", "回显成功");
            map.put("data", brand);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("msg", "回显失败");
        }
        return map;
    }

    /**
     * ajax修改
     *
     * @param brand
     * @return
     */
    @RequestMapping(value = "updateAjax")
    @ResponseBody
    @Log("修改品牌")
    public Map updateAjax(Brand brand) {
        Map map = new HashMap();
        try {
            brandService.update(brand);
            map.put("code", 200);
            map.put("msg", "修改成功");
            map.put("data", brand);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("msg", "修改失败 ");
        }
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return map;
    }

    /**
     * 更新自定义排序
     *
     * @param brand
     * @return
     */
    @RequestMapping(value = "/updateBrandSort")
    @ResponseBody
    public ServerResponse updateBrandSort(Brand brand) {
        brandService.updateBrand(brand);
        return ServerResponse.success();
    }

    /**
     * 更新热销状态
     *
     * @param brand
     * @return
     */
    @RequestMapping(value = "/updateBrandHot")
    @ResponseBody
    public ServerResponse updateBrandHot(Brand brand) {
        brandService.updateBrand(brand);
        return ServerResponse.success();
    }

}

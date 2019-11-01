package com.fh.shop.admin.controller.area;

import com.fh.shop.admin.biz.area.IAreaService;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.area.Area;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/area")
public class AreaController {

    @Resource(name = "areaService")
    private IAreaService areaService;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);

    /**
     * 跳转页面
     *
     * @return
     */
    @RequestMapping("/toList")
    public String toList() {
        return "/area/areaList";
    }

    /**
     * 三级联动
     *
     * @param pid
     * @return
     */
    @RequestMapping("/findAllAreaByPid")
    @ResponseBody
    public List<Area> findAllAreaByPid(Integer pid) {
        List<Area> list = areaService.findAllAreaByPid(pid);
        return list;
    }

    @RequestMapping(value = "/findChildAreaList")
    @ResponseBody
    public ServerResponse findChildAreaList(Integer id) {
        List<Area> childAreaList = areaService.findChildAreaList(id);
        return ServerResponse.success(childAreaList);
    }

    /**
     * 查询树
     *
     * @return
     */
    @RequestMapping("/findLayTree")
    @ResponseBody
    public List<Object> findLayTree() {
        List<Area> layTreeList = areaService.findLayTree();
        // 存放子节点
        List<Object> mapList = new ArrayList<>();
        for (Area area : layTreeList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", area.getId());
            map.put("name", area.getAreaName());
            map.put("pId", area.getPid());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 新增节点
     *
     * @param area
     */
    @RequestMapping("/saveZtree")
    @ResponseBody
    public ServerResponse saveZtree(Area area) {
        areaService.saveZtree(area);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ServerResponse.success(area.getId());
    }

    /**
     * 删除
     *
     * @param ids
     */
    @RequestMapping("/deleteZtreeByPash")
    @ResponseBody
    public ServerResponse deleteZtreeByPash(Integer[] ids) {
        areaService.deleteZtreeByPash(ids);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ServerResponse.success();
    }

    /**
     * 修改
     *
     * @param area
     */
    @RequestMapping("/updateZtree")
    @ResponseBody
    public ServerResponse updateZtree(Area area) {
        areaService.updateZtree2(area);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ServerResponse.success();
    }

}

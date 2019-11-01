package com.fh.shop.admin.controller.wealth;

import com.fh.shop.admin.biz.wealth.IWealthService;
import com.fh.shop.admin.common.Log;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.controller.product.ProdcutController;
import com.fh.shop.admin.po.wealth.Wealth;
import com.fh.shop.admin.vo.wealth.WealthVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/wealth")
public class WealthController {

    @Resource(name = "wealthService")
    private IWealthService wealthService;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(WealthController.class);

    /**
     * 跳转到查询菜单页面
     *
     * @return
     */
    @RequestMapping("/toList")
    public String toList() {
        return "/wealth/wealthList";
    }

    /**
     * 查询菜单
     *
     * @return
     */
    @RequestMapping("/findZtreeList")
    @ResponseBody
    public ServerResponse findZtreeList() {
        List<WealthVo> listVo = wealthService.findZtreeList();
        return ServerResponse.success(listVo);
    }

    /**
     * 添加菜单节点
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @Log("添加菜单节点")
    public ServerResponse add(Wealth wealth) {
        wealthService.add(wealth);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ServerResponse.success(wealth.getId());
    }

    /**
     * 删除节点
     *
     * @return
     */
    @RequestMapping("/deleteZtree")
    @ResponseBody
    @Log("删除节点")
    public ServerResponse deleteZtree(@RequestParam("ids[]") Integer[] ids) {
        wealthService.deleteZtree(ids);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ServerResponse.success();
    }

    /**
     * 修改
     *
     * @param wealth
     * @return
     */
    @RequestMapping("/updateZtree")
    @ResponseBody
    @Log("修改节点")
    public ServerResponse updateZtree(Wealth wealth) {
        wealthService.updateZtree(wealth);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ServerResponse.success();
    }
}

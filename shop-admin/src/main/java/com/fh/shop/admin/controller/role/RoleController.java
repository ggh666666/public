package com.fh.shop.admin.controller.role;

import com.fh.shop.admin.biz.role.IRoleService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.Log;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.controller.product.ProdcutController;
import com.fh.shop.admin.po.role.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/role")
public class RoleController {
    @Resource(name = "roleService")
    private IRoleService roleService;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    //me
    @RequestMapping(value = "/list")
    public @ResponseBody
    List<Role> list() {
        List<Role> list = roleService.list();

        return list;
    }

    /**
     * 跳转查询角色页面
     *
     * @return
     */
    @RequestMapping("/toList")
    public String toList() {
        return "/role/roleList";
    }

    /**
     * 这是查询角色信息的方法
     *
     * @return
     */
    @RequestMapping("/findRoleByList")
    @ResponseBody
    public DataTableResult findRoleByList(Role role) {
        DataTableResult roleByList = roleService.findRoleByList(role);
        return roleByList;
    }

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    @RequestMapping("/addRole")
    @ResponseBody
    @Log("添加角色")
    public ServerResponse addRole(Role role) {

        roleService.addRole(role);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ServerResponse.success();

    }

    /**
     * 回显角色信息
     *
     * @return
     */
    @RequestMapping("/toUpdateRole")
    @ResponseBody
    public ServerResponse toUpdateRole(Integer id) {
        try {
            Role role = roleService.toUpdateRole(id);
            return ServerResponse.success(role);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }

    /**
     * 修改角色
     *
     * @return
     */
    @RequestMapping("/updateRole")
    @ResponseBody
    @Log("修改角色")
    public ServerResponse updateRole(Role role) {
        try {
            roleService.updateRole(role);
            //打印日志
            LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
            return ServerResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }

    /**
     * 删除单个角色的方法
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteRole")
    @ResponseBody
    @Log("删除角色")
    public ServerResponse deleteRole(Integer id) {
        try {
            roleService.deleteRole(id);
            //打印日志
            LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
            return ServerResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }

    /**
     * 查询所有
     *
     * @return
     */
    @RequestMapping("/findRoleCheckbox")
    @ResponseBody
    public List<Role> findRoleCheckbox() {
        List<Role> list = roleService.findRoleCheckbox();
        return list;
    }
}

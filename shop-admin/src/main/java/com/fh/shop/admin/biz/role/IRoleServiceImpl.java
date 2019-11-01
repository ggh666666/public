package com.fh.shop.admin.biz.role;

import com.alibaba.fastjson.JSON;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.mapper.role.IRoleMapper;
import com.fh.shop.admin.po.role.Role;
import com.fh.shop.admin.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "roleService")
public class IRoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleMapper roleMapper;

    //me
    @Override
    public List<Role> list() {
        String roleListJson = RedisUtil.get("roleList");
        if (StringUtils.isEmpty(roleListJson)) {//如果缓存中没有数据 则从数据库中查询 并 将查询出的数据存到redis中
            List<Role> roleListDb = roleMapper.list();
            roleListJson = JSON.toJSONString(roleListDb);
            RedisUtil.set("roleList", roleListJson);
            return roleListDb;
        } else {//如果缓存中有数据 则 将数据转换成对应的java对象 并 返回
            List<Role> list = JSON.parseArray(roleListJson, Role.class);
            return list;
        }
    }

    /**
     * 这是查询当前页数据的方法
     *
     * @param role
     * @return
     */
    @Override
    public DataTableResult findRoleByList(Role role) {
        Map map = new HashMap();
        /*查询总条数*/
        Long count = roleMapper.findRoleByCount();
        /*查询当前页的数据*/
        List<Role> roleList = roleMapper.findRoleByList(role);
        for (Role roleInfo : roleList) {
            List<String> wealthNameList = roleMapper.toRoleOnWealthByName(roleInfo.getId());
            String join = StringUtils.join(wealthNameList, ",");
            roleInfo.setWealthNames(join);
        }
        DataTableResult dataTableResult = new DataTableResult(role.getDraw(), count, count, roleList);
        return dataTableResult;
    }

    /**
     * 添加角色
     *
     * @param role
     */
    @Override
    public void addRole(Role role) {
        roleMapper.addRole(role);
        //添加角色的后添加角色对应的资源到角色资源表
        Integer[] ids = role.getWealthIds();
        addRoleWealth(role, ids);

        //手动刷新 缓存
        //增 删 改 操作后 更新redis缓存中的数据
        updateCache();
    }

    public void updateCache() {//更新角色缓存
        List<Role> roleListDb = roleMapper.list();
        String roleListJson = JSON.toJSONString(roleListDb);
        RedisUtil.set("roleList", roleListJson);
    }

    public void deleteCache() {//删除角色缓存
        RedisUtil.del("roleList");
    }

    private void addRoleWealth(Role role, Integer[] ids) {
        List<Map> list = new ArrayList<>();
        if (ids.length > 0) {
            for (Integer id : ids) {
                Map map = new HashMap();
                map.put("roleId", role.getId());
                map.put("wealthId", id);
                list.add(map);
            }
            roleMapper.addRoleOnWealth(list);
        }
    }

    /**
     * 修改角色
     *
     * @param role
     */
    @Override
    public void updateRole(Role role) {
        roleMapper.updateRole(role);
        //删除角色对应的 角色资源中间表的数据
        roleMapper.deleteRoleOnWealth(role.getId());
        //重新添加
        Integer[] ids = role.getWealthIds();
        addRoleWealth(role, ids);

        //手动刷新 缓存
        //增 删 改 操作后 更新redis缓存中的数据
        updateCache();
    }

    /**
     * 回显角色信息
     *
     * @param id
     * @return
     */
    @Override
    public Role toUpdateRole(Integer id) {
        Role role = roleMapper.toUpdateRole(id);
        Integer[] ids = roleMapper.toRoleOnWealth(id);
        role.setWealthIds(ids);
        return role;
    }

    /**
     * 删除角色的方法
     *
     * @param id
     */
    @Override
    public void deleteRole(Integer id) {
        roleMapper.deleteRole(id);
        //删除user role中间表的数据
        roleMapper.deleteUserRole(id);
        //删除role wealth中间表的数据
        roleMapper.deleteRoleWealth(id);

        //手动刷新 缓存
        //增 删 改 操作后 更新redis缓存中的数据
        //updateCache();

        //或者直接删除缓存 查询时会自动进行缓存的存入
        deleteCache();
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<Role> findRoleCheckbox() {
        String roleListJson = RedisUtil.get("roleList");
        if (StringUtils.isEmpty(roleListJson)) {//如果缓存中没有数据 则从数据库中查询 并 将查询出的数据存到redis中
            List<Role> roleListDb = roleMapper.findRoleCheckbox();
            roleListJson = JSON.toJSONString(roleListDb);
            RedisUtil.set("roleList", roleListJson);
            return roleListDb;
        } else {//如果缓存中有数据 则 将数据转换成对应的java对象 并 返回
            List<Role> list = JSON.parseArray(roleListJson, Role.class);
            return list;
        }
    }
}

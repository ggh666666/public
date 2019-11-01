package com.fh.shop.admin.mapper.role;

import com.fh.shop.admin.po.role.Role;

import java.util.List;
import java.util.Map;

public interface IRoleMapper {
    List<Role> list();//me

    Long findRoleByCount();

    List<Role> findRoleByList(Role role);

    void addRole(Role role);

    void updateRole(Role role);

    Role toUpdateRole(Integer id);

    void deleteRole(Integer id);

    List<Role> findRoleCheckbox();

    void addRoleOnWealth(List<Map> list);

    Integer[] toRoleOnWealth(Integer id);

    void deleteRoleOnWealth(Integer id);

    List<String> toRoleOnWealthByName(Integer id);

    void deleteUserRole(Integer id);

    void deleteRoleWealth(Integer id);
}

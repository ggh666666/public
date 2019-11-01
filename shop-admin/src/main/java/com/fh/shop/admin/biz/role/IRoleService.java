package com.fh.shop.admin.biz.role;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.po.role.Role;

import java.util.List;

public interface IRoleService {
    List<Role> list();//me

    DataTableResult findRoleByList(Role role);

    void addRole(Role role);

    void updateRole(Role role);

    Role toUpdateRole(Integer id);

    void deleteRole(Integer id);

    List<Role> findRoleCheckbox();
}

package com.fh.shop.admin.mapper.wealth;

import com.fh.shop.admin.po.wealth.Wealth;

import java.util.List;

public interface IWealthMapper {
    List<Wealth> findZtreeList();

    void add(Wealth wealth);

    void deleteZtree(Integer[] ids);

    void updateZtree(Wealth wealth);

    void deleteRoleWealth(Integer[] ids);
}

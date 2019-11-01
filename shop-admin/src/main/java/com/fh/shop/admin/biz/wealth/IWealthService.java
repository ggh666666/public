package com.fh.shop.admin.biz.wealth;

import com.fh.shop.admin.po.wealth.Wealth;
import com.fh.shop.admin.vo.wealth.WealthVo;

import java.util.List;

public interface IWealthService {
    List<WealthVo> findZtreeList();

    void add(Wealth wealth);

    void deleteZtree(Integer[] ids);


    void updateZtree(Wealth wealth);

    List<Wealth> findAllList();
}

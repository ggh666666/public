package com.fh.shop.admin.biz.wealth;

import com.fh.shop.admin.mapper.wealth.IWealthMapper;
import com.fh.shop.admin.po.wealth.Wealth;
import com.fh.shop.admin.vo.wealth.WealthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("wealthService")
public class IWealthServiceImpl implements IWealthService {

    @Autowired
    private IWealthMapper wealthMapper;

    /**
     * 查询菜单
     *
     * @return
     */
    @Override
    public List<WealthVo> findZtreeList() {
        List<Wealth> list = wealthMapper.findZtreeList();
        //po转换vo
        List<WealthVo> listVo = new ArrayList<>();
        for (Wealth wealth : list) {
            WealthVo wealthVo = new WealthVo();
            wealthVo.setId(wealth.getId());
            wealthVo.setName(wealth.getMemuName());
            wealthVo.setpId(wealth.getFatherId());
            wealthVo.setMenuType(wealth.getMenuType());
            wealthVo.setUrl(wealth.getUrl());
            listVo.add(wealthVo);
        }
        return listVo;
    }

    @Override
    public void add(Wealth wealth) {
        wealthMapper.add(wealth);
    }

    @Override
    public void deleteZtree(Integer[] ids) {
        wealthMapper.deleteZtree(ids);
        //删除role wealth中间表的数据
        wealthMapper.deleteRoleWealth(ids);
    }

    @Override
    public void updateZtree(Wealth wealth) {
        wealthMapper.updateZtree(wealth);
    }

    @Override
    public List<Wealth> findAllList() {
        return wealthMapper.findZtreeList();
    }
}

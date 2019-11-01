package com.fh.shop.api.area.biz;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.area.mapper.IAreaMapper;
import com.fh.shop.api.area.po.Area;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "areaService")
public class AreaService {
    @Autowired
    private IAreaMapper areaMapper;

    public List<Area> findChildAreaList(Integer id) {
        String areaListJson = RedisUtil.get(SystemConst.AREA_LIST_KEY);
        List<Area> list;
        if (StringUtils.isEmpty(areaListJson)) {
            list = areaMapper.selectList(null);
            RedisUtil.set(SystemConst.AREA_LIST_KEY, JSON.toJSONString(list));
        } else {
            list = JSON.parseArray(areaListJson, Area.class);
        }
        return childList(list, id);
    }

    private List<Area> childList(List<Area> list, Integer id) {
        List areas = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Area area = list.get(i);
            if (id.equals(area.getPid()))//Integer 缓存了 -128 至 127    范围外的需要用equals判断
                areas.add(area);
        }

        return areas;
    }
}

package com.fh.shop.admin.mapper.area;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.po.area.Area;

import java.util.List;

public interface IAreaMapper extends BaseMapper<Area> {
    List<Area> findAllAreaByPid(Integer pid);

    List<Area> findLayTree();

    void saveZtree(Area area);

    void deleteZtreeByPash(Integer[] ids);

    void updateZtree(Area area);
}

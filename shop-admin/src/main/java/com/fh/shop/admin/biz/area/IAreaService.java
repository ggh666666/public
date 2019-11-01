package com.fh.shop.admin.biz.area;

import com.fh.shop.admin.po.area.Area;

import java.util.List;

public interface IAreaService {
    List<Area> findAllAreaByPid(Integer pid);

    List<Area> findLayTree();

    void saveZtree(Area area);

    void deleteZtreeByPash(Integer[] ids);

    void updateZtree2(Area area);

    List<Area> findChildAreaList(Integer id);
}

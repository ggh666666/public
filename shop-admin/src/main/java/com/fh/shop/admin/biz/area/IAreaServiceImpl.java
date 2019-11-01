package com.fh.shop.admin.biz.area;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.mapper.area.IAreaMapper;
import com.fh.shop.admin.po.area.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "areaService")
public class IAreaServiceImpl implements IAreaService {
    @Autowired
    private IAreaMapper areaMapper;

    /**
     * 查询地区三级联动
     *
     * @param pid
     * @return
     */
    @Override
    public List<Area> findAllAreaByPid(Integer pid) {
        return areaMapper.findAllAreaByPid(pid);
    }

    /**
     * 查询
     *
     * @return
     */
    @Override
    public List<Area> findLayTree() {
        return areaMapper.findLayTree();
    }

    /**
     * 新增
     *
     * @param area
     */
    @Override
    public void saveZtree(Area area) {
        areaMapper.saveZtree(area);
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void deleteZtreeByPash(Integer[] ids) {
        areaMapper.deleteZtreeByPash(ids);
    }

    /**
     * 修改
     *
     * @param area
     */
    @Override
    public void updateZtree2(Area area) {

        areaMapper.updateZtree(area);
    }

    @Override
    public List<Area> findChildAreaList(Integer id) {
        QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
        areaQueryWrapper.eq("Pid", id);
        List<Area> childAreaList = areaMapper.selectList(areaQueryWrapper);
        return childAreaList;
    }
}

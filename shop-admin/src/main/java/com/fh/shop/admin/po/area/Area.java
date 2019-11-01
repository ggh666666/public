package com.fh.shop.admin.po.area;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

public class Area implements Serializable {
    private Integer id;
    @TableField(value = "Name")
    private String areaName;
    private Integer pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}

package com.fh.shop.admin.param.log;

import com.fh.shop.admin.common.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LogSearchParam extends Page implements Serializable {

    //用户名
    private String userName;
    //真实姓名
    private String realName;
    //操作信息
    private String content;
    //状态
    private Integer status;
    //时间范围查询
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date minTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date maxTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getMinTime() {
        return minTime;
    }

    public void setMinTime(Date minTime) {
        this.minTime = minTime;
    }

    public Date getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Date maxTime) {
        this.maxTime = maxTime;
    }
}

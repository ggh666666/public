package com.fh.shop.admin.param.user;

import com.fh.shop.admin.common.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserSearchParam extends Page implements Serializable {
    private String userName;

    private String realName;

    private Integer beginAge;

    private Integer endAge;

    private Integer minPay;

    private Integer maxPay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginEntryTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endEntryTime;

    private List<Integer> roleIds;

    private int roleIdsLength;

    private Integer area1;//地区的下拉框等级    四级联动

    private Integer area2;

    private Integer area3;

    private Integer area4;

    public void setRoleIdsLength(int roleIdsLength) {
        this.roleIdsLength = roleIdsLength;
    }

    public Integer getArea1() {
        return area1;
    }

    public void setArea1(Integer area1) {
        this.area1 = area1;
    }

    public Integer getArea2() {
        return area2;
    }

    public void setArea2(Integer area2) {
        this.area2 = area2;
    }

    public Integer getArea3() {
        return area3;
    }

    public void setArea3(Integer area3) {
        this.area3 = area3;
    }

    public Integer getArea4() {
        return area4;
    }

    public void setArea4(Integer area4) {
        this.area4 = area4;
    }

    public int getRoleIdsLength() {
        if (roleIds != null) {
            return roleIds.size();
        }
        return roleIdsLength;
    }

//    public void setRoleIdsLength(int roleIdsLength) {
//        this.roleIdsLength = roleIdsLength;
//    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

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

    public Integer getBeginAge() {
        return beginAge;
    }

    public void setBeginAge(Integer beginAge) {
        this.beginAge = beginAge;
    }

    public Integer getEndAge() {
        return endAge;
    }

    public void setEndAge(Integer endAge) {
        this.endAge = endAge;
    }

    public Integer getMinPay() {
        return minPay;
    }

    public void setMinPay(Integer minPay) {
        this.minPay = minPay;
    }

    public Integer getMaxPay() {
        return maxPay;
    }

    public void setMaxPay(Integer maxPay) {
        this.maxPay = maxPay;
    }

    public Date getBeginEntryTime() {
        return beginEntryTime;
    }

    public void setBeginEntryTime(Date beginEntryTime) {
        this.beginEntryTime = beginEntryTime;
    }

    public Date getEndEntryTime() {
        return endEntryTime;
    }

    public void setEndEntryTime(Date endEntryTime) {
        this.endEntryTime = endEntryTime;
    }
}

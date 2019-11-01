package com.fh.shop.admin.vo.user;

import java.io.Serializable;
import java.util.List;

public class UserVo implements Serializable {
    private Long id;

    private String userName;

    private String realName;

    private Integer sex;
    private String sex2;

    public void setSex2(String sex2) {
        this.sex2 = sex2;
    }

    //中文sex
    public String getSex2() {
        if (sex == null)
            return "";
        return sex == 1 ? "男" : "女";
    }

    private Integer age;

    private Integer pay;

    private String entryTime;

    private String phone;

    private String email;

    private String roleNames;

    private List<Integer> roleIds;

    private boolean isLock;

    //
    private Integer area1;//地区的下拉框等级    四级联动

    private Integer area2;

    private Integer area3;

    private Integer area4;

    private String areaInfo;//前台处理好的地区信息

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

    public String getAreaInfo() {
        return areaInfo;
    }

    public void setAreaInfo(String areaInfo) {
        this.areaInfo = areaInfo;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    //@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC+8", locale = "zh")
    /*public Date getEntryTime() {
        return entryTime;
    }*/
    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

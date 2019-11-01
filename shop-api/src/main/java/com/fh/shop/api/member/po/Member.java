package com.fh.shop.api.member.po;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Member implements Serializable {
    private Long id;

    private String username;

    private String password;
    private String salt;//盐
    @TableField(exist = false)
    private String password2;//确认密码

    private String realName;

    private String mobile;
    @TableField(exist = false)
    private String code;

    private int area1;
    private int area2;
    private int area3;
    private int area4;
    private String areaInfo;


}

package com.fh.shop.api.member.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class MemberVo implements Serializable {

    private Long id;

    private String username;

    private String realName;

    private String uuid = UUID.randomUUID().toString();


}

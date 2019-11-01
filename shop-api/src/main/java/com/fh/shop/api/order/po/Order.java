package com.fh.shop.api.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Order implements Serializable {
    @TableId(type = IdType.INPUT)
    private String id;

    private Long memberId;

    private Integer payWay;

    private BigDecimal price;

    private Integer count;

    private Date createTime;

    private Date payTime;//支付时间

    private Integer status;

    private String statusDesc;//状态描述

    private Date deliveryTime;//发货时间

    private Date successTime;//交易完成时间

    private Date reviewTime;//完成评价时间

    private String addresseeMobile;//收件人手机号

    private String addresseeName;//收件人姓名

}

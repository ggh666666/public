package com.fh.shop.api.paylog.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Paylog implements Serializable {
    @TableId(value = "out_trade_no", type = IdType.INPUT)
    private String outTradeNo;

    private Long userId;

    private String orderId;

    private String transactionId;//流水号

    private Date createTime;

    private Date payTime;

    private BigDecimal payMoney;

    private Integer payType;

    private Integer payStatus;
}

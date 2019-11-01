package com.fh.shop.api.order.po;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetail {
    private String orderId;

    private Long productId;

    private Long memberId;

    private String productName;

    private BigDecimal price;

    private Integer count;

    private BigDecimal quantity;//小计

    private String mainImagePath;

}

package com.fh.shop.api.cart.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartItemVo implements Serializable {
    private Long productId;//商品id

    private String productName;//商品名

    private String mainImagePath;//商品主图

    private Integer count;//数量

    private String price;//单价

    private String quantity;//小结

}

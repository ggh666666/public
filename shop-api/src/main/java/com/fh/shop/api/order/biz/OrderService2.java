package com.fh.shop.api.order.biz;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.api.cart.vo.CartItemVo;
import com.fh.shop.api.cart.vo.CartVo;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.order.mapper.IOrderDetailMapper;
import com.fh.shop.api.order.mapper.IOrderMapper;
import com.fh.shop.api.order.param.OrderParam;
import com.fh.shop.api.order.po.Order;
import com.fh.shop.api.order.po.OrderDetail;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.util.BigDecimalUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("orderService2")
public class OrderService2 {

    @Autowired
    private IOrderMapper orderMapper;

    @Autowired
    private IOrderDetailMapper orderDetailMapper;

    @Autowired
    private IProductMapper productMapper;

    public ServerResponse addOrder(OrderParam param, Long memberId) {
        //查询购物车
        String cartJson = RedisUtil.hget(SystemConst.CART_REDIS_KEY, KeyUtil.buildCartField(memberId));
        if (StringUtils.isEmpty(cartJson)) {
            return ServerResponse.error(ResponseEnum.CART_IS_NULL);
        }
        CartVo cartVo = JSON.parseObject(cartJson, CartVo.class);
        List<CartItemVo> cartItemVoList = cartVo.getCartItemVoList();
        String orderId = IdWorker.getTimeId();//时间 + 雪花算法   订单id
        //添加订单明细
        List<CartItemVo> stockShortageList = new ArrayList();
        for (int i = 0; i < cartItemVoList.size(); i++) {
            CartItemVo cartItemVo = cartItemVoList.get(i);
            Long productStock = Long.valueOf(RedisUtil.hget(SystemConst.PRODUCT_STOCK_REDIS_KEY, KeyUtil.buildProductField(cartItemVo.getProductId())));
            Product product = productMapper.selectById(cartItemVo.getProductId());
            //检查库存是否充足
            if (cartItemVo.getCount() > productStock) {
                stockShortageList.add(cartItemVo);
                continue;//终止一次循环
            }
            //减库存
            Long value = RedisUtil.hincrBy(SystemConst.PRODUCT_STOCK_REDIS_KEY, KeyUtil.buildProductField(cartItemVo.getProductId()), -cartItemVo.getCount().longValue());
            if (value < 0) {
                //库存不足再加回来
                RedisUtil.hincrBy(SystemConst.PRODUCT_STOCK_REDIS_KEY, KeyUtil.buildProductField(cartItemVo.getProductId()), cartItemVo.getCount().longValue());

            }
            //库存充足再添加明细
            if (value >= 0) {

                //productMapper.updateStock(product.getId(), cartItemVo.getCount());
                //添加明细
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderId);
                orderDetail.setProductId(cartItemVo.getProductId());
                orderDetail.setMemberId(memberId);
                orderDetail.setProductName(cartItemVo.getProductName());
                orderDetail.setPrice(new BigDecimal(cartItemVo.getPrice()));
                orderDetail.setCount(cartItemVo.getCount());
                orderDetail.setQuantity(new BigDecimal(cartItemVo.getQuantity()));
                orderDetail.setMainImagePath(cartItemVo.getMainImagePath());
                orderDetailMapper.insert(orderDetail);
            }
        }
        //删除缺货商品项
        cartItemVoList.removeAll(stockShortageList);
        //更新购物车
        Integer totalCount = 0;
        BigDecimal countPrice = new BigDecimal("0.00");
        for (int i = 0; i < cartItemVoList.size(); i++) {
            totalCount += cartItemVoList.get(i).getCount();
            countPrice = BigDecimalUtil.add(countPrice.toString(), cartItemVoList.get(i).getQuantity());
        }
        cartVo.setCount(totalCount);
        cartVo.setPrice(countPrice.toString());
        //添加订单
        if (totalCount == 0) {
            //删除购物车 根据key field 删除redis中 hash中的数据
//            RedisUtil.hdel(SystemConst.CART_REDIS_KEY, KeyUtil.buildCartField(memberId));
            return ServerResponse.error(ResponseEnum.STOCK_IS_SHORTAGE);
        }
        Order order = new Order();
        order.setId(orderId);
        order.setMemberId(memberId);
        order.setPayWay(param.getPayType());
        order.setPrice(countPrice);
        order.setCount(totalCount);
        order.setCreateTime(new Date());
        order.setPayTime(null);
        order.setStatus(1);
        order.setStatusDesc("待支付");
        order.setDeliveryTime(null);
        order.setSuccessTime(null);
        order.setReviewTime(null);
        order.setAddresseeMobile(null);
        order.setAddresseeName(null);
        orderMapper.insert(order);
        //删除购物车 根据key field 删除redis中 hash中的数据
//        RedisUtil.hdel(SystemConst.CART_REDIS_KEY, KeyUtil.buildCartField(memberId));
        return ServerResponse.success(stockShortageList);
    }
}

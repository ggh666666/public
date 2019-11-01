package com.fh.shop.api.cart.biz;

import com.alibaba.fastjson.JSON;
import com.fh.shop.api.cart.vo.CartItemVo;
import com.fh.shop.api.cart.vo.CartVo;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemConst;
import com.fh.shop.api.util.BigDecimalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service(value = "cartService")
public class ICartServiceImpl implements ICartService {

    @Autowired
    private IProductMapper productMapper;

    @Override
    public ServerResponse addProduct(Long productId, Integer count, Long memberId) {
        //判断商品是否存在
        Product product = productMapper.selectById(productId);
        if (product == null) {
            return ServerResponse.error(ResponseEnum.PRODUCT_NOT_EXISTS);
        }
        //判断商品状态是否正常
        if (product.getStatus() != SystemConst.PRODUCT_UP) {
            return ServerResponse.error(ResponseEnum.PRODUCT_IS_DOWN);
        }
        //判断购物车是否存在 不存在就创建购物车 并 创建购物项
        CartVo cartVo = null;
        CartItemVo cartItemVo = null;
        String cartJson = RedisUtil.hget(SystemConst.CART_REDIS_KEY, KeyUtil.buildCartField(memberId));
        if (StringUtils.isEmpty(cartJson)) {//购物车不存在
            cartVo = new CartVo();
            //创建购物项
            cartItemVo = new CartItemVo();
            cartItemVo.setProductId(productId);
            cartItemVo.setProductName(product.getProductName());
            cartItemVo.setMainImagePath(product.getMainImagePath());
            cartItemVo.setPrice(product.getPrice().toString());
            cartItemVo.setCount(count);
            cartItemVo.setQuantity(BigDecimalUtil.multiply(cartItemVo.getPrice(), count.toString()).toString());
            //将购物项加入购物车
            cartVo.getCartItemVoList().add(cartItemVo);
        }
        if (StringUtils.isNotEmpty(cartJson)) {//购物车存在
            cartVo = JSON.parseObject(cartJson, CartVo.class);

            List<CartItemVo> cartItemList = cartVo.getCartItemVoList();
            for (int i = 0; i < cartItemList.size(); i++) {
                if (cartItemList.get(i).getProductId().equals(productId)) {
                    cartItemVo = cartItemList.get(i);
                    break;
                }
            }
            //判断购物项是否存在 不存在就创建购物项
            if (cartItemVo != null) {
                //更新数量 和 小计
                cartItemVo.setCount(cartItemVo.getCount() + count);
                cartItemVo.setQuantity(BigDecimalUtil.multiply(cartItemVo.getPrice(), cartItemVo.getCount().toString()).toString());
            }
            if (cartItemVo == null) {
                //创建购物项
                cartItemVo = new CartItemVo();
                cartItemVo.setProductId(productId);
                cartItemVo.setProductName(product.getProductName());
                cartItemVo.setMainImagePath(product.getMainImagePath());
                cartItemVo.setPrice(product.getPrice().toString());
                cartItemVo.setCount(count);
                cartItemVo.setQuantity(BigDecimalUtil.multiply(cartItemVo.getPrice(), cartItemVo.getCount().toString()).toString());
                //将购物项加入购物车
                cartVo.getCartItemVoList().add(cartItemVo);
            }


        }

        //添加的总价
        String addPrice = BigDecimalUtil.multiply(cartItemVo.getPrice(), count.toString()).toString();
        //更新购物车
        cartVo.setCount(cartVo.getCount() + count);
        cartVo.setPrice(BigDecimalUtil.add(cartVo.getPrice(), addPrice).toString());

        cartJson = JSON.toJSONString(cartVo);
        RedisUtil.hset(SystemConst.CART_REDIS_KEY, KeyUtil.buildCartField(memberId), cartJson);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findCart(Long memberId) {
        String cartJson = RedisUtil.hget(SystemConst.CART_REDIS_KEY, KeyUtil.buildCartField(memberId));
        CartVo cartVo = JSON.parseObject(cartJson, CartVo.class);
        //如果购物车是空的 则 删除购物车
        if (cartVo != null && cartVo.getCount().equals(0)) {
            RedisUtil.hdel(SystemConst.CART_REDIS_KEY, KeyUtil.buildCartField(memberId));
            cartVo = null;
        }
        return ServerResponse.success(cartVo);
    }

    @Override
    public ServerResponse delProduct(Long productId, Long memberId) {
        //查询购物车
        String cartJson = RedisUtil.hget(SystemConst.CART_REDIS_KEY, KeyUtil.buildCartField(memberId));
        CartVo cartVo = JSON.parseObject(cartJson, CartVo.class);
        //删除购物项
        List<CartItemVo> cartItemList = cartVo.getCartItemVoList();
        CartItemVo cartItemVo = null;
        for (int i = 0; i < cartItemList.size(); i++) {
            if (productId.equals(cartItemList.get(i).getProductId())) {
                cartItemVo = cartItemList.get(i);
                cartItemList.remove(i);
                break;
            }
        }
        //更新购物车
        Integer count = cartItemVo.getCount();
        BigDecimal newPrice = BigDecimalUtil.add(cartVo.getPrice(), "-" + cartItemVo.getQuantity());//总价减去删除购物项的小计
        cartVo.setCount(cartVo.getCount() - count);//购物车商品总数减去删除购物项数量
        cartVo.setPrice(newPrice.toString());
        cartJson = JSON.toJSONString(cartVo);
        RedisUtil.hset(SystemConst.CART_REDIS_KEY, KeyUtil.buildCartField(memberId), cartJson);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse clearCart(Long memberId) {
        //根据key field 删除redis中 hash中的数据
        RedisUtil.hdel(SystemConst.CART_REDIS_KEY, KeyUtil.buildCartField(memberId));
        return ServerResponse.success();
    }
}

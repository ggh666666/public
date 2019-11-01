package com.fh.shop.api.cart.controller;

import com.fh.shop.api.cart.biz.ICartService;
import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/carts")
public class CartApi {

    @Autowired
    private HttpServletRequest request;

    @Resource(name = "cartService")
    private ICartService cartService;

    /**
     * 添加购物项
     *
     * @param productId
     * @param count
     * @return
     */
    @PostMapping
    @Check
    public ServerResponse addProduct(Long productId, Integer count, MemberVo memberVo) {
//        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.CURR_MEMBER);
        return cartService.addProduct(productId, count, memberVo.getId());
    }

    /**
     * 查询购物车
     *
     * @return
     */
    @GetMapping
    @Check
    public ServerResponse findCart(MemberVo memberVo) {
//        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.CURR_MEMBER);
        return cartService.findCart(memberVo.getId());
    }

    /**
     * 删除购物项
     *
     * @param productId
     * @return
     */
    @DeleteMapping(value = "{productId}")
    @Check
    public ServerResponse delProduct(@PathVariable Long productId, MemberVo memberVo) {
//        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.CURR_MEMBER);
        return cartService.delProduct(productId, memberVo.getId());
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @DeleteMapping
    @Check
    public ServerResponse clearCart(MemberVo memberVo) {
//        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.CURR_MEMBER);
        return cartService.clearCart(memberVo.getId());
    }
}

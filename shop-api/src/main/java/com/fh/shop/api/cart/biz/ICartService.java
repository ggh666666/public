package com.fh.shop.api.cart.biz;

import com.fh.shop.api.common.ServerResponse;

public interface ICartService {
    ServerResponse addProduct(Long productId, Integer count, Long memberId);

    ServerResponse findCart(Long memberId);

    ServerResponse delProduct(Long productId, Long memberId);

    ServerResponse clearCart(Long memberId);
}

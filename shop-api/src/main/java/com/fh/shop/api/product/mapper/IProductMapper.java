package com.fh.shop.api.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.product.param.ProductSearchParam;
import com.fh.shop.api.product.po.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProductMapper extends BaseMapper<Product> {
    Long findProductByCount(ProductSearchParam productSearchParam);

    List<Product> productList(ProductSearchParam productSearchParam);

    void add(Product product);

    void deleteProduct(Integer id);

    Product toUpdateProduct(Integer id);

    void updateProduct(Product product);

    List<Product> findProductList(ProductSearchParam productSearchParam);

    void updateStatus(Product product);

    Long updateStock(@Param(value = "id") Long id, @Param(value = "count") Integer count);

    Long updateStock2(@Param(value = "id") Long id, @Param(value = "count") Integer count);
}

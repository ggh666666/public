package com.fh.shop.admin.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.product.Product;

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
}

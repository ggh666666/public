package com.fh.shop.api.product.biz;

import com.fh.shop.api.common.DataTableResult;
import com.fh.shop.api.product.param.ProductSearchParam;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.product.vo.ProductVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ProductService {

    DataTableResult productList(ProductSearchParam productSearchParam);

    void add(Product product);

    Map uploadFile(MultipartFile myfile);

    void deleteProduct(Integer id, String mainImagePath);

    Product toUpdateProduct(Integer id);

    void updateProduct(Product product);

    List<Product> findProductList(ProductSearchParam productSearchParam);

    File buildWordFile(ProductSearchParam productSearchParam);

    void updateStatus(Product product);

    List<ProductVo> selectList();

    List<Product> findStockLess();
}

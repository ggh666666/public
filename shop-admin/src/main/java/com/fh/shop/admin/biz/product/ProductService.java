package com.fh.shop.admin.biz.product;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.product.Product;
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
}

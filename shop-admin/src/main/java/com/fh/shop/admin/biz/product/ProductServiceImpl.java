package com.fh.shop.admin.biz.product;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.mapper.product.IProductMapper;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.product.Product;
import com.fh.shop.admin.util.FileUtil;
import com.fh.shop.admin.util.FtpUtil;
import com.fh.shop.admin.util.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private IProductMapper productMapper;

    /**
     * 查询商品
     *
     * @param productSearchParam
     * @return
     */
    @Override
    public DataTableResult productList(ProductSearchParam productSearchParam) {
        //查询总条数
        Long count = productMapper.findProductByCount(productSearchParam);
        //查询本页数据
        List<Product> list = productMapper.productList(productSearchParam);
        DataTableResult dataTableResult = new DataTableResult(productSearchParam.getDraw(), count, count, list);
        return dataTableResult;
    }

    /**
     * 添加商品
     *
     * @param product
     */
    @Override
    public void add(Product product) {
        productMapper.add(product);
    }

    @Override
    public Map uploadFile(MultipartFile myfile) {
        Map map = FtpUtil.addFile(myfile);
        return map;
    }

    /**
     * 删除商品
     *
     * @param id
     */
    @Override
    public void deleteProduct(Integer id, String mainImagePath) {
        productMapper.deleteProduct(id);
        //同时删除ftp服务器的图片  根据图片的名称
        FtpUtil.deleteFtpFile(mainImagePath);
    }

    /**
     * 回显
     *
     * @param id
     * @return
     */
    @Override
    public Product toUpdateProduct(Integer id) {
//        Product product = productMapper.toUpdateProduct(id);
        Product product = productMapper.selectById(id);
        return product;
    }

    /**
     * 修改
     *
     * @param product
     */
    @Override
    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }

    @Override
    public List<Product> findProductList(ProductSearchParam productSearchParam) {
        return productMapper.findProductList(productSearchParam);
    }

    @Override
    public File buildWordFile(ProductSearchParam productSearchParam) {
        // 根据条件动态查询数据
        List<Product> productList = findProductList(productSearchParam);
        // 构建数据
        Map resultMap = buildWordData(productList);
        // 转换为指定的格式
        File file = FileUtil.buildWord(resultMap, SystemConst.PRODUCT_WORD_TEMPLATE_FILE);
        return file;
    }

    @Override
    public void updateStatus(Product product) {
        if (product.getStatus() == 1) {
            product.setStatus(2);
        } else {
            product.setStatus(1);
        }
        productMapper.updateStatus(product);
    }

    private Map buildWordData(List<Product> productList) {
        Map resultMap = new HashMap();
        resultMap.put("products", productList);
        return resultMap;
    }
}

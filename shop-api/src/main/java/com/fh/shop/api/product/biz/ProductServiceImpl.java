package com.fh.shop.api.product.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.DataTableResult;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.param.ProductSearchParam;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.product.vo.ProductVo;
import com.fh.shop.api.util.FileUtil;
import com.fh.shop.api.util.FtpUtil;
import com.fh.shop.api.util.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
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
        Product product = productMapper.toUpdateProduct(id);
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

    @Override
    public List<ProductVo> selectList() {
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.orderByDesc("id");
        productQueryWrapper.eq("status", 1);
        List<Product> products = productMapper.selectList(productQueryWrapper);
        List<ProductVo> list = new ArrayList<>();
        convertProductVoList(list, products);
        return list;
    }

    @Override
    public List<Product> findStockLess() {
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.lt("stock", 6);
        List<Product> products = productMapper.selectList(productQueryWrapper);
        return products;
    }

    private void convertProductVoList(List<ProductVo> list, List<Product> productList) {
        for (Product product1 : productList) {
            ProductVo ProductVo = convertProductVo(product1);
            list.add(ProductVo);
        }
    }

    private ProductVo convertProductVo(Product product) {
        if (product == null)
            return null;
        ProductVo productVo = new ProductVo();
        productVo.setId(product.getId());
        productVo.setProductName(product.getProductName());
        productVo.setMainImagePath(product.getMainImagePath());
        productVo.setPrice(product.getPrice().toString());
        productVo.setProducedDate(product.getProducedDate());
        productVo.setStock(product.getStock());
        return productVo;

    }

    private Map buildWordData(List<Product> productList) {
        Map resultMap = new HashMap();
        resultMap.put("products", productList);
        return resultMap;
    }
}

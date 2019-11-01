package com.fh.shop.api.listener;

import com.fh.shop.api.product.biz.ProductService;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.util.EmailUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

//@Component
public class Test {

    @Resource(name = "productService")
    private ProductService productService;

    //    @Scheduled(cron="*/10 * * * * ?")   //秒 分 时 日* 月 周? 年    0/1 从0开始每1执行一次
    public void aa() {
        List<Product> stockLess = productService.findStockLess();
        String content = "以下商品需要补充:<";
        for (int i = 0; i < stockLess.size(); i++) {
            Product product = stockLess.get(i);
            //
            content += "<br>商品名:\t" + product.getProductName()
                    + "<br>价格:\t" + product.getPrice()
                    + "<br>库存:\t" + product.getStock()
                    + ""
                    + ""

            ;
        }

        try {
            EmailUtil.sendEmail("532028476@qq.com", content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.fh.shop.admin.controller.product;

import com.fh.shop.admin.biz.product.ProductService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.Log;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.product.Product;
import com.fh.shop.admin.util.*;
import freemarker.template.TemplateException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProdcutController {
    @Resource(name = "productService")
    private ProductService productService;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdcutController.class);

    /**
     * 导出word
     *
     * @param productSearchParam
     * @param response
     */
    @RequestMapping(value = "/exportWord")
    public void exportWord(ProductSearchParam productSearchParam, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {
        File file = productService.buildWordFile(productSearchParam);
        // 调用下载方法
        FileUtil.downloadFile(request, response, file.getPath(), file.getName());
        //删除垃圾文件
        file.delete();
    }

    /**
     * 跳转查询商品页面
     *
     * @return
     */
    @RequestMapping("/toList")
    public String toList() {
        return "/product/productList";
    }

    /**
     * 查询商品
     *
     * @return
     */
    @RequestMapping("/productList")
    @ResponseBody
    public DataTableResult productList(ProductSearchParam productSearchParam) {
        DataTableResult dataTableResult = productService.productList(productSearchParam);
        return dataTableResult;
    }

    /**
     * 添加商品
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @Log("添加商品")
    public Map add(Product product) {
        Map map = new HashMap();
        productService.add(product);
        map.put("code", "200");
        map.put("msg", "ok");
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return map;
    }

    /**
     * 图片上传
     *
     * @return
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map uploadFile(MultipartFile myfile) {
        System.out.println(myfile);
        Map map = productService.uploadFile(myfile);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return map;
    }


    /**
     * 图片删除
     *
     * @param url
     * @return
     */
    @RequestMapping("/deleteFile")
    @ResponseBody
    public Map deleteFile(String url) {
        FtpUtil.deleteFtpFile(url);
        Map map = new HashMap();
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return map;
    }

    /**
     * 删除商品
     *
     * @param id
     * @param mainImagePath
     * @return
     */
    @RequestMapping("/deleteProduct")
    @ResponseBody
    @Log("删除商品")
    public Map deleteProduct(Integer id, String mainImagePath) {
        Map map = new HashMap();
        productService.deleteProduct(id, mainImagePath);
        map.put("code", "200");
        map.put("msg", "ok");

        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return map;
    }

    /**
     * 回显
     *
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateProduct")
    @ResponseBody
    public Map toUpdateProduct(Integer id) {
        Map map = new HashMap();
        Product product = productService.toUpdateProduct(id);
        map.put("code", "200");
        map.put("msg", "ok");
        map.put("data", product);
        return map;
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/updateProduct")
    @ResponseBody
    @Log("修改商品")
    public Map updateProduct(Product product) {
        Map map = new HashMap();
        productService.updateProduct(product);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return map;
    }

    /**
     * 上架 下架
     *
     * @param product
     * @return
     */
    @RequestMapping("/updateStatus")
    @ResponseBody
    @Log("上架 下架")
    public ServerResponse updateStatus(Product product) {
        productService.updateStatus(product);
        //打印日志
        LOGGER.info(ServerResponse.printLog(this.getClass(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 导出excel
     *
     * @param productSearchParam
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(ProductSearchParam productSearchParam, HttpServletResponse response) {
        //根据条件查询数据
        List<Product> list = productService.findProductList(productSearchParam);
        //转换为workbook
//        XSSFWorkbook workbook = buildWorkBook(list);
        String[] headers = new String[]{"商品名称", "价格"};
        String[] props = new String[]{"productName", "price"};
        XSSFWorkbook workbook = ExcelUtil.buildWorkbook(list, "商品列表", headers, props, Product.class);
        //下载
        FileUtil.excelDownload(workbook, response);
    }

    private XSSFWorkbook buildWorkBook(List<Product> list) {
        XSSFWorkbook xsf = new XSSFWorkbook();
        //创建一个sheet
        XSSFSheet sheet = xsf.createSheet();
        //创建标题
        buildTitle(sheet, xsf);
        // 创建第一行  构造表头
        buildHeadRow(sheet);
        //将查询到的数据输入到文档中
        buildBody(list, sheet);
        System.out.println();
        return xsf;
    }

    private void buildTitle(XSSFSheet sheet, XSSFWorkbook xsf) {
        XSSFCellStyle cellStyle = buildTitleStyle(xsf);
        XSSFRow row = sheet.createRow(3);
        XSSFCell cell = row.createCell(7);
        cell.setCellValue("商品列表");
        //第四行到第六行   第8个单元格到第10个单元格
        CellRangeAddress cellRangeAddress = new CellRangeAddress(3, 5, 7, 9);
        sheet.addMergedRegion(cellRangeAddress);
        cell.setCellStyle(cellStyle);
    }

    private XSSFCellStyle buildTitleStyle(XSSFWorkbook xssfw) {
        XSSFCellStyle style = xssfw.createCellStyle();

        XSSFFont font = xssfw.createFont();
        font.setBold(true);//是否加粗
        font.setColor(IndexedColors.WHITE.index);//设置字体颜色
        style.setFont(font);//将字体应用到样式
        style.setFillForegroundColor(IndexedColors.TEAL.index);//设置前景色
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);//设置填充才能正常显示前景色

        //水平居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        //垂直居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return style;
    }

    private void buildHeadRow(XSSFSheet sheet) {
        //表头
        String[] head = {"商品名称", "价格"};
        XSSFRow row = sheet.createRow(6);
        for (int i = 0; i < head.length; i++) {
            row.createCell(i + 7).setCellValue(head[i]);
        }
    }

    private void buildBody(List<Product> list, XSSFSheet sheet) {
        for (int i = 0; i < list.size(); i++) {
            //从第二行开始创建
            XSSFRow rowTo = sheet.createRow(i + 7);
            rowTo.createCell(7).setCellValue(list.get(i).getProductName());
            rowTo.createCell(8).setCellValue(list.get(i).getPrice().doubleValue());
        }
    }

    /**
     * 导出pdf
     *
     * @param productSearchParam
     */
    @RequestMapping("/exportPdf")
    private void exportPdf(ProductSearchParam productSearchParam, HttpServletResponse response) {
        //根据条件查询数据
        List<Product> list = productService.findProductList(productSearchParam);
        // 构建模板数据
        Map data = buildData(list);
        // 生成模板对应的html
        String htmlContent = FileUtil.buildPdfHtml(data, SystemConst.PRODUCT_PDF_TEMPLATE_FILE);
        // 转为pdf并进行下载
        FileUtil.pdfDownloadFile(response, htmlContent);
    }


    private Map buildData(List<Product> productList) {
        Map data = new HashMap();
        //单位
        data.put("companyName", SystemConst.COMPANY_NAME);
        //数据
        data.put("products", productList);
        //导出的时间
        data.put("createDate", DateUtil.date2str(new Date(), DateUtil.Y_M_D));
        return data;
    }
}

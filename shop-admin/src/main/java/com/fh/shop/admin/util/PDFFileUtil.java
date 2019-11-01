package com.fh.shop.admin.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PDFFileUtil {
    /**
     * 导出pdf文件
     * 创建人：王少鹏 1664046428@qq.com
     * 创建时间：2019年7月24日 下午8:04:27
     * 修改人：王少鹏 1664046428@qq.com
     * 修改时间：2019年7月24日 下午8:04:27
     * 修改备注：
     *
     * @param wirthExcelWB
     * @param response     </pre>
     */
    public static void pdfDownload(HttpServletResponse response, ByteArrayOutputStream byffer) {

        // inline在浏览器中直接显示，不提示用户下载
        // attachment弹出对话框，提示用户进行下载保存本地
        // 默认为inline方式
        response.setHeader("Content-Disposition", "attachment; filename=" + UUID.randomUUID().toString() + ".pdf");
        // 不同类型的文件对应不同的MIME类型
        response.setContentType("application/octet-stream;charset=UTF-8");
        ServletOutputStream out;
        try {
            //获取输出流
            out = response.getOutputStream();
            //调用方法下载
            byffer.writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 此方法作用:设置标题
     * 创建人：王少鹏 1664046428@qq.com
     * 创建时间：2019年7月24日 下午2:29:57
     * 修改人：王少鹏 1664046428@qq.com
     * 修改时间：2019年7月24日 下午2:29:57
     * 修改备注：
     *
     * @param value   值
     * @param font    字体
     * @param align   对齐方式
     * @param colspan 占多少列
     * @return </pre>
     */
    public static PdfPCell createHeadline(String value, Font font) {
        // 创建一个单元格
        PdfPCell cell = new PdfPCell();
        // new Paragraph()是段落的处理，可以设置段落的对齐方式，缩进和间距。
        cell.setPhrase(new Paragraph(value, font));
        //设置单元格的水平对齐方式
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置单元格的垂直对齐方式
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);//设置表格行高
        cell.setBorderWidth(0f);//去除表格的边框
        cell.setColspan(19);//跨列
        return cell;
    }

    /**
     * 此方法作用:创建单元格 设置内容
     * 创建人：王少鹏 1664046428@qq.com
     * 创建时间：2019年7月24日 下午7:30:22
     * 修改人：王少鹏 1664046428@qq.com
     * 修改时间：2019年7月24日 下午7:30:22
     * 修改备注：
     *
     * @param value
     * @param font
     * @param align
     * @return </pre>
     */
    public static PdfPCell createCell(String value, Font font, int align) {
        // 创建一个单元格
        PdfPCell cell = new PdfPCell();
        // 设置单元格的垂直对齐方式
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        // 设置单元格的水平对齐方式
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));

        //
        cell.setBorderWidth(0f);
        return cell;
    }

    static int maxWidth = 520;// 总宽度

    /**
     * 创建一个书写器
     * 创建人：王少鹏 1664046428@qq.com
     * 创建时间：2019年7月24日 下午6:34:13
     * 修改人：王少鹏 1664046428@qq.com
     * 修改时间：2019年7月24日 下午6:34:13
     * 修改备注：
     *
     * @param colNumber
     * @return </pre>
     */
    public static PdfPTable createTable(int colNumber) {
        // 创建表格
        PdfPTable table = new PdfPTable(colNumber);
        // 设置表格的总宽度
        table.setTotalWidth(maxWidth);
        //锁定宽度
        table.setLockedWidth(true);
        // 设置表格的垂直对齐方式
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置边框
        table.getDefaultCell().setBorder(1);
        return table;
    }

    /**
     * @param @param wirthExcelWB
     * @param @param response 入参
     * @return void 返回类型
     * @throws @date 2018年11月1日 上午10:38:32
     * @Title: excelDownload
     * @Description: 导出excel
     * @author Zhangtw
     * @e-mail 1058202069@qq.com
     * @version V1.0
     */
    public static void excelDownload(XSSFWorkbook wirthExcelWB, HttpServletResponse response) {
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            // 让浏览器识别是什么类型的文件
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型
            // // 重点突出
            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-Disposition", "attachment;filename=" + UUID.randomUUID().toString() + ".xlsx");
            wirthExcelWB.write(out);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

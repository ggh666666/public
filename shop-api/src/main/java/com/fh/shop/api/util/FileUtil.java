package com.fh.shop.api.util;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.UUID;

/**
 * <pre>项目名称：yao-movie-admin
 * 类名称：FileUtil
 * 类描述：    文件工具类
 * 创建人：姚展博 yao_zb@163.com
 * 创建时间：2018年3月30日 下午5:46:27
 * 修改人：姚展博 yao_zb@163.com
 * 修改时间：2018年3月30日 下午5:46:27
 * 修改备注：
 * @version </pre>
 */
public class FileUtil {
    /**
     * ggh
     * 构建word文件
     *
     * @param data
     * @param templateFile
     * @return
     */
    public static File buildWord(Map data, String templateFile) {
        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        File file = null;
        try {
            Configuration configuration = new Configuration();
            configuration.setClassForTemplateLoading(FileUtil.class, SystemConst.TEMPLATE_PATH);
            configuration.setDefaultEncoding("utf-8");
            Template template = configuration.getTemplate(templateFile);
            file = new File(SystemConst.WORD_SAVE_PATH + UUID.randomUUID().toString() + SystemConst.WORD_SUFFIX);
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out, "utf-8");
            template.process(data, osw);
            osw.flush();
        } catch (TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != osw) {
                try {
                    osw.close();
                    osw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * @param @param  size
     * @param @return 入参
     * @return String    返回类型
     * @throws
     * @Title: calculateFileSize
     * @Description: 计算文件大小
     * @author Zhangtw
     * @e-mail 1058202069@qq.com
     * @date 2018年12月25日 下午2:28:41
     * @version V1.0
     */
    public static String calculateFileSize(long size) {
        DecimalFormat df = new DecimalFormat("0.00");
        if (size < 1024) {
            return size + "B";
        } else if (size < 1024 * 1024) {
            return df.format((double) size / 1024) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            return df.format((double) size / (1024 * 1024)) + "MB";
        } else {
            return df.format((double) size / (1024 * 1024 * 1024)) + "GB";
        }
    }

    /**
     * @param @param  file
     * @param @param  fileName
     * @param @param  folderPath
     * @param @return 入参
     * @return String    返回类型
     * @throws
     * @Title: copyFile
     * @Description:上传文件
     * @author Zhangtw
     * @e-mail 1058202069@qq.com
     * @date 2018年12月25日 下午2:29:12
     * @version V1.0
     */
    public static String copyFile(File file, String fileName, String folderPath) {
        // 上传物理文件到服务器硬盘
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        String uploadFileName = null;
        try {
            // 构建读文件的流即输入流
            fis = new FileInputStream(file);
            // 构建输入缓冲区，提高读取文件的性能
            bis = new BufferedInputStream(fis);
            // 自动建立文件夹
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            // 为了保证上传文件的唯一性，可以通过uuid来解决
            // 为了避免中文乱码问题则新生成的文件名为uuid+原来文件名的后缀
            uploadFileName = UUID.randomUUID().toString() + getSuffix(fileName);
            // 构建写文件的流即输出流
            fos = new FileOutputStream(new File(folderPath + "/" + uploadFileName));
            // 构建输出缓冲区，提高写文件的性能
            bos = new BufferedOutputStream(fos);
            // 通过输入流读取数据并将数据通过输出流写到硬盘文件中
            byte[] buffer = new byte[4096];// 构建4k的缓冲区
            int s = 0;
            while ((s = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, s);
                bos.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                    bos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bis != null) {
                try {
                    bis.close();
                    bis = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fis != null) {
                try {
                    fis.close();
                    fis = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return uploadFileName;
    }

    /**
     * @param @param request
     * @param @param response
     * @param @param downloadFile
     * @param @param fileName    入参
     * @return void    返回类型
     * @throws
     * @Title: downloadFile
     * @Description:
     * @author Zhangtw
     * @e-mail 1058202069@qq.com
     * @date 2018年10月26日 下午4:13:41
     * @version V1.0
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String downloadFile, String fileName) {

        BufferedInputStream bis = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            File file = new File(downloadFile);
            is = new FileInputStream(file);  //文件流的声明
            os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
            // 为了提高效率使用缓冲区流
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(os);
            // 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                fileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");
            } else {
                // 对文件名进行编码处理中文问题
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
                fileName = new String(fileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
            }
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);// 重点突出
            int bytesRead = 0;
            byte[] buffer = new byte[4096];
            while ((bytesRead = bis.read(buffer)) != -1) { //重点
                bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
                bos.flush();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            // 特别重要
            // 1. 进行关闭是为了释放资源
            // 2. 进行关闭会自动执行flush方法清空缓冲区内容
            try {
                if (null != bis) {
                    bis.close();
                    bis = null;
                }
                if (null != bos) {
                    bos.close();
                    bos = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
                if (null != os) {
                    os.close();
                    os = null;
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    /**
     * @param @param  fileName
     * @param @return 入参
     * @return String    返回类型
     * @throws
     * @Title: getSuffix
     * @Description: 获取后缀
     * @author Zhangtw
     * @e-mail 1058202069@qq.com
     * @date 2018年12月25日 下午2:29:33
     * @version V1.0
     */
    private static String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index);
        return suffix;
    }

    /**
     * @param @param wirthExcelWB
     * @param @param response    入参
     * @return void    返回类型
     * @throws
     * @Title: excelDownload
     * @Description: 导出excel
     * @author Zhangtw
     * @e-mail 1058202069@qq.com
     * @date 2018年11月1日 上午10:38:32
     * @version V1.0
     */
    public static void excelDownload(XSSFWorkbook wirthExcelWB, HttpServletResponse response) {
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            //让浏览器识别是什么类型的文件
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
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

    public static String buildPdfHtml(Map data, String pdfTemplateFile) {
        // 将其转换为html
        StringWriter sw = null;
        try {
            Configuration configuration = new Configuration();
            // 解决freemarker的乱码问题
            configuration.setDefaultEncoding("utf-8");
            //指定模板文件所属文件夹
            configuration.setClassForTemplateLoading(FileUtil.class, SystemConst.TEMPLATE_PATH);
            //指定模板文件
            Template template = configuration.getTemplate(pdfTemplateFile);
            sw = new StringWriter();
            template.process(data, sw);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return sw.toString();
    }

    public static void pdfDownloadFile(HttpServletResponse response, String htmlContent) {
        OutputStream os = null;
        com.itextpdf.text.Document document = null;
        PdfWriter writer = null;
        try {
            os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
            // 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
            response.setHeader("Content-Disposition", "attachment;filename=" + UUID.randomUUID().toString() + ".pdf");// 重点突出
            // step 1
            document = new com.itextpdf.text.Document();
            // step 2
            writer = PdfWriter.getInstance(document, os);
            // step 3
            document.open();
            // step 4
            XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontImp.register("simhei.ttf");
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                    new ByteArrayInputStream(htmlContent.getBytes("utf-8")), null, Charset.forName("UTF-8"), fontImp);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            // 特别重要
            // 1. 进行关闭是为了释放资源
            // 2. 进行关闭会自动执行flush方法清空缓冲区内容
            if (null != document) {
                document.close();
                document = null;
            }
            if (null != writer) {
                writer.close();
                writer = null;
            }
            if (null != os) {
                try {
                    os.close();
                    os = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

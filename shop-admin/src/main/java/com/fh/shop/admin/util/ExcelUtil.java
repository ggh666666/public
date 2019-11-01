package com.fh.shop.admin.util;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.xssf.usermodel.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    public static XSSFWorkbook buildWorkbook(List dataList, String sheetName, String[] headers, String[] props, Class clazz) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFCellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));//死格式 年/月/日
        XSSFCellStyle doubleStyle = workbook.createCellStyle();
        doubleStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        Map styleMap = new HashMap();
        styleMap.put("dateStyle", dateStyle);
        styleMap.put("doubleStyle", doubleStyle);

        XSSFSheet sheet = workbook.createSheet(sheetName);
        buildHeadRow(sheet, headers);
        try {
            buildBody(sheet, dataList, props, clazz, styleMap);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    private static void buildHeadRow(XSSFSheet sheet, String[] headers) {
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    private static void buildBody(XSSFSheet sheet, List dataList, String[] props, Class clazz, Map styleMap) throws NoSuchFieldException, IllegalAccessException {
        for (int i = 0; i < dataList.size(); i++) {
            Object data = dataList.get(i);
            buildBodyRow(sheet, data, props, clazz, i + 1, styleMap);
        }
    }

    private static void buildBodyRow(XSSFSheet sheet, Object data, String[] props, Class clazz, int i, Map<String, XSSFCellStyle> styleMap) throws NoSuchFieldException, IllegalAccessException {
        XSSFRow row = sheet.createRow(i);
        for (int j = 0; j < props.length; j++) {
            Field field = clazz.getDeclaredField(props[j]);
            field.setAccessible(true);//可使用 不设置报错IllegalAccessException
            Object o = field.get(data);
            XSSFCell cell = row.createCell(j);
            if (o == null)
                break;
            Class<?> type = field.getType();
            if (type == Integer.class)
                cell.setCellValue((Integer) o);
            if (type == Long.class)
                cell.setCellValue((Long) o);
            if (type == Double.class)
                cell.setCellValue((Double) o);
            if (type == String.class)
                cell.setCellValue((String) o);
            if (type == Date.class) {
                cell.setCellValue((Date) o);
                cell.setCellStyle(styleMap.get("dateStyle"));
            }
            if (type == BigDecimal.class) {
                cell.setCellValue(((BigDecimal) o).doubleValue());
                cell.setCellStyle(styleMap.get("doubleStyle"));
            }

//            cell.setCellValue(o.toString());
        }
    }


}

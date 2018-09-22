package com.rms.common.excel;

import lombok.extern.log4j.Log4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

@Log4j
public class ExcelExporter {
    private Class<?> modelType;
    private ExcelType excelType;  // 文件类型
    private Workbook workbook; // 当前导入文件
    private String dateFormat = "yyyy-MM-dd";//日期格式
    private Sheet sheet;//创建标签页

    public ExcelExporter() {

    }


    public <T> void Export(String path, List<T> list) throws Exception {
        if (StringUtils.isEmpty(path)) {
            throw new ParamException("path");
        }
        File file = new File(path);
//        if (!file.exists()) {
//            throw new ParamException("Template file is not exist!");
//        }
        if (list == null || list.size() == 0) {
            return;
        }
        modelType = list.get(0).getClass();
        //data=list.get(0);
        OpenWorkbook(path);

        if (workbook == null) {
            return;
        }

//        if (m_workbook.getNumberOfSheets() <= 0) {
//            //logger.Log("无效的文件，请重新下载导入模板！");
//            return;
//        }
        sheet = workbook.createSheet();

        Hashtable<Integer, Excel> mappings = ModelParser.GetColumnMappingInfo(modelType);

        Row defaultRow = sheet.getRow(1);

        setHeader(mappings);
        int rowIndex = 1;


        for (T data : list) {
            Row row = sheet.createRow(rowIndex);
            row.setHeight((short) (25 * 20));

            for (int column : mappings.keySet()) {
                Excel excel = mappings.get(column);
                String property = excel.PropertyName();
                Field field = modelType.getDeclaredField(property);
                field.setAccessible(true);
                Object value = field.get(data);
                Cell cell = row.createCell(column);
                if (defaultRow != null) {
                    // 应用第一行数据的样式
                    Cell defaultCell = defaultRow.getCell(column);
                    if (defaultCell != null) {
                        cell.setCellStyle(defaultCell.getCellStyle());
                    } else
                        SetCellStyle(cell);
                } else { // 应用默认样式
                    SetCellStyle(cell);
                }

                if (value != null) {
                    if (field.getType() == Date.class) {
                        Date date = (Date) value;
                        cell.setCellValue(dateFormat(date));
                    } else {
                        cell.setCellValue(value.toString());
                    }
                }
            }
            rowIndex++;
        }
        String sheetName = file.getName().replace("." + excelType.name(), "");
        workbook.setSheetName(0, sheetName);
        try {
            FileOutputStream fileoutputStream = new FileOutputStream(file);
            workbook.write(fileoutputStream);
            workbook.cloneSheet(0);
            fileoutputStream.flush();
            fileoutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHeader(Hashtable<Integer, Excel> mappings) throws NoSuchFieldException {
        Row defaultRow = sheet.getRow(0);
        Row row;
        int rowIndex = 0;
        if (defaultRow == null) {
            row = sheet.createRow(rowIndex);
            row.setHeight((short) (25 * 20));
        }else{
            row = defaultRow;
        }

        for (int column : mappings.keySet()) {
            Excel excel = mappings.get(column);
            sheet.setColumnWidth(column, 36*excel.ColumnWidth());
            Object value = excel.ColumnTitle();
            Cell cell = row.createCell(column);
            if (defaultRow != null) {
                // 应用第一行数据的样式
                Cell defaultCell = defaultRow.getCell(column);
                if (defaultCell != null) {
                    cell.setCellStyle(defaultCell.getCellStyle());
                } else
                    SetCellStyle(cell);
            } else { // 应用默认样式

                SetCellStyle(cell);
            }
            if (value != null) {
                cell.setCellValue(value.toString());
            }
        }
    }

    private String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String dateString = sdf.format(date);
        return dateString;
    }

    private void OpenWorkbook(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new ParamException("文件名不能为空！");
        }
//        File file = new File(path);
//        if (!file.exists()) {
//            throw new ParamException("文件不存在！!");
//        }
        //获取文件格式类型
        String fileType = path.substring(path.lastIndexOf("."), path.length());
        try {
            //InputStream inputStream = new FileInputStream(file);
            switch (fileType) {
                case ".xls":
                    workbook = new HSSFWorkbook();
                    excelType = ExcelType.xls;
                    break;

                case ".xlsx":
                    workbook = new XSSFWorkbook();
                    excelType = ExcelType.xlsx;
                    break;
            }
        } catch (Exception ex) {
            log.info("读取文件格式类型报错：" + ex.getMessage());
        }
    }

    private CellStyle commonStyle;

    private void SetCellStyle(Cell cell) {
        if (commonStyle != null) {
            cell.setCellStyle(commonStyle);
        } else {
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Microsoft YaHei");
            style.setFont(font);
            // 设置单元格

            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setRightBorderColor(HSSFColor.BLACK.index);
            style.setBorderBottom(CellStyle.BORDER_THIN);
            style.setBottomBorderColor(HSSFColor.BLACK.index);
            // 设置对齐
            style.setAlignment(CellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            commonStyle = style;
            cell.setCellStyle(style);
        }
    }
}

package com.rms.common.excel;

import com.rms.common.util.RequestHolderUtil;
import lombok.extern.log4j.Log4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

@Log4j
public class ExcelService {
    private Class<?> modelType;
    private ExcelType excelType;  // 文件类型
    private Workbook workbook; // 当前导入文件
    private String dateFormat = "yyyy-MM-dd";//日期格式
    private Sheet sheet;//创建标签页
    private String fileName; //文件名称

    public ExcelService() {
        //构造函数
    }

    /**
     * 下载文件的时候配置表头信息
     * @throws Exception
     */
    private void WritRespHeader() throws Exception {
        HttpServletResponse response = RequestHolderUtil.getResponse();
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
    }

    /**
     * 导出文件到客户端
     * @param fileName
     * @param list 数据源
     * @param <T>
     * @throws Exception
     */
    public <T> void ExportToClient(String fileName, List<T> list) throws Exception {
        HttpServletResponse response = RequestHolderUtil.getResponse();
        OutputStream outputStream = response.getOutputStream();
        this.fileName = fileName;
        if (list == null || list.size() == 0) {
            return;
        }
        modelType = list.get(0).getClass();
        CreateWorkbook();
        if (workbook == null) {
            return;
        }
        WritRespHeader();
        WriteData(list);
        String sheetName = fileName.replace("." + excelType.name(), "");
        workbook.setSheetName(0, sheetName);
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出数据到输入流
     * @param outputStream
     * @param fileName
     * @param list
     * @param <T>
     * @throws Exception
     */
    public <T> void ExportToOutputStream(OutputStream outputStream, String fileName, List<T> list) throws Exception {
        if (list == null || list.size() == 0) {
            return;
        }
        this.fileName = fileName;
        modelType = list.get(0).getClass();
        CreateWorkbook();
        if (workbook == null) {
            return;
        }
        WriteData(list);
        String sheetName = fileName.replace("." + excelType.name(), "");
        workbook.setSheetName(0, sheetName);
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出文件到本地路径
     * @param path 本地路径
     * @param list 数据源
     * @param <T>
     * @throws Exception
     */
    public <T> void ExportToLocal(String path, List<T> list) throws Exception {
        if (StringUtils.isEmpty(path)) {
            throw new ParamException("path");
        }
        if(path.contains("classpath:")){
            path=path.replace("classpath:","src/main/resources/");
        }

        this.fileName = path;
        File file = new File(fileName);
        if (list == null || list.size() == 0) {
            return;
        }
        modelType = list.get(0).getClass();
        CreateWorkbook();
        if (workbook == null) {
            return;
        }
        WriteData(list);
        String sheetName = file.getName().replace("." + excelType.name(), "");
        workbook.setSheetName(0, sheetName);
        try {
            FileOutputStream fileoutputStream = new FileOutputStream(file);
            workbook.write(fileoutputStream);
            fileoutputStream.flush();
            fileoutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入数据
     * @param list
     * @param <T>
     * @throws Exception
     */
    private <T> void WriteData(List<T> list) throws Exception {
        sheet = workbook.createSheet();
        Hashtable<Integer, Excel> mappings = ExcelUtil.GetColumnHashtableInfo(modelType);
        Row defaultRow = sheet.getRow(1);
        WriteHeaderTitle(mappings);
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
    }

    /**
     * 写入Excel表头
     * @param mappings
     * @throws NoSuchFieldException
     */
    private void WriteHeaderTitle(Hashtable<Integer, Excel> mappings) throws NoSuchFieldException {
        Row defaultRow = sheet.getRow(0);
        Row row;
        int rowIndex = 0;
        if (defaultRow == null) {
            row = sheet.createRow(rowIndex);
            row.setHeight((short) (25 * 20));
        } else {
            row = defaultRow;
        }

        for (int column : mappings.keySet()) {
            Excel excel = mappings.get(column);
            sheet.setColumnWidth(column, 36 * excel.ColumnWidth());
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

    /**
     * 日期格式
     * @param date
     * @return
     */
    private String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String dateString = sdf.format(date);
        return dateString;
    }

    /**
     * 创建工作表
     */
    private void CreateWorkbook() {
        if (StringUtils.isEmpty(fileName)) {
            throw new ParamException("文件名不能为空！");
        }
        //获取文件类型
        String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        try {
            //InputStream inputStream = new FileInputStream(file);
            switch (fileType) {
                case ".xls":
                    workbook = new HSSFWorkbook();
                    excelType = ExcelType.EXCEL_TYPE_XLS;
                    break;

                case ".xlsx":
                    workbook = new XSSFWorkbook();
                    excelType = ExcelType.EXCEL_TYPE_XLSX;
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

    /**
     * 读取excel文件
     *
     * @param path  导入数据文件路径包括文件名
     * @param clazz 导入list 实体class
     * @param <T>   泛型标识
     * @return 返回 List数组
     * @throws Exception
     */
    public <T> List<T> ImportToList(String path, Class<T> clazz) throws Exception {
        modelType = clazz;
        if (StringUtils.isEmpty(path)) {
            throw new ParamException("path不能为空");
        }
        if(path.contains("classpath:")){
            path=path.replace("classpath:","src/main/resources/");
        }
        ReadWorkbook(path);
        if (workbook == null) {
            return null;
        }
        if (workbook.getNumberOfSheets() <= 0) {
            log.info("无效的文件，请重新下载导入模板！");
            return null;
        }
        sheet = workbook.getSheetAt(0);
        List<T> result = ReadData();
        return result;
    }

    /**
     * 读取Excel 数据到List
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> List<T> ReadData() throws Exception {
        if (workbook == null) {
            return null;
        }
        sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return null;
        }
        Hashtable<Integer, Excel> mappings = ExcelUtil.GetColumnHashtableInfo(modelType);
        List<T> result = new ArrayList<>();
        int rowLength = sheet.getLastRowNum() + 1;
        int _columnIndex = 0;
        for (int curRowIndex = 1; curRowIndex < rowLength; curRowIndex++) {
            T obj = (T) modelType.newInstance();
            if (mappings != null) {
                Row row = sheet.getRow(curRowIndex);
                try {
                    for (int columnIndex : mappings.keySet()) {
                        _columnIndex = columnIndex;
                        Cell cell = row.getCell(columnIndex);
                        Object cellValue = null;
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                cellValue = cell.getStringCellValue() + "";
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                cellValue = cell.getNumericCellValue();
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                cellValue = cell.getBooleanCellValue();
                        }
                        String propertyName = mappings.get(columnIndex).PropertyName();
                        // 给对象属性赋值
                        Field field = modelType.getDeclaredField(propertyName);
                        field.setAccessible(true);
                        field.set(obj, cellValue);
                    }
                } catch (Exception ex) {
                    String errMsg = "读取失败：第 " + curRowIndex + " 行;第 " + _columnIndex + " 列;" + ex.getMessage();
                    log.error(errMsg);
                    throw new Exception(errMsg);
                }
            }
            result.add(obj);
        }
        return result;
    }

    /**
     * 获取工作表
     * @param path
     */
    private void ReadWorkbook(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new ParamException("文件名不能为空！");
        }
        File file = new File(path);
        if (!file.exists()) {
            throw new ParamException("文件不存在！!");
        }
        //获取文件格式类型 EXCEL 有两种文件格式类型
        String fileType = path.substring(path.lastIndexOf("."), path.length());
        try {
            InputStream inputStream = new FileInputStream(file);
            switch (fileType) {
                case ".xls":
                    workbook = new HSSFWorkbook(inputStream);
                    excelType = ExcelType.EXCEL_TYPE_XLS;
                    break;
                case ".xlsx":
                    workbook = new XSSFWorkbook(inputStream);
                    excelType = ExcelType.EXCEL_TYPE_XLSX;
                    break;
            }
        } catch (Exception ex) {
            log.info("workbook的创建报错：" + ex.getMessage());
        }
    }
}

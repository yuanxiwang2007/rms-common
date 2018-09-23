package com.rms.common.excel;

import lombok.extern.log4j.Log4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Log4j
public class ExcelImporter {
    private Class<?> modelType;
    private ExcelType excelType;  // 文件类型
    private Workbook workbook; // 当前导入文件
    private String dateFormat = "yyyy-MM-dd";//日期格式
    private Sheet sheet;//创建标签页

    public ExcelImporter() {
        //构造函数
    }

    /**
     * 导出excel文件
     *
     * @param path  导入数据文件路径包括文件名
     * @param clazz 导入list 实体class
     * @param <T>   泛型标识
     * @return 返回 List数组
     * @throws Exception
     */
    public <T> List<T> Import(String path, Class<T> clazz) throws Exception {
        modelType = clazz;
        if (StringUtils.isEmpty(path)) {
            throw new ParamException("path can not be null when Import");
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

    private <T> List<T> ReadData() throws Exception {
        if (workbook == null) {
            return null;
        }
        sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return null;
        }
        Hashtable<Integer, Excel> mappings = ModelParser.GetColumnMappingInfo(modelType);
        List<T> result = new ArrayList<>();
        int rowLength = sheet.getLastRowNum() + 1;
        for (int curRowIndex = 1; curRowIndex < rowLength; curRowIndex++) {
            T obj = (T) modelType.newInstance();
            if (mappings != null) {
                Row row = sheet.getRow(curRowIndex);
                try {
                    for (int columnIndex : mappings.keySet()) {
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
                    log.error("读取失败："+ex.getMessage());
                }
            }
            result.add(obj);
        }
        return result;
    }

    private void ReadWorkbook(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new ParamException("文件名不能为空！");
        }
        File file = new File(path);
        if (!file.exists()) {
            throw new ParamException("文件不存在！!");
        }
        //获取文件格式类型
        String fileType = path.substring(path.lastIndexOf("."), path.length());
        try {
            InputStream inputStream = new FileInputStream(file);
            switch (fileType) {
                case ".xls":
                    workbook = new HSSFWorkbook(inputStream);
                    excelType = ExcelType.xls;
                    break;
                case ".xlsx":
                    workbook = new XSSFWorkbook(inputStream);
                    excelType = ExcelType.xlsx;
                    break;
            }
        } catch (Exception ex) {
            log.info("workbook的创建报错：" + ex.getMessage());
        }
    }
}

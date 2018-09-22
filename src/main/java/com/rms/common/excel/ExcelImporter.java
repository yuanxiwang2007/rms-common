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

    public ExcelImporter(){

    }
    public <T> List<T> Import(String path, Class<T> clazz) throws Exception {
        modelType=clazz;
        if (StringUtils.isEmpty(path))
        {
            throw new ParamException("path can not be null when Import");
        }

        ReadWorkbook(path);
        if (workbook == null)
        {
            return null;
        }

        if (workbook.getNumberOfSheets() <= 0)
        {
            log.info("无效的文件，请重新下载导入模板！");
            return null;
        }

        sheet = workbook.getSheetAt(0);
        List<T> result = GetData();
        return result;
    }
    private <T> List<T> GetData() throws Exception {
        if (workbook == null)
        {
            return null;
        }

        sheet = workbook.getSheetAt(0);
        if (sheet == null)
        {
            return null;
        }

        //Type modelType = typeof(T);
        // 获取对象与Excel列的对应关系，key为Excel列号，value为对象属性名
        Hashtable<Integer, Excel> mappings = ModelParser.GetColumnMappingInfo(modelType);

        List<T> result = new ArrayList<>();

        //int curRowIndex =  1;
        int rowLength = sheet.getLastRowNum()+1;
        for (int curRowIndex=1;curRowIndex<rowLength;curRowIndex++)
        {
            T obj = (T) modelType.newInstance();
            if (mappings != null)
            {
                Row row = (Row)sheet.getRow(curRowIndex);

                try
                {
                    for (int columnIndex : mappings.keySet())
                    {
                        Cell cell = row.getCell(columnIndex);
                        Object cellValue = cell.getStringCellValue();
                        String propertyName = mappings.get(columnIndex).PropertyName();
                        // 给对象属性赋值
                        Field field= modelType.getDeclaredField(propertyName);
                        field.setAccessible(true);
                        field.set(obj,cellValue);
                    }
                }
                catch (Exception ex)
                {
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
            log.info("读取文件格式类型报错：" + ex.getMessage());
        }
    }
}

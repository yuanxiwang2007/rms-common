package com.rms.common.excel;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

public class ExcelUtil {

    /**
     * 读取excel文件
     * @param path 导入数据文件路径包括文件名
     * @param clazz 导入list 实体class
     * @param <T> 泛型标识
     * @return 返回 List数组
     * @throws Exception
     */
    public static  <T> List<T> Import(String path, Class<T> clazz) throws Exception{
        ExcelImporter excelImporter=new ExcelImporter();
        return excelImporter.Import(path,clazz);
    }

    /**
     * 导入excel文件
     * @param path 导入数据文件路径包括文件名
     * @param list 导入list 数组
     * @param <T> 泛型标识
     * @throws Exception
     */
    public static <T> void Export(String path, List<T> list) throws Exception {
        ExcelExporter excelExporter=new ExcelExporter();
        excelExporter.Export(path,list);
    }
    /**
     * 导入excel文件到输出流
     * @param outputStream 输出流
     * @param path 导入数据文件路径包括文件名
     * @param list 导入list 数组
     * @param <T> 泛型标识
     * @throws Exception
     */
    public static <T> void Export(OutputStream outputStream, String path, List<T> list) throws Exception {
        ExcelExporter excelExporter=new ExcelExporter();
        excelExporter.Export(outputStream,path,list);
    }
    /**
     * 导入excel到web下载文件
     * @param path 导入数据文件路径包括文件名
     * @param list 导入list 数组
     * @param <T> 泛型标识
     * @throws Exception
     */
    public static <T> void ExportToClient(String path, List<T> list) throws Exception {
        ExcelExporter excelExporter=new ExcelExporter();
        excelExporter.ExportToClient(path,list);
    }

}

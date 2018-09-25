package com.rms.common.excel;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    /**
     * 读取excel文件
     *
     * @param path  导入数据文件路径包括文件名
     * @param clazz 导入list 实体class
     * @param <T>   泛型标识
     * @return 返回 List数组
     * @throws Exception
     */
    public static <T> List<T> ImportToList(String path, Class<T> clazz) throws Exception {
        ExcelService excelService = new ExcelService();
        return excelService.ImportToList(path, clazz);
    }

    /**
     * 导入excel文件
     *
     * @param path 导入数据文件路径包括文件名
     * @param list 导入list 数组
     * @param <T>  泛型标识
     * @throws Exception
     */
    public static <T> void ExportToLocal(String path, List<T> list) throws Exception {
        ExcelService excelService = new ExcelService();
        excelService.ExportToLocal(path, list);
    }

    /**
     * 导入excel文件到输出流
     *
     * @param outputStream 输出流
     * @param path         导入数据文件路径包括文件名
     * @param list         导入list 数组
     * @param <T>          泛型标识
     * @throws Exception
     */
    public static <T> void ExportToOutputStream(OutputStream outputStream, String path, List<T> list) throws Exception {
        ExcelService  excelService= new ExcelService();
        excelService.ExportToOutputStream(outputStream, path, list);
    }

    /**
     * 导入excel到web下载文件
     *
     * @param fileName 文件名
     * @param list     导入list 数组
     * @param <T>      泛型标识
     * @throws Exception
     */
    public static <T> void ExportToClient(String fileName, List<T> list) throws Exception {
        ExcelService  excelService= new ExcelService();
        excelService.ExportToClient(fileName, list);
    }

    public static <T> Hashtable<Integer, Excel> GetColumnHashtableInfo(Class<T> modelType) throws Exception {
        Hashtable<Integer, Excel> dic = null;
        Field[] properties = modelType.getDeclaredFields();
        if (properties != null && properties.length > 0) {
            for (Field property : properties) {
                Excel atts = property.getAnnotation(Excel.class);
                if (atts != null) {
                    Excel mappingInfo = atts;

                    if (dic == null) {
                        dic = new Hashtable<>();
                    }
                    if (dic.get(mappingInfo.ColumnNum()) == null) {
                        //mappingInfo.PropertyName = property.getName();
                        ChangeAnnotationValue(mappingInfo, "PropertyName", property.getName());
                        dic.put(mappingInfo.ColumnNum(), mappingInfo);
                    }
                }
            }
        }

        return dic;
    }

    public static Object ChangeAnnotationValue(Annotation annotation, String key, Object newValue) throws Exception {
        InvocationHandler handler = Proxy.getInvocationHandler(annotation);
        Field field;
        try {
            field = handler.getClass().getDeclaredField("memberValues");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        field.setAccessible(true);
        Map<String, Object> memberValues;
        memberValues = (Map<String, Object>) field.get(handler);
        Object oldValue = memberValues.get(key);
        if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
            throw new IllegalArgumentException();
        }
        memberValues.put(key, newValue);
        return oldValue;
    }
}

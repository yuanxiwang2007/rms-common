package com.rms.common;


import com.rms.common.excel.*;
import com.rms.common.util.SysUtil;
import org.junit.Test;
import java.util.List;
public class TestClass {

    @Test
    public static  void main (String [] args){
        try {
            List<ExcelEntity> entityList;
            entityList=ExcelUtil.ImportToList("src/main/resources/template.xls",ExcelEntity.class);
            SysUtil.sortDesc(entityList,ExcelEntity::getCode,ExcelEntity::getName);
            SysUtil.sortAsc(entityList,ExcelEntity::getCode,ExcelEntity::getName);
            ExcelUtil.ExportToLocal("d:\\excel.xls",entityList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

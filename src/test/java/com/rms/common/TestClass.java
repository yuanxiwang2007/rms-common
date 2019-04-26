package com.rms.common;

import com.rms.common.excel.*;
import com.rms.common.util.FileTypeJudge;
import com.rms.common.util.SysUtil;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.List;
public class TestClass {

    public static  void main (String [] args){

        try {
            System.out.println(FileTypeJudge.getType("D:\\SiInterface.dll"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

package com.rms.common.controller;


import com.rms.common.excel.ExcelEntity;
import com.rms.common.excel.ExcelExporter;
import com.rms.common.excel.ExcelImporter;
import com.rms.common.excel.TypeReference;
import java.util.ArrayList;
import java.util.List;

public class TestClass {

    public static  void main (String [] args){
        try {

            //ExcelExporter<ExcelEntity>
            List<ExcelEntity> entityList=new ArrayList<>();

            ExcelImporter excelImporter=new ExcelImporter();
            entityList=excelImporter.Import("D:\\dd.xls",ExcelEntity.class);


            for(int i=0 ;i<10;i++){
                ExcelEntity  excelEntity=new ExcelEntity();
                excelEntity.setCode("001056"+i);
                excelEntity.setName("也希望"+i);
                excelEntity.setPhone("1330817023"+i);
                entityList.add(excelEntity);
            }
            Object obj=new TypeReference<ExcelEntity>(){};

            ExcelExporter excelExporter=new ExcelExporter();
            excelExporter.Export("D:\\dd.xls",entityList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.rms.common;


import com.rms.common.excel.*;

import java.util.ArrayList;
import java.util.List;
public class TestClass {

    public static  void main (String [] args){
        try {
            List<ExcelEntity> entityList=new ArrayList<>();
            entityList=ExcelUtil.Import("src/main/resources/template.xls",ExcelEntity.class);
            ExcelUtil.Export("d:\\excel.xls",entityList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

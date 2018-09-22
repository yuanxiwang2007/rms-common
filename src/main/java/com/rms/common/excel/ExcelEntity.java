package com.rms.common.excel;

import lombok.Data;

@Data
public class ExcelEntity {

    @Excel(ColumnNum = 0,ColumnTitle = "编号",ColumnWidth = 100,PropertyName = "code")
    private String code;

    @Excel(ColumnNum = 1,ColumnTitle = "名称",ColumnWidth = 100,PropertyName = "name")
    private String name;

    @Excel(ColumnNum = 2,ColumnTitle = "电话",ColumnWidth = 100,PropertyName = "phone")
    private String phone;
}

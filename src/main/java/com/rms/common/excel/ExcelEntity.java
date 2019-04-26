package com.rms.common.excel;

import java.io.Serializable;

public class ExcelEntity implements Serializable {

    @Excel(ColumnNum = 0, ColumnTitle = "编号", ColumnWidth = 100, PropertyName = "code")
    private String code;

    @Excel(ColumnNum = 1, ColumnTitle = "名称", ColumnWidth = 100, PropertyName = "name")
    private String name;

    @Excel(ColumnNum = 2, ColumnTitle = "电话", ColumnWidth = 100, PropertyName = "phone")
    private String phone;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

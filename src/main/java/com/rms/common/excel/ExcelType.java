package com.rms.common.excel;

public enum  ExcelType {
    EXCEL_TYPE_XLS("xls","2007以前版本格式"),
    EXCEL_TYPE_XLSX("xlsx","2007以后版本格式");
    private String code;
    private String message;

    ExcelType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

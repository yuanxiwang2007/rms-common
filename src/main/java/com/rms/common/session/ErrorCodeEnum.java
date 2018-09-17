package com.rms.common.session;

import com.rms.common.exception.ErrorCode;

public enum ErrorCodeEnum implements ErrorCode {
    SUCCESS(0, "success"),
    UNLOGIN(302, "未登录"),
    SYSTEM_ERROR(1000000, "系统繁忙，请稍后再试"),
    PARAM_ERROR(1000001, "请求参数错误");

    private int code;
    private String message;

    private ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
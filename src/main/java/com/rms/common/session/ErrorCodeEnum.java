package com.rms.common.session;

import com.rms.common.exception.ErrorCode;

public enum ErrorCodeEnum implements ErrorCode {
    SUCCESS(0, "success","ddd"),
    UNLOGIN(302, "未登录","ddd"),
    SYSTEM_ERROR(1000000, "系统繁忙，请稍后再试","ddd"),
    PARAM_ERROR(1000001, "请求参数错误","ddd");

    private int code;
    private String message;
    private String messageEn;

    private ErrorCodeEnum(int code, String message,String messageEn) {
        this.code = code;
        this.message = message;
        this.messageEn=messageEn;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getMessageEn() {
        return this.messageEn;
    }
}
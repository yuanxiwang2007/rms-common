package com.rms.common.exception;

public class BusinessException extends Exception {
    private static final long serialVersionUID = 165367809284687797L;
    private int code;
    private String message;

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(int code, String message, Object... args) {
        this.code = code;
        this.message = String.format(message, args);
    }

    public BusinessException(ErrorCode errorCode, Object... args) {
        if (errorCode != null) {
            this.code = errorCode.getCode();
            this.message = String.format(errorCode.getMessage(), args);
        }

    }

    public BusinessException(ErrorCode errorCode) {
        if (errorCode != null) {
            this.code = errorCode.getCode();
            this.message = errorCode.getMessage();
        }

    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

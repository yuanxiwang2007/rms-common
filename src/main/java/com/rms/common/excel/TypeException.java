package com.rms.common.excel;

public class TypeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TypeException() {
    }

    public TypeException(String message) {
        super(message);
    }

    public TypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeException(Throwable cause) {
        super(cause);
    }
}

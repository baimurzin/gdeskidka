package com.baimurzin.gs.exception;

import java.io.Serializable;

public class AppException extends RuntimeException implements Serializable {

    private String errorMessage;

    public AppException() {
    }

    public AppException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

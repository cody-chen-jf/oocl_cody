package com.oocl.crm.exception;

/**
 * Created by CHENCO7 on 7/7/2017.
 */
public class BaseException extends RuntimeException {
    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }
}

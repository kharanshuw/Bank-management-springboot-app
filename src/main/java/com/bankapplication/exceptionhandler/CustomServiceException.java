package com.bankapplication.exceptionhandler;

public class CustomServiceException extends RuntimeException {
    public CustomServiceException(String message) {
        super(message);
    }
}

package com.bankapplication.exceptionhandler;

public class UserRetrievalException extends RuntimeException {
    public UserRetrievalException(String message) {
        super(message);
    }

    public UserRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

}

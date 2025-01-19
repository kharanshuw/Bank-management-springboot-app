package com.bankapplication.exceptionhandler;

public class AccountRetrievalException extends RuntimeException {

    public AccountRetrievalException(String message) {
        super(message);
    }
}

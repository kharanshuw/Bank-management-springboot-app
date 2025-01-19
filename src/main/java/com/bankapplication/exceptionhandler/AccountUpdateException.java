package com.bankapplication.exceptionhandler;

public class AccountUpdateException extends RuntimeException {
    public AccountUpdateException(String message) {
        super(message);
    }
}

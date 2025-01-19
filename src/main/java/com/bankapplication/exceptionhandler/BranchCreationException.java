package com.bankapplication.exceptionhandler;

public class BranchCreationException extends RuntimeException {
    public BranchCreationException(String message) {
        super(message);
    }

    public BranchCreationException(String message, Throwable cause) {
        super(message, cause);
    }

}

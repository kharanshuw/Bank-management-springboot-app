package com.bankapplication.exceptionhandler;

public class BranchNotFountException extends RuntimeException {


    public BranchNotFountException() {
    }

    public BranchNotFountException(String message) {
        super(message);
    }
}

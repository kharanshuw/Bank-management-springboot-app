package com.bankapplication.exceptionhandler;

public class BranchNotFoundException extends  RuntimeException {

    public BranchNotFoundException(String message) {
        super(message);
    }
    
}

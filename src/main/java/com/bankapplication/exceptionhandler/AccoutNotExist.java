package com.bankapplication.exceptionhandler;

public class AccoutNotExist extends RuntimeException {

    public AccoutNotExist(String message) {
        super(message);
    }
}

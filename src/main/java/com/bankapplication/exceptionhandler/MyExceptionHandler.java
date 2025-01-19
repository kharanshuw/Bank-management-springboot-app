package com.bankapplication.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MyExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // Return 400 Bad Request
    public String runtimeExceptionHandler(Model model, RuntimeException runtimeException) {
        model.addAttribute("e", runtimeException.getMessage());
        model.addAttribute("r", runtimeException.getMessage());
        logger.error("runtime exception occured" + runtimeException.getMessage());
        return "error/error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // Return 500 Internal Server Error
    public String exceptionHandler(Model model, Exception e) {
        model.addAttribute("e", e.getMessage());

        model.addAttribute("r", "Bad Request");
        logger.error("An unexpected error occurred" + e);

        return "error/error";
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public String handleDuplicateEntryException(DuplicateEntryException duplicateEntryException, Model model) {
        model.addAttribute("e", duplicateEntryException.getMessage());
        model.addAttribute("r", "USE UNIQUE MOBILE NO");
        logger.error("duplication mobile no used exception occured");
        return "error/error";
    }
}

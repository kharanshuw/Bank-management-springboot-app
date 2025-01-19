package com.bankapplication.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Get the error status code
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMessage = "Something went wrong";

        if (statusCode != null) {
            switch (statusCode) {
                case 400:
                    errorMessage = "Bad Request";
                    break;
                case 401:
                    errorMessage = "Unauthorized";
                    break;
                case 403:
                    errorMessage = "Forbidden";
                    break;
                case 404:
                    errorMessage = "Page Not Found";
                    break;
                case 500:
                    errorMessage = "Internal Server Error";
                    break;
                default:
                    errorMessage = "Error";
            }
        }

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage);
        return "error/error2";
    }

}

package com.bankapplication.controller;

import com.bankapplication.dto.RequestDto;
import com.bankapplication.exceptionhandler.DuplicateEntryException;
import com.bankapplication.model.Country;
import com.bankapplication.service.CountryService;
import com.bankapplication.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * This class serves as a controller for handling registration related operations.
 * It manages HTTP requests related to user registration,success and more.
 * <p>
 * Annotations:
 * - @Controller: Indicates that this class is a Spring MVC controller.
 */
@Controller
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private CountryService countryService;

    private UserServiceImpl userServiceImpl;

    //Constructor for dependency injection
    @Autowired
    public RegistrationController(UserServiceImpl userServiceImpl, CountryService countryService) {
        super();
        this.userServiceImpl = userServiceImpl;
        this.countryService = countryService;
    }

    /**
     * Handles GET requests to the /register endpoint.
     * Logs the access and adds a new RequestDto object to the model.
     *
     * @param model the model to which the RequestDto object is added
     * @return the name of the registration view
     */
    @GetMapping("/register")
    public String registerUser(Model model) {

        List<Country> countries = countryService.getAllCountries();

        logger.info("registration page called ");

        model.addAttribute("countries", countries);

        RequestDto requestDto = new RequestDto();
        model.addAttribute("requestDto", requestDto);
        return "registration/registeration";
    }


    /**
     * Handles GET requests to the /successful endpoint.
     *
     * @return the name of the success view
     */
    @GetMapping("/successful")
    public String successfullRegisterd() {

        return "successful/index";
    }


    /**
     * Handles POST requests to the /processregistration endpoint.
     * Validates the RequestDto object and processes the user registration.
     *
     * @param requestDto    the RequestDto object containing user registration data
     * @param bindingResult the result of the validation and binding
     * @return the name of the view to be returned
     */
    @PostMapping("/processregistration")
    public String registerUser(@Valid @ModelAttribute("requestDto") RequestDto requestDto, BindingResult bindingResult) {

        // Check for validation errors in the request data
        if (bindingResult.hasErrors()) {
            logger.error("Error found in user registration. Below is the RequestDto received from form:");
            logger.error(requestDto.toString());
            // Log each field error for detailed debugging
            bindingResult.getFieldErrors().forEach(error -> {
                System.out.println(
                        "field" + error.getField() + ",error:" + error.getDefaultMessage()
                );
            });
            // Return the registration page if there are errors
            return "registration/registeration";
        }

        // Log the start of the registration processing
        logger.info("register processing started successully");

        logger.info("recived request dto is " + requestDto.toString());


        // Create the user with the provided data
        userServiceImpl.createuser(requestDto);

        // Redirect to the success page after successful registration
        return "redirect:/successful";
    }

    /**
     * This method handles all exceptions that are not explicitly caught elsewhere in the application.
     * It is annotated with @ExceptionHandler to indicate that it should be invoked when an Exception is thrown.
     */
    @ExceptionHandler(DuplicateEntryException.class)
    public String handleDuplicateException(DuplicateEntryException exception, Model model) {
        logger.error("exception occured duplicate number found");
        logger.error(exception.toString());
        model.addAttribute("e", exception.getMessage());
        model.addAttribute("r", HttpStatus.BAD_REQUEST);
        return "error/error";
    }

    /**
     * This method handles all exceptions that are not explicitly caught elsewhere in the application.
     * It is annotated with @ExceptionHandler to indicate that it should be invoked when an Exception is thrown.
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, Model model) {
        logger.error("exception occured");
        logger.error(exception.toString());
        model.addAttribute("e", exception.getMessage());
        model.addAttribute("r", HttpStatus.BAD_REQUEST);
        return "error/error";
    }


}

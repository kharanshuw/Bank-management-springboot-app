package com.bankapplication.controller;

import com.bankapplication.dto.ProfileUpdateDto;
import com.bankapplication.dto.ResponseDto;
import com.bankapplication.dto.response.ResponseAccountDto;
import com.bankapplication.getapplicationcontext.UserServiceAppContext;
import com.bankapplication.model.Country;
import com.bankapplication.model.Users;
import com.bankapplication.service.AccountService;
import com.bankapplication.service.AdminService;
import com.bankapplication.service.CountryService;
import com.bankapplication.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserServiceImpl userService;

    private AccountService accountService;

    private UserServiceAppContext userServiceAppContext;

    private CountryService countryService;

    private AdminService adminService;

    @Autowired
    public UserController(UserServiceImpl userService, UserServiceAppContext userServiceAppContext, AdminService adminService,
                          AccountService accountService, CountryService countryService) {
        this.userService = userService;
        this.userServiceAppContext = userServiceAppContext;
        this.countryService = countryService;
        this.accountService = accountService;
        this.adminService = adminService;
    }

    /**
     * Handles GET requests to the /home2 endpoint. Logs the access and determines
     * the appropriate home page based on user roles.
     *
     * @param model              the model to which attributes are added
     * @param httpServletRequest the HTTP request to check user roles
     * @return the name of the view to be returned
     */
    @GetMapping("/home2")
    public String homepage2(Model model, HttpServletRequest httpServletRequest) {

        logger.info("homepage2 method from usercontroller class called ");

        String emailString = userServiceAppContext.getLoggedInUserEmail();

        logger.info("the user logged in is :" + emailString);

        model.addAttribute("email", emailString);

        if (httpServletRequest.isUserInRole("USER") && httpServletRequest.isUserInRole("ADMIN")) {

            logger.info("USER AND ADMIN  profile home page called after successful login inside UserController");

            return "home/adminhome/multiple-user";

        } else if (httpServletRequest.isUserInRole("USER") && httpServletRequest.isUserInRole("MANAGER")) {

            logger.info("USER AND MANAGER  profile home page called after successful login inside UserController");

            return "home/adminhome/multiple-user";
        } else if (httpServletRequest.isUserInRole("ADMIN") && httpServletRequest.isUserInRole("MANAGER")) {
            logger.info("ADMIN AND MANAGER profile home page called after successful login inside UserController");

            return "home/adminhome/multiple-user";
        } else if (httpServletRequest.isUserInRole("ADMIN")) {

            logger.info("Admin home page called after successful login inside UserController");
            return "home/adminhome/adminhome";

        } else if (httpServletRequest.isUserInRole("USER")) {

            logger.info("User home page called after successful login inside UserController");

            return "home/userhome/userhome";

        } else if (httpServletRequest.isUserInRole("MANAGER")) {
            logger.info("MANAGER home page called after successful login inside UserController");

            return "home/manager/dashboard";
        } else {

            logger.error("User role not identified, redirecting to error page");
            return "redirect:/access-denied";
        }

    }

    /**
     * Handles GET requests to the /access-denied endpoint. Logs the access and
     * returns the access denied view.
     *
     * @return the name of the access denied view
     */
    @GetMapping("/access-denied")
    public String accessdenied() {
        logger.error("error occured redirecting to error/access-denied");
        return "error/access-denied";
    }

    /**
     * Handles GET requests to the /user/profile endpoint. Logs the access and
     * retrieves the logged-in user's profile details.
     *
     * @param model the model to which attributes are added
     * @return the name of the view to be returned
     */
    @GetMapping("/user/profile")
    public String userprofile(Model model) {
        logger.info("userprofile method from usercontroller class called ");

        String emailString = userServiceAppContext.getLoggedInUserEmail();

        logger.info("the user logged in is :" + emailString);

        ResponseDto user = userService.getLoggedInUserDetails(emailString);

        ArrayList<String> roleList = user.getrolestring();

        model.addAttribute("roles", roleList);
        // Handle user profile
        logger.info("user provile invoked");
        model.addAttribute("user", user);
        return "home/userhome/userprofiledetails";

    }

    /**
     * Handles HTTP GET requests to the /user/update endpoint.
     * <p>
     * This method is responsible for preparing the data required for updating a
     * user's profile. Returns the name of the view (home/userhome/updateprofile)
     * that will be rendered.
     *
     * @param model the model object used to pass data to the view
     * @return the name of the view to be rendered for updating the user's profile
     */
    @GetMapping("/user/update")
    public String updateProfiledata(Model model) {
        // Retrieve the list of all countries using the country service
        List<Country> countries = countryService.getAllCountries();

        // Add the list of countries to the model
        model.addAttribute("countries", countries);

        // Get the email of the currently logged-in user from the user service context
        String emailString = userServiceAppContext.getLoggedInUserEmail();
        // Retrieve the User object based on the logged-in user's email

        Users users = userService.getUserByEmail(emailString);
        // Convert the User object to a ProfileUpdateDto object using the user service

        ProfileUpdateDto profileUpdateDto = userService.convertToProfileUpdate(users);
        // Add the ProfileUpdateDto to the model

        model.addAttribute("profileUpdateDto", profileUpdateDto);
        // Return the view name for updating the profile

        return "home/userhome/updateprofile";
    }

    @PostMapping("/user/processupdate")
    public String processUpdate(@ModelAttribute("profileUpdateDto") ProfileUpdateDto profileUpdateDto) {
        System.out.println(profileUpdateDto.toString());
        return null;
    }

    /**
     * Handles the request to display the user home page after successful login.
     *
     * @return The view name for the user home page.
     */
    @GetMapping("/user/home")
    public String userHome() {
        // Log that the user home page is being called after successful login
        logger.info("User home page called after successful login inside UserController");

        // Return the view name for the user home page
        return "home/userhome/userhome";

    }

    /**
     * Handles the retrieval of user accounts and displays them in the model.
     *
     * @param principal The principal object representing the currently
     *                  authenticated user.
     * @param model     The model to add attributes to be used in the view.
     * @return The view name to display the user's accounts.
     */
    @GetMapping("/user/accounts")
    public String getUserAccounts(Principal principal, Model model) {
        // Retrieve the email of the currently authenticated user
        String userEmail = principal.getName();

        // Retrieve the user based on their email
        Users user = accountService.getUserByEmail(userEmail);
        logger.info("user logged in is " + user.getId());

        // Retrieve the user details ID
        int userId = user.getUserDetails().getId();
        logger.info("user details id is " + userId);

        // Retrieve the list of accounts for the user
        List<ResponseAccountDto> responseAccountDtoList = userService.getListOfAccounts(userId);

        // Add the list of ResponseAccountDto objects to the model
        model.addAttribute("list", responseAccountDtoList);
        return "account/account-list";
    }


    /**
     * Handles the request to delete a user's account.
     *
     * @param principal the currently authenticated principal
     * @return the view name for the account deletion result
     * @throws RuntimeException if the account deletion fails
     */
    @GetMapping("/user/delete")
    public String removeAccount(Principal principal) {

        logger.info("removeAccount method called for user: {}", principal.getName());

        // Log the start of the account deletion process
        logger.info("User name is {}", principal.getName());

        // Retrieve the user's email from the principal
        String email = principal.getName();

        // Retrieve the user entity using their email
        Users user = adminService.getUserByEmail(email);

        // Attempt to delete the user's account
        boolean result = adminService.deleteAccount(user.getId());

        // Check the result of the deletion process
        if (result) {
            logger.info("Account for user {} deleted successfully", principal.getName());
            return "home/userhome/user_account_delete_successful";
        } else {
            logger.error("Failed to delete account for user {}", principal.getName());
            throw new RuntimeException("Unable to delete account");
        }

    }

    /**
     * This method handles all exceptions that are not explicitly caught elsewhere
     * in the application. It is annotated with @ExceptionHandler to indicate that
     * it should be invoked when an Exception is thrown.
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

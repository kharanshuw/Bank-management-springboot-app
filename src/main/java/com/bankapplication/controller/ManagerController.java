package com.bankapplication.controller;

import com.bankapplication.dto.ResponseBranchDto;
import com.bankapplication.dto.ResponseDto;
import com.bankapplication.dto.response.ResponseAccountDto;
import com.bankapplication.getapplicationcontext.UserServiceAppContext;
import com.bankapplication.model.Users;
import com.bankapplication.service.AccountService;
import com.bankapplication.service.AdminService;
import com.bankapplication.service.ManagerService;
import com.bankapplication.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
    private UserServiceAppContext userServiceAppContext;
    private UserService userService;
    private AccountService accountService;
    private ManagerService managerService;
    private AdminService adminService;

    @Autowired
    public ManagerController(UserServiceAppContext userServiceAppContext, UserService userService, AccountService accountService, ManagerService managerService, AdminService adminService) {
        super();
        this.userServiceAppContext = userServiceAppContext;
        this.userService = userService;
        this.accountService = accountService;
        this.managerService = managerService;
        this.adminService = adminService;
    }

    /**
     * Handles the request to display the manager home page. * @param model the
     * model to pass attributes to the view
     *
     * @return the view name of the manager home page
     */
    @GetMapping("/home")
    public String getManagerHome(Model model, Principal principal) {
        logger.info("getManagerHome method from ManagerController called successfully");
        String email = principal.getName();
        logger.info("Logged-in user's email: {}", email);

        // Retrieve the user by email
        Users user = accountService.getUserByEmail(email);

        int managerid = user.getUserDetails().getId();

        try {

            model.addAttribute("managerid", managerid);

            // Return the view name for the manager home page
            return "home/manager/manager-home";

        } catch (Exception e) {
            logger.error("Exception occurred in getManagerHome method of ManagerController: {}", e.getMessage());

            model.addAttribute("e",
                    "Exception occurred in getManagerHome method of ManagerController:" + " " + e.getMessage());

            return "error/error";
        }

    }


    /**
     * Handles GET requests to the /profile endpoint.
     * Retrieves the logged-in manager's profile details and returns the view name.
     *
     * @param model the model to pass attributes to the view
     * @return the view name of the manager's profile page
     */
    @GetMapping("/profile")
    public String getManagerProfile(Model model) {

        logger.info("/manager/profile called successfully");


        // Get the logged-in user's email from the user service app context
        String email = userServiceAppContext.getLoggedInUserEmail();

        // Log the logged-in user's email
        logger.info("logged in user email is " + email.toString());


        // Get the logged-in user's details from the user service
        ResponseDto responseDto = userService.getLoggedInUserDetails(email);


        // Get the user's roles as a list of strings
        ArrayList<String> roleStrings = responseDto.getrolestring();

        // Add the user's roles and details to the model
        model.addAttribute("roles", roleStrings);
        model.addAttribute("user", responseDto);

        return "home/manager/manager-profile-data";
    }


    /**
     * Retrieves a list of accounts waiting for manager approval and adds them to the model.
     *
     * @param model the model to which the accounts will be added
     * @return the name of the view template for displaying the inactive accounts
     */
    @GetMapping("/pending_request")
    public String getAccountsWaitingForManagerApproval(Model model, Principal principal) {

        // Log the start of the method
        logger.info("Executing getAccountsWaitingForManagerApproval...");

        String email = principal.getName();

        logger.info("Logged-in user's email: {}", email);

        Users user = accountService.getUserByEmail(email);

        int managerid = user.getUserDetails().getId();


//         Retrieve the list of accounts with disabled status
        List<ResponseAccountDto> accounts = accountService.getAccountsWithDisablesStatus(managerid);

//         Log the successful retrieval of accounts
        logger.info("Successfully retrieved {} accounts waiting for manager approval", accounts.size());

//         Add the accounts to the model
        model.addAttribute("accounts", accounts);

        return "account/inactive_accounts";
    }


    /**
     * Handles the approval request to enable an account.
     *
     * @param accountNumber the account number to be enabled
     * @param requestType   the type of request
     * @param model         the model to which attributes will be added
     * @return the name of the view template for displaying the success message
     */
    @GetMapping("/approve")
    public String getApproval(@RequestParam("accountno") String accountNumber, @RequestParam("request") String requestType, Model model) {
        // Log the received account number and request type
        logger.info("Received account number: {}", accountNumber);
        logger.info("Received request type: {}", requestType);

        // Enable the account based on the account number
        accountService.enableAccount(accountNumber);

        // Add the account number to the model
        model.addAttribute("accountNo", accountNumber);

        // Log the successful account enabling
        logger.info("Successfully enabled account with account number: {}", accountNumber);

        return "account/account-enabled-successfully";
    }


    /**
     * Handles the request to get branch details for the logged-in manager.
     *
     * @param principal the Principal object representing the logged-in user
     * @param model     the Model object to which attributes will be added
     * @return the name of the view template for displaying the branch details
     */
    @GetMapping("/branch_details")
    public String getBranchDetails(Principal principal, Model model) {

        // Log the start of the method
        logger.info("getBranchDetails method called");

        // Retrieve the email of the logged-in user
        String email = principal.getName();

        logger.info("Logged-in user's email: {}", email);

        // Retrieve the user by email
        Users user = accountService.getUserByEmail(email);

        // Retrieve the branch details by manager ID
        ResponseBranchDto responseBranchDto = managerService.getBranchByManagerId(user);

        if (responseBranchDto == null) {
            logger.error("Branch details not found for user with email: {}", email);
            return "branch/branch_not_allotted";
        }

        // Log the retrieved branch details
        logger.info("Retrieved branch details: {}", responseBranchDto.toString());

        // Add the branch details to the model
        model.addAttribute("b", responseBranchDto);

        // Return the view template name for displaying the branch details
        return "branch/branch-details";

    }


    @GetMapping("/delete")
    public String deleteAccount(Principal principal) {
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
     * Retrieves a list of all accounts managed by the authenticated user and renders a table.
     *
     * @param principal the authenticated user
     * @param model     the model object
     * @return the name of the HTML template to render
     */
    @GetMapping("/accounts")
    public String getListOfAccounts(Principal principal, Model model) {
        // Log the start of the method
        logger.info("getListOfAccounts method called for manager email {}", principal.getName());
        // Retrieve the user's email from the principal
        String email = principal.getName();

        // Retrieve the user entity using their email
        Users user = adminService.getUserByEmail(email);

        // Retrieve the manager ID from the user's user details
        int managerid = user.getUserDetails().getId();

        // Retrieve a list of all accounts managed by the manager
        List<ResponseAccountDto> accounts = managerService.getAllAccounts(managerid);

        // Add the list of accounts to the model
        model.addAttribute("accounts", accounts);

        // Log the successful completion of the method
        logger.info("getListOfAccounts method completed successfully");

        // Return the name of the HTML template to render
        return "home/manager/accounts";
    }


    /**
     * Handles RuntimeExceptions thrown in the application.
     *
     * @param exception the RuntimeException that was thrown
     * @param model     the model to pass data to the view
     * @return the view name for error handling
     */
    @ExceptionHandler(value = RuntimeException.class)
    public String handleRuntimeException(RuntimeException exception, Model model) {
        logger.error("runtime exception " + exception.getMessage());

        model.addAttribute("e", exception.toString());
        model.addAttribute("r", exception.getMessage());

        return "error/error";
    }


    /**
     * Exception handler for global exception
     *
     * @param e
     * @param model
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e, Model model) {
        logger.error("exception occured " + e.getMessage());

        model.addAttribute("e", e.toString());
        model.addAttribute("r", e.getMessage());
        // Handle the exception
        return "error/error";
    }

}

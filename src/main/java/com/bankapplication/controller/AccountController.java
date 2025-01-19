package com.bankapplication.controller;

import com.bankapplication.dto.request.RequestAccountDto;
import com.bankapplication.dto.response.ResponseAccountDto;
import com.bankapplication.dto.response.ResponseTransactionDto;
import com.bankapplication.getapplicationcontext.UserServiceAppContext;
import com.bankapplication.model.Account;
import com.bankapplication.model.Branch;
import com.bankapplication.model.Users;
import com.bankapplication.service.AccountService;
import com.bankapplication.service.BranchService;
import com.bankapplication.service.TransactionService;
import com.bankapplication.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private UserServiceAppContext userServiceAppContext;
    private AccountService accountService;
    private BranchService branchService;
    private UserService userService;
    private TransactionService transactionService;


    @Autowired
    public AccountController(UserServiceAppContext userServiceAppContext, AccountService accountService, BranchService branchService, UserService userService, TransactionService transactionService) {
        this.userServiceAppContext = userServiceAppContext;
        this.accountService = accountService;
        this.branchService = branchService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    /**
     * Handles the request to create an account.
     *
     * @param model The model to pass attributes to the view.
     * @return The view name to be rendered.
     */
    @GetMapping("/choose-account-type")
    public String selectAccountType(Model model) {

        // Retrieve the logged-in user's email
        String email = userServiceAppContext.getLoggedInUserEmail();
        logger.info("Logged in user email: {}", email);

        // Retrieve the user by email
        Users users = accountService.getUserByEmail(email);
        logger.info("User logged in with ID: {}", users.getId());

        // Add user details to the model
        populateModelWithUserDetails(model, users);


        List<String> nonExistingAccount = accountService.getNonExistingAccount(users);
        model.addAttribute("nonExistingAccount", nonExistingAccount);

        logger.info("Non-existing account types: {}", nonExistingAccount);

        return "account/account-type";
    }

    /**
     * Populates the model with user details.
     *
     * @param model The model to populate.
     * @param users The user whose details are to be added to the model.
     */
    private void populateModelWithUserDetails(Model model, Users users) {
        model.addAttribute("fname", users.getUserDetails().getFname());
        model.addAttribute("lname", users.getUserDetails().getLname());
    }

    /**
     * Handles the creation of a new account form based on the specified account type.
     *
     * @param model       The model to add attributes to be used in the view.
     * @param accountType The type of account to be created.
     * @param principal   The principal object representing the currently authenticated user.
     * @return The view name for the account creation form.
     */
    @GetMapping("/create-account-form")
    private String createAccountWithType(Model model, @RequestParam(name = "type") String accountType, Principal principal) {
        // Retrieve the email of the currently authenticated user
        String userEmail = principal.getName();

        logger.info("account type is " + accountType);


        // Create a new RequestAccountDto object and set the account type
        RequestAccountDto requestAccountDto = new RequestAccountDto();
        requestAccountDto.setAccountType(accountType);

        // Retrieve the user based on their email
        Users user = accountService.getUserByEmail(userEmail);
        logger.info("user logged in is " + user.getId());

        // Retrieve the user details ID and set it in the RequestAccountDto object
        int userId = user.getUserDetails().getId();
        requestAccountDto.setUserId(userId);

        // Add the RequestAccountDto object to the model
        model.addAttribute("requestAccountForm", requestAccountDto);

        // Retrieve the list of all branches
        List<Branch> branchList = branchService.getAllBranch();
        logger.info("list of all branch with size is " + branchList.size());

        // Add the list of branches to the model
        model.addAttribute("branchlist", branchList);


        // Return the view name for the account creation form
        return "account/account-form";
    }

    /**
     * Handles the creation of a new bank account based on the provided RequestAccountDto.
     *
     * @param requestAccountDto The DTO containing the account creation request data.
     * @param model             The model to add attributes to be used in the view.
     * @param bindingResult     The BindingResult object for validating the request data.
     * @return The view name indicating the outcome of the account creation process.
     */
    @PostMapping("/create-account")
    public String createAccount(@Valid @ModelAttribute("requestAccountForm") RequestAccountDto requestAccountDto, Model model, BindingResult bindingResult) {
        // Check for validation errors in the request data
        if (bindingResult.hasErrors()) {
            logger.error("Error found in user bank account creation. Below is the requestAccountDto received from form:");
            logger.error(requestAccountDto.toString());
            // Log each field error for detailed debugging
            bindingResult.getFieldErrors().forEach(error -> {
                System.out.println(
                        "field" + error.getField() + ",error:" + error.getDefaultMessage()
                );
            });
            // Return the registration page if there are errors
            return "account/account-form";
        }


        logger.info("requestaccountdto is " + requestAccountDto.toString());


        // Save the new account using the account service
        Account account = accountService.saveNewAccount(requestAccountDto);

        logger.info("account created " + account.toString());
        return "account/successfull";
    }

    /**
     * Handles details request for account details
     *
     * @param accountNumber
     * @param model
     * @return account/account-details view
     */
    @GetMapping("/details")
    public String getAccountDetails(@RequestParam("accountNo") String accountNumber, Model model) {

        // Log the received account number for debugging
        logger.info("Received request for account details with account number: {}", accountNumber);


        // Retrieve the account details using the account service
        ResponseAccountDto responseAccountDto = accountService.getAccountDetailsByAccountNumber(accountNumber);

        logger.info("Retrieved account details: {}", responseAccountDto);

        model.addAttribute("dto", responseAccountDto);

        return "account/account-details";
    }

    /**
     * Handles the request to delete a bank account by account number.
     *
     * @param accountNumber the account number of the account to be deleted
     * @param model         the model to pass data to the view
     * @return the view name for the account deletion status
     */
    @GetMapping("/delete")
    public String deleteAccount(@RequestParam("accountNo") String accountNumber, Model model) {
        // Log the received account number for debugging
        logger.info("Received request for account delete with account number: {}", accountNumber);


        // Forward the request to the service layer for processing
        accountService.deleteAccountByAccountNumber(accountNumber);

        // Log successful deletion request forwarding
        logger.info("Successfully deleted account with account number: {}", accountNumber);

        model.addAttribute("accno", accountNumber);

        return "account/account_delete_successfully";
    }

    /**
     * Controller method to display the deposit form for a specific account.
     * This method retrieves account details based on the provided account number
     * and adds them to the model for rendering in the deposit form view.
     *
     * @param accountNumber the account number used to fetch account details
     * @param model         the model to which the account details will be added
     * @return the name of the view template for the deposit form
     */
    @GetMapping("/depositform")
    public String getDepositForm(@RequestParam("accountNo") String accountNumber, Model model) {
        logger.info("account number is " + accountNumber);


        // Retrieve account details using the account number
        ResponseAccountDto responseAccountDto = accountService.getAccountDetailsByAccountNumber(accountNumber);

        model.addAttribute("responseAccountDto", responseAccountDto);

        return "account/deposit-form";
    }


    /**
     * Handles the deposit request by validating the request data and calling the account service to deposit money.
     *
     * @param responseAccountDto the request data containing the account number and deposit amount
     * @param model              the Spring MVC model object
     * @param bindingResult      the binding result object containing any validation errors
     * @return the view name to render after processing the request
     */
    @PostMapping("/deposit")
    public String getDepositMoney(@Valid @ModelAttribute("responseAccountDto") ResponseAccountDto responseAccountDto, Model model, BindingResult bindingResult) {

        // Log the incoming request data
        logger.info("Received deposit request: {}", responseAccountDto.toString());

        // Check for validation errors in the request data
        if (bindingResult.hasErrors()) {
            logger.error("Validation errors found in deposit request:");


            // Log each field error for detailed debugging
            bindingResult.getFieldErrors().forEach(error -> {
                logger.error(
                        "field" + error.getField() + ",error:" + error.getDefaultMessage()
                );
            });
            // Return the deposit page if there are errors
            return "account/deposit-form";
        }

        logger.info("requestaccountdto is " + responseAccountDto.toString());


        // Call the account service to deposit money
        logger.debug("Depositing money into account: {}", responseAccountDto.getAccountNo());
        accountService.depositMoney(responseAccountDto.getAccountNo(), responseAccountDto.getBalance());

        logger.info("Account balance successfully updated");

        model.addAttribute("accountNo", responseAccountDto.getAccountNo());

        model.addAttribute("balance", responseAccountDto.getBalance());


        return "account/deposit-successful";
    }

    /**
     * Handles GET request to retrieve the withdrawal form for a specific account.
     *
     * @param accountNumber the account number of the account to retrieve
     * @param model         the model to add attributes to
     * @return the withdrawal form page
     */
    @GetMapping("/withdrawalform")
    public String getWithdrawForm(@RequestParam("accountNo") String accountNumber, Model model) {

        // Log the received account number for debugging purposes
        logger.info("Received account number: {}", accountNumber);

        // Retrieve account details using the account number
        // This method call may throw AccountNotFoundException if the account is not found
        ResponseAccountDto responseAccountDto = accountService.getAccountDetailsByAccountNumber(accountNumber);


        // Log the retrieved account details for debugging purposes
        logger.info("Retrieved account details: {}", responseAccountDto);


        model.addAttribute("responseAccountDto", responseAccountDto);

        return "account/withdraw-form";
    }


    /**
     * Handles the withdrawal request by validating the request data and calling the account service to withdraw money.
     *
     * @param responseAccountDto the request data containing the account number and withdrawal amount
     * @param model              the Spring MVC model object
     * @param bindingResult      the binding result object containing any validation errors
     * @return the view name to render after processing the request
     */
    @PostMapping("/withdraw")
    public String getwithdrawMoney(@Valid @ModelAttribute("responseAccountDto") ResponseAccountDto responseAccountDto, Model model, BindingResult bindingResult) {

        // Log the incoming request data            
        logger.info("Received withdrawal request: {}", responseAccountDto.toString());

        // Check for validation errors in the request data
        if (bindingResult.hasErrors()) {
            logger.error("Validation errors found in withdrawal request:");

            // Log each field error for detailed debugging
            bindingResult.getFieldErrors().forEach(error -> {
                logger.error(
                        "field" + error.getField() + ",error:" + error.getDefaultMessage()
                );
            });
            // Return the withdrawal page if there are errors
            return "account/withdraw-form";
        }

        logger.info("requestaccountdto is " + responseAccountDto.toString());

        // Call the account service to withdraw money
        logger.debug("Withdrawing money from account: {}", responseAccountDto.getAccountNo());

        accountService.withdrawMoney(responseAccountDto.getAccountNo(), responseAccountDto.getBalance());

        logger.info("Account balance successfully updated");

        model.addAttribute("accountNo", responseAccountDto.getAccountNo());

        model.addAttribute("balance", responseAccountDto.getBalance());

        return "account/withdrawl-successfull";
    }


    /**
     * Handles GET requests to retrieve transaction history for a specific account.
     * Adds the transaction history to the model to be displayed on the transactions page.
     *
     * @param accountNo  the account number for which to retrieve the transaction history
     * @param model      the model to which the transaction history will be added
     * @param pageNumber the page number for pagination (default: 0)
     * @param pageSize   the number of transactions per page (default: 1)
     * @return the name of the view template for displaying the transaction history
     */
    @GetMapping("/transactions")
    public String getTransaction(@RequestParam("accountNo") String accountNo, Model model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "1") int pageSize) {

        // Log the received account number, page number, and page size for debugging purposes
        logger.info("Received account number: {}, page number: {}, page size: {}", accountNo, pageNumber, pageSize);


        // Retrieve transaction history for the given account number
        Page<ResponseTransactionDto> responseTransactionDtos = transactionService.getTransactionHistory(accountNo, pageNumber, pageSize);

        // Add the transaction history to the model
        model.addAttribute("list", responseTransactionDtos);

        // Check if the transaction history is empty
        if (responseTransactionDtos.isEmpty()) {
            logger.info("No transaction history found for account number: {}", accountNo);
            model.addAttribute("accoutno", accountNo);
            return "account/no-transaction-history";
        }

        logger.info("Transaction history retrieved successfully for account number: {}", accountNo);

        model.addAttribute("hasContent", responseTransactionDtos.hasContent());
        logger.info("Has content: {}", responseTransactionDtos.hasContent());

        model.addAttribute("hasNext", responseTransactionDtos.hasNext());
        logger.info("Has next: {}", responseTransactionDtos.hasNext());

        model.addAttribute("hasPrevious", responseTransactionDtos.hasPrevious());
        logger.info("Has previous: {}", responseTransactionDtos.hasPrevious());

        model.addAttribute("accountNo", accountNo);

        model.addAttribute("size", responseTransactionDtos.getSize());
        logger.info("Size: {}", responseTransactionDtos.getSize());


        model.addAttribute("totalPages", responseTransactionDtos.getTotalPages());
        logger.info("Total pages: {}", responseTransactionDtos.getTotalPages());

        // Log the transaction history retrieval success
        logger.info("Transaction history retrieved successfully for account number: {}", accountNo);

        return "account/transactions";
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

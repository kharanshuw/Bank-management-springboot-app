package com.bankapplication.controller;

import com.bankapplication.dto.RequstBranchDto;
import com.bankapplication.dto.ResponseDto;
import com.bankapplication.exceptionhandler.DuplicateEntryException;
import com.bankapplication.getapplicationcontext.UserServiceAppContext;
import com.bankapplication.model.Country;
import com.bankapplication.model.Users;
import com.bankapplication.service.AdminService;
import com.bankapplication.service.CountryService;
import com.bankapplication.service.UserService;
import com.bankapplication.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * AdminController handles all admin-related requests and actions.
 */
@Controller
public class AdminController {

    // This sets up the logger for the AdminController class.
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    // This is a private field for the UserServiceImpl, which likely handles
    // user-related operations.
    private UserService userService;
    // This is a private field for UserServiceAppContext, which is a context class
    // managing the session or user context.
    private UserServiceAppContext userServiceAppContext;
    // This is a private field for AdminService, which likely handles administrative
    // operations.
    private AdminService adminService;

    private CountryService countryService;

    @Autowired
    public AdminController(UserServiceImpl userService, UserServiceAppContext userServiceAppContext,
                           AdminService adminService, CountryService countryService) {
        this.userService = userService;
        this.userServiceAppContext = userServiceAppContext;
        this.adminService = adminService;
        this.countryService = countryService;
    }

    /**
     * Handles the request to display the admin profile data.
     *
     * @param request the HTTP servlet request
     * @param model   the model to pass attributes to the view
     * @return the path to the admin profile page template
     */
    @GetMapping("/admin/profile")
    public String adminProfileData(HttpServletRequest request, Model model) {
        logger.info("admin profile method from admin controller called");
        try {
            // Retrieve the email of the logged-in user
            String email = userServiceAppContext.getLoggedInUserEmail();
            logger.info("The user logged in is: {} inside /admin/profile", email);

            // Retrieve the details of the logged-in user
            ResponseDto user = userService.getLoggedInUserDetails(email);
            logger.info("User details retrieved for email {}: {}", email, user);

            // Get the roles of the user
            ArrayList<String> roleList = user.getrolestring();
            model.addAttribute("roles", roleList);
            logger.info("Roles for user {}: {}", email, roleList);

            // Add the user details to the model
            model.addAttribute("user", user);

            return "home/adminhome/adminprofile";

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Exception occurred while retrieving admin profile data: {}", e.getMessage());
            model.addAttribute("e", "An error occurred while retrieving your profile data. Please try again later.");
            return "error/error";
        }
    }

    /**
     * This method handles the request to get all users.
     *
     * @param model the model to pass attributes to the view
     * @return the path to the all users page template if users are found, otherwise
     * returns an error page
     */
    @GetMapping("/admin/alluser")
    public String getAllUser(Model model) {

        logger.info("getalluser method from admincontroller class called successfully");

        try {
            // Retrieve the list of all users
            List<ResponseDto> responseDtos = userService.findAllUser();

            // Check if the user list is empty
            if (responseDtos.isEmpty()) {
                logger.error("ResponseDto list is empty in getAllUser method of AdminController");
                return "error/error";
            } else {

                // Add the user list to the model
                model.addAttribute("users", responseDtos);

                logger.info("Data sent from /admin/allusers to allusers.html");
                return "home/adminhome/allusers";
            }
        } catch (Exception e) {
            logger.error("Exception occurred in getAllUser method of AdminController: {}", e.getMessage());
            model.addAttribute("e", "An error occurred while retrieving the user list. Please try again later.");
            return "error/error";
        }

    }

    /**
     * Handles the request to find inactive users.
     *
     * @param model the model to pass attributes to the view
     * @return the path to the approve page template
     */
    @GetMapping("/admin/pending")
    public String findInactiveUser(Model model) {

        // Retrieve the list of inactive users

        List<ResponseDto> list = userService.findallInactiveUser();
        // Add the list of inactive users to the model

        model.addAttribute("users", list);

        return "home/adminhome/approve";
    }

    /**
     * Handles the request action based on the provided parameters.
     *
     * @param a  the action to perform (accept or reject)
     * @param id the ID of the user
     * @return a redirect to the pending page
     */
    @GetMapping("/admin/request")
    public String requestAction(@RequestParam String a, @RequestParam String id) {

        logger.info("input recived " + a);
        // Check if the action is to accept

        if (a.equals("accept")) {
            // Activate the user based on the ID

            logger.info("accept request");
            adminService.activateUser(a, id);
            return "redirect:/admin/pending";
        } else {

            logger.info("reject request");
            return "redirect:/admin/pending";
        }

    }

    /**
     * Handles the request to manage roles.
     *
     * @param model the model to pass attributes to the view
     * @return the path to the role management page template if users are found,
     * otherwise returns an error page
     */
    @GetMapping("/admin/rolemanagement")
    public String manageRole(Model model) {
        logger.info("/admin/rolemanagement from AdminController called successfully");

        try {

            // Retrieve the list of all users
            List<ResponseDto> responseDtoList = userService.findAllUser();

            // Check if the user list is empty
            if (responseDtoList.isEmpty()) {
                logger.error("ResponseDto list is empty in manageRole method of AdminController");
                model.addAttribute("errorMessage", "No users found.");
                return "error/error.html";
            } else {
                // Add the user list to the model
                model.addAttribute("users", responseDtoList);
                logger.info("Data sent from /admin/rolemanagement to manage-role.html");
                return "home/adminhome/manage-role";
            }

        } catch (Exception e) {
            logger.error("Exception occurred in manageRole method of AdminController: {}", e.getMessage());
            model.addAttribute("e", "An error occurred while retrieving the user list. Please try again later.");
            return "error/error.html";
        }

    }

    /**
     * This method handles the request to navigate to the add role page for an
     * existing user.
     *
     * @param a     a parameter indicating the action to perform
     * @param id    the ID of the user
     * @param model the model to pass attributes to the view
     * @return the path to the add role page template if adding a role, otherwise
     * redirects to the role list page
     */
    @GetMapping("/admin/addrolepage")
    public String addRolepage(@RequestParam String a, @RequestParam String id, Model model) {

        logger.info("/admin/addrolepage from admin controller  called successfully");
        // Check if the action is to add a role

        if (a.equals("addrole")) {
            logger.info("add role request recieved");

            // Retrieve the current roles of the user

            List<String> roleList = adminService.findRole(id);

            // Add the current roles and user ID to the mode
            model.addAttribute("rolelist", roleList);
            model.addAttribute("id", id);

            // Retrieve roles not assigned to the user and add to the model

            List<String> unassignedroleList = adminService.findUnassignedRoles(id);
            model.addAttribute("unassignedroleList", unassignedroleList);
            return "home/adminhome/addrole";
        } else {
            // If the action is not to add a role, log and redirect to the role list page

            logger.info("no changes in role list");
            return "redirect:/admin/getrolelist";
        }

    }

    /**
     * Handles HTTP GET requests to display the role removal page. Mapped to the
     * "/admin/removerolepage" URL.
     *
     * @param a     The action to be performed, typically "remove" to indicate role
     *              removal.
     * @param id    The ID of the user whose roles are being managed.
     * @param model The Model object to pass data to the view.
     * @return The view name to be rendered.
     */
    @GetMapping("/admin/removerolepage")
    public String removeRolepage(@RequestParam String a, @RequestParam String id, Model model) {
        logger.info(" /admin/removerolepage from admin controller  called successfully");

        // Log the received parameters for debugging purposes.
        logger.info(String.format("Received action and id: %s %s", a, id));

// Check if the action is "remove".
        if (a.equals("remove")) {
            logger.info("Remove request received at /admin/removepage in admincontroller");

            // Retrieve the current roles of the user

            List<String> roles = adminService.findRole(id);

            // Add the roles and user ID to the model to pass to the view.
            model.addAttribute("roles", roles);

            model.addAttribute("uid", id);

            return "home/adminhome/removerole";

        } else {
            logger.info("no change in role list of user ");

            // Redirect to the role list page if the action is not "remove".
            return "redirect:/admin/getrolelist";
        }

    }

    /**
     * Handles the request to add a role to a user.
     *
     * @param msg      the message parameter
     * @param userid   the ID of the user
     * @param rolename the name of the role to add
     * @return a redirect to the role management page
     */
    @GetMapping("/admin/addrole")
    public String addRole(@RequestParam String msg, @RequestParam String userid, @RequestParam String rolename) {
        logger.info("msg userid and rolename recived from addrole.html in addrole method of admincontroller is "
                + msg.toString() + " " + userid.toString() + " " + rolename.toString());
        // Call the service method to add the role to the user

        String response = adminService.addRoleToUser(rolename, userid);

        logger.info("response from adminservie.addroletouser method is " + response.toString());

        // Redirect to the role management page

        return "redirect:/admin/rolemanagement";
    }

    /**
     * Handles HTTP GET requests to remove a role from a user. Mapped to the
     * "/admin/removerole" URL.
     *
     * @param msg      A message received from the client, typically for logging or
     *                 display purposes.
     * @param userid   The ID of the user from whom the role is to be removed.
     * @param rolename The name of the role to be removed from the user.
     * @return A redirect to the role management page.
     */
    @GetMapping("/admin/removerole")
    public String removeRole(@RequestParam String msg, @RequestParam String userid, @RequestParam String rolename) {
        logger.info("msg userid and rolename recived from addrole.html in addrole method of admincontroller is "
                + msg.toString() + " " + userid.toString() + " " + rolename.toString());

        // Call the service method to remove the role to the user
        // This method should handle the business logic for role removal.
        String response = adminService.removeRoleFromUser(rolename, userid);

        System.out.println(
                "response recived from adminService.removeRoleFromUser(rolename,userid) is : " + response.toString());
        // Redirect the user to the role management page after the operation is
        // complete.
        return "redirect:/admin/rolemanagement";
    }

    /**
     * Handles the request to display the admin home page.
     *
     * @param model the model to pass attributes to the view
     * @return the view name of the admin home page
     */
    @GetMapping("/admin/home")
    public String getAdminHome(Model model) {
        logger.info("getAdminHome method from AdminController called successfully");
        try {
            // Return the view name for the admin home page
            return "home/adminhome/adminhome";
        } catch (Exception e) {
            logger.error("Exception occurred in getAdminHome method of AdminController: {}", e.getMessage());
            model.addAttribute("e",
                    "Exception occurred in getAdminHome method of AdminController" + " " + e.getMessage());
            return "error/error";
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

    /**
     * Displays the form for creating a new branch.
     *
     * @param model the model to pass attributes to the view
     * @return the view name of the create branch form
     */
    @GetMapping("/admin/branch/create")
    public String createBranch(Model model) {

        try {
            // Fetch all countries to be displayed in the form's dropdown
            List<Country> countries = countryService.getAllCountries();
            model.addAttribute("countries", countries);

            // Add an empty DTO to the model to bind form data
            RequstBranchDto requstBranchDto = new RequstBranchDto();
            model.addAttribute("dto", requstBranchDto);

            logger.info("Navigating to create branch form with countries list");
            return "branch/create-branch-form";
        } catch (Exception e) {
            logger.error("Error occurred while preparing create branch form: {}", e.getMessage());
            model.addAttribute("e", "Unable to load the form at this moment. Please try again later.");
            return "error/error";
        }

    }

    /**
     * Processes the creation of a branch using the data from RequstBranchDto.
     *
     * @param requstBranchDto the DTO containing branch data from the form
     * @param bindingResult   the result of validation binding
     * @return the view name or redirect URL based on the success or failure of the
     * operation
     */
    @PostMapping("/admin/branch/processbranch")
    public String processCreateBranch(@Valid @ModelAttribute("dto") RequstBranchDto requstBranchDto,
                                      BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

            logger.error("Error found in creation of branch. Below is the RequestbranchDto received from form:");
            logger.error(requstBranchDto.toString());
// Log each field error for detailed debugging
            bindingResult.getFieldErrors().forEach(error -> {
                System.out.println("field" + error.getField() + ",error:" + error.getDefaultMessage());
            });
            // Return the registration page if there are errors
            return "branch/create-branch-form";
        }

        // Log the DTO data for debugging
        logger.info("Processing branch creation for: {}", requstBranchDto.toString());

        try {
            // Process the branch creation logic
            boolean result = adminService.processBranchCreation(requstBranchDto);

            if (result) {
                logger.info("Branch created successfully for email: {}", requstBranchDto.getEmail());
                return "branch/successful";
            } else {
                logger.warn("Branch creation failed for email: {}", requstBranchDto.getEmail());
                return "branch/error";
            }
        } catch (DuplicateEntryException e) {
            // Log unexpected exceptions
            logger.error("Error occurred while creating the branch: {}", e.getMessage());
            model.addAttribute("e", e.getMessage());
            return "error/error";

        } catch (Exception e) {
            // Log unexpected exceptions
            logger.error("Error occurred while creating the branch: {}", e.getMessage());
            model.addAttribute("e", "Unable to load the form at this moment. Please try again later.");
            return "error/error";

        }

    }


    /**
     * Handles the request to delete an account.
     *
     * @param principal the currently authenticated principal
     * @return the view name for the account deletion result
     * @throws RuntimeException if the account deletion fails
     */
    @GetMapping("/admin/delete")
    public String removeAccount(Principal principal) {
        logger.info("Admin requested to delete account: {}", principal.getName());

        // Retrieve the user by their email (from principal)
        Users user = adminService.getUserByEmail(principal.getName());

        // Attempt to delete the user's account
        boolean result = adminService.deleteAccount(user.getId());

        if (result) {
            logger.info("Account for user {} deleted successfully", principal.getName());
            return "home/adminhome/user_account_delete_successful";
        } else {
            logger.error("Failed to delete account for user {}", principal.getName());
            throw new RuntimeException("Unable to delete account");
        }

    }

}

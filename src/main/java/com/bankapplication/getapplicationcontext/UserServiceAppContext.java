package com.bankapplication.getapplicationcontext;

import com.bankapplication.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceAppContext {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    public String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        logger.info("inside userserviceappcontext class getloggedinuseremail method");
        String email = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {

                logger.info("user data found inside userserviceappcontext class getloggedinuseremail method ");

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                email = userDetails.getUsername();
                //	return ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername(); // Use
                // getUsername()
                // method
            }
            return email;

        }

        logger.error("user unknown no data found in  userserviceappcontext class getloggedinuseremail method");
        return "Unknown User";
    }

}

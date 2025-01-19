package com.bankapplication.service;

import com.bankapplication.dto.ProfileUpdateDto;
import com.bankapplication.dto.RequestDto;
import com.bankapplication.dto.ResponseDto;
import com.bankapplication.dto.response.ResponseAccountDto;
import com.bankapplication.exceptionhandler.DuplicateEntryException;
import com.bankapplication.model.*;
import com.bankapplication.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// This class is a Spring Service that implements user-related operations
@Service
public class UserServiceImpl implements UserService {

    // Logger for this class
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    // Repositories for database operations
    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private UserDetailsRepository userDetailsRepository;

    private RoleRepository roleRepository;

    private CityRepository cityRepository;


    // Constructor for dependency injection
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDetailsRepository userDetailsRepository,
                           RoleRepository repository, CityRepository cityRepository, AccountRepository accountRepository) {

        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.roleRepository = repository;
        this.cityRepository = cityRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Creates a new user based on the provided RequestDto.
     *
     * @param requestDto The request DTO containing user details.
     * @return The original request DTO (may be null on error).
     * @throws IllegalArgumentException if password is empty or null.
     */
    @Override
    @Transactional
    public RequestDto createuser(RequestDto requestDto) {

        // Log the incoming request data
        logger.info("Request data: " + requestDto.getFname() + " " + requestDto.getLname() + " "
                + requestDto.getGender() + " " + requestDto.getPhoneno() + " " + requestDto.getEmail());

        String email = requestDto.getEmail();

        try {
            if (!email.isEmpty()) {
                int id = userRepository.findIdByEmail(email);
                if (id != 0) {
                    throw new IllegalArgumentException("User with email " + email + " already exists");
                }
            }
        } catch (Exception e) {
            System.out.println("exception occured in userservice class createuser method");
            logger.error(e.getMessage());
        }

        int cityid = requestDto.getCityid();
        System.out.println("city id from request dto is " + cityid);

        City city = cityRepository.findById(cityid).get();

        System.out.println("city name is " + city.getCityName());
        System.out.println(city.getCityName());

        if (city == null || city.getDistrict() == null || city.getDistrict().getState() == null
                || city.getDistrict().getState().getCountry() == null) {
            throw new IllegalArgumentException(
                    "Invalid city ID OR district or state or country in userserviceimpl class createuser method");
        }

        // Create a new User entity
        Users users = new Users();
        String password = requestDto.getPassword();

        // Prepare the password by adding "{noop}" prefix (for NoOpPasswordEncoder)
        StringBuffer stringBuffer = new StringBuffer(password);

        stringBuffer.insert(0, "{noop}");

        password = stringBuffer.toString();

        logger.info("new password" + password);

        // Set user properties
        users.setPassword(password);
        users.setEmail(requestDto.getEmail());

        // Create and set UserDetails entity
        UserDetails userDetails = new UserDetails();

        userDetails.setFname(requestDto.getFname());

        userDetails.setLname(requestDto.getLname());

        userDetails.setGender(requestDto.getGender());

        userDetails.setPhoneno(requestDto.getPhoneno());

        // Set the city in userdetails object
        userDetails.setCity(city);

        System.out.println("date of birth is " + requestDto.getDob());

        // set date of birth
        userDetails.setDob(requestDto.getDob());

//set the user in userdetails object
        userDetails.setUsers(users);

        // Establish bidirectional relationship
        users.setUserDetails(userDetails);

        // Find the "ROLE_USER" role
        Role role = null;

        try {
            role = roleRepository.findByRolename("ROLE_USER");

            if (role == null) {
                // this block of code will only run if user role does not exist in database
                // Log error if ROLE_USER is not found
                logger.error("ROLE_USER not found!");

                return null;
            }
        } catch (Exception e) {
            // Log any errors in finding the role

            logger.error("Error finding ROLE_USER: " + e.getMessage());

        }
        // Add the ROLE_USER to the user
        users.addrole(role);
        try {
            // Save the user to the database
            userDetailsRepository.save(userDetails);
            Users savedUser = userRepository.save(users);
            logger.info("user successfully saved in user service " + savedUser.toString());
            logger.info("Successfully registered user in user service.");

        }
        //catch duplicate number exception
        catch (DataIntegrityViolationException e) {
            logger.info("mobile number alredy exist exception ");
            throw new DuplicateEntryException("mobile number  or email alredy exist.");
        }
        //catch runtime exception 
        catch (Exception e) {
            // Log any errors in saving the user
            logger.error("Error saving user: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        // Return the original request DTO
        return requestDto;
    }

    /**
     * Retrieves the logged-in user's details based on the provided email.
     *
     * @param email The email address of the logged-in user.
     * @return The ResponseDto containing the user's details, or null if not found.
     */
    public ResponseDto getLoggedInUserDetails(String email) {

        try {
            // Log the received email address for auditing purposes
            logger.info("Email received at getLoggedInUserDetails method of UserServiceImpl class: {}", email);

            // Attempt to find the logged-in user's details by their email address
            Users users = userRepository.findUserByEmail(email);

            // Check if the user was found
            if (users == null) {
                // Log a message indicating that the user was not found
                logger.info("Logged in user not found");
                // Return null to indicate that the user was not found
                return null;
            }

            // Log the found user's details for auditing purposes
            logger.info("Found user: {}", users.toString());

            // Create a ResponseDto object to hold the user's details
            ResponseDto responseDto = createResponseDto(users);

            // Return the ResponseDto object containing the user's details
            return responseDto;
        } catch (Exception e) {
            // Log the exception for error tracking purposes
            logger.error("Error retrieving logged-in user details", e);
            System.out.println(e.getMessage());
            // Return null to indicate that an error occurred
            return null;
        }
    }

    /**
     * Creates a ResponseDto object from a given User object.
     * <p>
     * This method copies relevant fields from the User object to the ResponseDto
     * object.
     *
     * @param users The User object to be converted.
     * @return The created ResponseDto object.
     */
    private ResponseDto createResponseDto(Users users) {
        ResponseDto responseDto = new ResponseDto();

        responseDto.setId(users.getId());
        logger.debug("Setting ID: {}", users.getId());

        responseDto.setEmail(users.getEmail());
        logger.debug("Setting Email: {}", users.getEmail());

        responseDto.setFname(users.getUserDetails().getFname());
        logger.debug("Setting First Name: {}", users.getUserDetails().getFname());

        responseDto.setLname(users.getUserDetails().getLname());
        logger.debug("Setting Last Name: {}", users.getUserDetails().getLname());

        responseDto.setGender(users.getUserDetails().getGender());
        logger.debug("Setting Gender: {}", users.getUserDetails().getGender());


        responseDto.setPhoneno(users.getUserDetails().getPhoneno());
        logger.debug("Setting Phone Number: {}", users.getUserDetails().getPhoneno());

        responseDto.setRoles(users.getRole());
        logger.debug("Setting Roles: {}", users.getRole());

        responseDto.setCitynameString(users.getUserDetails().getCity().getCityName());
        logger.info("Setting city in responsedto" + users.getUserDetails().getCity().getCityName());

        responseDto.setDistrictnameString(users.getUserDetails().getCity().getDistrict().getDistrictName());
        logger.info(
                "Setting district in responsedto" + users.getUserDetails().getCity().getDistrict().getDistrictName());

        responseDto.setStateString(users.getUserDetails().getCity().getDistrict().getState().getStateName());
        logger.info("Setting state in responsedto"
                + users.getUserDetails().getCity().getDistrict().getState().getStateName());

        responseDto.setCountryString(
                users.getUserDetails().getCity().getDistrict().getState().getCountry().getCountryName());
        logger.info("Setting country in responsedto"
                + users.getUserDetails().getCity().getDistrict().getState().getCountry().getCountryName());

        logger.debug("ResponseDto created: {}", responseDto);
        return responseDto;
    }

    /**
     * Method to find all users and convert them into ResponseDto objects.
     *
     * @return a list of ResponseDto objects representing the users.
     */

    @Override
    public List<ResponseDto> findAllUser() {
        // Retrieve all users from the repository

        List<Users> users = userRepository.findAll();

        // Check if the users list is empty

        if (users.isEmpty()) {
            // Log an error if no users are found
            logger.error("Users list in findAllUser method of UserServiceImpl class is empty");
            return Collections.emptyList();

        } else {
            // Log information indicating users were found
            logger.info("List of users found in findAllUser method of UserServiceImpl class");
            // Create a list to hold the ResponseDto objects

            List<ResponseDto> list = new ArrayList<>();
            // Iterate through each user and convert to ResponseDto

            for (Users u : users) {

                // Create a ResponseDto from the User object

                ResponseDto responseDto = createResponseDto(u);
                // Log information indicating a response entity was created

                logger.info("response entity created ");
                // Add the ResponseDto to the list

                list.add(responseDto);

            }
            // Return the list of ResponseDto objects

            return list;

        }

    }

    /**
     * Retrieves a list of inactive users and converts them to a list of ResponseDto
     * objects.
     *
     * @return A list of ResponseDto objects representing inactive users.
     */
    @Override
    public List<ResponseDto> findallInactiveUser() {
        // Retrieve the list of inactive users from the repository
        List<Users> inactiveUsers = userRepository.findInactiveUsers();
        logger.debug("Found {} inactive users", inactiveUsers.size());

        // Create a list to store the inactive users in ResponseDto objects

        List<ResponseDto> list1 = new ArrayList<>();

        // Iterate over the inactive users and create a ResponseDto object for each one

        for (Users u : inactiveUsers) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setEmail(u.getEmail());
            responseDto.setId(u.getId());
            // Add the ResponseDto object to the list
            list1.add(responseDto);
            logger.debug("Added ResponseDto for user: {}", u.getId());

        }
        logger.debug("Returning {} inactive user ResponseDtos", list1.size());
        // Return the list of ResponseDto objects

        return list1;
    }

    /**
     * Converts a ResponseDto object to a ProfileUpdateDto object.
     * <p>
     * This method copies the relevant fields from the ResponseDto to the
     * ProfileUpdateDto.
     * <p>
     * // * @param responseDto The ResponseDto object to be converted.
     *
     * @return The converted ProfileUpdateDto object.
     */
    public ProfileUpdateDto convertToProfileUpdate(Users users) {
        // Check for null user
        if (users == null) {
            return null;
        }

        ProfileUpdateDto profileUpdateDto = new ProfileUpdateDto();

        profileUpdateDto.setEmail(users.getEmail());

        profileUpdateDto.setId(users.getId());

        profileUpdateDto.setFname(users.getUserDetails().getFname());
        profileUpdateDto.setLname(users.getUserDetails().getLname());
        profileUpdateDto.setGender(users.getUserDetails().getGender());
        profileUpdateDto.setPhoneno(users.getUserDetails().getPhoneno());

        return profileUpdateDto;

    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user to retrieve
     * @return the user associated with the given email address, or null if no user
     * is found
     */
    public Users getUserByEmail(String email) {
        Users users = null;
        try {
            users = userRepository.findUserByEmail(email);
            logger.info("User found with email: {}", email);
        } catch (Exception e) {
            logger.error("Error retrieving user by email: {}", email, e);
            System.out.println(e.getMessage());
        }
        return users;
    }

    /**
     * Retrieves a list of ResponseAccountDto objects for a given user ID.
     *
     * @param id The user ID for which to retrieve the accounts.
     * @return A list of ResponseAccountDto objects representing the user's accounts.
     */
    public List<ResponseAccountDto> getListOfAccounts(int id) {

        // Retrieve the list of accounts for the given user ID
        List<Account> accounts = accountRepository.findByUserId(id);

        // Log if no accounts are found for the user ID
        if (accounts.isEmpty()) {
            logger.info("No accounts found for user ID: " + id);
        }

        List<ResponseAccountDto> responseAccountDtoList = new ArrayList<>();

        // Convert the list of Account entities to ResponseAccountDto objects
        for (Account account : accounts) {
            logger.info("account is " + account.toString());
            ResponseAccountDto responseAccountDto = convertToResponseAccountDto(account);
            logger.info("responseAccountDto to be added in list is " + responseAccountDto.toString());
            responseAccountDtoList.add(responseAccountDto);
        }

        // Return the list of ResponseAccountDto objects
        return responseAccountDtoList;

    }

    /**
     * Converts an Account entity to a ResponseAccountDto object.
     *
     * @param account The Account entity to convert.
     * @return The corresponding ResponseAccountDto object.
     */
    public ResponseAccountDto convertToResponseAccountDto(Account account) {

        logger.info("convertToResponseAccountDto method invoked ");
        logger.info("account object recived is " + account.toString());

        // Check if the account object is null and throw an exception if it is
        if (Objects.isNull(account)) {
            logger.error("account object is null");
            throw new NullPointerException("account object is null ");
        }

        // Create a new ResponseAccountDto object
        ResponseAccountDto responseAccountDto = new ResponseAccountDto();
        responseAccountDto.setAccountNo(account.getAccountNumber());
        responseAccountDto.setBalance(account.getBalance());
        responseAccountDto.setAccountType(account.getAccountType().getType());
        responseAccountDto.setBranch(account.getBranch().getBranchName());
        responseAccountDto.setFname(account.getUserDetails().getFname());
        responseAccountDto.setLname(account.getUserDetails().getLname());
        responseAccountDto.setIfscCode(account.getIfscCode());

        logger.info("response entity generated is " + responseAccountDto.toString());

        // Return the ResponseAccountDto object
        return responseAccountDto;
    }

}
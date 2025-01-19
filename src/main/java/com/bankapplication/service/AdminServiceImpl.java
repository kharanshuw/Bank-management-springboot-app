package com.bankapplication.service;

import com.bankapplication.dto.RequstBranchDto;
import com.bankapplication.dto.ResponseDto;
import com.bankapplication.exceptionhandler.DuplicateEntryException;
import com.bankapplication.exceptionhandler.UserNotFoundException;
import com.bankapplication.exceptionhandler.UserRetrievalException;
import com.bankapplication.model.Branch;
import com.bankapplication.model.City;
import com.bankapplication.model.Role;
import com.bankapplication.model.Users;
import com.bankapplication.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

/**
 * AdminServiceImpl class handles all Admin related services
 */
@Service
public class AdminServiceImpl implements AdminService {
	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	// this is userrepositry object
	private UserRepository userRepository;
	// userdetailsrepository object
	private UserDetailsRepository userDetailsRepository;
	// rolerepository object
	private RoleRepository roleRepository;

	private CityRepository cityRepository;

	private BranchRepository branchRepository;

	@Autowired
	public AdminServiceImpl(UserRepository userRepository, UserDetailsRepository userDetailsRepository,
			RoleRepository roleRepository, CityRepository cityRepository, BranchRepository branchRepository) {
		this.userRepository = userRepository;
		this.userDetailsRepository = userDetailsRepository;
		this.roleRepository = roleRepository;
		this.cityRepository = cityRepository;
		this.branchRepository = branchRepository;
	}

	/**
	 * Activates a user by setting their active status to true.
	 *
	 * @param action the action to be performed (not used in this method)
	 * @param id     the ID of the user to be activated
	 * @return a message indicating the result of the operation
	 */
	@Transactional
	public String activateUser(String action, String id) {
		int id1 = Integer.valueOf(id);
		Optional<Users> userOptional = userRepository.findById(id1);
		// check user obj found in database or not
		if (userOptional.isPresent()) {

			Users users = userOptional.get();
			logger.info("user found " + users.toString());
			users.setActive(true);
			// Ensure the user is saved after updating the status
			userRepository.save(users);
			return "Successfully updated status";

		} else {
			logger.error("User with given ID is not available to edit");
			return "User not found";
		}
	}

	/**
	 * Helper method to convert a User object to a ResponseDto object.
	 *
	 * @param users the User object to be converted
	 * @return the converted ResponseDto object, or null if the user is not found
	 */
	@Transactional
	public ResponseDto convertUserToResponseDto(Users users) {
		if (users == null) {
			System.out.println("user not found");
			logger.error("user not found with id");
			return null;
		} else {
			ResponseDto responseDto = new ResponseDto();
			responseDto.setEmail(users.getEmail());
			responseDto.setFname(users.getUserDetails().getFname());
			responseDto.setLname(users.getUserDetails().getLname());
			responseDto.setId(users.getId());
			responseDto.setGender(users.getUserDetails().getGender());
			responseDto.setPhoneno(users.getUserDetails().getPhoneno());
			responseDto.setRoles(users.getRole());

			logger.info("user successfully convertd to response dto in adminserviceimpl");
			return responseDto;
		}

	}

	/**
	 * This method returns a list of roles that the user already has.
	 *
	 * @param idString the ID of the user
	 * @return a list of role names that the user has
	 */
	@Transactional
	public List<String> findRole(String idString) {
		int id = Integer.valueOf(idString);
		List<String> list = null;
		System.out.println("id recived in parameter is :" + id);

		Optional<Users> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {

			logger.info("user found in findrole");
			Users users = userOptional.get();
			// convert user to response dto
			ResponseDto responseDto = convertUserToResponseDto(users);

			// response dto method getrolestring returns arraylist<string> of roles assigned
			// to user
			list = responseDto.getrolestring();
			return list;
		} else {
			// if roles not assigned to user
			logger.error("list of user roles is null ");
			return list;
		}
	}

	/**
	 * This method finds all roles that a user does not have and returns them in a
	 * list.
	 *
	 * @param userid
	 * @return a list of role names that the user does not have
	 */
	@Transactional
	public List<String> findUnassignedRoles(String userid) {

		// This is the list of roles which the user already has

		List<String> userRoles = findRole(userid);

		// This list contains all the roles available in the database
		List<String> allRoles = roleRepository.findAllRoleName();

		// Empty list to store roles which are not assigned to the user
		List<String> unassignedRoles = new ArrayList<>();

		// Log and print all available roles in the database
		logger.info("All the roles available in the database are:");

		System.out.println("all the roles available in database are ");

		// print list2 which contains all available role in database
		for (String s : allRoles) {
			System.out.println(s);

			logger.info(s);
		}

		// Find roles not assigned to the user and add them to list3
		for (String s : allRoles) {
			if (!userRoles.contains(s)) {

				// Add only those roles which the user does not have
				logger.info("add only those role which user does not have");
				unassignedRoles.add(s);
			}
		}

		// Log and print roles that the user does not have
		logger.info("Roles that the user does not have:");
		for (String string : unassignedRoles) {
			System.out.println(string);
		}

		// Return the list of roles that the user does not have
		return unassignedRoles;

	}

	/**
	 * Adds a role to a user.
	 *
	 * @param rolename the name of the role to be added
	 * @param userid   the ID of the user
	 * @return a message indicating the result of the operation
	 */
	@Transactional
	public String addRoleToUser(String rolename, String userid) {
		int id = Integer.valueOf(userid);
		Optional<Users> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {

			Users users = userOptional.get();
			logger.info("user found " + users.toString());

			Role role = null;

			try {
				// Retrieve role by name
				role = roleRepository.findByRolename(rolename);

				// Check if role exists

				if (role == null) {
					// this block of code will only run if role does not exist in database
					// Log error if ROLE_USER is not found
					logger.error(rolename + " not found! in addroleuser method of adminserviceimpl class");

					return "role not found in user ";
				}
				// Add role to user and save

				users.addrole(role);
				userRepository.save(users);
				logger.info("Role added successfully in AdminServiceImpl class addRoleToUser method");

				return "Role added successfully";

			} catch (Exception e) {
				logger.error("Error finding given role in database");
				logger.error(e.toString());

			}

			// Ensure the user is saved after updating the status

		} else {
			logger.error(
					"User with given ID is not available to add role in AdminServiceImpl class addRoleToUser method  ");
			return "user not found";
		}
		return "user not found";
	}

	@Transactional
	public String removeRoleFromUser(String rolename, String userid) {
		int id = Integer.valueOf(userid);

		Optional<Users> userOptional = userRepository.findById(id);

		if (userOptional.isPresent()) {

			Users users = userOptional.get();

			logger.info("user found inside removeRoleFromUser from adminserviceimpl class " + users.toString());

			Role role = null;

			try {
				// Retrieve role by name from database
				role = roleRepository.findByRolename(rolename);

				// Check if returned role is null
				if (role == null) {
					// this block of code will only run if role does not exist in database
					// Log error if ROLE_USER is not found
					logger.error(rolename + " not found! in removerole method of adminserviceimpl class");

					return "role not found in user ";
				}
				// Add role to user and save
// Ensure the user is saved after updating the status
				users.removeRole(role);
				userRepository.save(users);
				logger.info("Role removed successfully in AdminServiceImpl class removeRoleFromUser method");

				return "Role removed successfully";

			} catch (Exception e) {
				logger.error("Error finding given role in database");
				logger.error(e.toString());

			}

		} else {
			logger.error(
					"User with given ID is not available to add role in AdminServiceImpl class removeRoleFromUser method  ");
			return "user not found";
		}
		return "user not found";
	}

	/**
	 * Retrieves a user by their ID from the repository.
	 *
	 * @param id the ID of the user to retrieve
	 * @return the user with the given ID if found
	 * @throws UserNotFoundException if the user with the given ID is not found
	 */
	@Transactional
	public Users getUserById(int id) {
		// Retrieve user by ID from the repository
		Optional<Users> optionalUser = userRepository.findById(id);
		// Check if the user is present
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new UserNotFoundException("User with ID " + id + " not found.");
		}
	}

	/**
	 * Retrieves the ID of a user by their email address.
	 *
	 * @param email the email address of the user
	 * @return the ID of the user with the given email address
	 * @throws UserNotFoundException if the user with the given email is not found
	 */
	@Transactional
	public int getIdByEmail(String email) {
		// Validate input parameter
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Email cannot be null or empty");
		}

		try {
			// Retrieve user ID by email from the repository
			int id = userRepository.findIdByEmail(email);

			// Check if ID is valid (assuming IDs are positive numbers)
			if (id <= 0) {
				throw new UserNotFoundException("User with email " + email + " not found.");
			}

			return id;
		} catch (Exception e) {
			// Handle any unexpected exceptions
			throw new UserRetrievalException("Error occurred while retrieving user ID by email", e);
		}
	}

	/**
	 * Retrieves a user by their email address.
	 *
	 * @param email the email address of the user to retrieve
	 * @return the user with the given email address
	 * @throws UserNotFoundException if the user with the given email is not found
	 */
	public Users getUserByEmail(String email) {
		// Validate input parameter
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Email cannot be null or empty");
		}
		// Retrieve user by email from the repository
		Users users = userRepository.findUserByEmail(email);
		// Check if user is found
		if (users == null) {
			// Log error and throw custom exception
			logger.error("User not found by email: " + email);
			throw new UserNotFoundException("User not found by email: " + email);
		}
		return users;

	}

	/**
	 * Converts a RequstBranchDto object to a Branch object.
	 *
	 * @param requstBranchDto the DTO containing branch data
	 * @return the converted Branch object
	 * @throws IllegalArgumentException if the DTO contains invalid data
	 * @throws EntityNotFoundException  if the city ID is not found
	 */
	public Branch convertToBranch(RequstBranchDto requstBranchDto) {

		// Validate input parameter
		if (requstBranchDto == null) {
			throw new IllegalArgumentException("RequstBranchDto cannot be null");
		}

		// Extract and validate data from DTO
		String email = requstBranchDto.getEmail();
		String branchname = requstBranchDto.getBranchName();
		String contactno = requstBranchDto.getContactNo();
		String requstBranchDtoCityid = requstBranchDto.getCityid();

		if (email == null || email.isEmpty() || branchname == null || branchname.isEmpty() || contactno == null
				|| contactno.isEmpty() || contactno == null || contactno.isEmpty()) {
			throw new IllegalArgumentException("Invalid data in RequstBranchDto");
		}

		int cityId;
		try {
			cityId = Integer.parseInt(requstBranchDtoCityid);
			System.out.println("City ID converted to int: " + cityId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid city ID format: " + requstBranchDtoCityid, e);
		}
		// Retrieve the city entity
		Optional<City> cityOptional = cityRepository.findById(cityId);
		if (!cityOptional.isPresent()) {
			throw new EntityNotFoundException("City with ID " + cityId + " not found");
		}
		City city = cityOptional.get();
		System.out.println("City found: " + city);
		// Create and populate the Branch entity
		Branch branch = new Branch();
		branch.setBranchName(branchname);
		branch.setEmail(email);
		branch.setContactNo(contactno);
		branch.setCity(city);
		return branch;
	}

	/**
	 * Processes the creation of a branch using the data from RequstBranchDto.
	 *
	 * @param requstBranchDto the DTO containing branch data
	 * @return true if the branch is successfully saved, otherwise false
	 * @throws IllegalArgumentException if the DTO contains invalid data
	 */
	public boolean processBranchCreation(RequstBranchDto requstBranchDto) {
		boolean result = false;

		// Validate input parameter
		if (requstBranchDto == null) {
			logger.error("RequstBranchDto cannot be null");
			throw new IllegalArgumentException("RequstBranchDto cannot be null");
		}

		try {
			// Convert DTO to Branch entity
			Branch branch = convertToBranch(requstBranchDto);

			// Log the branch details (for debugging purposes)
			logger.info("requestbranchdto converted to branch object" + branch.toString());

			String bankCode = "SPHE";

			branch.setIfscCode(generateUniqueIFSCCode(bankCode));

			// Save the branch to the repository
			Branch branch1 = branchRepository.save(branch);
			logger.info("branch saved successfully is " + branch1.toString());
			result = true;
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateEntryException("mobile number alredy exist.");
		} catch (UserNotFoundException e) {
			// Handle case where city is not found
			logger.error(e.getMessage());
			System.err.println(e.getMessage());
		} catch (Exception e) {
			// Handle any unexpected exceptions during branch creation
			logger.error(e.getMessage());
			System.err.println("Error occurred while creating the branch: " + e.getMessage());
		}

		return result;
	}

	/**
	 * Generates a unique IFSC code for the given bank code.
	 *
	 * @param bankCode the 4-character bank code
	 * @return the generated unique IFSC code
	 * @throws IllegalArgumentException if the bank code is null or not exactly 4
	 *                                  characters long
	 */
	public String generateUniqueIFSCCode(String bankCode) {
		String ifscCode;
		int attempt = 0;

		// Validate the bank code
		if (bankCode == null || bankCode.length() != 4) {
			logger.error("Invalid bank code: {}", bankCode);
			throw new IllegalArgumentException("Bank code must be exactly 4 characters long.");
		}

		// Attempt to generate a unique IFSC code
		do {
			ifscCode = generateIFSC(bankCode);
			attempt++;
			logger.info("Attempt {}: Generated IFSC code: {}", attempt, ifscCode);
		} while (branchRepository.existsByIfscCode(ifscCode));

		return ifscCode;
	}

	/**
	 * Generates an IFSC code for the given bank code.
	 *
	 * @param bankCode the 4-character bank code
	 * @return the generated IFSC code
	 * @throws IllegalArgumentException if the bank code is null or not exactly 4
	 *                                  characters long
	 */
	private String generateIFSC(String bankCode) {
		// Validate the bank code
		if (bankCode == null || bankCode.length() != 4) {
			logger.error("Invalid bank code: {}", bankCode);
			throw new IllegalArgumentException("Bank code must be exactly 4 characters long.");
		}
		// Generate a random 6-digit branch code
		String branchCode = generateRandomBranchCode();

		// Concatenate to form the IFSC code
		String ifscCode = bankCode + "0" + branchCode;

		// Log the generated IFSC code
		logger.info("Generated IFSC code: {}", ifscCode);

		return ifscCode;

	}

	/**
	 * Generates a random 6-digit branch code.
	 *
	 * @return a 6-digit branch code as a string
	 */
	private String generateRandomBranchCode() {
		String branchCode = null;
		try {
			// Creating an instance of Random class
			Random random = new Random();

			// Generating a number between 100000 and 999999
			int branchCodeNumber = 100000 + random.nextInt(900000);

			// Converting the number to a string
			branchCode = String.valueOf(branchCodeNumber);

			// Logging the generated branch code
			logger.info("Generated branch code: {}", branchCode);
		} catch (Exception e) {
			// Logging any exceptions that occur
			logger.error("An error occurred while generating the branch code", e);
			System.out.println(e);
		}

		// Returning the generated branch code
		return branchCode;
	}

	/**
	 * Deletes an account by its ID.
	 *
	 * @param id the ID of the account to be deleted
	 * @return true if the account was successfully deleted, false otherwise
	 */
	@Transactional
	public boolean deleteAccount(int id) {
		try {
			// Attempt to delete the account by ID
			Optional<Users> account = userRepository.findById(id);
			if (!account.isPresent()) {
				logger.warn("Account with ID {} does not exist", id);
				return false;
			}

			Users users = account.get();
			Set<Role> roles = users.getRole();
			
			userRepository.deleteUserRole(id);

			userRepository.deleteById(id);
			logger.info("Account with ID {} deleted successfully", id);
			return true;
		} catch (Exception e) {
			// Log the error
			logger.error("Error deleting account with ID {}: {}", id, e.getMessage());
			return false;
		}
	}

}

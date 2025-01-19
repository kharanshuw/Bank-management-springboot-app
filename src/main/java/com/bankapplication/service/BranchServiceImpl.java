package com.bankapplication.service;

import com.bankapplication.dto.RequstBranchDto;
import com.bankapplication.dto.ResponseBranchDto;
import com.bankapplication.exceptionhandler.BranchNotFoundException;
import com.bankapplication.model.Branch;
import com.bankapplication.model.UserDetails;
import com.bankapplication.repository.BranchRepository;
import com.bankapplication.repository.UserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {
    private static final Logger logger = LoggerFactory.getLogger(BranchServiceImpl.class);

    private BranchRepository branchRepository;

    private UserDetailsRepository userDetailsRepository;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, UserDetailsRepository userDetailsRepository) {
        this.branchRepository = branchRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    /**
     * Retrieves all branches from the database.
     *
     * @return a list of all branches
     */
    @Override
    public List<Branch> getAllBranch() {
        logger.info("getAllBranch method called");

        try {
            // Retrieve the list of all branches
            List<Branch> branchList = branchRepository.findAll();

            // Log the number of branches retrieved
            logger.info("Number of branches retrieved: {}", branchList.size());

            return branchList;
        } catch (Exception e) {
            // Log the exception with a meaningful message
            logger.error("Exception occurred while retrieving branches: {}", e.getMessage());

            // Optionally, rethrow the exception or handle it accordingly
            throw e;
        }

    }


    /**
     * Retrieves a branch by its ID.
     *
     * @param id the ID of the branch
     * @return the branch if found
     * @throws BranchNotFoundException if no branch is found with the given ID
     */
    public Branch getBranchById(int id) {

        logger.info("getBranchById method called with ID: {}", id);

        // Find branch by ID using the repository
        Branch branch = branchRepository.findById(id).orElseThrow(() -> {
            logger.error("Branch with ID: {} not found.", id);
            return new BranchNotFoundException("Branch with ID " + id + " not found.");
        });

        logger.info("Branch with ID: {} found.", id);
        return branch;


    }

    /**
     * Converts a Branch object to a RequestBranchDto object.
     *
     * @param branch the Branch object to be converted
     * @return the converted RequestBranchDto object
     */
    public RequstBranchDto branchToRequestDTO(Branch branch) {
        logger.info("branchToRequestDTO method called");
        // Create a new RequestBranchDto object
        RequstBranchDto requstBranchDto = new RequstBranchDto();

        try {
            // Set the branch name
            requstBranchDto.setBranchName(branch.getBranchName());
            logger.info("Branch name set: {}", branch.getBranchName());

            // Set the contact number
            requstBranchDto.setContactNo(branch.getContactNo());
            logger.info("Contact number set: {}", branch.getContactNo());

            // Set the email
            requstBranchDto.setEmail(branch.getEmail());
            logger.info("Email set: {}", branch.getEmail());

            // Retrieve and set the city ID
            Integer cityId = branch.getCity().getId();
            requstBranchDto.setCityid(cityId.toString());
            logger.info("City ID set: {}", cityId);
        } catch (NullPointerException e) {
            logger.error("Null value encountered: {}", e.getMessage());
            throw new IllegalArgumentException("Branch object contains null values", e);
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getMessage());
            throw new RuntimeException("Failed to convert Branch to RequestBranchDto", e);
        }

        return requstBranchDto;
    }

    /**
     * Converts a Branch object to a ResponseBranchDto object.
     *
     * @param branch the Branch object to be converted
     * @return the converted ResponseBranchDto object
     */
    public ResponseBranchDto branchToResponseDto(Branch branch) {
        // Create a new ResponseBranchDto object
        ResponseBranchDto responseBranchDto = new ResponseBranchDto();

        try {
            // Set the branch ID
            responseBranchDto.setId(branch.getId());
            logger.info("Branch ID set: {}", branch.getId());

            // Set the branch name
            responseBranchDto.setBranchName(branch.getBranchName());
            logger.info("Branch name set: {}", branch.getBranchName());

            // Set the city name
            responseBranchDto.setCityName(branch.getCity().getCityName());
            logger.info("City name set: {}", branch.getCity().getCityName());

            // Set the contact number
            responseBranchDto.setContactNo(branch.getContactNo());
            logger.info("Contact number set: {}", branch.getContactNo());

            // Set the email
            responseBranchDto.setEmail(branch.getEmail()); // Corrected
            logger.info("Email set: {}", branch.getEmail());


        } catch (NullPointerException e) {
            logger.error("Null value encountered: {}", e.getMessage());
            throw new IllegalArgumentException("Branch object contains null values", e);
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getMessage());
            throw new RuntimeException("Failed to convert Branch to ResponseBranchDto", e);
        }

        return responseBranchDto;
    }

    /**
     * Converts branch object to ResponseDto class object
     * this is 2nd method which is reponsible for converting  branch object to ResponseDto class object  *
     * it takes object of branch class
     * returns object of ReponseBranchDto class
     **/
    @Override
    public ResponseBranchDto branchToResponseDto2(Branch branch) {
        ResponseBranchDto responseBranchDto = new ResponseBranchDto();
        try {
            // Set the branch ID
            responseBranchDto.setId(branch.getId());
            logger.info("Branch ID set: {}", branch.getId());

            // Set the branch name
            responseBranchDto.setBranchName(branch.getBranchName());
            logger.info("Branch name set: {}", branch.getBranchName());

            // Set the city name
            responseBranchDto.setCityName(branch.getCity().getCityName());
            logger.info("City name set: {}", branch.getCity().getCityName());

            // Set the contact number
            responseBranchDto.setContactNo(branch.getContactNo());
            logger.info("Contact number set: {}", branch.getContactNo());

            // Set the email
            responseBranchDto.setEmail(branch.getEmail()); // Corrected
            logger.info("Email set: {}", branch.getEmail());

            //set manager id 
            Integer managerid = branch.getManagerId().getId();
            responseBranchDto.setManagerId(managerid.toString());
            logger.info("manager id set: {}", managerid.toString());


        } catch (NullPointerException e) {
            logger.error("Null value encountered: {}", e.getMessage());
            throw new IllegalArgumentException("Branch object contains null values", e);
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getMessage());
            throw new RuntimeException("Failed to convert Branch to ResponseBranchDto", e);
        }
        return responseBranchDto;
    }

    /**
     * Processes and updates the branch data based on the provided ResponseBranchDto object.
     *
     * @param responseBranchDto the DTO object containing updated branch data
     * @return true if the branch is successfully updated, false otherwise
     */
    @Override
    public boolean processAndUpdateBranch(ResponseBranchDto responseBranchDto) {
        logger.info("processAndUpdateBranch method called with DTO: {}", responseBranchDto);
        try {
            // Extract data from the DTO
            int id = responseBranchDto.getId();
            String managerId = responseBranchDto.getManagerId();
            logger.info("manager id recived from dto is " + managerId);

            Integer managerIdInteger = Integer.parseInt(managerId);
            logger.info("manager id in integer is " + managerIdInteger.toString());

            // Retrieve manager details
            Optional<UserDetails> optionalUserDetails = userDetailsRepository.findById(managerIdInteger);
            if (optionalUserDetails.isEmpty()) {
                logger.error("Manager with ID {} not found", managerId);
                return false;
            }
            UserDetails userDetails = optionalUserDetails.get();

            // Retrieve branch details
            Optional<Branch> optionalBranch = branchRepository.findById(id);
            if (optionalBranch.isEmpty()) {
                logger.error("Branch with ID {} not found", id);
                return false;
            }
            Branch branch = optionalBranch.get();

            // Update branch with new data
            branch.setBranchName(responseBranchDto.getBranchName());
            branch.setContactNo(responseBranchDto.getContactNo());
            branch.setEmail(responseBranchDto.getEmail());
            branch.setManagerId(userDetails);

            // Save updated branch
            branchRepository.save(branch);
            logger.info("Branch with ID {} successfully updated", id);

            return true;
        } catch (Exception e) {
            logger.error("Exception occurred while processing and updating branch: {}", e.getMessage(), e);
            return false;
        }

    }

    /**
     * Removes the manager from the specified branch.
     * Retrieves the branch by its ID, sets the manager to null, and updates the branch.
     * If the branch ID is invalid or the branch is not found, throws a RuntimeException.
     *
     * @param branchId the ID of the branch from which the manager is to be removed
     * @return true if the manager was successfully removed, false otherwise
     */
    @Override
    public boolean removeManagerFromBranch(String branchId) {
        try {
            // Convert branch ID to integer
            int intBranchId = Integer.parseInt(branchId);
            logger.info("Branch ID in int is: {}", intBranchId);

            // Retrieve the branch by ID
            Optional<Branch> optionalBranch = branchRepository.findById(intBranchId);
            if (optionalBranch.isPresent()) {
                Branch branch = optionalBranch.get();
                logger.info("Branch found: {}", branch);

                // Retrieve the manager ID
                if (branch.getManagerId() != null) {
                    int branchManagerId = branch.getManagerId().getId();
                    logger.info("Branch manager ID is: {}", branchManagerId);

                    // Remove the manager from the branch
                    branch.setManagerId(null);
                    branchRepository.save(branch);
                    return true;
                } else {
                    logger.warn("No manager found for branch: {}", intBranchId);
                    return false;
                }
            } else {
                logger.error("Branch not found for ID: {}", intBranchId);
                throw new RuntimeException("Branch not found");
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid branch ID format: " + e.getMessage());
            throw new RuntimeException("Invalid branch ID format", e);
        } catch (Exception e) {
            logger.error("Exception occurred while removing manager from branch: {}", e.getMessage(), e);
            throw new RuntimeException("Exception occurred while removing manager from branch", e);
        }

    }


    /**
     * Retrieves a Branch by the manager's ID.
     *
     * @param managerId the ID of the manager
     * @return the Branch managed by the given manager ID, or null if no such Branch exists
     */
    public Branch getBranchByManagerId(int managerId) {
        logger.info("getBranchByManagerId method called with managerId: {}", managerId);

        Branch branch = null;

        try {
            // Retrieve the Branch by manager ID
            branch = branchRepository.findByCustomManagerId(managerId);

            if (branch == null) {
                // Log that no branch was found
                logger.error("No branch found for managerId: {}", managerId);
            } else {
                // Log the successful retrieval
                logger.info("Branch found for managerId: {}", managerId);
            }
        } catch (Exception e) {
            // Log the exception
            logger.error("Error retrieving branch for managerId: {}", managerId, e);

            // Optionally, you could rethrow the exception or handle it as needed
            throw new RuntimeException("Error retrieving branch for managerId: ");
        }

        return branch;

    }


    /**
     * Removes a branch by its ID.
     *
     * @param id the ID of the branch to be removed
     * @return true if the branch was successfully removed, false otherwise
     */
    public boolean removeBranch(int id) {
        logger.info("removeBranch method called with id: {}", id);

        // Check if branch exists
        Optional<Branch> optionalBranch = branchRepository.findById(id);

        if (!optionalBranch.isPresent()) {
            logger.error("Branch with ID {} not found", id);
            throw new BranchNotFoundException("Branch not found with ID " + id);
        }

        try {
            // Attempt to delete the branch by ID
            branchRepository.deleteById(id);

            // Log successful deletion
            logger.info("Branch with ID {} deleted successfully", id);
            return true;
        } catch (Exception e) {
            // Log the error
            logger.error("Error deleting branch with ID {}: {}", id, e.getMessage());

            // Throw a runtime exception with a custom message
            throw new RuntimeException("Error deleting branch with ID " + id, e);
        }
    }


}

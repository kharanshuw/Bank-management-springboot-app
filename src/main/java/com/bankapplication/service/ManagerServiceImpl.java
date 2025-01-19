package com.bankapplication.service;

import com.bankapplication.dto.ManagerDto;
import com.bankapplication.dto.ResponseBranchDto;
import com.bankapplication.dto.response.ResponseAccountDto;
import com.bankapplication.model.Account;
import com.bankapplication.model.Branch;
import com.bankapplication.model.Users;
import com.bankapplication.repository.ManagerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    private static final Logger logger = LoggerFactory.getLogger(ManagerServiceImpl.class);

    private ManagerRepository managerRepository;

    private BranchService branchService;

    private AccountService accountService;

    @Autowired
    public ManagerServiceImpl(ManagerRepository managerRepository, BranchService branchService, AccountService accountService) {
        this.managerRepository = managerRepository;
        this.branchService = branchService;
        this.accountService = accountService;
    }

    /**
     * Retrieves a list of all ManagerDto objects.
     *
     * @return a list of ManagerDto objects
     */
    public List<ManagerDto> getAllManagers() {
        logger.info("getAllManagers method called");

        try {
            // Retrieve list of all ManagerDto objects from the repository
            List<ManagerDto> list = managerRepository.findAllManagers();
            logger.info("Successfully retrieved {} managers", list.size());

            return list;

        } catch (Exception e) {
            // Log and handle any potential exceptions
            logger.error("Exception occurred while retrieving managers: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve managers", e);
        }

    }

    /**
     * Retrieves the branch managed by the given user and converts it to a ResponseBranchDto.
     *
     * @param users the user whose manager ID will be used to find the branch
     * @return the ResponseBranchDto object containing branch details
     */
    public ResponseBranchDto getBranchByManagerId(Users users) {
        ResponseBranchDto responseBranchDto = new ResponseBranchDto();

        try {
            // Log the start of the method
            logger.info("getBranchByManagerId method called with user ID: {}", users.getId());

            // Retrieve the manager ID from the user details
            int managerId = users.getUserDetails().getId();
            logger.info("Manager ID is: {}", managerId);

            // Retrieve the branch by manager ID
            Branch branch = branchService.getBranchByManagerId(managerId);

            if (branch != null) {
                // Populate ResponseBranchDto with data from the Branch object
                responseBranchDto.setId(branch.getId());
                responseBranchDto.setBranchName(branch.getBranchName());
                responseBranchDto.setContactNo(branch.getContactNo());
                responseBranchDto.setContactNo(branch.getContactNo());
                responseBranchDto.setCityName(branch.getCity().getCityName());
                responseBranchDto.setEmail(branch.getEmail());
                responseBranchDto.setManagerId(Integer.toString(managerId));

                responseBranchDto.setManagerName(branch.getManagerNameString());
                responseBranchDto.setIfsc(branch.getIfscCode());
                responseBranchDto.setEstablishDate(branch.getEstablishDate().toString());


                // Log the successful retrieval
                logger.info("Successfully retrieved branch with manager ID: {}", managerId);
            } else {
                // Log that no branch was found
                responseBranchDto = null;
                logger.warn("No branch found for manager ID: {}", managerId);
            }

        } catch (Exception e) {
            // Log the exception
            logger.error("Error retrieving branch for manager ID: {}", users.getUserDetails().getId(), e);

            // Optionally, handle the exception as needed
            throw new RuntimeException("Error retrieving branch for manager ID: " + users.getUserDetails().getId(), e);
        }

        return responseBranchDto;

    }


    /**
     * Retrieves a list of all accounts managed by the specified manager ID.
     *
     * @param managerid the ID of the manager
     * @return a list of ResponseAccountDto objects representing the accounts
     */
    public List<ResponseAccountDto> getAllAccounts(int managerid) {
        // Log the start of the method
        logger.info("getAllAccounts method called for manager ID: {}", managerid);

        try {
            // Retrieve accounts from the repository
            List<Account> accounts = managerRepository.findAccountIdsByManagerIdAndStatus(managerid);
            logger.info("Successfully retrieved {} accounts", accounts.size());

            // Initialize an empty list to store ResponseAccountDto objects
            List<ResponseAccountDto> responseAccountDtoList = new ArrayList<>();

            // Iterate over the accounts and convert each to a ResponseAccountDto
            for (Account account : accounts) {
                // Log the account being processed
                logger.debug("Processing account: {}", account.getAccountNumber());
                ResponseAccountDto responseAccountDto = accountService.getResponseDtoByAccount(account);
                responseAccountDtoList.add(responseAccountDto);
            }

            // Log the successful completion of the method
            logger.info("getAllAccounts method completed successfully");

            return responseAccountDtoList;
        } catch (Exception e) {
            // Log the exception and rethrow it
            logger.error("Error retrieving accounts for manager ID: {}", managerid, e);
            throw new RuntimeException("Error retrieving accounts", e);
        }
    }
}

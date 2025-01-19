package com.bankapplication.service;

import com.bankapplication.dto.response.ResponseTransactionDto;
import com.bankapplication.model.Account;
import com.bankapplication.model.Transactions;
import com.bankapplication.repository.AccountRepository;
import com.bankapplication.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private AccountRepository accountRepository;

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Creates a new transaction in the database.
     *
     * @param transactions the transaction object to be saved
     */
    @Override
    public void createNewTransaction(Transactions transactions) {

        // Check if the transactions object is not null to prevent NullPointerExceptions
        Objects.requireNonNull(transactions, "Transactions object cannot be null");

        logger.debug("Creating new transaction: {}", transactions);


        try {
            // Save the transactions object to the database using the transactionRepository
            transactionRepository.save(transactions);
            logger.info("Transaction created successfully: {}", transactions);
        } catch (Exception e) {
            // Log an error message to indicate that the transaction creation failed
            logger.error("Error creating transaction: {}", e.getMessage());
            // Throw a RuntimeException if the transaction creation fails
            throw new RuntimeException("Error creating transaction", e);
        }

    }


    /**
     * Retrieves the transaction history for a specific account and converts the transactions
     * to ResponseTransactionDto objects.
     *
     * @param accountNo  the account number for which to retrieve the transaction history
     * @param pageNumber the page number for pagination
     * @param pageSize   the number of transactions per page
     * @return a Page of ResponseTransactionDto objects representing the transaction history
     */
    public Page<ResponseTransactionDto> getTransactionHistory(String accountNo, int pageNumber, int pageSize) {


        // List to hold the response transaction DTOs
        List<ResponseTransactionDto> responseTransactionDtos = new ArrayList<>();

        Page<Transactions> transactions = null;


        try {
            // Retrieve transactions for the given account number from the repository
            logger.debug("Fetching transactions for account number: {}", accountNo);

            Account account = accountRepository.findByAccountNumber(accountNo);


            // Create a pageable object for pagination
            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            // Retrieve transactions for the given account number from the repository
            logger.debug("Fetching transactions for account number: {}", accountNo);
            transactions = transactionRepository.findTransactionsByAccountNumber(accountNo, pageable);

            // Convert each transaction to a ResponseTransactionDto and add to the list
            for (Transactions t : transactions) {
                ResponseTransactionDto responseTransactionDto = getResponseTransactionDto(t);
                responseTransactionDtos.add(responseTransactionDto);
            }

            logger.debug("Transaction history successfully retrieved for account number: {}", accountNo);

        } catch (Exception e) {
            // Log an error message if an exception occurs
            logger.error("Error retrieving transaction history for account number: {}", accountNo, e);
        }

        // Create a new Page object with the response transaction DTOs
        Page<ResponseTransactionDto> responseTransactionDtoPage = new PageImpl<>(responseTransactionDtos, PageRequest.of(pageNumber, pageSize), transactions.getTotalElements());


        return responseTransactionDtoPage;
    }


    /**
     * Creates a new ResponseTransactionDto object based on the given Transactions object.
     *
     * @param transactions the Transactions object to create the ResponseTransactionDto from
     * @return the created ResponseTransactionDto object
     */
    public ResponseTransactionDto getResponseTransactionDto(Transactions transactions) {
        ResponseTransactionDto responseTransactionDto = null;

        try {
            // Log a debug message to indicate that the method is being executed
            logger.debug("Creating ResponseTransactionDto object from Transactions object");

            // Create a new ResponseTransactionDto object
            responseTransactionDto = new ResponseTransactionDto();

            // Log a debug message to indicate that the ResponseTransactionDto object is being created
            logger.debug("Created ResponseTransactionDto object");

            // Set the transaction type property of the ResponseTransactionDto object
            responseTransactionDto.setTransactionType(transactions.getTransactionType());
            logger.debug("Set transaction type property to {}", transactions.getTransactionType());

            // Set the amount property of the ResponseTransactionDto object
            responseTransactionDto.setAmount(transactions.getAmount());
            logger.debug("Set amount property to {}", transactions.getAmount());

            // Set the date property of the ResponseTransactionDto object
            responseTransactionDto.setDate(transactions.getDate());
            logger.debug("Set date property to {}", transactions.getDate());

            // Set the account number property of the ResponseTransactionDto object
            responseTransactionDto.setAccountNo(transactions.getAccount().getAccountNumber());
            logger.debug("Set account number property to {}", transactions.getAccount().getAccountNumber());

        } catch (Exception e) {
            // Log an error message to indicate that an error occurred
            logger.error("Error creating ResponseTransactionDto: {}", e.getMessage());
            throw new RuntimeException("Error creating ResponseTransactionDto", e);
        }

        // Log a debug message to indicate that the ResponseTransactionDto object is being returned
        logger.debug("Returning ResponseTransactionDto object");

        // Return the created ResponseTransactionDto object, or null if an error occurred
        return responseTransactionDto;
    }


}

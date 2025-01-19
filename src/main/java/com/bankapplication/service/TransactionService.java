package com.bankapplication.service;

import com.bankapplication.dto.response.ResponseTransactionDto;
import com.bankapplication.model.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    public void createNewTransaction(Transactions transactions);

    public Page<ResponseTransactionDto> getTransactionHistory(String accountNo, int pageNumber, int pageSize);
}

package com.bankapplication.repository;

import com.bankapplication.model.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TransactionRepository extends JpaRepository<Transactions, Integer> {

    @Query("select t from Transactions t where t.account.accountNumber = :accountNumber")
    public Page<Transactions> findTransactionsByAccountNumber(@Param("accountNumber") String accountNumber, Pageable pageable);
}

package com.bankapplication.repository;

import com.bankapplication.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * this query is responsible for checking whether given acc no is exist or not
     *
     * @param accountno
     * @return
     */
    @Query("select case when count(a) > 0 then true else false end from Account a where a.accountNumber = :accountNumber ")
    public boolean existsByAccountNumber(@Param("accountNumber") String accountno);


    /**
     * this query is responsible for fetching the list of account with given id
     *
     * @param id
     * @return
     */
    @Query("select a from Account a where a.userDetails.id = :id AND a.accountStatus = true ")
    public List<Account> findByUserId(@Param("id") int id);

    /**
     * Retrieves an Account entity based on the account number.
     *
     * @param accountno the account number to search for
     * @return the Account entity with the specified account number
     */
    @Query("select a from Account a where a.accountNumber = :accountno ")
    public Account findByAccountNumber(@Param("accountno") String accountno);


    /**
     * Retrieves a list of Account entities based on their status.
     *
     * @param status the account status to search for (true for active, false for disabled)
     * @return a list of Account entities with the specified status
     */
    @Query("SELECT a FROM Account a WHERE a.accountStatus = :status")
    public List<Account> findAccountByStatusDisabled(@Param("status") Boolean status);


    /**
     * Retrieves a list of Account entities managed by a specific manager.
     *
     * @param managerId the ID of the manager
     * @return a list of Account entities managed by the specified manager
     */
    @Query("SELECT a FROM Account a JOIN a.branch b WHERE b.managerId.id = :managerId")
    List<Account> findAccountsByBranchIdAndManagerId(@Param("managerId") int managerId);


    /**
     * Retrieves a list of Accounts where the accounts are managed by a specific manager and have a disabled status.
     *
     * @param managerId the ID of the manager
     * @return a list of Account IDs with the specified manager and disabled status
     */
    @Query("SELECT a FROM Account a JOIN a.branch b WHERE b.managerId.id = :managerId AND a.accountStatus = false")
    List<Account> findAccountIdsByManagerIdAndStatus(@Param("managerId") int managerId);

}

package com.bankapplication.repository;

import com.bankapplication.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {

    @Query("SELECT a FROM AccountType a WHERE a.type = :type")
    public AccountType findByType(@Param("type") String type);
}

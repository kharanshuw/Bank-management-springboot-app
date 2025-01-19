package com.bankapplication.repository;

import com.bankapplication.dto.ManagerDto;
import com.bankapplication.model.Account;
import com.bankapplication.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT new com.bankapplication.dto.ManagerDto(ud.id, r.rolename, ud.fname, ud.lname) " +
            "FROM UserDetails ud " +
            "JOIN ud.users u " +
            "JOIN u.role r " +
            "WHERE r.rolename = 'ROLE_MANAGER'")
    public List<ManagerDto> findAllManagers();
    
    
    @Query("SELECT a FROM Account a JOIN a.branch b WHERE b.managerId.id = :managerid AND a.accountStatus = true")
    public List<Account> findAccountIdsByManagerIdAndStatus(@Param("managerid") int managerid);


}

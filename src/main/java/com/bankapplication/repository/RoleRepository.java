package com.bankapplication.repository;

import com.bankapplication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRolename(String rolename);

    @Query("SELECT r.rolename FROM Role r")
    List<String> findAllRoleName();

}

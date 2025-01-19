package com.bankapplication.repository;

import com.bankapplication.dto.LoggesInUserDetails;
import com.bankapplication.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {


    @Query("SELECT new com.bankapplication.dto.LoggesInUserDetails(u.id, u.email, ud.fname, ud.lname, ud.gender, ud.phoneno, r.rolename) " +
            "FROM Users u " +
            "INNER JOIN u.userDetails ud " +
            "JOIN u.role r " +
            "WHERE u.email = :email")
    public LoggesInUserDetails findUserRoleDetailsByEmail(@Param("email") String email);


    //    
//    @Query("SELECT new com.bankapplication.dto.ResponseDto(u.id, u.email, ud.fname, ud.lname, ud.gender, ud.phoneno, r.rolename) " +
//    	       "FROM User u " +
//    	       "JOIN u.userDetails ud " +
//    	       "JOIN u.role r")
//    	public List<ResponseDto> findAllUserDetails();
    @Query("SELECT u FROM Users u WHERE u.isActive = false ")
    public List<Users> findInactiveUsers();


    @Query("SELECT u FROM Users u WHERE u.email = :email")
    public Users findUserByEmail(@Param("email") String email);

    @Query("SELECT u.id FROM Users u WHERE u.email = :email")
    public int findIdByEmail(@Param("email") String email);
    
    @Modifying
    @Query(value = "delete from user_role ur where ur.user_id = :id" , nativeQuery = true)
     public void deleteUserRole(@Param("id") int id);
    
   
    
   


}

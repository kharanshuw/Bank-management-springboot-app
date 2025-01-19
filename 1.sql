CREATE DATABASE bankmanagement;

use bankmanagement;

DROP DATABASE bankmanagement;

SELECT * FROM role 

SELECT * from users

SELECT * from userdetails

SELECT * from user_role

SELECT * from role;

SELECT * from account_type

SELECT * from branch;
TRUNCATE TABLE branch;

SELECT * from state;

SELECT * from account_type;

SELECT * from district;

TRUNCATE TABLE district

SELECT * from City

TRUNCATE TABLE City

SELECT * from ACCOUNT;

SELECT * from ACCOUNT WHERE account_number = "2924964580999"

SELECT * from TRANSACTION;

SELECT * from ACCOUNT WHERE user_id = 1 and account_status = "true"

SELECT * from ACCOUNT WHERE user_id = 4 and account_status = 1

SELECT a from Account as a JOIN branch on Account.branch_id = branch.id WHERE branch.id = 1 and branch.manager_id=4

SELECT a.id from Account as a JOIN branch on a.branch_id = branch.id WHERE branch.manager_id=4 and a.account_status = 1

SELECT a.id from Account as a JOIN branch on a.branch_id = branch.id WHERE branch.manager_id=2 and a.account_status = "false"


INSERT INTO role(id,role_name) VALUES (1,'ROLE_ADMIN');
INSERT INTO role(id,role_name) VALUES (2,'ROLE_MANAGER');
INSERT INTO role(id,role_name) VALUES (3,'ROLE_USER');

INSERT INTO user_role(role_id,user_id) VALUES(1,1)

INSERT INTO account_type(id,type) VALUES(1,"SAVING ACCOUNT");
INSERT INTO account_type(id,type) VALUES(2,"CURRENT ACCOUNT");
INSERT INTO account_type(id,type) VALUES(3,"RECURRING DEPOSIT ACCOUNT");
INSERT INTO account_type(id,type) VALUES(4,"FIXED DEPOSIT ACCOUNT");

DROP TABLE branch;
DROP TABLE ACCOUNT;
DROP TABLE account_type;
DROP TABLE district;

UPDATE users
SET enable = 1 
WHERE id = 1;

UPDATE district
set state_id=15
WHERE state_id = 14


delete from users where id = 1

delete from user_role where user_id = 3

delete from userdetails where id = 1

ALTER TABLE branch 
MODIFY COLUMN contact_no VARCHAR(10) NOT NULL,
ADD UNIQUE (contact_no);

ALTER TABLE ACCOUNT
ADD INDEX (id)

ALTER TABLE account_type
ADD INDEX (id)

ALTER TABLE TRANSACTION DROP COLUMN created_at;

ALTER TABLE userdetails DROP COLUMN create_by;

ALTER TABLE userdetails DROP COLUMN lastmodified_by;



@Query("SELECT new com.bankapplication.dto.LoggesInUserDetails(u.id, u.email, ud.fname, ud.lname, ud.gender, ud.phoneno, r.rolename) " +
"FROM User u " + "INNER JOIN u.userDetails ud " +"JOIN u.role r " +"WHERE u.email = :email")
			
			
@Query("SELECT new com.bankapplication.dto.ResponseDto(u.id, u.email, ud.fname, ud.lname, ud.gender, ud.phoneno, r.rolename) " +
"FROM User u " +
"JOIN u.userDetails ud " +
"JOIN u.role r")
	   
	   
@Query("SELECT new com.bankapplication.dto.ResponseDto(u.id, u.email, ud.fname, ud.lname, ud.gender, ud.phoneno, r.rolename) " +
"FROM User u " +
"JOIN u.userDetails ud " +
"JOIN u.role r")

@Query("SELECT a FROM AccountType a WHERE a.type = :type")

public interface AccountRepository extends JpaRepository<Account, Integer> {
    
    @Query("SELECT a FROM Account a WHERE a.userDetails.id = :userId AND a.accountStatus = true")
    List<Account> findAccountsByUserIdAndStatus(@Param("userId") int userId);
}

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT a FROM Account a JOIN a.branch b WHERE b.id = :branchId AND b.managerId.id = :managerId")
    List<Account> findAccountsByBranchIdAndManagerId(@Param("branchId") int branchId, @Param("managerId") int managerId);
}



public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT a.id FROM Account a JOIN a.branch b WHERE b.managerId.id = :managerId AND a.accountStatus = false")
    List<Integer> findAccountIdsByManagerIdAndStatus(@Param("managerId") int managerId);
}



//endpoint to call location for testing
http://localhost:9191/api/countries

http://localhost:9191/api/states/1

http://localhost:9191/api/city/1

http://localhost:9191/api/district/1

http://localhost:9191/user/update

http://localhost:9191/


INSERT INTO country(country_name) VALUE('INDIA');

INSERT INTO state(state_name,country_id) VALUE()

INSERT INTO state (state_name, country_id,state_code ) VALUES ('Andhra Pradesh', 1,37);
INSERT INTO state (state_name, country_id,state_code ) VALUES ('Arunachal Pradesh', 1,12);
INSERT INTO state (state_name, country_id, state_code) VALUES ('Assam', 1,18);
INSERT INTO state (state_name, country_id,state_code ) VALUES ('Bihar', 1,10);
INSERT INTO state (state_name, country_id, state_code) VALUES ('Chhattisgarh', 1,22);
INSERT INTO state (state_name, country_id, state_code) VALUES ('Goa', 1,30);
INSERT INTO state (state_name, country_id,state_code) VALUES ('Gujarat', 1,24);
INSERT INTO state (state_name, country_id ,state_code) VALUES ('Haryana', 1,6);
INSERT INTO state (state_name, country_id , state_code) VALUES ('Himachal Pradesh', 1,2);
INSERT INTO state (state_name, country_id ,state_code ) VALUES ('Jharkhand', 1,20);
INSERT INTO state (state_name, country_id ,state_code ) VALUES ('Karnataka', 1,29);
INSERT INTO state (state_name, country_id , state_code) VALUES ('Kerala', 1,32);
INSERT INTO state (state_name, country_id ,state_code ) VALUES ('Madhya Pradesh', 1,23);
INSERT INTO state (state_name, country_id,state_code ) VALUES ('Maharashtra', 1,27);
INSERT INTO state (state_name, country_id , state_code) VALUES ('Manipur', 1,14);
INSERT INTO state (state_name, country_id, state_code) VALUES ('Meghalaya', 1,17);
INSERT INTO state (state_name, country_id ,state_code ) VALUES ('Mizoram', 1,15);
INSERT INTO state (state_name, country_id ,state_code ) VALUES ('Nagaland', 1,13);
INSERT INTO state (state_name, country_id , state_code) VALUES ('Odisha', 1,21);
INSERT INTO state (state_name, country_id,state_code ) VALUES ('Punjab', 1,3);
INSERT INTO state (state_name, country_id,state_code ) VALUES ('Rajasthan', 1,8);
INSERT INTO state (state_name, country_id,state_code ) VALUES ('Sikkim', 1,11);
INSERT INTO state (state_name, country_id, state_code) VALUES ('Tamil Nadu', 1,33);
INSERT INTO state (state_name, country_id,state_code ) VALUES ('Telangana', 1,36);
INSERT INTO state (state_name, country_id , state_code) VALUES ('Tripura', 1,16);
INSERT INTO state (state_name, country_id,state_code) VALUES ('Uttar Pradesh', 1,9);
INSERT INTO state (state_name, country_id,state_code ) VALUES ('Uttarakhand', 1,05);
INSERT INTO state (state_name, country_id,state_code ) VALUES ('West Bengal', 1,19);
INSERT INTO state (state_name, country_id , state_code) VALUES ('Jammu & Kashmir', 1,1);
INSERT INTO state (state_name, country_id,state_code) VALUES ('Delhi', 1,07);
INSERT INTO state (state_name, country_id,state_code) VALUES ('Chandigarh', 1,04);
INSERT INTO state (state_name, country_id,state_code) VALUES ('Lakshadweep', 1,31);
INSERT INTO state (state_name, country_id,state_code) VALUES ('Puducherry', 1,34);
INSERT INTO state (state_name, country_id,state_code) VALUES ('Andaman & Nicobar Islands', 1,35);
INSERT INTO state (state_name, country_id,state_code) VALUES ('Ladakh', 1,38);


SELECT * from district;

INSERT INTO district (district_name, state_id) VALUES ('Ahmednagar', 14);
INSERT INTO district (district_name, state_id) VALUES ('Akola', 14);
INSERT INTO district (district_name, state_id) VALUES ('Amravati', 14);
INSERT INTO district (district_name, state_id) VALUES ('Aurangabad', 14);
INSERT INTO district (district_name, state_id) VALUES ('Beed', 14);
INSERT INTO district (district_name, state_id) VALUES ('Bhandara', 14);
INSERT INTO district (district_name, state_id) VALUES ('Buldhana', 14);
INSERT INTO district (district_name, state_id) VALUES ('Chandrapur', 14);
INSERT INTO district (district_name, state_id) VALUES ('Dhule', 14);
INSERT INTO district (district_name, state_id) VALUES ('Gadchiroli', 14);
INSERT INTO district (district_name, state_id) VALUES ('Gondia', 14);
INSERT INTO district (district_name, state_id) VALUES ('Hingoli', 14);
INSERT INTO district (district_name, state_id) VALUES ('Jalgaon', 14);
INSERT INTO district (district_name, state_id) VALUES ('Jalna', 14);
INSERT INTO district (district_name, state_id) VALUES ('Kolhapur', 14);
INSERT INTO district (district_name, state_id) VALUES ('Latur', 14);
INSERT INTO district (district_name, state_id) VALUES ('Mumbai City', 14);
INSERT INTO district (district_name, state_id) VALUES ('Mumbai Suburban', 14);
INSERT INTO district (district_name, state_id) VALUES ('Nanded', 14);
INSERT INTO district (district_name, state_id) VALUES ('Nandurbar', 14);
INSERT INTO district (district_name, state_id) VALUES ('Nagpur', 14);
INSERT INTO district (district_name, state_id) VALUES ('Nashik', 14);
INSERT INTO district (district_name, state_id) VALUES ('Osmanabad', 14);
INSERT INTO district (district_name, state_id) VALUES ('Palghar', 14);
INSERT INTO district (district_name, state_id) VALUES ('Parbhani', 14);
INSERT INTO district (district_name, state_id) VALUES ('Pune', 14);
INSERT INTO district (district_name, state_id) VALUES ('Raigad', 14);
INSERT INTO district (district_name, state_id) VALUES ('Ratnagiri', 14);
INSERT INTO district (district_name, state_id) VALUES ('Sangli', 14);
INSERT INTO district (district_name, state_id) VALUES ('Satara', 14);
INSERT INTO district (district_name, state_id) VALUES ('Sindhudurg', 14);
INSERT INTO district (district_name, state_id) VALUES ('Solapur', 14);
INSERT INTO district (district_name, state_id) VALUES ('Thane', 14);
INSERT INTO district (district_name, state_id) VALUES ('Wardha', 14);
INSERT INTO district (district_name, state_id) VALUES ('Washim', 14);
INSERT INTO district (district_name, state_id) VALUES ('Yavatmal', 14);


SELECT * from City;

INSERT INTO City (city_name, district_id) VALUES ('Nagpur', 21);
INSERT INTO City (city_name, district_id) VALUES ('Ramtek', 21);
INSERT INTO City (city_name, district_id) VALUES ('Umred', 21);
INSERT INTO City (city_name, district_id) VALUES ('Kalameshwar', 21);
INSERT INTO City (city_name, district_id) VALUES ('Katol', 21);
INSERT INTO City (city_name, district_id) VALUES ('Kamptee', 21);
INSERT INTO City (city_name, district_id) VALUES ('Kuhi', 21);
INSERT INTO City (city_name, district_id) VALUES ('Narkhed', 21);
INSERT INTO City (city_name, district_id) VALUES ('Bhiwapur', 21);
INSERT INTO City (city_name, district_id) VALUES ('Mouda', 21);
INSERT INTO City (city_name, district_id) VALUES ('Savner', 21);
INSERT INTO City (city_name, district_id) VALUES ('Hingna', 21);
INSERT INTO City (city_name, district_id) VALUES ('Parseoni', 21);
INSERT INTO City (city_name, district_id) VALUES ('Mandhal', 21);
INSERT INTO City (city_name, district_id) VALUES ('Sonegaon', 21);
INSERT INTO City (city_name, district_id) VALUES ('Tekadi', 21);
INSERT INTO City (city_name, district_id) VALUES ('Wadi', 21);
INSERT INTO City (city_name, district_id) VALUES ('Walani', 21);
INSERT INTO City (city_name, district_id) VALUES ('Yerkheda', 21);


EACH district is assigned with Census District Codes


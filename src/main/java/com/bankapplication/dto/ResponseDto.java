package com.bankapplication.dto;

import com.bankapplication.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Set;

public class ResponseDto {

    private static final Logger logger = LoggerFactory.getLogger(ResponseDto.class);

    private int id;
    private String email;
    private String fname;
    private String lname;
    private String gender;
    private String phoneno;
    private Set<Role> roles;

    private String citynameString;

    private String districtnameString;

    private String countryString;

    private String stateString;

    public ResponseDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ResponseDto(int id, String email, String fname, String lname, String gender, String phoneno,
                       Set<Role> roles) {
        super();
        this.id = id;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.phoneno = phoneno;
        this.roles = roles;
    }

    public static Logger getLogger() {
        return logger;
    }

    public String getCitynameString() {
        return citynameString;
    }

    public void setCitynameString(String citynameString) {
        this.citynameString = citynameString;
    }

    public String getDistrictnameString() {
        return districtnameString;
    }

    public void setDistrictnameString(String districtnameString) {
        this.districtnameString = districtnameString;
    }

    public String getCountryString() {
        return countryString;
    }

    public void setCountryString(String countryString) {
        this.countryString = countryString;
    }

    public String getStateString() {
        return stateString;
    }

    public void setStateString(String stateString) {
        this.stateString = stateString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "ResponseDto [id=" + id + ", email=" + email + ", fname=" + fname + ", lname=" + lname + ", gender="
                + gender + ", phoneno=" + phoneno + ", rolenameString=" + roles + "]";
    }

    public ArrayList<String> getrolestring() {
        logger.info("getrolestring from responsedto called succesfully");

        ArrayList<String> arrayList = new ArrayList<>();
        for (Role r : roles) {
            System.out.println(r.toString());
            arrayList.add(r.getRolename());
        }
        return arrayList;
    }

}

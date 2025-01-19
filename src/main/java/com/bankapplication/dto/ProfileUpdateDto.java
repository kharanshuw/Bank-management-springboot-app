package com.bankapplication.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProfileUpdateDto {

    private int id;


    @Column(nullable = false, unique = true)
    @Size(min = 1, max = 128, message = "email length must be between 0 to 128")
    @NotEmpty(message = "email should not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Invalid email address")
    private String email;

    @Column(nullable = false)
    @Size(min = 1, max = 128, message = "first name length must be between 0 to 128")
    @NotEmpty(message = "Last name should not be empty")
    private String fname;

    @Column(nullable = false)
    @Size(min = 1, max = 128, message = "last name length must be between 0 to 128")
    @NotEmpty(message = "Last name should not be empty")
    private String lname;

    private String gender;

    @Column(name = "phoneno", unique = true)
    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneno;

    private Integer cityid;


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

    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }


    public String toString() {
        return "ProfileUpdateDto{" +
                "email='" + email + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneno='" + phoneno + '\'' +
                ", cityid=" + cityid +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

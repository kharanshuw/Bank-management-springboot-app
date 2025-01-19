package com.bankapplication.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public class RequstBranchDto {

    @NotNull(message = "branch name should not be empty")
    @Column(name = "branch_name", unique = true, nullable = false)
    @NotEmpty(message = "branch name should not be empty")
    @Size(min = 1, max = 30, message = "branch name must be between 2 to 50 characters")
    @Pattern(regexp = "^[A-Z][a-zA-Z]+$", message = "invalid branch name format")
    private String branchName;

    @NotNull(message = "contact number should not be empty")
    @Size(min = 10, max = 10, message = "contact number must be 10 digits")
    @Pattern(regexp = "^\\d{10}$", message = "invalid phone number format")
    @Column(name = "contact_no")
    private String contactNo;

    private String cityid;

    @Email(message = "Invalid email address")
    @NotNull(message = "email should not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "invalid email format")
    private String email;


    public @NotNull(message = "branch name should not be empty") @NotEmpty(message = "branch name should not be empty") @Size(min = 2, max = 50, message = "branch name must be between 2 to 50 characters") String getBranchName() {
        return branchName;
    }

    public void setBranchName(@NotNull(message = "branch name should not be empty") @NotEmpty(message = "branch name should not be empty") @Size(min = 2, max = 50, message = "branch name must be between 2 to 50 characters") String branchName) {
        this.branchName = branchName;
    }

    public @NotNull(message = "contact number should not be empty") @Size(min = 10, max = 10, message = "contact number must be 10 digits") @Pattern(regexp = "^\\d{10}$", message = "invalid phone number format") String getContactNo() {
        return contactNo;
    }

    public void setContactNo(@NotNull(message = "contact number should not be empty") @Size(min = 10, max = 10, message = "contact number must be 10 digits") @Pattern(regexp = "^\\d{10}$", message = "invalid phone number format") String contactNo) {
        this.contactNo = contactNo;
    }


    public @Email(message = "Invalid email address") @NotNull(message = "email should not be empty") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid email address") @NotNull(message = "email should not be empty") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "invalid email format") String email) {
        this.email = email;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    @Override
    public String toString() {
        return "RequstBranchDto{" +
                ", branchName='" + branchName + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", cityid='" + cityid + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}

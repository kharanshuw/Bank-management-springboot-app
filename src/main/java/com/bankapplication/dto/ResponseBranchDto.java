package com.bankapplication.dto;

import jakarta.validation.constraints.*;

public class ResponseBranchDto {

    private int id;


    private String branchName;


    private String contactNo;

    private String cityName;


    private String email;

    private String managerId;
    
    public String managerName;
    
    private String ifsc;
    
    private String establishDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "ResponseBranchDto{" +
                "id=" + id +
                ", branchName='" + branchName + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", cityName='" + cityName + '\'' +
                ", email='" + email + '\'' +
                ", managerId='" + managerId + '\'' +
                '}';
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }
}

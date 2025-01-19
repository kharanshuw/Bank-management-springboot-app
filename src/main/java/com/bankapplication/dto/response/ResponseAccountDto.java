package com.bankapplication.dto.response;

import jakarta.validation.constraints.Digits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class ResponseAccountDto {

    private static final Logger log = LoggerFactory.getLogger(ResponseAccountDto.class);
    public String accountType;

    private String accountNo;


    @Digits(integer = 10, fraction = 2, message = "only digit allows for balance")
    private BigDecimal balance;

    private String branch;

    private String fname;

    private String lname;

    private String ifscCode;

    private boolean status;

    public ResponseAccountDto(String accountNo, BigDecimal balance, String accountType, String branch, String fname, String lname, String ifscCode) {
        this.accountNo = accountNo;
        this.balance = balance;
        this.accountType = accountType;
        this.branch = branch;
        this.fname = fname;
        this.lname = lname;
        this.ifscCode = ifscCode;
    }

    public ResponseAccountDto() {

    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }


    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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


    public boolean isStatus() {
        return status;
    }
    
    public boolean getStatus()
    {
        return  status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String userName() {
        String name = fname + " " + lname;
        return name;
    }


    @Override
    public String toString() {
        return "ResponseAccountDto{" +
                "accountNo='" + accountNo + '\'' +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                ", branch='" + branch + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                '}';
    }
}

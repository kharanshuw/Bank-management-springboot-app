package com.bankapplication.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Entity
@Table(name = "account")
public class Account {
    @ManyToOne
    @JoinColumn(name = "account_type_id")
    public AccountType accountType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private UserDetails userDetails;


    @Column(name = "account_number")
    private String accountNumber;


    @Column(name = "balance")
    @NotNull
    @Min(value = 0, message = "Balance should be positive")
    @Digits(integer = 10, fraction = 2, message = "only digit allows for balance")
    private BigDecimal balance;


    @Column(nullable = false, name = "ifsc_code", unique = false)
    private String ifscCode;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    private Boolean accountStatus = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Boolean getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Boolean accountStatus) {
        this.accountStatus = accountStatus;
    }


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userDetails=" + userDetails +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", accountType=" + accountType +
                ", ifscCode='" + ifscCode + '\'' +
                ", branch=" + branch +
                ", accountStatus=" + accountStatus +
                '}';
    }
}

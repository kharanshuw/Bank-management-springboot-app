package com.bankapplication.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class RequestAccountDto {
    @NotNull(message = "account type should not be empty")
    @NotEmpty(message = "Account type should not be empty")
    @Size(min = 3, max = 40, message = "Account type should be between 3 and 20 characters")
    private String accountType;


    @NotNull(message = "branch id should not be empty")
    @Min(value = 1, message = "Branch id should be a positive integer")
    private int branchId;

    @Column(name = "balance")
    @Min(value = 0, message = "Balance should be positive")
    @NotNull(message = "initial balance should not be empty")
    private BigDecimal initialBalance;


    @Min(value = 1, message = "User id should be a positive integer")
    @NotNull(message = "user id should not be empty")
    private int userId;


    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }


    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RequestAccountDto{" +
                "accountType='" + accountType + '\'' +
                ", branchId=" + branchId +
                ", initialBalance=" + initialBalance +
                ", userId=" + userId +
                '}';
    }
}

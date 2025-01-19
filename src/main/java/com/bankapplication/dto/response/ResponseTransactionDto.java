package com.bankapplication.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ResponseTransactionDto {

    public LocalDate date;

    private BigDecimal amount;


    private String transactionType;

    private String accountNo;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @Override
    public String toString() {
        return "ResponseTransactionDto{" +
                "date=" + date +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                ", accountNo='" + accountNo + '\'' +
                '}';
    }
}

package com.bankapplication.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "transaction")
@Entity
public class Transactions {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "transaction_date", nullable = false)
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @Temporal(TemporalType.DATE)
    private LocalDate date = LocalDate.now();

    @Column(name = "amount", nullable = false)
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than or equal to 0.01")
    @Digits(integer = 10, fraction = 2, message = "Amount must have a maximum of 10 digits and 2 decimal places")
    private BigDecimal amount;


    @Column(name = "transaction_type", nullable = false)
    @NotNull(message = "Transaction type is required")
    @NotEmpty(message = "Transaction type cannot be empty")
    @Size(min = 1, max = 50, message = "Transaction type must be between 1 and 50 characters")
    private String transactionType;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

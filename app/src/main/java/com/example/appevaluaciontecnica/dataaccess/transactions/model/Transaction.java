package com.example.appevaluaciontecnica.dataaccess.transactions.model;

import com.example.appevaluaciontecnica.dataaccess.account.model.Account;

import java.util.List;

public class Transaction {
    private long id;


    private Account account;
    private TransactionType transaction_type;
    private String currency;
    private String operated_at;
    private String authorized_at;
    private TransactionStatus status;
    private String notes;
    private List<TransactionDetail> details;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(TransactionType transactionType) {
        this.transaction_type = transactionType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOperated_at() {
        return operated_at;
    }

    public void setOperated_at(String operated_at) {
        this.operated_at = operated_at;
    }

    public String getAuthorized_at() {
        return authorized_at;
    }

    public void setAuthorized_at(String authorized_at) {
        this.authorized_at = authorized_at;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<TransactionDetail> getDetails() {
        return details;
    }

    public void setDetails(List<TransactionDetail> details) {
        this.details = details;
    }
}

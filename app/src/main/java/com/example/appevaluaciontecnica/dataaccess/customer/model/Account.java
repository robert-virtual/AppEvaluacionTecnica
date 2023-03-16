package com.example.appevaluaciontecnica.dataaccess.customer.model;

public class Account {
    public Account(AccountType accountType) {
        this.accountType = accountType;
    }

    private long account_number;
    private String held_balance;
    private AccountType accountType;
    private AccountStatus accountStatus;

    private String available_balance;

    public long getAccount_number() {
        return account_number;
    }

    public void setAccount_number(long account_number) {
        this.account_number = account_number;
    }

    public String getHeld_balance() {
        return held_balance;
    }

    public void setHeld_balance(String held_balance) {
        this.held_balance = held_balance;
    }

    public String getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(String available_balance) {
        this.available_balance = available_balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}

package com.example.appevaluaciontecnica.dataaccess.transactions.model;

public class TransactionDetail {
    private long id;
    private long transaction_id;
    private String account_holder;
    private String target_account;
    private String amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getAccount_holder() {
        return account_holder;
    }

    public void setAccount_holder(String account_holder) {
        this.account_holder = account_holder;
    }

    public String getTarget_account() {
        return target_account;
    }

    public void setTarget_account(String target_account) {
        this.target_account = target_account;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}

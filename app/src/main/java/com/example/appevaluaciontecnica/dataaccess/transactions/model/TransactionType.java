package com.example.appevaluaciontecnica.dataaccess.transactions.model;

public class TransactionType {
    public TransactionType(long id) {
        this.id = id;
    }

    private long id;
    private String name;
    private String description;
    private boolean status;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}

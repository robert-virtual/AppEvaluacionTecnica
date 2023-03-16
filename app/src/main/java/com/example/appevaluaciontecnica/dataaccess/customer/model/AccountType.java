package com.example.appevaluaciontecnica.dataaccess.customer.model;

public class AccountType {
    private long id;

    public AccountType(long id) {
        this.id = id;
    }

    private String name;

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
}

package com.example.appevaluaciontecnica.auth.model;


import java.time.LocalDateTime;
import java.util.List;

public class User {
    private List<Role> roles;
    private String customerId;
    private LocalDateTime last_login;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getLast_login() {
        return last_login;
    }

    public void setLast_login(LocalDateTime last_login) {
        this.last_login = last_login;
    }
}

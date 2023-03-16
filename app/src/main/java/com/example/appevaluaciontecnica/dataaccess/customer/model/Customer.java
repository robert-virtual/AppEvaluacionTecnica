package com.example.appevaluaciontecnica.dataaccess.customer.model;


import com.example.appevaluaciontecnica.dataaccess.account.model.Account;
import com.example.appevaluaciontecnica.dataaccess.auth.model.User;

import java.util.List;

public class Customer {

    public Customer(
            String id,
            String name,
            String lastname,
            String contact_name,
            String birthdate,
            String phone,
            String email,
            String address_1,
            String address_2,
            boolean is_company,
            List<Account>  accounts
    ) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.contact_name = contact_name;
        this.phone = phone;
        this.email = email;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.is_company = is_company;
        this.accounts = accounts;
    }


    private String id;
    private String name;
    private String contact_name;
    private String lastname;
    private List<Account> accounts;
    private String birthdate;
    private String phone;
    private String email;
    private String address_1;
    private String address_2;
    private String status;
    private boolean is_company;
    private List<Customer> employees;
    private List<User> users;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIs_company() {
        return is_company;
    }

    public void setIs_company(boolean is_company) {
        this.is_company = is_company;
    }

    public List<Customer> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Customer> employees) {
        this.employees = employees;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }
}

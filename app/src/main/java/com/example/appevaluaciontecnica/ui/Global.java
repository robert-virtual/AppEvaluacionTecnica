package com.example.appevaluaciontecnica.ui;

import com.example.appevaluaciontecnica.dataaccess.account.model.Account;
import com.example.appevaluaciontecnica.dataaccess.auth.model.User;
import com.example.appevaluaciontecnica.dataaccess.customer.model.Customer;
import com.example.appevaluaciontecnica.dataaccess.transactions.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Global {
    public static List<Account> accounts = new ArrayList<>();
    public static Customer customer;
    public static User user;
    public static List<Transaction> transactions = new ArrayList<>();
    public static Transaction lastTransaction;
}

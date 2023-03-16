package com.example.appevaluaciontecnica.dataaccess.transactions;

import com.example.appevaluaciontecnica.dataaccess.BasicResponse;
import com.example.appevaluaciontecnica.dataaccess.account.model.Account;
import com.example.appevaluaciontecnica.dataaccess.customer.model.Customer;
import com.example.appevaluaciontecnica.dataaccess.transactions.model.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TransactionService {
    @POST("/transaction/create")
    Call<BasicResponse<Customer>> createTransaction(@Header("Authorization") String authorization, @Body Transaction transaction);

    @GET("/transaction/all/{account}")
    Call<BasicResponse<List<Transaction>>> allTransactions(@Header("Authorization") String authorization, @Path("account") long path);
}

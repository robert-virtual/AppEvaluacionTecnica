package com.example.appevaluaciontecnica.dataaccess.account;

import com.example.appevaluaciontecnica.dataaccess.BasicResponse;
import com.example.appevaluaciontecnica.dataaccess.account.model.Account;
import com.example.appevaluaciontecnica.dataaccess.customer.model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountService {
    @POST("/account/create")
    Call<BasicResponse<Customer>> createAccount(@Header("Authorization") String authorization,@Body Account account);
    @GET("/customer/account")
    Call<BasicResponse<List<Account>>> allAccounts(@Header("Authorization") String authorization);
    @GET("/account/{account}")
    Call<BasicResponse<Account>> getAccount(@Header("Authorization") String authorization, @Path("account") long account);
}

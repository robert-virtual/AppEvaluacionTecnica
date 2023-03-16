package com.example.appevaluaciontecnica.dataaccess.customer;

import com.example.appevaluaciontecnica.dataaccess.BasicResponse;
import com.example.appevaluaciontecnica.dataaccess.customer.model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CustomerService {
    @POST("/customer/create")
    Call<BasicResponse<Customer>> createCustomer(@Header("Authorization") String authorization,@Body Customer customer);
    @GET("/customer/all")
    Call<BasicResponse<List<Customer>>> allCustomers(@Header("Authorization") String authorization);
    @GET("/customer/{dni}")
    Call<BasicResponse<Customer>> getCustomer(@Header("Authorization") String authorization, @Path("dni") String  dni);
}

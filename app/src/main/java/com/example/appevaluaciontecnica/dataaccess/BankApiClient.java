package com.example.appevaluaciontecnica.dataaccess;

import com.example.appevaluaciontecnica.dataaccess.auth.AuthService;
import com.example.appevaluaciontecnica.dataaccess.customer.CustomerService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BankApiClient {

    //            .client(new OkHttpClient.Builder().addInterceptor(Intercepto())
    // http://ec2-18-218-93-62.us-east-2.compute.amazonaws.com
    //https://fragrant-firefly-2123.fly.dev
   // http://192.168.0.9:8080
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://ec2-18-218-93-62.us-east-2.compute.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static AuthService getAuthService() {
        return retrofit.create(AuthService.class);
    }

    public static CustomerService getCustomerService() {
        return retrofit.create(CustomerService.class);
    }
}

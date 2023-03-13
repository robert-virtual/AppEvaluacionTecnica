package com.example.appevaluaciontecnica;

import com.example.appevaluaciontecnica.auth.AuthService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BankApiClient {
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://fragrant-firefly-2123.fly.dev")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    static AuthService getAuthService() {
        return retrofit.create(AuthService.class);
    }
}

package com.example.appevaluaciontecnica.auth;

import com.example.appevaluaciontecnica.BasicResponse;
import com.example.appevaluaciontecnica.auth.model.LoginRequest;
import com.example.appevaluaciontecnica.auth.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AuthService {
    String BANK_API_BASE_URL = "https://fragrant-firefly-2123.fly.dev";
    @POST("/auth/login")
    Call<BasicResponse<LoginResponse>> login(@Body LoginRequest body);

    @POST("/auth/register")
    Call<BasicResponse<LoginResponse>> register(@Body LoginRequest body);

    @POST("/auth/password")
    Call<BasicResponse<LoginResponse>> forgotPassword(@Body LoginRequest body);

    @PUT("/auth/password")
    Call<BasicResponse<LoginResponse>> updatePassword(@Body LoginRequest body);
}

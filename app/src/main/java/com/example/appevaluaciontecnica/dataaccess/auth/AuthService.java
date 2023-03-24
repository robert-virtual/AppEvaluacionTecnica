package com.example.appevaluaciontecnica.dataaccess.auth;

import com.example.appevaluaciontecnica.dataaccess.auth.model.ForgotPasswordRequest;
import com.example.appevaluaciontecnica.dataaccess.auth.model.User;
import com.example.appevaluaciontecnica.dataaccess.BasicResponse;
import com.example.appevaluaciontecnica.dataaccess.auth.model.LoginRequest;
import com.example.appevaluaciontecnica.dataaccess.auth.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AuthService {
    @GET("/auth/info")
    Call<BasicResponse<User>> userInfo(@Header("Authorization") String authorization);
    @POST("/auth/login")
    Call<BasicResponse<LoginResponse>> login(@Body LoginRequest body);

    @POST("/auth/register")
    Call<BasicResponse<LoginResponse>> register(@Body LoginRequest body);

    @POST("/auth/password")
    Call<BasicResponse<String>> forgotPassword(@Body ForgotPasswordRequest body);

    @PUT("/auth/password")
    Call<BasicResponse<String>> updatePassword(@Body LoginRequest body);
}

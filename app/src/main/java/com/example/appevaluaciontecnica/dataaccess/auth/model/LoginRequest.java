package com.example.appevaluaciontecnica.dataaccess.auth.model;



public class LoginRequest {
    private String email;
    private String password;
    private String otp;

    public LoginRequest(String email, String password, String otp) {
        this.email = email;
        this.password = password;
        this.otp = otp;
    }

    public LoginRequest(String email_, String password_) {
        email = email_;
        password = password_;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

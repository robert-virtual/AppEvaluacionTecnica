package com.example.appevaluaciontecnica.dataaccess.auth.model;

public class ForgotPasswordRequest {
    public ForgotPasswordRequest(String email) {
        this.email = email;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

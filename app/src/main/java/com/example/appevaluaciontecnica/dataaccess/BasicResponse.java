package com.example.appevaluaciontecnica.dataaccess;


import androidx.annotation.Nullable;

public class BasicResponse<T> {
    @Nullable
    private T data;
    private int data_count;
    private String error;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getData_count() {
        return data_count;
    }

    public void setData_count(int data_count) {
        this.data_count = data_count;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

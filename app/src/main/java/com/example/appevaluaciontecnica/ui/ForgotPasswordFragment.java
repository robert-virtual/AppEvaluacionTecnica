package com.example.appevaluaciontecnica.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appevaluaciontecnica.R;
import com.example.appevaluaciontecnica.dataaccess.BankApiClient;
import com.example.appevaluaciontecnica.dataaccess.BasicResponse;
import com.example.appevaluaciontecnica.dataaccess.auth.model.ForgotPasswordRequest;
import com.example.appevaluaciontecnica.dataaccess.auth.model.LoginRequest;
import com.example.appevaluaciontecnica.dataaccess.auth.model.LoginResponse;
import com.example.appevaluaciontecnica.databinding.FragmentForgotPasswordBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSendOtp.setOnClickListener(view1 -> {
            BankApiClient.getAuthService().forgotPassword(new ForgotPasswordRequest(Objects.requireNonNull(binding.email.getText()).toString())).enqueue(new Callback<BasicResponse<String>>() {
                @Override
                public void onResponse(Call<BasicResponse<String>> call, Response<BasicResponse<String>> response) {
                    if (response.body() != null && response.body().getData() != null) {

                        binding.title.setText("A Otp was sent to: " + binding.email.getText() + ".\nType the OTP in the field and provide a new password");
                        // hide
                        binding.emailTitle.setVisibility(View.GONE);
                        binding.btnSendOtp.setVisibility(View.GONE);
                        // show
                        binding.btnUpdatePassword.setVisibility(View.VISIBLE);
                        binding.passwordTitle.setVisibility(View.VISIBLE);
                        binding.confirmPasswordTitle.setVisibility(View.VISIBLE);
                        binding.otpTitle.setVisibility(View.VISIBLE);
                        return;
                    }
                    Snackbar.make(view,"Ups there was a problem sending the otp", Snackbar.LENGTH_LONG).show();

                }
                @Override
                public void onFailure(Call<BasicResponse<String>> call, Throwable t) {
                    Snackbar.make(view,"Ups there was a problem sending the otp", Snackbar.LENGTH_LONG).show();
                }
            });
        });
        binding.btnUpdatePassword.setOnClickListener(view1 -> {
                BankApiClient.getAuthService().updatePassword(
                        new LoginRequest(
                                binding.email.getText().toString(),
                                binding.password.getText().toString(),
                                binding.otp.getText().toString()
                        )
                ).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<BasicResponse<String>> call, Response<BasicResponse<String>> response) {
                            if (response.body() != null && response.body().getData() != null){
                                Snackbar.make(view,"Password updated successfully",Snackbar.LENGTH_LONG).show();
                                Navigation.findNavController(view).popBackStack();
                                return;
                            }
                            if (response.body() != null && response.body().getError() != null){
                                Snackbar.make(view,response.body().getError(),Snackbar.LENGTH_LONG).show();
                                return;
                            }
                            Snackbar.make(view,"Ups there was a problem updating your password",Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<BasicResponse<String>> call, Throwable t) {
                        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_LONG).show();
                    }
                });
        });
    }
}
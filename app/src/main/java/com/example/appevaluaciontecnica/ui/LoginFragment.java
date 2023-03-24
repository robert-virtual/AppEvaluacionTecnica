package com.example.appevaluaciontecnica.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.appevaluaciontecnica.dataaccess.auth.model.User;
import com.example.appevaluaciontecnica.dataaccess.BankApiClient;
import com.example.appevaluaciontecnica.dataaccess.BasicResponse;
import com.example.appevaluaciontecnica.R;
import com.example.appevaluaciontecnica.dataaccess.auth.AuthService;
import com.example.appevaluaciontecnica.dataaccess.auth.model.LoginRequest;
import com.example.appevaluaciontecnica.dataaccess.auth.model.LoginResponse;
import com.example.appevaluaciontecnica.databinding.FragmentLoginBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private AuthService authService = BankApiClient.getAuthService();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        authService.userInfo(
                "Bearer " + Objects.requireNonNull(getActivity()).
                        getPreferences(Context.MODE_PRIVATE)
                        .getString("token", "")
        ).enqueue(new Callback<BasicResponse<User>>() {
            @Override
            public void onResponse(Call<BasicResponse<User>> call, Response<BasicResponse<User>> response) {
                User user = response.body() != null ? response.body().getData() : null;
                if (user == null) {
                    return;
                }
                if (user.getRoles().stream().anyMatch(role -> Objects.equals(role.getName(), "customer_creator"))) {
                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.action_LoginFragment_to_customersFragment);
                    return;
                }
                // navigate to home (customer user)
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_LoginFragment_to_HomeFragment);
            }

            @Override
            public void onFailure(Call<BasicResponse<User>> call, Throwable t) {

            }
        });
        // set underline to forgot password text
        String forgotPasswordString = "Olvide mi contraseña";
        SpannableString forgotPasswordText = new SpannableString(forgotPasswordString);
        forgotPasswordText.setSpan(new UnderlineSpan(), 0, forgotPasswordString.length(), 0);
        binding.forgotPassword.setText(forgotPasswordText);
        // set underline to forgot password text

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.forgotPassword.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_LoginFragment_to_forgotPasswordFragment);
        });
        binding.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.editTextTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                return;
            }
            binding.editTextTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        });


        binding.buttonLogin.setOnClickListener(view1 -> {
            login(view1);
        });
    }

    void login(View view) {

        authService.login(
                new LoginRequest(
                        binding.editTextTextEmailAddress.getText().toString(),
                        binding.editTextTextPassword.getText().toString()
                )
        ).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BasicResponse<LoginResponse>> call, Response<BasicResponse<LoginResponse>> response) {
                // check that the response contains a token
                assert response.body() != null;
                if (response.body().getData() == null) {
                    Snackbar.make(view, response.body().getError(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                LoginResponse loginResponse = response.body().getData();
                // save token to shared preferences
                SharedPreferences sharedPrefs = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("token", loginResponse.getToken());
                editor.apply();
                // check roles
                if (loginResponse.getUser().getRoles().stream().anyMatch(role -> Objects.equals(role.getName(), "customer_creator"))) {
                    // admin user
                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.action_LoginFragment_to_customersFragment);
                    return;
                }
                // navigate to home (customer user)
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_LoginFragment_to_HomeFragment);

            }

            @Override
            public void onFailure(Call<BasicResponse<LoginResponse>> call, Throwable t) {
                call.cancel();
                System.out.println(t.getMessage());
                Snackbar.make(view, String.format("Ups ha ocurrido un error: %s", t.getMessage()), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
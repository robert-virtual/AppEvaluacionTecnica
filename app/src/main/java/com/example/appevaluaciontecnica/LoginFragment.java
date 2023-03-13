package com.example.appevaluaciontecnica;

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

import com.example.appevaluaciontecnica.auth.AuthService;
import com.example.appevaluaciontecnica.auth.model.LoginRequest;
import com.example.appevaluaciontecnica.auth.model.LoginResponse;
import com.example.appevaluaciontecnica.databinding.FragmentLoginBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
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
        binding.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                binding.editTextTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                return;
            }
            binding.editTextTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        });
        binding.buttonLogin.setOnClickListener(view1 -> {

            AuthService authService = BankApiClient.getAuthService();

            System.out.println(
                    binding.editTextTextEmailAddress.getText().toString() +","+
                    binding.editTextTextPassword.getText().toString()
            );
            authService.login(
                    new LoginRequest(
                            binding.editTextTextEmailAddress.getText().toString(),
                            binding.editTextTextEmailAddress.getText().toString()
                    )
            ).enqueue(new Callback<BasicResponse<LoginResponse>>() {
                @Override
                public void onResponse(Call<BasicResponse<LoginResponse>> call, Response<BasicResponse<LoginResponse>> response) {
                    // check that the response contains a token
                    assert response.body() != null;
                    if (response.body().getData() == null){
                        Snackbar.make(view, "Credenciales incorrectas, vuelva a intentar", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return;
                    }
                    LoginResponse loginResponse = response.body().getData();
                    // save token to shared preferences
                    SharedPreferences sharedPrefs = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("token",loginResponse.getToken());
                    editor.apply();
                    // navigate to home
                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.button_login);

                }

                @Override
                public void onFailure(Call<BasicResponse<LoginResponse>> call, Throwable t) {
                    call.cancel();
                    Snackbar.make(view, String.format("Ups ha ocurrido un error: %s",t.getMessage()), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
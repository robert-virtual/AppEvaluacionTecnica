package com.example.appevaluaciontecnica.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appevaluaciontecnica.dataaccess.BankApiClient;
import com.example.appevaluaciontecnica.dataaccess.BasicResponse;
import com.example.appevaluaciontecnica.dataaccess.account.model.Account;
import com.example.appevaluaciontecnica.dataaccess.customer.model.Customer;
import com.example.appevaluaciontecnica.databinding.FragmentCreditoCuentaAhorroBinding;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavingAccountCreditFragment extends Fragment {


    private FragmentCreditoCuentaAhorroBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreditoCuentaAhorroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.targetAccount.setOnFocusChangeListener((view1, b) -> {
            System.out.println("focus change");
        });
        binding.targetAccount.setOnKeyListener((view1, i, keyEvent) -> {
            if (binding.targetAccount.length() == 16) {
                BankApiClient.getCustomerService().getCustomer(
                        "Bearer " + Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE).getString("token", ""),
                        Objects.requireNonNull(binding.dni.getText()).toString()
                ).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<BasicResponse<Customer>> call, Response<BasicResponse<Customer>> response) {
                        if (response.body() != null && response.body().getData() != null) {
                            Customer customer = response.body().getData();
                            if (customer.getAccounts().stream().anyMatch(account -> account.getAccount_number() == Long.parseLong(binding.targetAccount.getText().toString()))) {
                                binding.accountHolder.setText("Beneficiary: " + customer.getName() + " " + customer.getLastname());
                                binding.accountHolder.setVisibility(View.VISIBLE);
                                return;
                            }
                            binding.accountHolder.setText("Account not found for the given customer");
                            binding.accountHolder.setVisibility(View.VISIBLE);
                            return;
                        }
                        binding.accountHolder.setText("Customer Not Found");
                        binding.accountHolder.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<BasicResponse<Customer>> call, Throwable t) {
                        binding.accountHolder.setText("Account not found for the given customer");
                        binding.accountHolder.setVisibility(View.VISIBLE);

                    }
                });

            }
            return false;
        });
        binding.nextButton.setOnClickListener(view1 -> {

        });
    }
}
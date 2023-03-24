package com.example.appevaluaciontecnica.ui.savingaccountcredit;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.appevaluaciontecnica.R;
import com.example.appevaluaciontecnica.dataaccess.BankApiClient;
import com.example.appevaluaciontecnica.dataaccess.BasicResponse;
import com.example.appevaluaciontecnica.dataaccess.account.model.Account;
import com.example.appevaluaciontecnica.dataaccess.customer.model.Customer;
import com.example.appevaluaciontecnica.dataaccess.transactions.model.Transaction;
import com.example.appevaluaciontecnica.dataaccess.transactions.model.TransactionDetail;
import com.example.appevaluaciontecnica.dataaccess.transactions.model.TransactionType;
import com.example.appevaluaciontecnica.databinding.FragmentCreditoCuentaAhorroBinding;
import com.example.appevaluaciontecnica.ui.Global;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;
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
        MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) binding.sourceAccount;

        ArrayAdapter adapter = new ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                Global.accounts.stream().map(x -> Long.toString(x.getAccount_number())).toArray()
        );
        autoCompleteTextView.setAdapter(adapter);
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
            List<String> errors = validateForm();
            if (errors.isEmpty()) {
                binding.nextButton.setEnabled(false);
                BankApiClient.getTransactionService().createTransaction(
                        "Bearer "+Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE).getString("token", ""),
                        new Transaction(
                                new Account(
                                        Long.parseLong(binding.sourceAccount.getText().toString())
                                ),
                                new TransactionType(1),
                                "LPS",
                                Objects.requireNonNull(binding.notes.getText()).toString(),
                                List.of(
                                        new TransactionDetail(
                                                Objects.requireNonNull(binding.targetAccount.getText()).toString(),
                                                Objects.requireNonNull(binding.amount.getText()).toString()
                                        )
                                )
                        )
                ).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<BasicResponse<Transaction>> call, Response<BasicResponse<Transaction>> response) {

                        if (response.body() != null && response.body().getData() != null) {
                            Global.lastTransaction = response.body().getData();
                            Navigation.findNavController(view).navigate(R.id.action_savingAccountCreditFragment_to_receiptFragment);
                            return;
                        }
                        if (response.body() != null && response.body().getError() != null) {
                            binding.nextButton.setEnabled(true);
                            Snackbar.make(view, "Ups transaction failed: " + response.body().getError(), Snackbar.LENGTH_LONG).show();
                            return;
                        }
                        binding.nextButton.setEnabled(true);
                        Snackbar.make(view, "Ups transaction failed with status code: " + response.code(), Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<BasicResponse<Transaction>> call, Throwable t) {
                        binding.nextButton.setEnabled(true);
                        Snackbar.make(view, "Ups transaction failed try again", Snackbar.LENGTH_LONG).show();
                    }
                });
                return;
            }
            Snackbar.make(view, "Invalid fields: " + String.join(", ", errors), Snackbar.LENGTH_LONG).show();
        });
    }

    private List<String> validateForm() {
        List<String> errors = new ArrayList<>();
        if (binding.sourceAccount.getText().toString().isEmpty()) errors.add("source account");
        if (Objects.requireNonNull(binding.targetAccount.getText()).toString().isEmpty())
            errors.add("target account");
        if (Objects.requireNonNull(binding.amount.getText()).toString().isEmpty())
            errors.add("amount");
        return errors;
    }
}
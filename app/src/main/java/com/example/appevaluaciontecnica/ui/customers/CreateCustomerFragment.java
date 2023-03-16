package com.example.appevaluaciontecnica.ui.customers;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.example.appevaluaciontecnica.R;
import com.example.appevaluaciontecnica.dataaccess.BankApiClient;
import com.example.appevaluaciontecnica.dataaccess.BasicResponse;
import com.example.appevaluaciontecnica.dataaccess.customer.CustomerService;
import com.example.appevaluaciontecnica.dataaccess.customer.model.Account;
import com.example.appevaluaciontecnica.dataaccess.customer.model.AccountType;
import com.example.appevaluaciontecnica.dataaccess.customer.model.Customer;
import com.example.appevaluaciontecnica.databinding.FragmentCreateCustomerBinding;
import com.example.appevaluaciontecnica.ui.LoginFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCustomerFragment extends Fragment {


    private FragmentCreateCustomerBinding binding;
    private final CustomerService customerService = BankApiClient.getCustomerService();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateCustomerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.birthdate.setInputType(EditorInfo.TYPE_NULL);
        binding.birthdate.setOnFocusChangeListener((view1, focus) -> {
            if (!focus) return;
            MaterialDatePicker<Long> picker = MaterialDatePicker
                    .Builder
                    .datePicker()
                    .setTitleText("Birthdate")
                    .build();
            picker.show(requireActivity().getSupportFragmentManager(), "tag");
            picker.addOnNegativeButtonClickListener(dialogInterface -> {
                binding.address1.requestFocus();
            });
            picker.addOnDismissListener(dialogInterface -> {
                binding.address1.requestFocus();
            });
            picker.addOnCancelListener(dialogInterface -> {
                binding.address1.requestFocus();
            });
            picker.addOnPositiveButtonClickListener(selection -> {
                TimeZone tz = TimeZone.getTimeZone("UTC");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
                df.setTimeZone(tz);

                String date1 =
                        df.format(new Date(selection));
                binding.birthdate.setText(date1);
            });
        });
        binding.btnCreate.setOnClickListener(view1 -> {
            customerService.createCustomer(
                    "Bearer " + getActivity().getPreferences(Context.MODE_PRIVATE).getString("token", ""),
                    new Customer(
                            Objects.requireNonNull(binding.dni.getText()).toString(),
                            Objects.requireNonNull(binding.name.getText()).toString(),
                            Objects.requireNonNull(binding.contactName.getText()).toString(),
                            Objects.requireNonNull(binding.lastname.getText()).toString(),
                            Objects.requireNonNull(binding.birthdate.getText()).toString(),
                            Objects.requireNonNull(binding.phone.getText()).toString(),
                            Objects.requireNonNull(binding.email.getText()).toString(),
                            Objects.requireNonNull(binding.address1.getText()).toString(),
                            Objects.requireNonNull(binding.address2.getText()).toString(),
                            binding.isCompany.isChecked(),
                           List.of(new Account(new AccountType(1)))
                    )
            ).enqueue(new Callback<BasicResponse<Customer>>() {
                @Override
                public void onResponse(Call<BasicResponse<Customer>> call, Response<BasicResponse<Customer>> response) {
                    System.out.println(response);
                    System.out.println(response.code());
                    System.out.println(response.headers());
                    if (response.body() != null && response.body().getData() != null){
                        NavHostFragment.findNavController(CreateCustomerFragment.this)
                                .popBackStack();
                        return;
                    }
                    if (response.body() != null && response.body().getError() != null){
                        System.out.println(response.body().getError());
                    }
                    Snackbar.make(binding.getRoot(), "Ups could not create the customer", Snackbar.LENGTH_LONG)
                            .setAction("ok", null).show();
                }

                @Override
                public void onFailure(Call<BasicResponse<Customer>> call, Throwable t) {
                    System.out.println(t.getMessage());
                    Snackbar.make(binding.getRoot(), "Ups could not create the customer", Snackbar.LENGTH_LONG)
                            .setAction("ok", null).show();
                }
            });
        });
    }
}
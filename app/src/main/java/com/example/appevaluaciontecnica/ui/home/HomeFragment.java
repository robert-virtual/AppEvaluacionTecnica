package com.example.appevaluaciontecnica.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appevaluaciontecnica.dataaccess.BankApiClient;
import com.example.appevaluaciontecnica.dataaccess.BasicResponse;
import com.example.appevaluaciontecnica.dataaccess.account.model.Account;
import com.example.appevaluaciontecnica.dataaccess.customer.model.Customer;
import com.example.appevaluaciontecnica.databinding.FragmentHomeBinding;
import com.example.appevaluaciontecnica.ui.customers.CustomerRecyclerViewAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = binding.getRoot().getContext();
        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        BankApiClient.
                getAccountService().
                allAccounts(
                        "Bearer " + getActivity().
                                getPreferences(Context.MODE_PRIVATE).
                                getString("token", "")
                ).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<BasicResponse<List<Account>>> call, Response<BasicResponse<List<Account>>> response) {

                        if (response.body() == null) {

                            Snackbar.make(view, "Ups could not get accounts information: " + response.code(), Snackbar.LENGTH_LONG)
                                    .setAction("ok", null).show();
                            return;
                        }
                        List<Account> accounts = response.body().getData();
                        recyclerView.setAdapter(
                                new AccountRecyclerViewAdapter(
                                        accounts,
                                       HomeFragment.this
                                )
                        );
                    }

                    @Override
                    public void onFailure(Call<BasicResponse<List<Account>>> call, Throwable t) {
                        Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("ok", null).show();
                    }
                });

        binding.fab.setOnClickListener(view1 -> {

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
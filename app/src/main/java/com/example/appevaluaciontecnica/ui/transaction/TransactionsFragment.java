package com.example.appevaluaciontecnica.ui.transaction;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appevaluaciontecnica.R;
import com.example.appevaluaciontecnica.dataaccess.BankApiClient;
import com.example.appevaluaciontecnica.dataaccess.BasicResponse;
import com.example.appevaluaciontecnica.dataaccess.account.model.Account;
import com.example.appevaluaciontecnica.dataaccess.transactions.model.Transaction;
import com.example.appevaluaciontecnica.databinding.FragmentTransactionsBinding;
import com.example.appevaluaciontecnica.ui.home.AccountRecyclerViewAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionsFragment extends Fragment {

    private FragmentTransactionsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTransactionsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        long account = TransactionsFragmentArgs.fromBundle(getArguments()).getAccount();
        Context context = binding.getRoot().getContext();
        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        BankApiClient.
                getTransactionService().
                allTransactions(
                        "Bearer " + getActivity().
                                getPreferences(Context.MODE_PRIVATE).
                                getString("token", ""),
                       account
                ).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<BasicResponse<List<Transaction>>> call, Response<BasicResponse<List<Transaction>>> response) {

                        if (response.body() == null) {

                            Snackbar.make(view, "Ups could not get accounts information: " + response.code(), Snackbar.LENGTH_LONG)
                                    .setAction("ok", null).show();
                            return;
                        }
                        List<Transaction> transactions = response.body().getData();
                        recyclerView.setAdapter(
                                new TransactionRecyclerViewAdapter(
                                        transactions,
                                        account
                                )
                        );
                    }

                    @Override
                    public void onFailure(Call<BasicResponse<List<Transaction>>> call, Throwable t) {
                        Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("ok", null).show();
                    }
                });
    }
}
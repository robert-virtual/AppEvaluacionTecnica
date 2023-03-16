package com.example.appevaluaciontecnica.ui.customers;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appevaluaciontecnica.R;
import com.example.appevaluaciontecnica.dataaccess.BankApiClient;
import com.example.appevaluaciontecnica.dataaccess.BasicResponse;
import com.example.appevaluaciontecnica.dataaccess.customer.model.Customer;
import com.example.appevaluaciontecnica.databinding.FragmentCustomersListBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomersFragment extends Fragment {

    private FragmentCustomersListBinding binding;

    public CustomersFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCustomersListBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.floatingActionButton.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(CustomersFragment.this)
                    .navigate(R.id.action_customersFragment_to_createCustomerFragment);
        });

        Context context = binding.getRoot().getContext();
        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        BankApiClient.
                getCustomerService().
                allCustomers(
                        "Bearer " + getActivity().
                                getPreferences(Context.MODE_PRIVATE).
                                getString("token", "")
                ).enqueue(new Callback<BasicResponse<List<Customer>>>() {
                    @Override
                    public void onResponse(Call<BasicResponse<List<Customer>>> call, Response<BasicResponse<List<Customer>>> response) {

                        assert response.body() != null;
                        List<Customer> customers = response.body().getData();
                        recyclerView.setAdapter(
                                new CustomerRecyclerViewAdapter(
                                        customers
                                )
                        );
                    }

                    @Override
                    public void onFailure(Call<BasicResponse<List<Customer>>> call, Throwable t) {

                    }
                });
    }
}
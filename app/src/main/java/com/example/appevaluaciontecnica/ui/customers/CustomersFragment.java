package com.example.appevaluaciontecnica.ui.customers;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.appevaluaciontecnica.R;
import com.example.appevaluaciontecnica.dataaccess.BankApiClient;
import com.example.appevaluaciontecnica.dataaccess.BasicResponse;
import com.example.appevaluaciontecnica.dataaccess.customer.model.Customer;
import com.example.appevaluaciontecnica.databinding.FragmentCustomersBinding;
import com.example.appevaluaciontecnica.databinding.FragmentCustomersListBinding;
import com.example.appevaluaciontecnica.placeholder.PlaceholderContent;
import com.example.appevaluaciontecnica.ui.LoginFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 */
public class CustomersFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private FragmentCustomersListBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CustomersFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CustomersFragment newInstance(int columnCount) {
        CustomersFragment fragment = new CustomersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCustomersListBinding.inflate(getLayoutInflater());
        binding.floatingActionButton.setOnClickListener(view -> {
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
                                new MyItemRecyclerViewAdapter(
                                        customers
                                )
                        );
                    }

                    @Override
                    public void onFailure(Call<BasicResponse<List<Customer>>> call, Throwable t) {

                    }
                });
        return binding.getRoot();
    }
}
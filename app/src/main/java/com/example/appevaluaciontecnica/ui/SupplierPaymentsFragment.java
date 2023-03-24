package com.example.appevaluaciontecnica.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appevaluaciontecnica.R;
import com.example.appevaluaciontecnica.databinding.FragmentSupplierPaymentsBinding;

public class SupplierPaymentsFragment extends Fragment {


    private FragmentSupplierPaymentsBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSupplierPaymentsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
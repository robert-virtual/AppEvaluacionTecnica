package com.example.appevaluaciontecnica.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appevaluaciontecnica.R;
import com.example.appevaluaciontecnica.databinding.FragmentCheckingAccountCreditBinding;

public class CheckingAccountCreditFragment extends Fragment {


    private FragmentCheckingAccountCreditBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCheckingAccountCreditBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
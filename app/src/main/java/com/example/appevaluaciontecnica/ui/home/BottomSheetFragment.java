package com.example.appevaluaciontecnica.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appevaluaciontecnica.R;
import com.example.appevaluaciontecnica.databinding.FragmentBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {


    private FragmentBottomSheetBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cca.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(BottomSheetFragment.this)
                    .navigate(R.id.action_bottomSheetFragment_to_savingsAccountCreditFragment);
        });
        binding.cch.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(BottomSheetFragment.this)
                    .navigate(R.id.action_bottomSheetFragment_to_checkingAccountCreditFragment);
        });
        binding.ach.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(BottomSheetFragment.this)
                    .navigate(R.id.action_bottomSheetFragment_to_achFragment);
        });
        binding.ppa.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(BottomSheetFragment.this)
                    .navigate(R.id.action_bottomSheetFragment_to_payrollFragment);
        });
        binding.ppr.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(BottomSheetFragment.this)
                    .navigate(R.id.action_bottomSheetFragment_to_supplierPaymentsFragment);
        });
    }
}
package com.example.appevaluaciontecnica.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appevaluaciontecnica.R;
import com.example.appevaluaciontecnica.dataaccess.account.model.Account;
import com.example.appevaluaciontecnica.databinding.FragmentAccountBinding;
import com.example.appevaluaciontecnica.ui.home.HomeFragmentDirections;

import java.util.List;

public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<AccountRecyclerViewAdapter.ViewHolder> {

    private final List<Account> mValues;
    private final Fragment mFragment;

    public AccountRecyclerViewAdapter(List<Account> items, Fragment fragment) {
        mValues = items;
        mFragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentAccountBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mAccountTypeView.setText(mValues.get(position).getAccountType().getName().toUpperCase() + " account");
        holder.mAccountNumberView.setText(Long.toString(mValues.get(position).getAccount_number()));
        holder.mAvailableBalanceView.setText("Lps. " + mValues.get(position).getAvailable_balance());

        holder.mCardView.setOnClickListener(view -> {
            NavDirections action = HomeFragmentDirections
                    .actionHomeFragmentToTransactionsFragment(
                            mValues.get(position).getAccount_number()
                    );

            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView mCardView;
        public final TextView mAccountTypeView;
        public final TextView mAccountNumberView;
        public final TextView mAvailableBalanceView;
        public Account mItem;

        public ViewHolder(FragmentAccountBinding binding) {
            super(binding.getRoot());
            mAccountTypeView = binding.accountType;
            mAccountNumberView = binding.accountNumber;
            mAvailableBalanceView = binding.availableBalance;
            mCardView = binding.card;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mAccountNumberView.getText() + "'";
        }
    }
}
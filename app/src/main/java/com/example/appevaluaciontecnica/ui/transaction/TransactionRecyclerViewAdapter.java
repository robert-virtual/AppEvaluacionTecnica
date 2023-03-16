package com.example.appevaluaciontecnica.ui.transaction;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appevaluaciontecnica.dataaccess.transactions.model.Transaction;
import com.example.appevaluaciontecnica.databinding.FragmentTransactionBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransactionRecyclerViewAdapter.ViewHolder> {

    private final List<Transaction> mValues;
    private final long mAccount;

    public TransactionRecyclerViewAdapter(List<Transaction> items, long acocount) {
        mValues = items;
        mAccount = acocount;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentTransactionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        long account = mValues.get(position).getAccount().getAccount_number();
        holder.mTransactionTypeView.setText(mValues.get(position).getTransaction_type().getDescription().toUpperCase());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMM yyyy hh:mm a");
        holder.mDateView.setText(
                LocalDateTime.parse(
                        mValues.get(position).getOperated_at(),
                        DateTimeFormatter.ISO_DATE_TIME
                ).format(dtf)
        );

        String status = mValues.get(position).getStatus().getName();
        holder.mStatusView.setText(status);
        holder.mStatusView.setTextColor(Objects.equals(status, "pending") ? Color.RED : Color.GREEN);
        holder.mAmountView.setText(
                "Lps. " + mValues
                        .get(position)
                        .getDetails()
                        .stream()
                        .mapToDouble(
                                transactionDetail -> Double.parseDouble(transactionDetail.getAmount())
                        ).sum()
        );
        holder.mAmountView.setTextColor(account == mAccount ? Color.RED : Color.GREEN);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTransactionTypeView;
        public final TextView mDateView;
        public final TextView mAmountView;
        public final TextView mStatusView;
        public Transaction mItem;

        public ViewHolder(FragmentTransactionBinding binding) {
            super(binding.getRoot());
            mTransactionTypeView = binding.transactionType;
            mDateView = binding.date;
            mAmountView = binding.amount;
            mStatusView = binding.status;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTransactionTypeView.getText() + "'";
        }
    }
}
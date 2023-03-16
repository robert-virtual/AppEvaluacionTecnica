package com.example.appevaluaciontecnica.ui.customers;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appevaluaciontecnica.dataaccess.customer.model.Customer;
import com.example.appevaluaciontecnica.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.appevaluaciontecnica.databinding.FragmentCustomersBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CustomerRecyclerViewAdapter extends RecyclerView.Adapter<CustomerRecyclerViewAdapter.ViewHolder> {

    private final List<Customer> mValues;

    public CustomerRecyclerViewAdapter(List<Customer> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentCustomersBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mEmailView.setText(mValues.get(position).getEmail());
        holder.mIsCompanyView.setVisibility(
                mValues.get(position).isIs_company()
                        ? View.VISIBLE : View.INVISIBLE
        );
        holder.mDNIView.setText(mValues.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mEmailView;
        public final TextView mDNIView;
        public final TextView mIsCompanyView;
        public Customer mItem;

        public ViewHolder(FragmentCustomersBinding binding) {
            super(binding.getRoot());
            mNameView = binding.name;
            mEmailView = binding.email;
            mDNIView = binding.dni;
            mIsCompanyView = binding.isCompany;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mEmailView.getText() + "'";
        }
    }
}
package com.banjohann.lojasuplementos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banjohann.lojasuplementos.model.Customer;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customers;
    private final OnCustomerClickListener listener;

    public interface OnCustomerClickListener {
        void onCustomerClick(Customer customer);
    }

    public CustomerAdapter(List<Customer> customers, OnCustomerClickListener listener) {
        this.customers = customers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customers.get(position);
        holder.bind(customer, listener);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public void updateData(List<Customer> newCustomers) {
        this.customers = newCustomers;
        notifyDataSetChanged();
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        private final TextView initialLetterView;
        private final TextView nameView;
        private final TextView phoneView;
        private final TextView emailView;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            initialLetterView = itemView.findViewById(R.id.customerInitialLetter);
            nameView = itemView.findViewById(R.id.customerName);
            phoneView = itemView.findViewById(R.id.customerPhone);
            emailView = itemView.findViewById(R.id.customerEmail);
        }

        public void bind(final Customer customer, final OnCustomerClickListener listener) {
            String firstLetter = customer.getName() != null && !customer.getName().isEmpty() ?
                    String.valueOf(customer.getName().charAt(0)).toUpperCase() : "?";
            initialLetterView.setText(firstLetter);

            String fullName = customer.getName() + " " + (customer.getLastName() != null ? customer.getLastName() : "");
            nameView.setText(fullName.trim());

            phoneView.setText(customer.getPhone() != null ? customer.getPhone() : "");

            emailView.setText(customer.getEmail() != null ? customer.getEmail() : "");

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCustomerClick(customer);
                }
            });
        }
    }
}

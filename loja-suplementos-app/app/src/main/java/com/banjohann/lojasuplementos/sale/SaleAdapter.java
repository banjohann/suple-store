package com.banjohann.lojasuplementos.sale;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banjohann.lojasuplementos.R;
import com.banjohann.lojasuplementos.model.Sale;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Locale;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.SaleViewHolder> {
    private List<Sale> sales;
    private OnSaleClickListener listener;
    private NumberFormat currencyFormat;
    private DateTimeFormatter dateFormatter;

    public interface OnSaleClickListener {
        void onSaleClick(Sale sale);
    }

    public SaleAdapter(List<Sale> sales, OnSaleClickListener listener) {
        this.sales = sales;
        this.listener = listener;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    @NonNull
    @Override
    public SaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sale, parent, false);
        return new SaleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleViewHolder holder, int position) {
        Sale sale = sales.get(position);
        holder.bind(sale);
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    public void updateData(List<Sale> newSales) {
        this.sales = newSales;
        notifyDataSetChanged();
    }

    class SaleViewHolder extends RecyclerView.ViewHolder {
        private TextView saleId;
        private TextView customerName;
        private TextView saleDate;
        private TextView totalAmount;
        private TextView paymentMethod;
        private TextView status;

        public SaleViewHolder(@NonNull View itemView) {
            super(itemView);
            saleId = itemView.findViewById(R.id.saleId);
            customerName = itemView.findViewById(R.id.customerName);
            saleDate = itemView.findViewById(R.id.saleDate);
            totalAmount = itemView.findViewById(R.id.totalAmount);
            paymentMethod = itemView.findViewById(R.id.paymentMethod);
            status = itemView.findViewById(R.id.status);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onSaleClick(sales.get(position));
                    }
                }
            });
        }

        public void bind(Sale sale) {
            saleId.setText("Venda #" + sale.getId());
            customerName.setText(sale.getCustomer().getName() + " " + sale.getCustomer().getLastName());
            saleDate.setText(DateFormat.getDateInstance().format(sale.getDateCreated()));
            totalAmount.setText(currencyFormat.format(sale.getPayment().getAmount()));
            paymentMethod.setText(sale.getPayment().getPaymentMethod().getDescription());
            status.setText(sale.getPayment().getStatus().getDescription());
        }
    }
}

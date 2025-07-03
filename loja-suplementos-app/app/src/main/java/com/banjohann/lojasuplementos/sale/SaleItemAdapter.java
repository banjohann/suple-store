package com.banjohann.lojasuplementos.sale;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banjohann.lojasuplementos.R;
import com.banjohann.lojasuplementos.model.SaleItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SaleItemAdapter extends RecyclerView.Adapter<SaleItemAdapter.SaleItemViewHolder> {
    private List<SaleItem> saleItems;
    private OnSaleItemListener listener;
    private NumberFormat currencyFormat;

    public interface OnSaleItemListener {
        void onQuantityChanged(SaleItem item, int newQuantity);
        void onRemoveItem(SaleItem item);
    }

    public SaleItemAdapter(List<SaleItem> saleItems, OnSaleItemListener listener) {
        this.saleItems = saleItems;
        this.listener = listener;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    }

    @NonNull
    @Override
    public SaleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sale_product, parent, false);
        return new SaleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleItemViewHolder holder, int position) {
        SaleItem item = saleItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return saleItems.size();
    }

    class SaleItemViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView unitPrice;
        private TextView quantity;
        private TextView totalPrice;
        private Button decreaseButton;
        private Button increaseButton;
        private ImageButton removeButton;

        public SaleItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            unitPrice = itemView.findViewById(R.id.unitPrice);
            quantity = itemView.findViewById(R.id.quantity);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
            increaseButton = itemView.findViewById(R.id.increaseButton);
            removeButton = itemView.findViewById(R.id.removeButton);
        }

        public void bind(SaleItem item) {
            productName.setText(item.getProduct().getName());
            unitPrice.setText(currencyFormat.format(item.getPrice().doubleValue()));
            quantity.setText(String.valueOf(item.getQuantity()));
            totalPrice.setText(currencyFormat.format(item.getPrice().doubleValue()));

            decreaseButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onQuantityChanged(item, item.getQuantity() - 1);
                }
            });

            increaseButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onQuantityChanged(item, item.getQuantity() + 1);
                }
            });

            removeButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRemoveItem(item);
                }
            });
        }
    }
}
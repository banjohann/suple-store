package com.banjohann.lojasuplementos.sale;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banjohann.lojasuplementos.R;
import com.banjohann.lojasuplementos.model.SaleItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SaleItemDetailAdapter extends RecyclerView.Adapter<SaleItemDetailAdapter.ViewHolder> {

    private List<SaleItem> items;
    private NumberFormat currencyFormat;

    public SaleItemDetailAdapter(List<SaleItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sale_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SaleItem item = items.get(position);

        holder.productNameText.setText(item.getProduct().getName());

        holder.quantityText.setText("Qtd: " + item.getQuantity());

        double unitPrice = item.getProduct().getPrice().doubleValue();
        holder.unitPriceText.setText("Preço unit.: " + currencyFormat.format(unitPrice));

        double totalPrice = unitPrice * item.getQuantity();
        holder.totalPriceText.setText("Total: " + currencyFormat.format(totalPrice));
    }

    @Override
    public int getItemCount() {
        int count = items.size();
        return count;
    }

    public void updateItems(List<SaleItem> newItems) {
        this.items = newItems != null ? newItems : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameText, quantityText, unitPriceText, totalPriceText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameText = itemView.findViewById(R.id.productNameText);
            quantityText = itemView.findViewById(R.id.quantityText);
            unitPriceText = itemView.findViewById(R.id.unitPriceText);
            totalPriceText = itemView.findViewById(R.id.totalPriceText);
        }
    }
}


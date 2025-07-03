package com.banjohann.lojasuplementos.sale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.banjohann.lojasuplementos.R;
import com.banjohann.lojasuplementos.model.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductListAdapter extends BaseAdapter {
    private Context context;
    private List<Product> products;
    private LayoutInflater inflater;
    private NumberFormat currencyFormat;

    public ProductListAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
        this.inflater = LayoutInflater.from(context);
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_product_selection, parent, false);
            holder = new ViewHolder();
            holder.productName = convertView.findViewById(R.id.productName);
            holder.productPrice = convertView.findViewById(R.id.productPrice);
            holder.productDescription = convertView.findViewById(R.id.productDescription);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(currencyFormat.format(product.getPrice()));

        if (product.getDescription() != null && !product.getDescription().isEmpty()) {
            holder.productDescription.setText(product.getDescription());
            holder.productDescription.setVisibility(View.VISIBLE);
        } else {
            holder.productDescription.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView productName;
        TextView productPrice;
        TextView productDescription;
    }
}
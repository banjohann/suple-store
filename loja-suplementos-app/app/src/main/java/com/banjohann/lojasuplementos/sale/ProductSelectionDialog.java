package com.banjohann.lojasuplementos.sale;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.banjohann.lojasuplementos.R;
import com.banjohann.lojasuplementos.model.Product;

import java.util.List;

public class ProductSelectionDialog extends Dialog {
    private List<Product> products;
    private OnProductSelectedListener listener;
    private ListView productListView;

    public interface OnProductSelectedListener {
        void onProductSelected(Product product);
    }

    public ProductSelectionDialog(@NonNull Context context, List<Product> products, OnProductSelectedListener listener) {
        super(context);
        this.products = products;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_product_selection);

        productListView = findViewById(R.id.productListView);
        Button cancelButton = findViewById(R.id.cancelButton);

        ProductListAdapter adapter = new ProductListAdapter(getContext(), products);
        productListView.setAdapter(adapter);

        productListView.setOnItemClickListener((parent, view, position, id) -> {
            Product selectedProduct = products.get(position);
            if (listener != null) {
                listener.onProductSelected(selectedProduct);
            }
            dismiss();
        });

        cancelButton.setOnClickListener(v -> dismiss());
    }
}
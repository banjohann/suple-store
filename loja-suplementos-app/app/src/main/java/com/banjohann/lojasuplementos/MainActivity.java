package com.banjohann.lojasuplementos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialCardView salesCardView = findViewById(R.id.salesCardView);
        MaterialCardView customersCardView = findViewById(R.id.customersCardView);

        salesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BarcodeScannerActivity.class);
                startActivity(intent);
            }

        });

        customersCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Área de Clientes", Toast.LENGTH_SHORT).show();
                // TODO: Navigate to Customers Activity
                // Intent intent = new Intent(MainActivity.this, CustomersActivity.class);
                // startActivity(intent);
            }
        });
    }
}

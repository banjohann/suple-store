package com.banjohann.lojasuplementos.sale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banjohann.lojasuplementos.R;
import com.banjohann.lojasuplementos.api.ApiClient;
import com.banjohann.lojasuplementos.api.SaleApiService;
import com.banjohann.lojasuplementos.model.Sale;
import com.banjohann.lojasuplementos.model.SaleItem;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleDetailActivity extends AppCompatActivity {

    public static final String EXTRA_SALE_ID = "extra_sale_id";

    private TextView saleIdText, saleDateText, totalAmountText;
    private TextView customerNameText, customerEmailText;
    private TextView shippingStatusText, deliveryAddressText, trackingCodeText;
    private TextView paymentMethodText, paymentStatusText, paymentAmountText;
    private RecyclerView itemsRecyclerView;
    private View progressBar;

    private Sale sale;
    private SaleApiService apiService;
    private NumberFormat currencyFormat;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);

        initViews();
        setupToolbar();
        initFormatters();

        apiService = ApiClient.getSaleService();

        Long saleId = getIntent().getLongExtra(EXTRA_SALE_ID, -1);
        if (saleId == -1) {
            Toast.makeText(this, "Erro ao carregar detalhes da venda", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadSaleDetails(saleId);
    }

    private void initViews() {
        saleIdText = findViewById(R.id.saleIdText);
        saleDateText = findViewById(R.id.saleDateText);
        totalAmountText = findViewById(R.id.totalAmountText);
        customerNameText = findViewById(R.id.customerNameText);
        customerEmailText = findViewById(R.id.customerEmailText);
        shippingStatusText = findViewById(R.id.shippingStatusText);
        deliveryAddressText = findViewById(R.id.deliveryAddressText);
        trackingCodeText = findViewById(R.id.trackingCodeText);
        paymentMethodText = findViewById(R.id.paymentMethodText);
        paymentStatusText = findViewById(R.id.paymentStatusText);
        paymentAmountText = findViewById(R.id.paymentAmountText);
        progressBar = findViewById(R.id.progressBar);
    }

    private void loadSaleDetails(Long saleId) {
        progressBar.setVisibility(View.VISIBLE);

        apiService.getSaleById(saleId).enqueue(new Callback<Sale>() {
            @Override
            public void onResponse(Call<Sale> call, Response<Sale> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    sale = response.body();
                    populateData();
                } else {
                    Toast.makeText(SaleDetailActivity.this,
                            "Erro ao carregar detalhes: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Sale> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SaleDetailActivity.this,
                        "Falha na conexão: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalhes da Venda");
    }

    private void initFormatters() {
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt", "BR"));
    }

    private void populateData() {
        saleIdText.setText("Venda #" + sale.getId());
        saleDateText.setText("Data: " + dateFormat.format(sale.getDateCreated()));

        double total = sale.getSaleItems().stream()
                .mapToDouble(item -> item.getPrice().doubleValue())
                .sum();
        totalAmountText.setText("Total: " + currencyFormat.format(total));

        customerNameText.setText(sale.getCustomer().getName() + " " + sale.getCustomer().getLastName());
        customerEmailText.setText(sale.getCustomer().getEmail());

        shippingStatusText.setText("Status: " + sale.getShipping().getStatus().getDescription());

        if (sale.getShipping().getDeliveryAddress() != null) {
            String address = sale.getShipping().getDeliveryAddress().getStreet() + ", " +
                    sale.getShipping().getDeliveryAddress().getNumber() + " - " +
                    sale.getShipping().getDeliveryAddress().getCity() + ", " +
                    sale.getShipping().getDeliveryAddress().getState();
            deliveryAddressText.setText(address);
        }

        trackingCodeText.setText("Código: " + sale.getShipping().getTrackingNumber());

        paymentMethodText.setText("Método: " + sale.getPayment().getPaymentMethod().getDescription());
        paymentStatusText.setText("Status: " + sale.getPayment().getStatus().getDescription());
        paymentAmountText.setText("Valor: " + currencyFormat.format(sale.getPayment().getAmount()));

        populateItems();
    }

    private void populateItems() {
        LinearLayout itemsContainer = findViewById(R.id.itemsContainer);
        itemsContainer.removeAllViews();

        LayoutInflater inflater = getLayoutInflater();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        for (int i = 0; i < sale.getSaleItems().size(); i++) {
            SaleItem item = sale.getSaleItems().get(i);

            View itemView = inflater.inflate(R.layout.item_sale_detail, itemsContainer, false);

            TextView productNameText = itemView.findViewById(R.id.productNameText);
            TextView quantityText = itemView.findViewById(R.id.quantityText);
            TextView unitPriceText = itemView.findViewById(R.id.unitPriceText);
            TextView totalPriceText = itemView.findViewById(R.id.totalPriceText);

            productNameText.setText(item.getProduct().getName());
            quantityText.setText("Qtd: " + item.getQuantity());

            double unitPrice = item.getProduct().getPrice().doubleValue();
            unitPriceText.setText("Preço unit.: " + currencyFormat.format(unitPrice));

            double totalPrice = unitPrice * item.getQuantity();
            totalPriceText.setText("Total: " + currencyFormat.format(totalPrice));

            itemsContainer.addView(itemView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

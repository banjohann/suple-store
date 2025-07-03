package com.banjohann.lojasuplementos.sale;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.banjohann.lojasuplementos.R;
import com.banjohann.lojasuplementos.api.ApiClient;
import com.banjohann.lojasuplementos.api.CustomerApiService;
import com.banjohann.lojasuplementos.api.ProductApiService;
import com.banjohann.lojasuplementos.api.SaleApiService;
import com.banjohann.lojasuplementos.model.Customer;
import com.banjohann.lojasuplementos.model.DeliveryAddress;
import com.banjohann.lojasuplementos.model.Payment;
import com.banjohann.lojasuplementos.model.PaymentMethod;
import com.banjohann.lojasuplementos.model.PaymentStatus;
import com.banjohann.lojasuplementos.model.Product;
import com.banjohann.lojasuplementos.model.Sale;
import com.banjohann.lojasuplementos.model.SaleItem;
import com.banjohann.lojasuplementos.model.Shipping;
import com.banjohann.lojasuplementos.model.ShippingStatus;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewSaleActivity extends AppCompatActivity {

    private Spinner customerSpinner;
    private Spinner deliveryAddressSpinner;
    private RecyclerView productsRecyclerView;
    private TextView totalAmountText;
    private Button saveSaleButton;
    private FloatingActionButton addProductFab;

    private CustomerApiService customerApiService;
    private ProductApiService productApiService;
    private SaleApiService saleApiService;

    private List<Customer> customers = new ArrayList<>();
    private List<Product> availableProducts = new ArrayList<>();
    private List<SaleItem> saleItems = new ArrayList<>();
    private SaleItemAdapter saleItemAdapter;

    private Customer selectedCustomer;
    private DeliveryAddress selectedDeliveryAddress;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private NumberFormat currencyFormat;
    private boolean isRestoringState = false;

    private static final int BARCODE_SCANNER_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nova Venda");

        initViews();
        initServices();
        setupRecyclerView();
        loadCustomers();
        loadProducts();
    }

    private void initViews() {
        customerSpinner = findViewById(R.id.customerSpinner);
        deliveryAddressSpinner = findViewById(R.id.deliveryAddressSpinner);
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        totalAmountText = findViewById(R.id.totalAmountText);
        saveSaleButton = findViewById(R.id.saveSaleButton);
        addProductFab = findViewById(R.id.addProductFab);

        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isRestoringState) return;

                if (position > 0) {
                    selectedCustomer = customers.get(position - 1);
                    loadDeliveryAddresses();
                } else {
                    selectedCustomer = null;
                    clearDeliveryAddresses();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (isRestoringState) return;
                selectedCustomer = null;
                clearDeliveryAddresses();
            }
        });

        deliveryAddressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isRestoringState) return;

                if (selectedCustomer != null && position > 0) {
                    selectedDeliveryAddress = selectedCustomer.getDeliveryAddresses().get(position - 1);
                } else {
                    selectedDeliveryAddress = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (isRestoringState) return;
                selectedDeliveryAddress = null;
            }
        });

        addProductFab.setOnClickListener(v -> showProductSelectionDialog());
        saveSaleButton.setOnClickListener(v -> saveSale());
    }

    private void initServices() {
        customerApiService = ApiClient.getCustomerService();
        productApiService = ApiClient.getProductService();
        saleApiService = ApiClient.getSaleService();
    }

    private void setupRecyclerView() {
        saleItemAdapter = new SaleItemAdapter(saleItems, new SaleItemAdapter.OnSaleItemListener() {
            @Override
            public void onQuantityChanged(SaleItem item, int newQuantity) {
                updateSaleItemQuantity(item, newQuantity);
            }

            @Override
            public void onRemoveItem(SaleItem item) {
                removeSaleItem(item);
            }
        });
        productsRecyclerView.setAdapter(saleItemAdapter);
    }

    private void loadCustomers() {
        customerApiService.getCustomers().enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    customers = response.body();
                    setupCustomerSpinner();
                } else {
                    Toast.makeText(NewSaleActivity.this, "Erro ao carregar clientes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Toast.makeText(NewSaleActivity.this, "Falha ao carregar clientes: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProducts() {
        productApiService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    availableProducts = response.body();
                } else {
                    Toast.makeText(NewSaleActivity.this, "Erro ao carregar produtos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(NewSaleActivity.this, "Falha ao carregar produtos: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupCustomerSpinner() {
        List<String> customerNames = new ArrayList<>();
        customerNames.add("Selecione um cliente");
        for (Customer customer : customers) {
            customerNames.add(customer.getName() + " " + customer.getLastName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, customerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerSpinner.setAdapter(adapter);
    }

    private void loadDeliveryAddresses() {
        if (selectedCustomer == null || selectedCustomer.getDeliveryAddresses() == null) {
            clearDeliveryAddresses();
            return;
        }

        List<String> addressDescriptions = new ArrayList<>();
        addressDescriptions.add("Selecione um endereço");
        for (DeliveryAddress address : selectedCustomer.getDeliveryAddresses()) {
            addressDescriptions.add(address.getStreet() + ", " + address.getNumber() + " - " + address.getCity());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, addressDescriptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deliveryAddressSpinner.setAdapter(adapter);
        deliveryAddressSpinner.setEnabled(true);
    }

    private void clearDeliveryAddresses() {
        List<String> emptyList = new ArrayList<>();
        emptyList.add("Primeiro selecione um cliente");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, emptyList);
        deliveryAddressSpinner.setAdapter(adapter);
        deliveryAddressSpinner.setEnabled(false);
    }

    private void showProductSelectionDialog() {
        if (availableProducts.isEmpty()) {
            Toast.makeText(this, "Nenhum produto disponível", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductSelectionDialog dialog = new ProductSelectionDialog(this, availableProducts, product -> {
            addProductToSale(product);
        });
        dialog.show();
    }

    private void addProductToSale(Product product) {
        for (SaleItem item : saleItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                updateSaleItemQuantity(item, item.getQuantity() + 1);
                return;
            }
        }

        SaleItem newItem = new SaleItem(null, null, product, 1, product.getPrice());
        int position = saleItems.size();
        saleItems.add(newItem);
        saleItemAdapter.notifyItemInserted(position);
        updateTotalAmount();
    }

    private void updateSaleItemQuantity(SaleItem item, int newQuantity) {
        if (newQuantity <= 0) {
            removeSaleItem(item);
            return;
        }

        item.setQuantity(newQuantity);
        BigDecimal totalPrice = item.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity));
        item.setPrice(totalPrice);

        int position = saleItems.indexOf(item);
        saleItemAdapter.notifyItemChanged(position);
        updateTotalAmount();
    }

    private void removeSaleItem(SaleItem item) {
        int position = saleItems.indexOf(item);
        saleItems.remove(item);
        saleItemAdapter.notifyItemRemoved(position);
        updateTotalAmount();
    }


    private void updateTotalAmount() {
        totalAmount = BigDecimal.ZERO;
        for (SaleItem item : saleItems) {
            totalAmount = totalAmount.add(item.getPrice());
        }
        totalAmountText.setText("Total: " + currencyFormat.format(totalAmount));
    }

    private void saveSale() {
        if (selectedCustomer == null) {
            Toast.makeText(this, "Selecione um cliente", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedDeliveryAddress == null) {
            Toast.makeText(this, "Selecione um endereço de entrega", Toast.LENGTH_SHORT).show();
            return;
        }

        if (saleItems.isEmpty()) {
            Toast.makeText(this, "Adicione pelo menos um produto", Toast.LENGTH_SHORT).show();
            return;
        }

        Shipping newShipping = new Shipping(null, UUID.randomUUID().toString(), "Não entregue", ShippingStatus.NOT_SHIPPED, selectedDeliveryAddress);
        Payment newPayment = new Payment(null, PaymentMethod.PIX, PaymentStatus.PENDING, totalAmount, new Date());

        Sale newSale = new Sale(null, new Date(), newPayment, selectedCustomer, newShipping, saleItems);

        saleApiService.createSale(newSale).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NewSaleActivity.this, "Venda criada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(NewSaleActivity.this, "Erro ao criar venda: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(NewSaleActivity.this, "Falha ao criar venda: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
package com.banjohann.lojasuplementos;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.banjohann.lojasuplementos.api.ApiClient;
import com.banjohann.lojasuplementos.api.CustomerApiService;
import com.banjohann.lojasuplementos.model.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomersActivity extends AppCompatActivity implements CustomerAdapter.OnCustomerClickListener {

    private RecyclerView customersRecyclerView;
    private CustomerAdapter customerAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private SearchView searchView;
    private View progressBar;
    private CustomerApiService apiService;
    private List<Customer> allCustomers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customersRecyclerView = findViewById(R.id.customersRecyclerView);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        searchView = findViewById(R.id.searchView);
        progressBar = findViewById(R.id.progressBar);
        FloatingActionButton addCustomerFab = findViewById(R.id.addCustomerFab);

        customerAdapter = new CustomerAdapter(new ArrayList<>(), this);
        customersRecyclerView.setAdapter(customerAdapter);

        apiService = ApiClient.getCustomerService();

        swipeRefresh.setColorSchemeResources(R.color.colorPrimaryDark);
        swipeRefresh.setOnRefreshListener(this::loadCustomers);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCustomers(newText);
                return true;
            }
        });

        addCustomerFab.setOnClickListener(v -> {
            Toast.makeText(CustomersActivity.this, "Adicionar novo cliente", Toast.LENGTH_SHORT).show();
        });

        loadCustomers();
    }

    private void loadCustomers() {
        progressBar.setVisibility(View.VISIBLE);

        apiService.getCustomers().enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);

                if (response.isSuccessful() && response.body() != null) {
                    allCustomers = response.body();
                    customerAdapter.updateData(allCustomers);

                    if (allCustomers.isEmpty()) {
                        showEmptyState();
                    }
                } else {
                    Toast.makeText(CustomersActivity.this,
                            "Erro ao carregar clientes: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                Toast.makeText(CustomersActivity.this,
                        "Falha na conexão: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEmptyState() {
        // TODO: Show empty state view
        Toast.makeText(this, "Nenhum cliente encontrado", Toast.LENGTH_SHORT).show();
    }

    private void filterCustomers(String query) {
        if (query == null || query.isEmpty()) {
            customerAdapter.updateData(allCustomers);
            return;
        }

        List<Customer> filteredList = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();

        for (Customer customer : allCustomers) {
            String fullName = customer.getName() + " " + customer.getLastName();
            if (fullName.toLowerCase().contains(lowerCaseQuery) ||
                (customer.getEmail() != null && customer.getEmail().toLowerCase().contains(lowerCaseQuery)) ||
                (customer.getPhone() != null && customer.getPhone().contains(lowerCaseQuery)) ||
                (customer.getCpf() != null && customer.getCpf().contains(lowerCaseQuery))) {
                filteredList.add(customer);
            }
        }

        customerAdapter.updateData(filteredList);
    }

    @Override
    public void onCustomerClick(Customer customer) {
        Intent intent = new Intent(this, CustomerEditActivity.class);
        intent.putExtra("customer", customer);
        startActivity(intent);
        Toast.makeText(this, "Cliente selecionado: " + customer.getName(), Toast.LENGTH_SHORT).show();
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

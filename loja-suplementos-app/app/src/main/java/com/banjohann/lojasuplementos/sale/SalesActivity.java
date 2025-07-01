package com.banjohann.lojasuplementos.sale;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.banjohann.lojasuplementos.R;
import com.banjohann.lojasuplementos.api.ApiClient;
import com.banjohann.lojasuplementos.api.SaleApiService;
import com.banjohann.lojasuplementos.model.Sale;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesActivity extends AppCompatActivity implements SaleAdapter.OnSaleClickListener {

    private RecyclerView salesRecyclerView;
    private SaleAdapter saleAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private SearchView searchView;
    private View progressBar;
    private SaleApiService apiService;
    private List<Sale> allSales = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        salesRecyclerView = findViewById(R.id.salesRecyclerView);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        searchView = findViewById(R.id.searchView);
        progressBar = findViewById(R.id.progressBar);
        FloatingActionButton addSaleFab = findViewById(R.id.addSaleFab);

        saleAdapter = new SaleAdapter(new ArrayList<>(), this);
        salesRecyclerView.setAdapter(saleAdapter);

        apiService = ApiClient.getSaleService();

        swipeRefresh.setColorSchemeResources(R.color.colorPrimaryDark);
        swipeRefresh.setOnRefreshListener(this::loadSales);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSales(newText);
                return true;
            }
        });

        addSaleFab.setOnClickListener(v -> {
            Toast.makeText(SalesActivity.this, "Adicionar nova venda", Toast.LENGTH_SHORT).show();
        });

        loadSales();
    }

    private void loadSales() {
        progressBar.setVisibility(View.VISIBLE);

        apiService.getSales().enqueue(new Callback<List<Sale>>() {
            @Override
            public void onResponse(Call<List<Sale>> call, Response<List<Sale>> response) {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);

                if (response.isSuccessful() && response.body() != null) {
                    allSales = response.body();
                    saleAdapter.updateData(allSales);

                    if (allSales.isEmpty()) {
                        showEmptyState();
                    }
                } else {
                    Toast.makeText(SalesActivity.this,
                            "Erro ao carregar vendas: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Sale>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                Toast.makeText(SalesActivity.this,
                        "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEmptyState() {
        Toast.makeText(this, "Nenhuma venda encontrada", Toast.LENGTH_SHORT).show();
    }

    private void filterSales(String query) {
        if (query == null || query.isEmpty()) {
            saleAdapter.updateData(allSales);
            return;
        }

        List<Sale> filteredList = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();

        for (Sale sale : allSales) {
            String customerName = sale.getCustomer().getName() + " " + sale.getCustomer().getLastName();
            if (customerName.toLowerCase().contains(lowerCaseQuery) ||
                    sale.getId().toString().contains(lowerCaseQuery) ||
                    sale.getPayment().getPaymentMethod().getDescription().toLowerCase().contains(lowerCaseQuery) ||
                    sale.getPayment().getStatus().getDescription().toLowerCase().contains(lowerCaseQuery)) {
                filteredList.add(sale);
            }
        }

        saleAdapter.updateData(filteredList);
    }

    @Override
    public void onSaleClick(Sale sale) {
        Toast.makeText(this, "Venda selecionada: #" + sale.getId(), Toast.LENGTH_SHORT).show();
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

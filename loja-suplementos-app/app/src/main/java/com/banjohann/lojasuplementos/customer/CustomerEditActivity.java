package com.banjohann.lojasuplementos.customer;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.banjohann.lojasuplementos.R;
import com.banjohann.lojasuplementos.api.ApiClient;
import com.banjohann.lojasuplementos.api.CustomerApiService;
import com.banjohann.lojasuplementos.api.Utils;
import com.banjohann.lojasuplementos.model.Customer;
import com.banjohann.lojasuplementos.model.DeliveryAddress;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerEditActivity extends AppCompatActivity {

    private EditText nameInput, lastNameInput, emailInput, phoneInput, cpfInput, birthDateInput;
    private Button saveButton, deleteButton;
    private Customer customer;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private CustomerApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_edit);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInput = findViewById(R.id.editTextName);
        lastNameInput = findViewById(R.id.editTextLastName);
        emailInput = findViewById(R.id.editTextEmail);
        phoneInput = findViewById(R.id.editTextPhone);
        cpfInput = findViewById(R.id.editTextCpf);
        birthDateInput = findViewById(R.id.editTextBirthDate);
        saveButton = findViewById(R.id.buttonSave);
        deleteButton = findViewById(R.id.buttonDelete);

        apiService = ApiClient.getCustomerService();

        customer = (Customer) getIntent().getSerializableExtra("customer");

        if (customer != null) {
            preencherCampos();
        } else {
            deleteButton.setVisibility(View.GONE);
        }

        saveButton.setOnClickListener(v -> salvarCliente());
        deleteButton.setOnClickListener(v -> deletarCliente());

        birthDateInput.setOnClickListener(v -> mostrarDatePicker());
    }

    private void preencherCampos() {
        nameInput.setText(customer.getName());
        lastNameInput.setText(customer.getLastName());
        emailInput.setText(customer.getEmail());
        phoneInput.setText(customer.getPhone());
        cpfInput.setText(customer.getCpf());
        if (customer.getBirthDate() != null) {
            birthDateInput.setText(dateFormat.format(customer.getBirthDate()));
        }
    }

    private void mostrarDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        if (!birthDateInput.getText().toString().isEmpty()) {
            try {
                Date date = dateFormat.parse(birthDateInput.getText().toString());
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    birthDateInput.setText(dateFormat.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void salvarCliente() {
        String name = nameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String email = emailInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String cpf = cpfInput.getText().toString();
        Date birthDate = null;

        try {
            if (!birthDateInput.getText().toString().isEmpty()) {
                birthDate = dateFormat.parse(birthDateInput.getText().toString());
            }
        } catch (ParseException e) {
            Toast.makeText(this, "Data inválida", Toast.LENGTH_SHORT).show();
            return;
        }

        if (name.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(this, "Nome e sobrenome são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (customer == null) {
            Customer novo = new Customer(null, name, lastName, email, phone, cpf, birthDate, new ArrayList<DeliveryAddress>());
            apiService.createCustomer(novo).enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CustomerEditActivity.this, "Cliente criado", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        String erro = Utils.getErrorMessage(response.errorBody());
                        Toast.makeText(CustomerEditActivity.this, "Erro ao salvar cliente: " + erro, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    Toast.makeText(CustomerEditActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            customer.setName(name);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setCpf(cpf);
            customer.setBirthDate(birthDate);

            apiService.updateCustomer(customer.getId(), customer).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CustomerEditActivity.this, "Cliente atualizado", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        String erro = Utils.getErrorMessage(response.errorBody());
                        Toast.makeText(CustomerEditActivity.this, "Erro ao atualizar cliente: " + erro, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CustomerEditActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void deletarCliente() {
        if (customer == null || customer.getId() == null) return;

        apiService.deleteCustomer(customer.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CustomerEditActivity.this, "Cliente excluído", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String erro = Utils.getErrorMessage(response.errorBody());
                    Toast.makeText(CustomerEditActivity.this, "Erro ao excluir cliente: " + erro, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CustomerEditActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

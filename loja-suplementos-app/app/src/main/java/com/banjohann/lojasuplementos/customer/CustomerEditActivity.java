package com.banjohann.lojasuplementos.customer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.EditText;





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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nameInput = findViewById(R.id.editTextName);
        lastNameInput = findViewById(R.id.editTextLastName);
        emailInput = findViewById(R.id.editTextEmail);
        phoneInput = findViewById(R.id.editTextPhone);
        cpfInput = findViewById(R.id.editTextCpf);
        birthDateInput = findViewById(R.id.editTextBirthDate);
        saveButton = findViewById(R.id.buttonSave);
        deleteButton = findViewById(R.id.buttonDelete);

        addListeners();
        apiService = ApiClient.getCustomerService();

        customer = (Customer) getIntent().getSerializableExtra("customer");

        if (customer != null) {
            toolbar.setTitle("Editar cliente");
            preencherCampos();
        } else {
            toolbar.setTitle("Adicionar cliente");
            deleteButton.setVisibility(View.GONE);
        }

        saveButton.setOnClickListener(v -> salvarCliente());
        deleteButton.setOnClickListener(v -> deletarCliente());

        birthDateInput.setOnClickListener(v -> mostrarDatePicker());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addListeners(){
        cpfInput.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString().replaceAll("[^\\d]", "");

                StringBuilder formatted = new StringBuilder();

                int length = str.length();
                int i = 0;

                while (i < length && i < 11) {
                    if (i == 3 || i == 6) {
                        formatted.append('.');
                    } else if (i == 9) {
                        formatted.append('-');
                    }
                    formatted.append(str.charAt(i));
                    i++;
                }

                isUpdating = true;
                cpfInput.setText(formatted.toString());
                cpfInput.setSelection(formatted.length());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        phoneInput.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String digits = s.toString().replaceAll("\\D", "");

                if (digits.length() > 11)
                    digits = digits.substring(0, 11);

                StringBuilder formatted = new StringBuilder();
                int len = digits.length();

                if (len > 0) {
                    formatted.append('(');
                    formatted.append(digits.substring(0, Math.min(2, len)));
                    if (len > 2) {
                        formatted.append(") ");

                        // Para números com 11 dígitos (9XXXX-XXXX)
                        if (len == 11) {
                            formatted.append(digits.substring(2, 7));
                            if (len > 7) {
                                formatted.append('-');
                                formatted.append(digits.substring(7));
                            }
                        }
                        // Para números com 10 dígitos (XXXX-XXXX)
                        else {
                            formatted.append(digits.substring(2, Math.min(6, len)));
                            if (len > 6) {
                                formatted.append('-');
                                formatted.append(digits.substring(6));
                            }
                        }
                    }
                }

                isUpdating = true;
                phoneInput.setText(formatted.toString()); // Corrigido: usar phoneInput
                phoneInput.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private boolean validarCampos() {
        if (nameInput.getText().toString().isEmpty()) {
            nameInput.setError("Nome é obrigatório");
            nameInput.requestFocus();
            return false;
        }

        if (lastNameInput.getText().toString().isEmpty()) {
            lastNameInput.setError("Sobrenome é obrigatório");
            lastNameInput.requestFocus();
            return false;
        }

        if (emailInput.getText().toString().isEmpty()) {
            emailInput.setError("Email é obrigatório");
            emailInput.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput.getText().toString()).matches()) {
            emailInput.setError("Email inválido");
            emailInput.requestFocus();
            return false;
        }

        if (phoneInput.getText().toString().isEmpty()) {
            phoneInput.setError("Telefone é obrigatório");
            phoneInput.requestFocus();
            return false;
        }

        if (phoneInput.getText().toString().replaceAll("\\D", "").length() < 10) {
            phoneInput.setError("Telefone inválido");
            phoneInput.requestFocus();
            return false;
        }

        if (cpfInput.getText().toString().isEmpty()) {
            cpfInput.setError("CPF é obrigatório");
            cpfInput.requestFocus();
            return false;
        }

        if (birthDateInput.getText().toString().isEmpty()) {
            birthDateInput.setError("Data de nascimento é obrigatória");
            birthDateInput.requestFocus();
            return false;
        }

        if (!isValidDate(birthDateInput.getText().toString())) {
            birthDateInput.setError("Formato de data inválido (dd/MM/yyyy)");
            birthDateInput.requestFocus();
            return false;
        }

        return true;
    }
    public boolean isValidDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date date = sdf.parse(dateStr);

            if (date.after(new Date())) return false;

            return true;
        } catch (ParseException e) {
            return false;
        }
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
        String cpf = cpfInput.getText().toString().replaceAll("\\D", "");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date birthDate;
        sdf.setLenient(false);
        try {
            birthDate = sdf.parse(birthDateInput.getText().toString());
        }
        catch (ParseException e){
            birthDateInput.setError("Data de nascimento inválida");
            birthDateInput.requestFocus();
            return;
        }
            if (!validarCampos())
                return;

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

                        startActivity(new Intent(CustomerEditActivity.this, CustomersActivity.class));
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        Log.e("CustomerEditActivity", "Erro ao criar cliente", t);
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
                        } else {
                            String erro = Utils.getErrorMessage(response.errorBody());
                            Toast.makeText(CustomerEditActivity.this, "Erro ao atualizar cliente: " + erro, Toast.LENGTH_SHORT).show();
                        }

                        startActivity(new Intent(CustomerEditActivity.this, CustomersActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("CustomerEditActivity", "Erro ao criar cliente", t);
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
                Log.e("CustomerEditActivity", "Erro ao criar cliente", t);
            }
        });
    }

}

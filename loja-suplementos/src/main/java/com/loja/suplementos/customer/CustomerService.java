package com.loja.suplementos.customer;

import com.loja.suplementos.customer.domain.Customer;
import com.loja.suplementos.address.DeliveryAddress;
import com.loja.suplementos.customer.repository.CustomerRepository;
import com.loja.suplementos.address.repository.DeliveryAddressRepository;
import com.loja.suplementos.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer customer) {
        customerValidation(customer);
        Customer existingCustomer = customerRepository.findByEmailOrCpf(customer.getEmail(), customer.getCpf());
        if (existingCustomer != null) {
            if (existingCustomer.getCpf().equals(customer.getCpf())) {
                throw new IllegalArgumentException("CPF já cadastrado");
            }
            if (existingCustomer.getEmail().equals(customer.getEmail())) {
                throw new IllegalArgumentException("Email já cadastrado");
            }
        }

        customerRepository.save(customer);
        return customer;
    }

    public void save(Map<String, String> params) {
        var email = params.get("email");
        var cpf = params.get("cpf");

        Customer existingCustomer = customerRepository.findByEmailOrCpf(email, cpf);
        if (existingCustomer != null) {
            if (existingCustomer.getCpf().equals(cpf)) throw new IllegalArgumentException("CPF já cadastrado");

            if (existingCustomer.getEmail().equals(email)) throw new IllegalArgumentException("Email já cadastrado");
        }

        Customer customer = Customer
            .builder()
            .name(params.get("name"))
            .lastName(params.get("lastName"))
            .email(params.get("email"))
            .phone(params.get("phoneNumber"))
            .birthDate(Utils.convertStringToDate(params.get("birthDate")))
            .cpf(params.get("cpf"))
            .build();

        customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
    }

    public void update(Map<String, String> params) {
        var email = params.get("email");
        var cpf = params.get("cpf");

        Customer existingCustomer = customerRepository
            .findById(Long.valueOf(params.get("id")))
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        if (!existingCustomer.getCpf().equals(cpf)) throw new IllegalArgumentException("Não é possível alterar o CPF");
        if (!existingCustomer.getEmail().equals(email)) throw new IllegalArgumentException("Não é possível alterar o email");

        existingCustomer.setName(params.get("name"));
        existingCustomer.setLastName(params.get("lastName"));
        existingCustomer.setBirthDate(Utils.convertStringToDate(params.get("birthDate")));
        existingCustomer.setPhone(params.get("phoneNumber"));

        customerRepository.save(existingCustomer);
    }

    public void update(Long id, Customer customer) {
        Customer existingCustomer = customerRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        customerValidation(customer);

        existingCustomer.setName(customer.getName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setBirthDate(customer.getBirthDate());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setCpf(customer.getCpf());

        customerRepository.save(existingCustomer);
    }

    public void delete(Long customerId) {
        var customer = customerRepository
            .findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        customerRepository.delete(customer);
    }

    private void customerValidation(Customer customer) {
        if (customer.getName() == null || customer.getName().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }

        if (!customer.getEmail().matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
            throw new IllegalArgumentException("Email inválido");
        }

        if (customer.getPhone() == null || customer.getPhone().isBlank()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }

        if (!customer.getPhone().matches("^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-?\\d{4}$")) {
            throw new IllegalArgumentException("Telefone inválido (formato esperado: (XX)XXXXX-XXXX)");
        }

        if (customer.getCpf() == null || customer.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}

package com.banjohann.lojasuplementos.api;

import com.banjohann.lojasuplementos.model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomerApiService {
    @GET("customers")
    Call<List<Customer>> getCustomers();

    @GET("customers/{id}")
    Call<Customer> getCustomerById(@Path("id") int id);

    @POST("customers")
    Call<Customer> createCustomer(@Body Customer customer);

    @PUT("customers/{id}")
    Call<Void> updateCustomer(@Path("id") int id, @Body Customer customer);

    @DELETE("customers/{id}")
    Call<Void> deleteCustomer(@Path("id") int id);
}

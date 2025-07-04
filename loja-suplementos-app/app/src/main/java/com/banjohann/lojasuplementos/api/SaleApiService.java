package com.banjohann.lojasuplementos.api;

import com.banjohann.lojasuplementos.model.Sale;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SaleApiService {
    @GET("sales")
    Call<List<Sale>> getSales();

    @GET("sales/{id}")
    Call<Sale> getSaleById(@Path("id") Long id);

    @POST("sales/app")
    Call<Void> createSale(@Body Sale sale);
}

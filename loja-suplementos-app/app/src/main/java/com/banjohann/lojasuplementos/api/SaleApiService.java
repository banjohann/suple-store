package com.banjohann.lojasuplementos.api;

import com.banjohann.lojasuplementos.model.Sale;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SaleApiService {
    @GET("sales")
    Call<List<Sale>> getSales();

    @POST("sales/mobile")
    Call<Void> createSale(Sale sale);
}

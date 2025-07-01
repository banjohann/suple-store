package com.banjohann.lojasuplementos.api;

import com.banjohann.lojasuplementos.model.Sale;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SaleApiService {
    @GET("sales")
    Call<List<Sale>> getSales();
}

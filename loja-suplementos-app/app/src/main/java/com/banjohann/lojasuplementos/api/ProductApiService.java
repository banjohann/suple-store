package com.banjohann.lojasuplementos.api;


import com.banjohann.lojasuplementos.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductApiService {

    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products/barcode/{barcode}")
    Call<Product> getProductByBarcode(@Path("barcode") String barcode);
}

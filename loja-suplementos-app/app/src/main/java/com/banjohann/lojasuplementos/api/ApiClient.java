package com.banjohann.lojasuplementos.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //Troque a URL pelo IP do seu server
    private static final String BASE_URL = "http://10.0.2.2:8080/api/";;
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static CustomerApiService getCustomerService() {
        return getClient().create(CustomerApiService.class);
    }

    public static SaleApiService getSaleService() {
        return getClient().create(SaleApiService.class);
    }
}

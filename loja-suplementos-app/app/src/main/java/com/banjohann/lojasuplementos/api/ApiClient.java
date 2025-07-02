package com.banjohann.lojasuplementos.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //Troque a URL pelo IP do seu server
    private static final String BASE_URL = "http://10.0.2.2:8080/mobile/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
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

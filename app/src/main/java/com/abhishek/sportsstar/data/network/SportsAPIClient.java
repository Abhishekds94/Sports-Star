package com.abhishek.sportsstar.data.network;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SportsAPIClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/";

    public static SportsAPI getClient(Context context) {

        if (retrofit != null) {
            return retrofit.create(SportsAPI.class);
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new NetworkConnectionInterceptor(context))
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(SportsAPI.class);
    }
}

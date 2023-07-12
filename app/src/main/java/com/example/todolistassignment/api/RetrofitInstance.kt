package com.example.androidapiproject.api

import com.example.androidapiproject.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    // We can modify http request inside httpClient
    private val httpClient by lazy {
        OkHttpClient.Builder()
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
    }

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(Constants.BASE_URL).client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val api:SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}
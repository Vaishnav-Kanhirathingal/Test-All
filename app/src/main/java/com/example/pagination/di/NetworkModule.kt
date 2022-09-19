package com.example.pagination.di

import com.example.pagination.retro.QuotesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://quotable.io/"

class NetworkModule {
    fun getRetroFit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getQuoteApi(retrofit: Retrofit): QuotesApi {
        return retrofit.create(QuotesApi::class.java)
    }
}
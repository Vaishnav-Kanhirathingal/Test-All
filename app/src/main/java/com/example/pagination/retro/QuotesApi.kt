package com.example.pagination.retro

import com.example.pagination.model.QuoteList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface QuotesApi {
    @GET("/quotes")
    suspend fun getQuotes(@Query("page") page: Int): QuoteList

    companion object {
        val retroFitApi: QuotesApi = Retrofit
            .Builder()
            .baseUrl("https://quotable.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuotesApi::class.java)
    }
}
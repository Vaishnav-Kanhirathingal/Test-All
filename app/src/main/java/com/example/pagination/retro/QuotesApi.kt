package com.example.pagination.retro

import com.example.pagination.model.QuoteList
import retrofit2.http.GET
import retrofit2.http.Query

interface QuotesApi {
    @GET("/quotes")
    suspend fun getQuotes(@Query("page") page: Int): QuoteList
}
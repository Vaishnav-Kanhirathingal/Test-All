package com.example.pagination.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.pagination.model.Result
import com.example.pagination.paging.QuotePagingSource
import com.example.pagination.retro.QuotesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://quotable.io/"

class QuoteRepository() {
    fun getQuotes(): LiveData<PagingData<Result>> {
        val quotesApi = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuotesApi::class.java)
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = { QuotePagingSource(quotesApi = quotesApi) }
        ).liveData
    }
}


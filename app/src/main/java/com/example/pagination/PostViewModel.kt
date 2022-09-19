package com.example.pagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.pagination.paging.QuotePagingSource
import com.example.pagination.retro.QuotesApi

class PostViewModel() : ViewModel() {
    val pagingData = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { QuotePagingSource(quotesApi = QuotesApi.retroFitApi) }
    ).liveData.cachedIn(viewModelScope)
}
package com.example.pagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pagination.repository.QuoteRepository

class PostViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {
    val list = quoteRepository.getQuotes().cachedIn(viewModelScope)
}
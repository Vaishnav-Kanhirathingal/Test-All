package com.example.pagination

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.pagination.databinding.ActivityMainBinding
import com.example.pagination.paging.PostPagingAdapter
import com.example.pagination.paging.QuotePagingSource
import com.example.pagination.retro.QuotesApi

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**
         * a PagingDataAdapter is similar to a list adapter. It loads data as and when required.
         */
        val adapter = PostPagingAdapter()
        binding.quotesList.setHasFixedSize(true)
        binding.quotesList.adapter = adapter

        Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = { QuotePagingSource(quotesApi = QuotesApi.retroFitApi) }
        ).liveData.cachedIn(this.lifecycleScope).observe(this) {
            /**
             * here, the paging data object takes paging source as a parameter. That paging source
             * has the responsibility of loading a list of Results at a time.
             */
            Log.d(this::class.java.simpleName, "observer called, pagingData updated")
            adapter.submitData(lifecycle, it)
        }
    }
}
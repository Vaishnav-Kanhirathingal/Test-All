package com.example.pagination

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pagination.databinding.ActivityMainBinding
import com.example.pagination.paging.PostPagingAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = PostViewModel()
        binding.quotesList.setHasFixedSize(true)

        /**
         * a PagingDataAdapter is similar to a list adapter. It loads data as and when required.
         */
        val adapter = PostPagingAdapter()
        binding.quotesList.adapter = adapter
        viewModel.pagingData.observe(this) {
            /**
             * here, the data received from the view-model is a paging data object which takes
             * paging source as a parameter. That paging source has the responsibility of loading
             * a list of Results at a time.
             */
            Log.d(this::class.java.simpleName, "observer called, pagingData updated")
            adapter.submitData(lifecycle, it)
        }
    }
}
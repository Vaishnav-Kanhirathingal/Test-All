package com.example.pagination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pagination.databinding.ActivityMainBinding
import com.example.pagination.paging.PostPagingAdapter
import com.example.pagination.repository.QuoteRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = PostViewModel(QuoteRepository())
        binding.quotesList.setHasFixedSize(true)
        val adapter = PostPagingAdapter()
        binding.quotesList.adapter = adapter
        viewModel.list.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}
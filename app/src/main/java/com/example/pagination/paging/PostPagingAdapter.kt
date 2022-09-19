package com.example.pagination.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagination.databinding.QuotesItemBinding
import com.example.pagination.model.Result

class PostPagingAdapter : PagingDataAdapter<Result, PostPagingAdapter.PostViewHolder>(diffUtil) {
    class PostViewHolder(private val binding: QuotesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result?) {
            binding.quote.text = result?.content ?: "not found"
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result) =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: Result, newItem: Result) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            QuotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
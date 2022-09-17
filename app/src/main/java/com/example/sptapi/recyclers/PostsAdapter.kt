package com.example.sptapi.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sptapi.api_resource.Posts
import com.example.sptapi.databinding.PostsListItemBinding

class PostsAdapter : ListAdapter<Posts, PostsAdapter.ViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Posts>() {
            override fun areContentsTheSame(oldItem: Posts, newItem: Posts) = (oldItem == newItem)
            override fun areItemsTheSame(oldItem: Posts, newItem: Posts) =
                (oldItem.id == newItem.id)
        }
    }

    class ViewHolder(private val binding: PostsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(posts: Posts) {
            binding.apply {
                id.text = posts.id.toString()
                title.text = posts.title
                userId.text = posts.userId.toString()
                body.text = posts.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PostsListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
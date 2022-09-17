package com.example.sptapi.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sptapi.api_resource.Comments
import com.example.sptapi.databinding.CommentListItemBinding

class CommentAdapter() : ListAdapter<Comments, CommentAdapter.CommentViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Comments>() {
            override fun areContentsTheSame(oldItem: Comments, newItem: Comments) =
                (oldItem == newItem)

            override fun areItemsTheSame(oldItem: Comments, newItem: Comments) =
                (oldItem.id == newItem.id)
        }
    }

    class CommentViewHolder(private val binding: CommentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comments: Comments) {
            binding.apply {
                postId.text = comments.postId.toString()
                name.text = comments.name
                postNumber.text = comments.id.toString()
                email.text = comments.email
                body.text = comments.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            CommentListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentAdapter.CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
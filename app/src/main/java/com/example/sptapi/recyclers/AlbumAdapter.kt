package com.example.sptapi.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sptapi.api_resource.Albums
import com.example.sptapi.databinding.AlbumListItemBinding

class AlbumAdapter : ListAdapter<Albums, AlbumAdapter.ViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Albums>() {
            override fun areContentsTheSame(oldItem: Albums, newItem: Albums) = (oldItem == newItem)
            override fun areItemsTheSame(oldItem: Albums, newItem: Albums) =
                (oldItem.id == newItem.id)
        }
    }

    class ViewHolder(private val binding: AlbumListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(albums: Albums) {
            binding.apply {
                idText.text = albums.id.toString()
                albumTitle.text = albums.title
                userIdText.text = albums.userId.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AlbumListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
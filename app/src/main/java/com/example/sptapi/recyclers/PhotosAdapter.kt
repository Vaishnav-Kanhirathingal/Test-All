package com.example.sptapi.recyclers

import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.sptapi.api_resource.Photos
import com.example.sptapi.databinding.PhotosListItemBinding
import com.example.sptapi.databinding.PromptViewFullImageBinding

class PhotosAdapter(
    private val layoutInflater: LayoutInflater,
    private val context: Context
) :
    ListAdapter<Photos, PhotosAdapter.ViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Photos>() {
            override fun areContentsTheSame(oldItem: Photos, newItem: Photos) = (oldItem == newItem)
            override fun areItemsTheSame(oldItem: Photos, newItem: Photos) =
                (oldItem.id == newItem.id)
        }
    }

    class ViewHolder(
        private val binding: PhotosListItemBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photos: Photos, layoutInflater: LayoutInflater) {
            binding.apply {
                idText.text = photos.id.toString()
                title.text = photos.title
                albumId.text = photos.albumId.toString()
                thumbnail.load(photos.thumbnailUrl)
                thumbnail.setOnClickListener {
                    val promptBinding = PromptViewFullImageBinding.inflate(layoutInflater)
                    promptBinding.imageTitle.text = photos.title
                    promptBinding.fullViewImage.load(photos.url)
                    val dialogBox = Dialog(context)
                    dialogBox.apply {
                        setContentView(promptBinding.root)
                        window!!.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                        setCancelable(true)
                        show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PhotosListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            context
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), layoutInflater)
    }
}
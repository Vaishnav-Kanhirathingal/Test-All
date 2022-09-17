package com.example.sptapi.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sptapi.R
import com.example.sptapi.api_resource.Todos
import com.example.sptapi.databinding.TodosListItemBinding

class TodosAdapter : ListAdapter<Todos, TodosAdapter.ViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Todos>() {
            override fun areContentsTheSame(oldItem: Todos, newItem: Todos) = (oldItem == newItem)
            override fun areItemsTheSame(oldItem: Todos, newItem: Todos) =
                (oldItem.id == newItem.id)
        }
    }

    class ViewHolder(private val binding: TodosListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todos: Todos) {
            binding.apply {
                idText.text = todos.id.toString()
                title.text = todos.title
                userId.text = todos.userId.toString()
                completedImage.setImageResource(
                    if (todos.completed) R.drawable.check_circle_24 else R.drawable.cancel_24
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodosListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
package com.example.sptapi.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sptapi.api_resource.Users
import com.example.sptapi.databinding.UsersLitItemBinding

class UsersAdapter : ListAdapter<Users, UsersAdapter.ViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Users>() {
            override fun areContentsTheSame(oldItem: Users, newItem: Users) = (oldItem == newItem)
            override fun areItemsTheSame(oldItem: Users, newItem: Users) =
                (oldItem.id == newItem.id)
        }
    }

    class ViewHolder(private val binding: UsersLitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Users) {
            binding.apply {
                idText.text = users.id.toString()
                userName.text = users.username
                name.text = users.name
                email.text = users.email
                street.text = users.address.street
                suite.text = users.address.suite
                city.text = users.address.city
                zipCode.text = users.address.zipcode
                latitude.text = users.address.geo.lat
                longitude.text = users.address.geo.lng
                phone.text = users.phone
                website.text = users.website
                companyName.text = users.company.name
                companyCatchPhrase.text = users.company.catchPhrase
                companyBs.text = users.company.bs
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UsersLitItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
package com.dicoding.githubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.ItemUserBinding
import com.dicoding.githubuser.ui.activity.UserDetailActivity

class UserAdapter: ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        holder.itemView.setOnClickListener {
            val detailUser = Intent(holder.itemView.context, UserDetailActivity::class.java)
            detailUser.putExtra("username", user.login)
            holder.itemView.context.startActivity(detailUser)
        }
    }

    class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: ItemsItem) {
            binding.tvUsername.text = profile.login
            Glide.with(binding.imgUserPhoto.context)
                .load(profile.avatarUrl)
                .into(binding.imgUserPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
package com.dicoding.githubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.database.Favorite
import com.dicoding.githubuser.databinding.ItemUserBinding
import com.dicoding.githubuser.ui.activity.UserDetailActivity

class FavoriteAdapter: ListAdapter<Favorite, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        holder.itemView.setOnClickListener {
            val detailUser = Intent(holder.itemView.context, UserDetailActivity::class.java)
            detailUser.putExtra("username", user.username)
            holder.itemView.context.startActivity(detailUser)
        }
    }

    class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: Favorite) {
            binding.tvUsername.text = profile.username
            Glide.with(binding.imgUserPhoto.context)
                .load(profile.avatarUrl)
                .into(binding.imgUserPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem.username == newItem.username
            }
        }
    }
}
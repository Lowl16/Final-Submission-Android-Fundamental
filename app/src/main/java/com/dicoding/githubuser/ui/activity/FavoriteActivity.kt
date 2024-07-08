package com.dicoding.githubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.database.Favorite
import com.dicoding.githubuser.databinding.ActivityFavoriteBinding
import com.dicoding.githubuser.ui.adapter.FavoriteAdapter
import com.dicoding.githubuser.ui.viewmodel.FavoriteViewModel
import com.dicoding.githubuser.ui.viewmodel.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite GitHub User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory: FavoriteViewModelFactory = FavoriteViewModelFactory.getInstance(this@FavoriteActivity)
        val favoriteViewModel: FavoriteViewModel by viewModels { factory }

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        favoriteViewModel.getAllFavorites().observe(this) { data ->
            setFavoriteData(data)
        }
    }

    private fun setFavoriteData(user: List<Favorite>) {
        val adapter = FavoriteAdapter()
        adapter.submitList(user)
        binding.rvFavorite.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
package com.dicoding.githubuser.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.response.GitHubUserDetailResponse
import com.dicoding.githubuser.database.Favorite
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.dicoding.githubuser.ui.adapter.SectionPagerAdapter
import com.dicoding.githubuser.ui.viewmodel.FavoriteViewModel
import com.dicoding.githubuser.ui.viewmodel.FavoriteViewModelFactory
import com.dicoding.githubuser.ui.viewmodel.UserDetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val userDetailViewModel by viewModels<UserDetailViewModel>()
    private lateinit var adapter: SectionPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "GitHub User Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra("username")

        userDetailViewModel.detailUser(username.toString())

        userDetailViewModel.user.observe(this) { user ->
            setUser(user)
        }

        userDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        adapter = sectionPagerAdapter

        if (username != null) {
            adapter.username = username
        }

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        val tabLayout: TabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val factory: FavoriteViewModelFactory = FavoriteViewModelFactory.getInstance(this@UserDetailActivity)
        val favoriteViewModel: FavoriteViewModel by viewModels { factory }

        favoriteViewModel.getFavoriteUserByUsername(username.toString()).observe(this) { existingUser ->
            val isFavorite = existingUser.isNotEmpty()
            val favoriteDrawable = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            binding.fab.setImageDrawable(ContextCompat.getDrawable(binding.fab.context, favoriteDrawable))

            binding.fab.setOnClickListener{
                val data = userDetailViewModel.user.value
                val favorite = Favorite(
                    data!!.login,
                    data.avatarUrl
                )

                if (isFavorite) {
                    favoriteViewModel.delete(favorite.username)
                } else {
                    favoriteViewModel.insert(favorite)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }

    @SuppressLint("SetTextI18n")
    private fun setUser(user: GitHubUserDetailResponse) {
        with(binding) {
            tvName.text = user.name
            tvUser.text = user.login
            tvFollowers.text = "${user.followers} Followers"
            tvRepositories.text = "${user.publicRepos} Repositories"
            tvFollowing.text = "${user.following} Following"
            Glide.with(this@UserDetailActivity)
                .load(user.avatarUrl)
                .into(imgUserPhoto)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuShare -> {
                val url = userDetailViewModel.user.value?.htmlUrl
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, url)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(intent, "Share GitHub User URL"))
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}
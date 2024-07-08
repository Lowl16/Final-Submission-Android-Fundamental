package com.dicoding.githubuser.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.preferences.SettingPreferences
import com.dicoding.githubuser.preferences.dataStore
import com.dicoding.githubuser.ui.adapter.UserAdapter
import com.dicoding.githubuser.ui.viewmodel.SettingViewModel
import com.dicoding.githubuser.ui.viewmodel.UserViewModel
import com.dicoding.githubuser.ui.viewmodel.SettingViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        darkModeCheck()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    if (searchBar.text?.isEmpty() == true) {
                        userViewModel.findUser()
                    } else {
                        userViewModel.findUser(searchBar.text.toString())
                    }
                    false
                }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        userViewModel.user.observe(this) { user ->
            setUserData(user)
        }

        userViewModel.isLoading.observe(this) { loading ->
            showLoading(loading)
        }

        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuFavorite -> {
                    val favoriteIntent = Intent(this, FavoriteActivity::class.java)
                    startActivity(favoriteIntent)
                    true
                }
                R.id.menuSetting -> {
                    val settingIntent = Intent(this, SettingActivity::class.java)
                    startActivity(settingIntent)
                    true
                }
                else -> false
            }
        }
    }

    private fun darkModeCheck() {
        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setUserData(user: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }
}
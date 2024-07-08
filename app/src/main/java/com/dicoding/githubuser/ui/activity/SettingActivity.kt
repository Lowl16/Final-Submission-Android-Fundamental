package com.dicoding.githubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivitySettingBinding
import com.dicoding.githubuser.preferences.SettingPreferences
import com.dicoding.githubuser.preferences.dataStore
import com.dicoding.githubuser.ui.viewmodel.SettingViewModel
import com.dicoding.githubuser.ui.viewmodel.SettingViewModelFactory

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Setting"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val switchTheme = binding.switchTheme

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
                binding.imgDarkMode.setImageResource(R.drawable.ic_dark_mode_dark)
                binding.imgLightMode.setImageResource(R.drawable.ic_light_mode_dark)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
                binding.imgDarkMode.setImageResource(R.drawable.ic_dark_mode_light)
                binding.imgLightMode.setImageResource(R.drawable.ic_light_mode_light)
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
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
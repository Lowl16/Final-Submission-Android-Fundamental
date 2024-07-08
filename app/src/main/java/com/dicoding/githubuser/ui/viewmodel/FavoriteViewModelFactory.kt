package com.dicoding.githubuser.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.di.Injection
import com.dicoding.githubuser.repository.FavoriteRepository

class FavoriteViewModelFactory private constructor(private val favoriteRepository: FavoriteRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: FavoriteViewModelFactory? = null

        fun getInstance(context: Context): FavoriteViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteViewModelFactory(Injection.provideRepository(context))
            }.also { INSTANCE = it }
    }
}
package com.dicoding.githubuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuser.database.Favorite
import com.dicoding.githubuser.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    fun insert(favorite: Favorite) = viewModelScope.launch {
        favoriteRepository.insert(favorite)
    }

    fun delete(username: String) = viewModelScope.launch {
        favoriteRepository.delete(username)
    }

    fun getFavoriteUserByUsername(username: String) = favoriteRepository.getFavoriteUserByUsername(username)

    fun getAllFavorites() = favoriteRepository.getAllFavorites()
}
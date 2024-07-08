package com.dicoding.githubuser.repository

import androidx.lifecycle.LiveData
import com.dicoding.githubuser.database.Favorite
import com.dicoding.githubuser.database.FavoriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteRepository private constructor(private val favoriteDao: FavoriteDao) {

    suspend fun insert(favorite: Favorite) = withContext(Dispatchers.IO) {
        favoriteDao.insert(favorite)
    }

    suspend fun delete(username: String) = withContext(Dispatchers.IO) {
        favoriteDao.delete(username)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<List<Favorite>> = favoriteDao.getFavoriteUserByUsername(username)

    fun getAllFavorites(): LiveData<List<Favorite>> = favoriteDao.getAllFavorites()

    companion object {
        @Volatile
        private var INSTANCE: FavoriteRepository? = null

        fun getInstance(
            favoriteDao: FavoriteDao
        ): FavoriteRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteRepository(favoriteDao)
            }.also { INSTANCE = it }
    }
}
package com.dicoding.githubuser.di

import android.content.Context
import com.dicoding.githubuser.database.FavoriteRoomDatabase
import com.dicoding.githubuser.repository.FavoriteRepository

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val database = FavoriteRoomDatabase.getInstance(context)
        val dao = database.favoriteDao()
        return FavoriteRepository.getInstance(dao)
    }
}
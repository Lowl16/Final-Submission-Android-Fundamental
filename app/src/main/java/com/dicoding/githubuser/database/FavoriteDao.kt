package com.dicoding.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Query("DELETE FROM Favorite WHERE username = :username")
    fun delete(username: String)

    @Query("SELECT username FROM Favorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<List<Favorite>>

    @Query("SELECT * FROM Favorite")
    fun getAllFavorites(): LiveData<List<Favorite>>
}
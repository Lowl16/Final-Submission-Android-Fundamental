package com.dicoding.githubuser.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite")
class Favorite(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var username: String = "",

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null
)
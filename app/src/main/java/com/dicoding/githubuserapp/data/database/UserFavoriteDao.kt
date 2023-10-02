package com.dicoding.githubuserapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: UserFavorite)

    @Query("DELETE FROM favorite_users WHERE login = :favoriteUser")
    fun delete(favoriteUser: String)

    @Query("SELECT * FROM favorite_users WHERE login = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<UserFavorite>

    @Query("SELECT * FROM favorite_users")
    fun getFavoriteUsers(): LiveData<List<UserFavorite>>
}
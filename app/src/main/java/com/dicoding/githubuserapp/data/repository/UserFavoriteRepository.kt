package com.dicoding.githubuserapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubuserapp.data.database.UserFavorite
import com.dicoding.githubuserapp.data.database.UserFavoriteDao
import com.dicoding.githubuserapp.data.database.UserFavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserFavoriteRepository(application: Application) {

    private val userFavoriteDao: UserFavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserFavoriteRoomDatabase.getDatabase(application)

        userFavoriteDao = db.userFavoriteDao()
    }

    fun insert(favoriteUser: UserFavorite) {
        executorService.execute { userFavoriteDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: String) {
        executorService.execute { userFavoriteDao.delete(favoriteUser) }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<UserFavorite> =
        userFavoriteDao.getFavoriteUserByUsername(username)

    fun getFavoriteUsers() = userFavoriteDao.getFavoriteUsers()



}
package com.dicoding.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.database.UserFavorite
import com.dicoding.githubuserapp.data.repository.UserFavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val userFavoriteRepository: UserFavoriteRepository = UserFavoriteRepository(application)
    fun getFavoriteUsers(): LiveData<List<UserFavorite>> = userFavoriteRepository.getFavoriteUsers()

}
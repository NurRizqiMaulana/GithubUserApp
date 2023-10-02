package com.dicoding.githubuserapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.database.UserFavorite
import com.dicoding.githubuserapp.data.repository.UserFavoriteRepository
import com.dicoding.githubuserapp.data.response.DetailResponseUsers
import com.dicoding.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val userFavoriteRepository: UserFavoriteRepository = UserFavoriteRepository(application)

    private val _user = MutableLiveData<DetailResponseUsers?>()
    val user: MutableLiveData<DetailResponseUsers?> = _user


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getUser(username: String) {

        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailResponseUsers> {
            override fun onResponse(
                call: Call<DetailResponseUsers>,
                response: Response<DetailResponseUsers>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        _user.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponseUsers>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    fun insert(userFavorite: UserFavorite) {
        userFavoriteRepository.insert(userFavorite)
    }

    fun delete(userFavorite: String) {
        userFavoriteRepository.delete(userFavorite)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<UserFavorite> =
        userFavoriteRepository.getFavoriteUserByUsername(username)

}
package com.dicoding.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.GitHubUserResponse
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {

    private val _user = MutableLiveData<List<ItemsItem>>()
    val user: LiveData<List<ItemsItem>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findUser()
    }

    fun findUser(username: String = USER) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<GitHubUserResponse> {
            override fun onResponse(
                call: Call<GitHubUserResponse>,
                response: Response<GitHubUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _user.value = response.body()?.items
                    }
                } else {
                    when (response.code()) {
                        401 -> Log.e("UserDetailModel", "onFailure: Bad Request")
                        403 -> Log.e("UserDetailModel", "onFailure: Forbidden")
                        404 -> Log.e("UserDetailModel", "onFailure: Not Found")
                        else -> Log.e("UserDetailModel", "onFailure: ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<GitHubUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val USER = "arif"
    }
}
package com.dicoding.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.GitHubUserDetailResponse
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {

    private val _user = MutableLiveData<GitHubUserDetailResponse>()
    var user: LiveData<GitHubUserDetailResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingFollow = MutableLiveData<Boolean>()
    var isLoadingFollow: LiveData<Boolean> = _isLoadingFollow

    private val _userFollowers = MutableLiveData<List<ItemsItem>>()
    var userFollowers: LiveData<List<ItemsItem>> = _userFollowers

    private val _userFollowing = MutableLiveData<List<ItemsItem>>()
    var userFollowing: LiveData<List<ItemsItem>> = _userFollowing

    fun detailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().detailUser(username)
        client.enqueue(object : Callback<GitHubUserDetailResponse> {
            override fun onResponse(
                call: Call<GitHubUserDetailResponse>,
                response: Response<GitHubUserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _user.value = response.body()
                } else {
                    when (response.code()) {
                        401 -> Log.e("UserDetailModel", "onFailure: Bad Request")
                        403 -> Log.e("UserDetailModel", "onFailure: Forbidden")
                        404 -> Log.e("UserDetailModel", "onFailure: Not Found")
                        else -> Log.e("UserDetailModel", "onFailure: ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<GitHubUserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("UsersViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun findFollowers(username: String) {
        _isLoadingFollow.value = true
        val client = ApiConfig.getApiService().userFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollow.value = false
                if (response.isSuccessful){
                    _userFollowers.value = response.body()
                } else {
                    when (response.code()) {
                        401 -> Log.e("UserDetailModel", "onFailure: Bad Request")
                        403 -> Log.e("UserDetailModel", "onFailure: Forbidden")
                        404 -> Log.e("UserDetailModel", "onFailure: Not Found")
                        else -> Log.e("UserDetailModel", "onFailure: ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollow.value = false
                Log.e("UserDetailModel", "onFailure: ${t.message}")
            }
        })
    }

    fun findFollowing(username: String) {
        _isLoadingFollow.value = true
        val client = ApiConfig.getApiService().userFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollow.value = false
                if (response.isSuccessful){
                    _userFollowing.value = response.body()
                } else {
                    when (response.code()) {
                        401 -> Log.e("UserDetailModel", "onFailure: Bad Request")
                        403 -> Log.e("UserDetailModel", "onFailure: Forbidden")
                        404 -> Log.e("UserDetailModel", "onFailure: Not Found")
                        else -> Log.e("UserDetailModel", "onFailure: ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollow.value = false
                Log.e("UserDetailModel", "onFailure: ${t.message}")
            }
        })
    }
}
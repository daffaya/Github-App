package com.example.navigationgithub.Fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationgithub.Api.ApiConfig
import com.example.navigationgithub.Fragment.FollowersFragment.Companion.follower
import com.example.navigationgithub.Response.DetailUserResponse
import com.example.navigationgithub.Response.SearchUser
import com.example.navigationgithub.UserDetailViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {

    private val _detailFollower = MutableLiveData<List<SearchUser>>(listOf())
    val detailFollower: LiveData<List<SearchUser>> = _detailFollower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "FollowerViewModel"
        private const val USER_ID = "daffaya"
    }

    fun findFollower(follower: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(follower)
        client.enqueue(object : Callback<List<SearchUser>> {
            override fun onResponse(
                call: Call<List<SearchUser>>,
                response: Response<List<SearchUser>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _detailFollower.value = response.body()
                } else {
                    Log.e(UserDetailViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<SearchUser>>, t: Throwable) {
                _isLoading.value = false
                Log.e(UserDetailViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}
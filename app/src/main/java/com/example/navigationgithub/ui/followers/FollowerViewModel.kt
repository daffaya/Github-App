package com.example.navigationgithub.ui.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationgithub.data.response.SearchUser
import com.example.navigationgithub.ui.userDetail.UserDetailViewModel
import com.example.navigationgithub.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {

    private val _detailFollower = MutableLiveData<List<SearchUser>>(listOf())
    val detailFollower: LiveData<List<SearchUser>> = _detailFollower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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
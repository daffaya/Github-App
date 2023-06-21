package com.example.navigationgithub.Fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationgithub.Api.ApiConfig
import com.example.navigationgithub.Response.SearchUser
import com.example.navigationgithub.UserDetailViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {

    private val _detailFollowing = MutableLiveData<List<SearchUser>>(listOf())
    val detailFollowing: LiveData<List<SearchUser>> = _detailFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "FollowingViewModel"
        private const val USER_ID = "daffaya"
    }

    fun findFollowing(following: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(following)
        client.enqueue(object : Callback<List<SearchUser>> {
            override fun onResponse(
                call: Call<List<SearchUser>>,
                response: Response<List<SearchUser>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _detailFollowing.value = response.body()
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
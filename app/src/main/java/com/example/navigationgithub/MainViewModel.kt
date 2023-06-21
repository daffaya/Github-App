package com.example.navigationgithub

import android.app.Application
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.*
import com.example.navigationgithub.Api.ApiConfig
import com.example.navigationgithub.Preference.SettingPreferences
import com.example.navigationgithub.Response.SearchUser
import com.example.navigationgithub.Response.SearchUserResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application, dataStore: DataStore<Preferences>) : ViewModel() {
    private val _searchUser = MutableLiveData<List<SearchUser>>()
    val searchUser: LiveData<List<SearchUser>> = _searchUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val pref = SettingPreferences.getInstance(dataStore)

    companion object {
        private const val TAG = "MainViewModel"
        private const val USER_ID = "daffaya"
    }

    fun findUser(name: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(name)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _searchUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkThemeEnabled: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkThemeEnabled)
        }
    }
}
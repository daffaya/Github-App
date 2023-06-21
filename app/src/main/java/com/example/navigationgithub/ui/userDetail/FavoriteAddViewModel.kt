package com.example.navigationgithub.ui.userDetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.navigationgithub.Database.FavoriteUser
import com.example.navigationgithub.data.repository.FavoriteRepository

class FavoriteAddViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> =
        mFavoriteRepository.getFavoriteUserByUsername(username)

    fun insert(favorite: FavoriteUser) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: FavoriteUser) {
        mFavoriteRepository.delete(favorite)
    }
}

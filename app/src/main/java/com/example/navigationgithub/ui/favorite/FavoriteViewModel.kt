package com.example.navigationgithub.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.navigationgithub.Database.FavoriteUser
import com.example.navigationgithub.data.repository.FavoriteRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mFavoriteRepository.getAllFavorite()

    fun delete(): Unit = mFavoriteRepository.delete(favorite = FavoriteUser())
}
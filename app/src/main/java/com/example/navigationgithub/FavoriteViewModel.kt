package com.example.navigationgithub

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.navigationgithub.Database.FavoriteUser
import com.example.navigationgithub.Repository.FavoriteRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mFavoriteRepository.getAllFavorite()

    fun delete(): Unit = mFavoriteRepository.delete(favorite = FavoriteUser())
}
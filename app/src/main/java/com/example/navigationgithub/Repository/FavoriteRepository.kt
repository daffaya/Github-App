package com.example.navigationgithub.Repository

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import com.example.navigationgithub.Database.FavoriteRoomDatabase
import com.example.navigationgithub.Database.FavoriteUser
import com.example.navigationgithub.Database.FavoriteUserDao
import com.example.navigationgithub.UserDetailActivity.Companion.username
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavorite()

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mFavoriteUserDao.getFavoriteUserByUsername(username)

    fun insert(favorite: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insert(favorite) }
    }

    fun delete(favorite: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.delete(favorite) }
    }

    fun update(favorite: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.update(favorite) }
    }
}
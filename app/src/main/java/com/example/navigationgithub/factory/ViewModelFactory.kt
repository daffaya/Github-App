package com.example.navigationgithub.factory

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navigationgithub.ui.favorite.FavoriteViewModel
import com.example.navigationgithub.ui.userDetail.FavoriteAddViewModel
import com.example.navigationgithub.MainViewModel

class ViewModelFactory private constructor(
    private val mApplication: Application,
    private val mDataStore: DataStore<Preferences>
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            application: Application,
            dataStore: DataStore<Preferences>
        ): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, dataStore)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApplication, mDataStore) as T
        } else if (modelClass.isAssignableFrom(FavoriteAddViewModel::class.java)) {
            return FavoriteAddViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
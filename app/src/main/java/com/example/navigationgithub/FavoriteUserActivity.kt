package com.example.navigationgithub

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationgithub.Adapter.DetailUserAdapter
import com.example.navigationgithub.Response.DetailUserResponse
import com.example.navigationgithub.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var mFavoriteViewModel : FavoriteViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mFavoriteViewModel = ViewModelFactory.getInstance(application, dataStore).create(FavoriteViewModel::class.java)

        mFavoriteViewModel.getAllFavorite().observe(this){
            binding.rvFavorite.layoutManager = LinearLayoutManager(this)
            val adapter = DetailUserAdapter(it)
            binding.rvFavorite.adapter = adapter
        }

    }


}
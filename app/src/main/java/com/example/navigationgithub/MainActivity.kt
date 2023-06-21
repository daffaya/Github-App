package com.example.navigationgithub

import  android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationgithub.Adapter.UserAdapter
import com.example.navigationgithub.data.local.SettingPreferences
import com.example.navigationgithub.data.response.SearchUser
import com.example.navigationgithub.databinding.ActivityMainBinding
import com.example.navigationgithub.factory.ViewModelFactory
import com.example.navigationgithub.ui.favorite.FavoriteUserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isDarkThemeEnabled = false
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var mainViewModel: MainViewModel
    private lateinit var pref: SettingPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = SettingPreferences.getInstance(dataStore)

        mainViewModel =
            ViewModelFactory.getInstance(application, dataStore).create(MainViewModel::class.java)

        mainViewModel.searchUser.observe(this) { searchUser ->
            setUserData(searchUser)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }


        val layoutManager = LinearLayoutManager(this)
        binding.rvUserSearch.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserSearch.addItemDecoration(itemDecoration)
    }

    private fun setUserData(searchUser: List<SearchUser>) {
        binding.rvUserSearch.layoutManager = LinearLayoutManager(this)
        val adapter = UserAdapter(searchUser)
        binding.rvUserSearch.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.action_bar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        val themeButton = menu.findItem(R.id.theme)

        mainViewModel.getThemeSettings().observe(this) { isDarkThemeEnabled: Boolean ->
            this.isDarkThemeEnabled = isDarkThemeEnabled
            if (isDarkThemeEnabled) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themeButton.icon =
                    AppCompatResources.getDrawable(this, R.drawable.baseline_dark_mode_24)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeButton.icon =
                    AppCompatResources.getDrawable(this, R.drawable.baseline_wb_sunny_24)
            }
        }


        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
            }
            R.id.theme -> {
                mainViewModel.saveThemeSetting(!this.isDarkThemeEnabled)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
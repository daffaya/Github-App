package com.example.navigationgithub.ui.userDetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.navigationgithub.Adapter.SectionsPagerAdapter
import com.example.navigationgithub.Database.FavoriteUser
import com.example.navigationgithub.R
import com.example.navigationgithub.data.response.DetailUserResponse
import com.example.navigationgithub.databinding.ActivityUserDetailBinding
import com.example.navigationgithub.factory.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private val userDetailViewModel by viewModels<UserDetailViewModel>()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


    private lateinit var favorite: FavoriteUser
    private var isFavorite: Boolean = false
    private val favoriteAddViewModel by viewModels<FavoriteAddViewModel> {
        ViewModelFactory.getInstance(application, dataStore)
    }
    private val favoriteDeleteViewModel by viewModels<FavoriteAddViewModel> {
        ViewModelFactory.getInstance(application, dataStore)
    }


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )

        const val username: String = "USERNAME"
        const val avatar: String = "AVATAR"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDetailViewModel.detailUser.observe(this) { detailUser ->
            setUserDetailData(detailUser)
        }
        userDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        favorite = FavoriteUser()


        favoriteAddViewModel.getFavoriteUserByUsername(
            intent.getStringExtra(username) ?: ""
        ).observe(this) { data ->
            checkFavorited(data)
            isFavorite = (data != null)
        }



        binding.floatingActionButton.setOnClickListener {
            isFavorited(isFavorite)
        }

        val uname = intent.getStringExtra(username)

        userDetailViewModel.findUserDetail(uname.toString())

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }


    private fun setUserDetailData(detailUser: DetailUserResponse?) {
        Glide.with(this)
            .load(detailUser?.avatarUrl)
            .into(binding.imgProfilePhoto)

        binding.tvUsername.text = detailUser?.login
        binding.tvName.text = detailUser?.name ?: "No Name"

        binding.tvFollowerCount.text = detailUser?.followers.toString()
        binding.tvFollowingCount.text = detailUser?.following.toString()
        binding.tvRepositoryCount.text = detailUser?.publicRepos.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun checkFavorited(data: FavoriteUser?) {
        if (data != null) {
            binding.floatingActionButton.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            binding.floatingActionButton.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    private fun isFavorited(fav: Boolean) {
        val dataAvatarImg: String? = intent?.getStringExtra(avatar)
        val dataUsername: String? = intent?.getStringExtra(username)


        favorite.username = dataUsername ?: ""
        favorite.avatarUrl = dataAvatarImg

        if (!fav) {
            favoriteAddViewModel.insert(favorite)
        } else {
            favoriteDeleteViewModel.delete(favorite)
        }
    }
}
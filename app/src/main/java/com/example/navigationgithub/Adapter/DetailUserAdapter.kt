package com.example.navigationgithub.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navigationgithub.Database.FavoriteUser
import com.example.navigationgithub.databinding.ProfileDetailBinding
import com.example.navigationgithub.ui.userDetail.UserDetailActivity

class DetailUserAdapter(private val listUser: List<FavoriteUser>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        UserAdapter.ViewHolder(
            ProfileDetailBinding.inflate(
                LayoutInflater.from(viewGroup.context), viewGroup, false
            )
        )


    override fun onBindViewHolder(viewHolder: UserAdapter.ViewHolder, position: Int) {
        viewHolder.name.text = listUser[position].username
        Glide.with(viewHolder.itemView)
            .load(listUser[position].avatarUrl)
            .into(viewHolder.image)

        viewHolder.itemView.setOnClickListener{
            val intent = Intent(viewHolder.itemView.context, UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.username, listUser[position].username)
            intent.putExtra(UserDetailActivity.avatar, listUser[position].avatarUrl)
            viewHolder.itemView.context.startActivity(intent)
        }
    }


    override fun getItemCount() = listUser.size

}
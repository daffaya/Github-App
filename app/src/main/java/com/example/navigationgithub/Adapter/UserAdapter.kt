package com.example.navigationgithub.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navigationgithub.data.response.SearchUser
import com.example.navigationgithub.ui.userDetail.UserDetailActivity
import com.example.navigationgithub.databinding.ProfileDetailBinding
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val listUser: List<SearchUser>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            ProfileDetailBinding.inflate(
                LayoutInflater.from(viewGroup.context), viewGroup, false
            )
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text = listUser[position].login
        Glide.with(viewHolder.itemView)
            .load(listUser[position].avatarUrl)
            .into(viewHolder.image)

        viewHolder.itemView.setOnClickListener{
            val intent = Intent(viewHolder.itemView.context, UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.username, listUser[position].login)
            intent.putExtra(UserDetailActivity.avatar, listUser[position].avatarUrl)
            viewHolder.itemView.context.startActivity(intent)
        }
    }


    override fun getItemCount() = listUser.size

    class ViewHolder(binding: ProfileDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.tvUsername
//        val following: TextView = binding.tvFollowing
//        val follower: TextView = binding.tvFollower

        val image: CircleImageView = binding.imgProfilePhoto


    }
}
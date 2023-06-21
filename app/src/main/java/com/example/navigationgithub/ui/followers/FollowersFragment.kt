package com.example.navigationgithub.ui.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationgithub.Adapter.UserAdapter
import com.example.navigationgithub.data.response.SearchUser
import com.example.navigationgithub.ui.userDetail.UserDetailActivity
import com.example.navigationgithub.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {
    private lateinit var followerBinding: FragmentFollowersBinding
    private lateinit var followerViewModel: FollowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followerBinding = FragmentFollowersBinding.inflate(inflater, container, false)
        followerViewModel = ViewModelProvider(this).get(FollowerViewModel::class.java)
        // Inflate the layout for this fragment
        return followerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followerViewModel.findFollower(requireActivity().intent.getStringExtra(UserDetailActivity.username).toString())

        followerViewModel.detailFollower.observe(viewLifecycleOwner) { detailFollower ->
            // Handle the detailFollower data
            setFollower(detailFollower)
        }

        // Observe the isLoading LiveData object
        followerViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Handle the isLoading state
            showLoading(isLoading)
        }

    }

    private fun setFollower(detailFollower: List<SearchUser>) {
        val adapter = UserAdapter(detailFollower)
        followerBinding.rvFollower.adapter = adapter
        followerBinding.rvFollower.layoutManager = LinearLayoutManager(context)

    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            followerBinding.progressBar.visibility = View.VISIBLE
        } else {
            followerBinding.progressBar.visibility = View.GONE
        }
    }


}
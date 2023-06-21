package com.example.navigationgithub.ui.following

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
import com.example.navigationgithub.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private lateinit var followingBinding: FragmentFollowingBinding
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        followingBinding = FragmentFollowingBinding.inflate(inflater, container, false)
        followingViewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
        return followingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followingViewModel.findFollowing(requireActivity().intent.getStringExtra(UserDetailActivity.username).toString())

        followingViewModel.detailFollowing.observe(viewLifecycleOwner) {detailFollowing ->
            setFollowing(detailFollowing)
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {isLoading ->
            showLoading(isLoading)
        }
    }

    private fun setFollowing(detailFollowing: List<SearchUser>) {
        val adapter = UserAdapter(detailFollowing)
        followingBinding.rvFollowing.adapter = adapter
        followingBinding.rvFollowing.layoutManager = LinearLayoutManager(context)
    }

    private fun showLoading(isloading: Boolean) {
        if (isloading) {
            followingBinding.progressBar.visibility = View.VISIBLE
        } else {
            followingBinding.progressBar.visibility = View.GONE
        }
    }

}
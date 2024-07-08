package com.dicoding.githubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.FragmentFollowBinding
import com.dicoding.githubuser.ui.adapter.UserAdapter
import com.dicoding.githubuser.ui.viewmodel.UserDetailViewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private val userDetailViewModel by viewModels<UserDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username")
        val position = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        if (position == 1){
            userDetailViewModel.findFollowers(username.toString())

            userDetailViewModel.userFollowers.observe(viewLifecycleOwner) { followers ->
                setFollow(followers)
            }

            userDetailViewModel.isLoadingFollow.observe(viewLifecycleOwner) {
                showLoadingFollow(it)
            }
        } else {
            userDetailViewModel.findFollowing(username.toString())

            userDetailViewModel.userFollowing.observe(viewLifecycleOwner) { following ->
                setFollow(following)
            }

            userDetailViewModel.isLoadingFollow.observe(viewLifecycleOwner){
                showLoadingFollow(it)
            }
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)
    }

    private fun setFollow(followers: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(followers)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoadingFollow(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
}
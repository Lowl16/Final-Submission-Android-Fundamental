package com.dicoding.githubuser.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubuser.ui.fragment.FollowFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_SECTION_NUMBER, position + 1)
            putString("username", username)
        }
        return fragment
    }
}
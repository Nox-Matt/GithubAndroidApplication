package com.ukrida.mygithubapplication.ui.Adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ukrida.mygithubapplication.ui.FollowersFragment
import com.ukrida.mygithubapplication.ui.FollowingFragment

class SectionPageAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0-> fragment = FollowersFragment()
            1-> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }
}
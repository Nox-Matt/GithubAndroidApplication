package com.ukrida.mygithubapplication.ui

import SectionPageAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ukrida.mygithubapplication.R
import com.ukrida.mygithubapplication.databinding.ActivityDetailBinding
import com.ukrida.mygithubapplication.ui.ViewModels.DetailViewModel

class DetailActivity : AppCompatActivity() {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val GITHUB_USERNAME = "github_username"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(GITHUB_USERNAME)
        Log.d("Tags", "Username: $username")

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
        if (username != null) {
            viewModel.setUserDetail(username)

            viewModel.getUserDetail().observe(this) {
                binding.apply {
                    tvName.text = it.name.toString()
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailActivity)
                        .load((it.avatarUrl))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(imageView)
                }
            }

            val bundle = Bundle().apply {
                putString(GITHUB_USERNAME, username)
            }
            val sectionPageAdapter = SectionPageAdapter(this, bundle)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionPageAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            tabs.background = ContextCompat.getDrawable(this, R.drawable.tab_selector)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f
        }
    }
}
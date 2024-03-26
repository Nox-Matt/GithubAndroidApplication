package com.ukrida.mygithubapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.ukrida.mygithubapplication.R
import com.ukrida.mygithubapplication.databinding.ActivityDetailBinding
import com.ukrida.mygithubapplication.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val GITHUB_USERNAME = "github_username"
    }
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
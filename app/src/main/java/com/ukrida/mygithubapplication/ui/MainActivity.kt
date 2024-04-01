package com.ukrida.mygithubapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.ukrida.mygithubapplication.R
import com.ukrida.mygithubapplication.data.respond.GithubItem
import com.ukrida.mygithubapplication.databinding.ActivityMainBinding
import com.ukrida.mygithubapplication.settings.SettingsPreference
import com.ukrida.mygithubapplication.settings.dataStore
import com.ukrida.mygithubapplication.ui.Adapters.GithubAdapter
import com.ukrida.mygithubapplication.ui.Adapters.SettingsViewModelFactory
import com.ukrida.mygithubapplication.ui.ViewModels.MainViewModel
import com.ukrida.mygithubapplication.ui.ViewModels.SettingsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : GithubAdapter
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var settingsPreference: SettingsPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingsPreference = SettingsPreference.getInstance(this.dataStore)

        settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(settingsPreference))
            .get(SettingsViewModel::class.java)

        settingsViewModel.getTheme().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    viewModel.setUsersData(searchView.editText.text.toString())
                    searchView.hide()
                    showLoading(true)
                    Toast.makeText(this@MainActivity, "You searched For ${searchView.editText.text}", Toast.LENGTH_SHORT).show()
                    false
                }
        }

        supportActionBar?.show()
        showLoading(true)

        adapter = GithubAdapter(ArrayList())
        adapter.setOnItemClickCallback(object: GithubAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GithubItem) {
                Intent(this@MainActivity, DetailActivity::class.java).also{
                    it.putExtra(DetailActivity.GITHUB_USERNAME,data.login)
                    it.putExtra(DetailActivity.GITHUB_ID,data.id)
                    it.putExtra(DetailActivity.GITHUB_PP,data.avatarUrl)
                    startActivity(it)
                }
            }
        })
        binding.rvGithubUser.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
        if (viewModel.getUsersData().value == null) {
            viewModel.setUsersData("Nox")
        }

        viewModel.getUsersData().observe(this) { users ->
            adapter.updateData(users)
            showLoading(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_option, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.option_favorite -> {
                Log.e("MainActivity", "Option Favorite selected")
                Intent(this,FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.option_theme -> {
                Log.e("MainActivity", "Option Theme selected")
                Intent(this,ThemeActivity::class.java).also{
                    startActivity(it)
                }
            }
            else -> {
                Log.e("MainActivity", "Unknown menu item selected: ${item.itemId}")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
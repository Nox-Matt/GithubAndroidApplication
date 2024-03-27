package com.ukrida.mygithubapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ukrida.mygithubapplication.data.respond.GithubItem
import com.ukrida.mygithubapplication.databinding.ActivityMainBinding
import com.ukrida.mygithubapplication.ui.Adapters.GithubAdapter
import com.ukrida.mygithubapplication.ui.ViewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : GithubAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        supportActionBar?.hide()
        showLoading(true)

        adapter = GithubAdapter(ArrayList())
        adapter.setOnItemClickCallback(object: GithubAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GithubItem) {
                Intent(this@MainActivity, DetailActivity::class.java).also{
                    it.putExtra(DetailActivity.GITHUB_USERNAME,data.login)
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
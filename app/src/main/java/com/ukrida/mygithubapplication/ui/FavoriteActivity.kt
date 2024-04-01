package com.ukrida.mygithubapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ukrida.mygithubapplication.data.respond.GithubItem
import com.ukrida.mygithubapplication.database.FavoriteUser
import com.ukrida.mygithubapplication.databinding.ActivityFavoriteBinding
import com.ukrida.mygithubapplication.ui.Adapters.GithubAdapter
import com.ukrida.mygithubapplication.ui.ViewModels.FavoriteViewModel
import kotlin.collections.ArrayList

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: GithubAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = GithubAdapter(ArrayList())

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        adapter.setOnItemClickCallback(object : GithubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubItem) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.GITHUB_USERNAME, data.login)
                    putExtra(DetailActivity.GITHUB_ID, data.id)
                    putExtra(DetailActivity.GITHUB_PP, data.avatarUrl)
                    startActivity(this)
                }
            }
        })

        binding.rvFavorite.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = this@FavoriteActivity.adapter
        }

        viewModel.getFavorite()?.observe(this) { favorites ->
            favorites?.let {
                val list = mapList(favorites)
                adapter.updateData(list)
            }
        }
    }

    private fun mapList(favorites: List<FavoriteUser>): ArrayList<GithubItem> {
        val listFavorite = ArrayList<GithubItem>()
        for (favorite in favorites) {
            val favMap = GithubItem(
                favorite.id,
                favorite.login,
                favorite.avatarUrl,
            )
            listFavorite.add(favMap)
        }
        return listFavorite
    }
}

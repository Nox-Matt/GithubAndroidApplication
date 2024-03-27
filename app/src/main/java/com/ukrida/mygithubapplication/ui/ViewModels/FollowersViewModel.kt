package com.ukrida.mygithubapplication.ui.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ukrida.mygithubapplication.data.respond.DetailResponse
import com.ukrida.mygithubapplication.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response


class FollowersViewModel : ViewModel() {

    companion object {
        private const val TAGX = "FollowersViewModel"
    }

    val listOfFollowers = MutableLiveData<List<DetailResponse>>()
    private val isLoading = MutableLiveData<Boolean>()
    private var currentPage = 1

    fun setListFollowers(username: String) {
        isLoading.value = true
        loadFollowers(username, currentPage)
    }

    private fun loadFollowers(username: String, page: Int) {
        val client = ApiConfig.getApiService().getUsersFollowers(username, page)
        client.enqueue(object : retrofit2.Callback<List<DetailResponse>> {
            override fun onResponse(
                call: Call<List<DetailResponse>>,
                response: Response<List<DetailResponse>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    val followers = response.body()
                    if (followers != null) {
                        if (followers.isNotEmpty()) {
                            val currentList = listOfFollowers.value ?: emptyList()
                            listOfFollowers.value = currentList + followers
                            currentPage++
                            loadFollowers(username, currentPage)
                        }
                    } else {
                        Log.e(TAGX, "No Followers")
                    }
                } else {
                    Log.e(TAGX, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DetailResponse>>, t: Throwable) {
                isLoading.value = false
                Log.e(TAGX, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowers(): MutableLiveData<List<DetailResponse>> {
        return listOfFollowers
    }
}

package com.ukrida.mygithubapplication.ui.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ukrida.mygithubapplication.data.respond.DetailResponse
import com.ukrida.mygithubapplication.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response


class FollowersViewModel : ViewModel() {

    companion object {
        private const val TAGX = "FollowersViewModel"
        private const val DEFAULT_PER_PAGE = 1000
    }

    private val listOfFollowers = MutableLiveData<ArrayList<DetailResponse>>()
    private val isLoading = MutableLiveData<Boolean>()
    private var currentPage = 1

    fun setListFollowers(username: String) {
        isLoading.value = true

        val firstPageClient = ApiConfig.getApiService().getUsersFollowers(username, currentPage, DEFAULT_PER_PAGE)
        firstPageClient.enqueue(object : retrofit2.Callback<List<DetailResponse>> {
            override fun onResponse(
                call: Call<List<DetailResponse>>,
                response: Response<List<DetailResponse>>
            ) {
                if (response.isSuccessful) {
                    val totalCount = response.headers()["X-Total-Count"]?.toIntOrNull() ?: 0
                    val perPage = if (totalCount > 0) totalCount else DEFAULT_PER_PAGE
                    fetchFollowersPages(username, perPage)
                } else {
                    isLoading.value = false
                    Log.e(TAGX, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DetailResponse>>, t: Throwable) {
                isLoading.value = false
                Log.e(TAGX, "onFailure: ${t.message}")
            }
        })
    }

    private fun fetchFollowersPages(username: String, perPage: Int) {
        val client = ApiConfig.getApiService().getUsersFollowers(username, currentPage, perPage)
        client.enqueue(object : retrofit2.Callback<List<DetailResponse>> {
            override fun onResponse(
                call: Call<List<DetailResponse>>,
                response: Response<List<DetailResponse>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    val newList = response.body() as ArrayList<DetailResponse>
                    val currentList = listOfFollowers.value ?: ArrayList()
                    currentList.addAll(newList)
                    listOfFollowers.value = currentList
                    if (newList.size == perPage) {
                        currentPage++
                        fetchFollowersPages(username, perPage)
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

    fun getFollowers(): LiveData<ArrayList<DetailResponse>> {
        return listOfFollowers
    }
}

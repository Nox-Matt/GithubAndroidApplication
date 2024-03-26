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
    }

    val listOfFollowers = MutableLiveData<ArrayList<DetailResponse>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun setListFollowers(username: String) {
        isLoading.value = true
        val client = ApiConfig.getApiService().getUsersFollowers(username)
        client.enqueue(object : retrofit2.Callback<List<DetailResponse>> {
            override fun onResponse(
                call: Call<List<DetailResponse>>,
                response: Response<List<DetailResponse>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    listOfFollowers.value = response.body() as ArrayList<DetailResponse>
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

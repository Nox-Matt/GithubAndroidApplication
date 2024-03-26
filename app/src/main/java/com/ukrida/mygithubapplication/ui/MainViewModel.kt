package com.ukrida.mygithubapplication.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ukrida.mygithubapplication.data.respond.GithubItem
import com.ukrida.mygithubapplication.data.respond.GithubResponse
import com.ukrida.mygithubapplication.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response


class MainViewModel :ViewModel() {
    private val listUser = MutableLiveData<ArrayList<GithubItem>>()
    private val isLoading = MutableLiveData<Boolean>()

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun setUsersData(query: String) {
        isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(query)
        client.enqueue(object : retrofit2.Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    listUser.value = response.body()?.items as ArrayList<GithubItem>
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    fun getUsersData(): LiveData<ArrayList<GithubItem>>{
        return listUser
    }
}
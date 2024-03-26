package com.ukrida.mygithubapplication.ui.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ukrida.mygithubapplication.data.respond.DetailResponse
import com.ukrida.mygithubapplication.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel(){

    val user = MutableLiveData<DetailResponse>()
    private val isLoading = MutableLiveData<Boolean>()
    companion object {
        private const val DETAIL = "DetailViewModel"
    }
    fun setUserDetail(username: String){
        isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUsers(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    user.postValue(response.body())
                } else {
                    Log.e(DETAIL, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                isLoading.value = false
                Log.e(DETAIL, "onFailure: ${t.message}")
            }
        })
    }
    fun getUserDetail() : LiveData<DetailResponse>{
        return user
    }
}
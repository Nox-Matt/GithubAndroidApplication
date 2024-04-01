package com.ukrida.mygithubapplication.ui.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ukrida.mygithubapplication.data.respond.DetailResponse
import com.ukrida.mygithubapplication.data.retrofit.ApiConfig
import com.ukrida.mygithubapplication.database.FavDAO
import com.ukrida.mygithubapplication.database.FavDatabase
import com.ukrida.mygithubapplication.database.FavoriteUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application){

    val user = MutableLiveData<DetailResponse>()
    private val isLoading = MutableLiveData<Boolean>()
    private var favDao: FavDAO?
    private var favoriteDatabase : FavDatabase?

    init {
        favoriteDatabase = FavDatabase.getDatabase(application)
        favDao = favoriteDatabase?.favoriteDAO()
    }
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
    fun addToFavorite(id: Int, username: String, avatarUrl: String) {
        GlobalScope.launch(Dispatchers.IO) {
            var favorite = FavoriteUser(
                id,
                username,
                avatarUrl
            )
            favDao?.addToFavorite(favorite)
        }
    }
    suspend fun checkFavorite(id:Int) = favDao?.checkFavorite(id)

    fun removeFavorite(id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            favDao?.removeFavorite(id)
        }
    }
}
package com.ukrida.mygithubapplication.ui.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ukrida.mygithubapplication.database.FavDAO
import com.ukrida.mygithubapplication.database.FavDatabase
import com.ukrida.mygithubapplication.database.FavoriteUser

class FavoriteViewModel (application: Application): AndroidViewModel(application){
    private var favDao: FavDAO?
    private var favoriteDatabase : FavDatabase?

    init {
        favoriteDatabase = FavDatabase.getDatabase(application)
        favDao = favoriteDatabase?.favoriteDAO()
    }
    fun getFavorite(): LiveData<List<FavoriteUser>>?{
        return favDao?.getFavorite()
    }
}
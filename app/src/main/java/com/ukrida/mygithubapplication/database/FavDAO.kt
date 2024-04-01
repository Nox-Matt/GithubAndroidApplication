package com.ukrida.mygithubapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun getFavorite(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM favorite_user WHERE id = :id")
    suspend fun checkFavorite(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE id = :id")
    suspend fun removeFavorite(id: Int): Int
}
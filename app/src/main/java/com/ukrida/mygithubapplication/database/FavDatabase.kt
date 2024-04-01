package com.ukrida.mygithubapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities =[FavoriteUser::class],
    version = 1
)
abstract class FavDatabase : RoomDatabase() {
    abstract fun favoriteDAO(): FavDAO

    companion object {
        @Volatile
        var INSTANCE : FavDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context):FavDatabase?{
            if(INSTANCE == null){
                synchronized(FavDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,FavDatabase::class.java, "fav_database").build()
                }
            }
            return INSTANCE as FavDatabase
        }
    }
}

package com.ukrida.mygithubapplication.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_user")
@Parcelize
data class FavoriteUser(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatarUrl: String
) : Parcelable

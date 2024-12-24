package com.example.testassessment.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "album_table")
data class AlbumEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,

)
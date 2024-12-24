package com.example.testassessment.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "photos_table")
data class PhotosEntity(
    @PrimaryKey val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
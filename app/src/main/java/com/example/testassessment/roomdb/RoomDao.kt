package com.example.testassessment.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertPhotos(photos: List<PhotosEntity>)

        @Query("SELECT * FROM photos_table")
        fun getAllPhotos(): Flow<List<PhotosEntity>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertAlbums(photos: List<AlbumEntity>)

        @Query("SELECT * FROM album_table")
        fun getAllAlbums(): Flow<List<AlbumEntity>>


}
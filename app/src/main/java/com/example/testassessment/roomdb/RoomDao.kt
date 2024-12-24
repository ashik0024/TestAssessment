package com.example.testassessment.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testassessment.network.response.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertPhotos(photos: List<PhotosEntity>)

        @Query("SELECT * FROM photos_table")
        fun getAllPhotos(): Flow<List<PhotosEntity>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertAlbums(albums: List<AlbumEntity>)

        @Query("SELECT * FROM album_table")
        fun getAllAlbums(): Flow<List<AlbumEntity>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertUsers(user: List<UserEntity>)

        @Query("SELECT * FROM user_table")
        fun getAllUsers(): Flow<List<UserEntity>>


}
package com.example.testassessment.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testassessment.roomdb.AlbumEntity
import com.example.testassessment.roomdb.PhotosEntity
import com.example.testassessment.roomdb.RoomDao
import com.example.testassessment.roomdb.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val roomDao: RoomDao
) : ViewModel() {
    private val _photos = MutableStateFlow<List<PhotosEntity>>(emptyList())
    val photos: StateFlow<List<PhotosEntity>> = _photos

    private val _albums= MutableStateFlow<List<AlbumEntity>>(emptyList())
    val albums: StateFlow<List<AlbumEntity>> = _albums

    private val _users = MutableStateFlow<List<UserEntity>>(emptyList())
    val users: StateFlow<List<UserEntity>> = _users

    private val _combinedPhotoData = MutableStateFlow<List<CombinedPhotoData>>(emptyList())
    val combinedPhotoData: StateFlow<List<CombinedPhotoData>> = _combinedPhotoData

    init {
        fetchPhotosFromRoom()
        fetchAlbumsFromRoom()
        fetchUsersFromRoom()
        observeFlowsAndCombineData()

    }

     fun fetchPhotosFromRoom() {
         viewModelScope.launch {
             roomDao.getAllPhotos().collect { photosList ->
                 _photos.value = photosList
                 Log.d("PhotoFetchService", "Photos updated in ViewModel: ${_photos.value.size}")
             }
         }
    }
    fun fetchAlbumsFromRoom() {
        viewModelScope.launch {
            roomDao.getAllAlbums().collect { albumsList ->
                _albums.value = albumsList
                Log.d("PhotoFetchService", "albums updated in ViewModel: ${_albums.value.size}")
            }
        }
    }
    fun fetchUsersFromRoom() {
        viewModelScope.launch {
            roomDao.getAllUsers().collect { usersList ->
                _users.value = usersList
                Log.d("PhotoFetchService", "Users updated in ViewModel: ${_users.value.size}")
            }
        }
    }
    private fun observeFlowsAndCombineData() {
        viewModelScope.launch {
            launch { _photos.collect { updateCombinedData() } }
            launch { _albums.collect { updateCombinedData() } }
            launch { _users.collect { updateCombinedData() } }
        }
    }
    private fun updateCombinedData() {
        val photosList = _photos.value
        val albumsList = _albums.value
        val usersList = _users.value

    if (photosList.isNotEmpty() && albumsList.isNotEmpty() && usersList.isNotEmpty()){


        val albumGroups = photosList
            .groupBy { it.albumId }
            .map { (albumId, itemList) ->
               AlbumGroup(
                  albumId,
                    listOf(
                       AlbumInfo(
                        id = itemList[0].id,
                        title = itemList[0].title,
                        url = itemList[0].url,
                        thumbnailUrl = itemList[0].thumbnailUrl
                    )
                )
            )
        }

    val combinedData = albumGroups.flatMap { item ->
        val albums = albumsList.find { it.id == item.albumId }
        val users = usersList.find { it.id == albums?.userId }
        item.items.map {
            CombinedPhotoData(
                photoId = it.id,
                title = it.title,
                url = it.url,
                thumbnailUrl = it.thumbnailUrl,
                albumName = albums?.title ?: "",
                userName = users?.userName ?: ""
            )
        }
    }

    _combinedPhotoData.value = combinedData

    Log.d("PhotoFetchService", "Combined data size: ${_combinedPhotoData.value.size}")
    _combinedPhotoData.value.forEach { item ->
        Log.d("CombinedPhotoData", "PhotoId: ${item.photoId}, Title: ${item.title}, " +
                "URL: ${item.url}, ThumbnailURL: ${item.thumbnailUrl}, " +
                "AlbumName: ${item.albumName}, UserName: ${item.userName}")
            }


        }
    }
    override fun onCleared() {
        super.onCleared()
    }
}
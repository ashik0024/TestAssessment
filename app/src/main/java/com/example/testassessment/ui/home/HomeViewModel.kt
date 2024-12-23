package com.example.testassessment.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testassessment.roomdb.PhotosEntity
import com.example.testassessment.roomdb.RoomDao
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

    init {
        fetchPhotosFromRoom()
    }

     fun fetchPhotosFromRoom() {
         viewModelScope.launch {
             roomDao.getAllPhotos().collect { photosList ->
                 _photos.value = photosList
                 Log.d("PhotoFetchService", "Photos updated in ViewModel: ${_photos.value.size}")
             }
         }
    }
    override fun onCleared() {
        super.onCleared()
    }
}
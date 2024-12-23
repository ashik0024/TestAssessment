package com.example.testassessment.network.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.testassessment.network.Results
import com.example.testassessment.network.response.Photos
import com.example.testassessment.network.retrofit.ApiInterface
import com.example.testassessment.roomdb.PhotosEntity
import com.example.testassessment.roomdb.RoomDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PhotoFetchService : Service() {

    @Inject
    lateinit var apiService: ApiInterface
    @Inject
    lateinit var getPhotosPaging: GetPhotosPaging
    @Inject
    lateinit var roomDao: RoomDao
    private val channelId = "photo_fetch_service_channel"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification()
        startForeground(1, notification)

        fetchPhotosInBackground()
        return START_NOT_STICKY
    }
    private fun fetchPhotosInBackground() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
               val response = getPhotosPaging.getPhotoData()
                Log.d("PhotoFetchService", "Photo Title: ${response}")

                response.let {
                    when(it){
                        is Results.Success -> {
                            val photosList = it.data.map { photo ->
                                PhotosEntity(
                                    id = photo.id?:-1,
                                    title = photo.title?:"",
                                    url = photo.url?:"",
                                    thumbnailUrl = photo.thumbnailUrl?:""
                                )
                            }
                            roomDao.insertPhotos(photosList)
                            Log.d("PhotoFetchService", "Photos saved to Room DB")
                        }
                        is Results.Error -> {
                            val error = it.exception
                            Log.d("PhotoFetchService", "error: ${error.cause}")
                        }
                        is Results.Loading -> {

                        }

                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            finally {
                stopSelf()
            }
        }
    }
    private fun createNotification(): Notification {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Photo Fetch Service",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Photo Fetch Service")
            .setContentText("Fetching photos in the background...")
            .setSmallIcon(android.R.drawable.stat_notify_sync)
            .build()

    }
}
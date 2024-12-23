
package com.example.testassessment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.testassessment.network.service.PhotoFetchService
import com.example.testassessment.roomdb.RoomDao
import com.example.testassessment.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var roomDao: RoomDao
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("PhotoFetchService", "Started")

        checkAndStartPhotoFetchService()


        }
    private fun checkAndStartPhotoFetchService() {
        lifecycleScope.launch(Dispatchers.IO) {
            val photosCount = roomDao.getAllPhotos().firstOrNull()?.size ?: 0
            if (photosCount == 0) {

                val serviceIntent = Intent(this@MainActivity, PhotoFetchService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent)
                    Log.d("PhotoFetchService", "Foreground Service started")
                } else {
                    startService(serviceIntent)
                    Log.d("PhotoFetchService", "BackGround Service started")
                }

            } else {
                Log.d("PhotoFetchService", "Photos already exist in Room DB")
            }
        }
    }

}
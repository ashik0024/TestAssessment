
package com.example.testassessment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.testassessment.databinding.ActivityMainBinding
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
    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        checkAndStartPhotoFetchService()
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_sync -> {
                    Toast.makeText(this, "Data Syncing", Toast.LENGTH_SHORT).show()
                    getDataFromService()
                    true
                }
                else -> false
            }
        }
        observeData()
    }
    private fun checkAndStartPhotoFetchService() {
        lifecycleScope.launch(Dispatchers.IO) {
            val photosCount = roomDao.getAllPhotos().firstOrNull()?.size ?: 0
            if (photosCount == 0) {

                getDataFromService()
                binding.loader.visibility= View.VISIBLE
            } else {

            }
        }
    }
    fun getDataFromService(){
        val serviceIntent = Intent(this@MainActivity, PhotoFetchService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)

        } else {
            startService(serviceIntent)

        }
    }
    private fun observeData() {
        lifecycleScope.launch {
            viewModel.combinedPhotoData.collect { combinedData ->
                if (combinedData.isNotEmpty()) {
                    binding.loader.visibility= View.GONE
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}
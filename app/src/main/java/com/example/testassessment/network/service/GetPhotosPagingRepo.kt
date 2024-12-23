package com.example.testassessment.network.service

import android.util.Log
import com.example.testassessment.common.BaseApiService
import com.example.testassessment.network.PagingErrorHandling
import com.example.testassessment.network.Results
import com.example.testassessment.network.response.Photos
import com.example.testassessment.network.retrofit.ApiInterface
import com.example.testassessment.network.safeApiCall
import javax.inject.Inject



class GetPhotosPaging @Inject constructor(
    private val apiService: ApiInterface) {

    suspend fun getPhotoData(): Results<List<Photos>> {
        return safeApiCall {
            val response = apiService.getPhotos()
            response
        }
    }
}

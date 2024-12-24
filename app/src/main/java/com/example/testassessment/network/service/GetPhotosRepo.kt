package com.example.testassessment.network.service

import com.example.testassessment.network.Results
import com.example.testassessment.network.response.Photos
import com.example.testassessment.network.retrofit.ApiInterface
import com.example.testassessment.network.safeApiCall
import javax.inject.Inject



class GetPhotosRepo @Inject constructor(
    private val apiService: ApiInterface) {

    suspend fun getPhotoData(): Results<List<Photos>> {
        return safeApiCall {
            val response = apiService.getPhotos()
            response
        }
    }
}

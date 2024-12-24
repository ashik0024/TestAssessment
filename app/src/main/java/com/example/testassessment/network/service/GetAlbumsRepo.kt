package com.example.testassessment.network.service

import com.example.testassessment.network.Results
import com.example.testassessment.network.response.Albums
import com.example.testassessment.network.retrofit.ApiInterface
import com.example.testassessment.network.safeApiCall
import javax.inject.Inject



class GetAlbumsRepo @Inject constructor(
    private val apiService: ApiInterface) {

    suspend fun getAlbumData(): Results<List<Albums>> {
        return safeApiCall {
            val response = apiService.getAlbums()
            response
        }
    }
}

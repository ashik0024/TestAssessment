package com.example.testassessment.network.retrofit

import com.example.testassessment.network.response.Photos
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("photos")
    suspend fun getPhotos(): List<Photos>
}
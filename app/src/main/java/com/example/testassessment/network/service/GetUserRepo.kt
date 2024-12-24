package com.example.testassessment.network.service

import com.example.testassessment.network.Results
import com.example.testassessment.network.response.Albums
import com.example.testassessment.network.response.Users
import com.example.testassessment.network.retrofit.ApiInterface
import com.example.testassessment.network.safeApiCall
import javax.inject.Inject



class GetUserRepo @Inject constructor(
    private val apiService: ApiInterface) {

    suspend fun getUserData(): Results<List<Users>> {
        return safeApiCall {
            val response = apiService.getusers()
            response
        }
    }
}

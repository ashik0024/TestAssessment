package com.example.testassessment.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Results<T> {
    return withContext(Dispatchers.IO) {
        try {
            // Execute the API call
            Results.Success(apiCall.invoke())
        } catch (e: IOException) {
            // Handle network errors (e.g., no internet connection)
            Results.Error(IOException("Network error, please check your connection", e))
        } catch (e: HttpException) {
            // Handle HTTP errors (e.g., 404, 500 errors)
            val errorMessage = e.response()?.errorBody()?.string() ?: "An unexpected HTTP error occurred"
            Results.Error(HttpException(e.response() ?: Response.error<Any>(500, "".toResponseBody())))
        } catch (e: Exception) {
            // Handle any other unexpected errors
            Results.Error(Exception("An unknown error occurred", e))
        }
    }
}
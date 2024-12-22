package com.example.testassessment.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> ApiCallHandler(apiCall: suspend () -> T): Results<T> {
    return withContext(Dispatchers.IO) {
        try {

            Results.Success(apiCall.invoke())
        } catch (e: IOException) {

            Results.Error(IOException("Network error, please check your connection", e))
        } catch (e: HttpException) {

            val errorMessage = e.response()?.errorBody()?.string() ?: "An unexpected HTTP error occurred"
            Results.Error(HttpException(e.response() ?: Response.error<Any>(500, "".toResponseBody())))
        } catch (e: Exception) {
            Results.Error(Exception("An unknown error occurred", e))
        }
    }
}
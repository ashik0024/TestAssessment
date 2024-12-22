package com.example.testassessment.network

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    protected suspend fun <T> ApiCallHandler(call: suspend () -> Response<T>): Results<T> {
        return try {

            val response = call()
            if (response.isSuccessful) {

                response.body()?.let {
                    Results.Success(it)
                } ?: Results.Error(Exception("Empty response body"))
            } else {

                Results.Error(HttpException(response))
            }
        } catch (e: IOException) {

            Results.Error(IOException("Network error, please check your connection", e))
        } catch (e: HttpException) {

            /**
             * Handle HTTP errors (e.g., 404, 500 errors)
             */
            val errorMessage = e.response()?.errorBody()?.string()
                ?: "An unexpected HTTP error occurred"
            Results.Error(HttpException(e.response() ?: Response.error<Any>(500, "".toResponseBody())))
        } catch (e: Exception) {

            /**
             * Handle any other unexpected errors
             */

            Results.Error(Exception("An unknown error occurred", e))
        }
    }
}
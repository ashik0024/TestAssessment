package com.example.testassessment.network


sealed class Results<out T> {
    object Loading : Results<Nothing>()
    data class Success<out T>(val data: T) : Results<T>()
    data class Error(val exception: Throwable) : Results<Nothing>()
}
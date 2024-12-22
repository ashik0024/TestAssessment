
package com.example.testassessment.common

interface BaseApiService<T: Any> {
    suspend fun loadData(offset: Int, limit: Int): List<T>
}
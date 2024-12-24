package com.example.testassessment.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Albums (
    @SerialName("userId")
    var userId : Int?= null,
    @SerialName("id")
    var id : Int?= null,
    @SerialName("title")
    var title : String? = null
)
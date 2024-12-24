package com.example.testassessment.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Users (

    @SerialName("id")
    var id : Int? = null,
    @SerialName("name")
    var name : String?= null,
    @SerialName("username")
    var username : String?= null,
    @SerialName("email")
    var email : String? = null,
    @SerialName("address")
    var address : Address? = Address(),
    @SerialName("phone")
    var phone : String?= null,
    @SerialName("website")
    var website : String? = null,
    @SerialName("company")
    var company : Company?= Company()

)
@Serializable
data class Address (

    @SerialName("street")
    var street:String? = null,
    @SerialName("suite")
    var suite: String? = null,
    @SerialName("city")
    var city: String? = null,
    @SerialName("zipcode")
    var zipcode : String? = null,
    @SerialName("geo")
    var geo : Geo?    = Geo()

)
@Serializable
data class Geo (

    @SerialName("lat")
    var lat : String? = null,
    @SerialName("lng")
    var lng : String? = null

)
@Serializable
data class Company (

    @SerialName("name")
    var name : String? = null,
    @SerialName("catchPhrase")
    var catchPhrase : String? = null,
    @SerialName("bs")
    var bs  : String? = null
)
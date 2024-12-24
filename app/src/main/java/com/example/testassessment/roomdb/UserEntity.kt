package com.example.testassessment.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val id: Int,
    val userName: String,

)
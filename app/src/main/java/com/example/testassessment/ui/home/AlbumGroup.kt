package com.example.testassessment.ui.home

data class AlbumGroup(
    var albumId: Int,
    val items: List<AlbumInfo>
)
data class AlbumInfo (
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
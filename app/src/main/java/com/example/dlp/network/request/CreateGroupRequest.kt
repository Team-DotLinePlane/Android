package com.example.dlp.network.request

data class CreateGroupRequest(
    val memberToken: String,
    val teamName: String
)
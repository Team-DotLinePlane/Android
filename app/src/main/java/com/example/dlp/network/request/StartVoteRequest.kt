package com.example.dlp.network.request

data class StartVoteRequest(
    val memberToken: String,
    val teamId: Int
)
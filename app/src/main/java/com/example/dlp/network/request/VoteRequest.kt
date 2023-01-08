package com.example.dlp.network.request

data class VoteRequest(
    val memberToken: String,
    val voteId: Int,
    val voteResults: List<Int>
)
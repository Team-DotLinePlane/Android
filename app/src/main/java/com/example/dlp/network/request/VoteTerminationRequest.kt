package com.example.dlp.network.request

data class VoteTerminationRequest(
    val memberToken: String,
    val voteId: Int
)
package com.example.dlp.network

import com.example.dlp.network.request.*
import com.example.dlp.network.response.GetUerInfoReponse
import com.example.dlp.network.response.IsVotingResponse
import com.example.dlp.network.response.VoteTerminationResponse
import retrofit2.Call
import retrofit2.http.*
import javax.inject.Qualifier

interface VoteService {

    // 투표하기
    @POST("/api/votes")
    fun votes(
        @Body voteRequest: VoteRequest,
    ): Call<Void>

    @POST("/api/votes/start")
    fun startVote(
        @Body startVoteRequest: StartVoteRequest,
    ): Call<Void>

    @POST("/api/votes/termination")
    fun terminateVote(
        @Body voteTerminationRequest: VoteTerminationRequest,
    ): Call<VoteTerminationResponse>

    @GET("/api/votes/check/{voteId}")
    fun isVoting(
        @Path("voteId") voteId: Int,
        @Query("memberToken") memberToken: String,
    ): Call<IsVotingResponse>

}
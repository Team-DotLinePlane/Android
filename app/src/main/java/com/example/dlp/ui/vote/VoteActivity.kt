package com.example.dlp.ui.vote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dlp.VoteData
import com.example.dlp.ui.vote.adapter.VoteLIstRVAdapter
import com.example.dlp.databinding.ActivityVoteBinding
import com.example.dlp.network.NetworkModule.voteService
import com.example.dlp.network.request.VoteRequest
import com.example.dlp.util.PreferenceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VoteActivity : AppCompatActivity() {
    private val viewBinding: ActivityVoteBinding by lazy {
        ActivityVoteBinding.inflate(layoutInflater)
    }
    private val gridListAdapter by lazy {
        VoteLIstRVAdapter(gridList) {
        }
    }
    private var voteId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        voteId = intent.getIntExtra("voteId", 0)
        viewBinding.btnArrowLeft.setOnClickListener {
            finish()
        }

        viewBinding.btnVoteDone.setOnClickListener {
            val intent = Intent(this@VoteActivity, VoteDoneActivity::class.java)
            finish()
            startActivity(intent)
//            voteService.votes(VoteRequest(
//                PreferenceUtil.getFCMToken(),
//                voteId,
//                gridListAdapter.preferList
//            )).enqueue(object : Callback<Void> {
//                override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                    if (response.isSuccessful) {
//                        val intent = Intent(this@VoteActivity, VoteDoneActivity::class.java)
//                        finish()
//                        startActivity(intent)
//                    }
//                }
//
//                override fun onFailure(call: Call<Void>, t: Throwable) {
//                    t.printStackTrace()
//                }
//            })

        }

        val gridListManager = GridLayoutManager(this, 2)
        gridListAdapter.setOnItemClickListener { voteData ->

        }
        viewBinding.rvVote.adapter = gridListAdapter
        viewBinding.rvVote.layoutManager = GridLayoutManager(this, 2)


    }

    companion object {
        val gridList: ArrayList<VoteData> = arrayListOf<VoteData>().apply {
            add(VoteData("족발/보쌈"))
            add(VoteData("찜/탕/찌개"))
            add(VoteData("일식"))
            add(VoteData("피자"))
            add(VoteData("고기/구이"))
            add(VoteData("양식"))
            add(VoteData("치킨"))
            add(VoteData("중식"))
            add(VoteData("아시안"))
            add(VoteData("백반/죽/국수"))
            add(VoteData("분식"))
            add(VoteData("패스트푸드"))
        }
    }
}
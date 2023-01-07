package com.example.dlp.ui.vote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dlp.VoteData
import com.example.dlp.ui.vote.adapter.VoteLIstRVAdapter
import com.example.dlp.databinding.ActivityVoteBinding


class VoteActivity : AppCompatActivity() {
    private val viewBinding : ActivityVoteBinding by lazy {
        ActivityVoteBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.btnArrowLeft.setOnClickListener {
            finish()
        }

        viewBinding.btnVoteDone.setOnClickListener {
            val intent = Intent(this, VoteDoneActivity::class.java)
            finish()
            startActivity(intent)

        }

        val gridList: ArrayList<VoteData> = arrayListOf()
        gridList.apply{
            add(VoteData("족발/보쌈"))
            add(VoteData("찜/탕/찌개"))
            add(VoteData("일식"))
            add(VoteData("피자"))
            add(VoteData("고기/구이"))
            add(VoteData("치킨"))
            add(VoteData("중식"))
            add(VoteData("아시안"))
            add(VoteData("백반/죽/국수"))
            add(VoteData("분식"))
            add(VoteData("패스트푸드"))
        }

        val gridListManager = GridLayoutManager(this, 2)
        val gridListAdapter = VoteLIstRVAdapter(gridList){
            //RecyclerView Click 시 실행

            }
        gridListAdapter.setOnItemClickListener { voteData->

        }
        viewBinding.rvVote.adapter = gridListAdapter
        viewBinding.rvVote.layoutManager = GridLayoutManager(this, 2)
    }
}
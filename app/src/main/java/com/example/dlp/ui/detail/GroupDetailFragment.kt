package com.example.dlp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dlp.MainActivity
import com.example.dlp.MainActivityViewModel
import com.example.dlp.databinding.FragGroupDetailBinding
import com.example.dlp.network.NetworkModule.groupService
import com.example.dlp.network.NetworkModule.voteService
import com.example.dlp.network.request.StartVoteRequest
import com.example.dlp.network.request.VoteTerminationRequest
import com.example.dlp.network.response.IsVotingResponse
import com.example.dlp.network.response.SelectGroupResponse
import com.example.dlp.network.response.VoteTerminationResponse
import com.example.dlp.ui.home.HomeFragment
import com.example.dlp.ui.vote.VoteActivity
import com.example.dlp.ui.vote.VoteDoneActivity
import com.example.dlp.util.PreferenceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GroupDetailFragment(private val teamId: Int) : Fragment() {
    private lateinit var binding: FragGroupDetailBinding
    private lateinit var activityViewModel: MainActivityViewModel
    private val memberAdapter by lazy {
        GroupMemberAdapter()
    }

    private var voteId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragGroupDetailBinding.inflate(inflater, container, false)
        activityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        getDetail()
        initView()

        return binding.root
    }

    private fun initView() {

        binding.apply {
            joinVoteBt.setOnClickListener {
                Toast.makeText(requireContext(), "이미 투표를 진행하셨습니다!", Toast.LENGTH_SHORT).show()
            }
            btnBack.setOnClickListener {
                (activity as MainActivity).replaceFramgent(HomeFragment())
            }

            voteTerminateBt.setOnClickListener {
                voteService.terminateVote(VoteTerminationRequest(PreferenceUtil.getFCMToken(),
                    voteId)).enqueue(object : Callback<VoteTerminationResponse> {
                    override fun onResponse(
                        call: Call<VoteTerminationResponse>,
                        response: Response<VoteTerminationResponse>,
                    ) {
                        if (response.isSuccessful) {
                            val result = response.body()!!
                            val intent = Intent(requireContext(), VoteDoneActivity::class.java)
                            (activity as MainActivity).replaceFramgent(HomeFragment())
                            intent.putExtra("onEnd", true)
                            intent.putExtra("menu", result.menu)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<VoteTerminationResponse>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
            }

            layoutGotoVote.setOnClickListener {
                voteService.isVoting(memberToken = PreferenceUtil.getFCMToken(), voteId = voteId)
                    .enqueue(object : Callback<IsVotingResponse> {
                        override fun onResponse(
                            call: Call<IsVotingResponse>,
                            response: Response<IsVotingResponse>,
                        ) {
                            if (response.isSuccessful) {
                                val result = response.body()!!
                                if (result.voted) {
                                    // 이미 투표를 함를
                                    Toast.makeText(requireContext(),
                                        "이미 투표를 하셨습니다!",
                                        Toast.LENGTH_SHORT).show()
//                                    val intent =
//                                        Intent(requireContext(), VoteDoneActivity::class.java)
//                                    startActivity(intent)
                                } else {
                                    startVote()
                                }
                            }
                        }

                        override fun onFailure(call: Call<IsVotingResponse>, t: Throwable) {
                            t.printStackTrace()
                        }
                    })
            }
        }
    }

    private fun startVote() {
        voteService.startVote(StartVoteRequest(PreferenceUtil.getFCMToken(), teamId))
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        val intent = Intent(requireContext(), VoteActivity::class.java)
                        (activity as MainActivity).replaceFramgent(HomeFragment())
                        intent.putExtra("voteId", voteId)
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    private fun getDetail() {
        groupService.selectGroup(teamId).enqueue(object : Callback<SelectGroupResponse> {
            override fun onResponse(
                call: Call<SelectGroupResponse>,
                response: Response<SelectGroupResponse>,
            ) {
                if (response.isSuccessful) {
                    val result = response.body() ?: return
                    binding.tvGroupname.text = result.teamName
                    binding.teamCodeTv.text = result.teamCode
                    binding.personCountTv.text = "${result.memberResponses.count()}명"
                    binding.memberRv.layoutManager =
                        LinearLayoutManager(requireContext()).apply {
                            orientation = LinearLayoutManager.VERTICAL
                        }
                    memberAdapter.submitList(result.memberResponses)
                    binding.memberRv.adapter = memberAdapter

                    voteId = result.voteId
                    if (!result.isVoteProgress) {
                        binding.voteNotStartedLayout.visibility = View.VISIBLE
                        binding.voteStartedLayout.visibility = View.GONE
                    } else {
                        binding.voteNotStartedLayout.visibility = View.GONE
                        binding.voteStartedLayout.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<SelectGroupResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}
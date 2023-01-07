package com.example.dlp.ui.home

import BottomSheetDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dlp.MainActivity
import com.example.dlp.R
import com.example.dlp.databinding.FragHomeBinding
import com.example.dlp.network.HomeViewModel
import com.example.dlp.network.NetworkModule.groupService
import com.example.dlp.network.request.JoinGroupRequest
import com.example.dlp.network.response.GetMemberListResponse
import com.example.dlp.ui.creategroup.CreateGroupFragment
import com.example.dlp.ui.detail.GroupDetailFragment
import com.example.dlp.ui.dialogs.InsertGroupCodeDialogInterface
import com.example.dlp.ui.vote.VoteActivity
import com.example.dlp.util.PreferenceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), InsertGroupCodeDialogInterface {
    private lateinit var binding: FragHomeBinding
    private lateinit var viewModel: HomeViewModel
    private val homeRvAdapter: HomeRvAdapter by lazy {
        HomeRvAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_home, container, false)
        initClickListener()
        initView()
        initGroup()
        initObserver()
        return binding.root
    }

    private fun initView() {
        binding.tvCreategroup.setOnClickListener {
            (activity as MainActivity).replaceFramgent(CreateGroupFragment())
        }
        binding.tvParticipategroup.setOnClickListener {
            val bottomSheet = BottomSheetDialog(this@HomeFragment)
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun initObserver() {
        viewModel.groups.observe(this.viewLifecycleOwner) {
            it?.let {
                if (it.isEmpty()) {
                    binding.noGroupLayout.visibility = View.VISIBLE
                    binding.groupLayout.visibility = View.GONE
                } else {
                    binding.noGroupLayout.visibility = View.GONE
                    binding.groupLayout.visibility = View.VISIBLE
                    binding.groupRv.adapter = homeRvAdapter

                }
            }
        }
    }

    private fun initGroup() {
        groupService.getGroup(PreferenceUtil.getFCMToken())
            .enqueue(object : Callback<GetMemberListResponse> {
                override fun onResponse(
                    call: Call<GetMemberListResponse>,
                    response: Response<GetMemberListResponse>
                ) {
                    if (response.isSuccessful) {
                        val teamList = response.body()!!.teamList ?: emptyList()
                        if (teamList.isEmpty()) {
                            binding.noGroupLayout.visibility = View.VISIBLE
                            binding.groupLayout.visibility = View.GONE
                            binding.groupInfoLayout.visibility = View.GONE
                            binding.groupRv.visibility = View.GONE
                        } else {
                            binding.noGroupLayout.visibility = View.GONE
                            binding.groupLayout.visibility = View.VISIBLE
                            binding.groupInfoLayout.visibility = View.VISIBLE
                            binding.groupRv.visibility = View.VISIBLE
                            binding.groupRv.adapter = homeRvAdapter
                            binding.groupRv.layoutManager =
                                LinearLayoutManager(requireContext()).apply {
                                    orientation = LinearLayoutManager.VERTICAL
                                }
                            homeRvAdapter.submitList(teamList)
                            homeRvAdapter.setOnItemClickListener {
                                (activity as MainActivity).replaceFramgent(GroupDetailFragment(it.teamId))
                            }
                            homeRvAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<GetMemberListResponse>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    private fun initClickListener() {
        binding.tvEmptygroup.setOnClickListener {
            val intent = Intent(context, VoteActivity::class.java)
            startActivity(intent)
        }
        binding.apply {
            btnParticipateGroup.setOnClickListener {
                val bottomSheet = BottomSheetDialog(this@HomeFragment)
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
            btnCreategroup.setOnClickListener {
                (activity as MainActivity).replaceFramgent(CreateGroupFragment())
            }

        }
    }

    override fun onCompleteButtonClicked(content: String) {
        groupService.joinGroup(JoinGroupRequest(content, token = PreferenceUtil.getFCMToken())).enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    initGroup()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}
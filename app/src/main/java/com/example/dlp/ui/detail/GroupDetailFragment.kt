package com.example.dlp.ui.detail

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dlp.MainActivity
import com.example.dlp.MainActivityViewModel
import com.example.dlp.databinding.FragGroupDetailBinding
import com.example.dlp.network.NetworkModule.groupService
import com.example.dlp.network.response.SelectGroupResponse
import com.example.dlp.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GroupDetailFragment(private val itemId: Int) : Fragment() {
    private lateinit var binding: FragGroupDetailBinding
    private lateinit var activityViewModel: MainActivityViewModel
    private val memberAdapter by lazy {
        GroupMemberAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragGroupDetailBinding.inflate(inflater, container, false)
        activityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        binding.btnBack.setOnClickListener {
            (activity as MainActivity).replaceFramgent(HomeFragment())
        }
        getDetail()

        return binding.root
    }

    private fun getDetail() {
        groupService.selectGroup(itemId).enqueue(object: Callback<SelectGroupResponse>{
            override fun onResponse(
                call: Call<SelectGroupResponse>,
                response: Response<SelectGroupResponse>
            ) {
                if(response.isSuccessful){
                    val result = response.body() ?: return
                    binding.tvGroupname.text = result.teamName
                    binding.teamCodeTv.text = result.teamCode
                    binding.personCountTv.text = "${result.memberResponses.count()}명"
                    binding.memberRv.layoutManager =
                        LinearLayoutManager(requireContext()).apply {
                            orientation = LinearLayoutManager.VERTICAL
                        }
                    memberAdapter.submitList(result.memberResponses)
                    binding.memberRv.adapter =memberAdapter
                }
            }

            override fun onFailure(call: Call<SelectGroupResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}
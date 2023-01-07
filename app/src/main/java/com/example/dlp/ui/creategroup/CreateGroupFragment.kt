package com.example.dlp.ui.creategroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dlp.MainActivity
import com.example.dlp.MainActivityViewModel
import com.example.dlp.R
import com.example.dlp.databinding.FragCreategroupBinding
import com.example.dlp.network.NetworkModule.groupService
import com.example.dlp.network.request.CreateGroupRequest
import com.example.dlp.network.response.TeamMealTime
import com.example.dlp.util.PreferenceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateGroupFragment : Fragment() {

    private lateinit var binding: FragCreategroupBinding
    private lateinit var activityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragCreategroupBinding.inflate(inflater, container, false)
        activityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        binding.createNewGroupBt.setOnClickListener {
            val teamName = binding.etInsertgroupname.text.toString()
            if (teamName.isBlank()) {
                Toast.makeText(requireContext(), "팀 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                groupService.createGroup(CreateGroupRequest(PreferenceUtil.getFCMToken(), teamName))
                    .enqueue(object : Callback<TeamMealTime> {
                        override fun onResponse(
                            call: Call<TeamMealTime>,
                            response: Response<TeamMealTime>
                        ) {
                            if (response.isSuccessful) {
                                activityViewModel.setTeamCode(response.body()!!.teamCode)
                                (activity as MainActivity).replaceFramgent(CreateGroupCodeFragment())
                            }
                        }

                        override fun onFailure(call: Call<TeamMealTime>, t: Throwable) {
                            t.printStackTrace()
                        }
                    })
            }
        }
        return binding.root
    }
}
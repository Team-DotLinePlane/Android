package com.example.dlp.ui.creategroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dlp.ui.home.HomeFragment
import com.example.dlp.MainActivity
import com.example.dlp.MainActivityViewModel
import com.example.dlp.databinding.FragGroupcodeBinding

class CreateGroupCodeFragment : Fragment() {

    private lateinit var binding: FragGroupcodeBinding
    private lateinit var activityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragGroupcodeBinding.inflate(inflater, container, false)
        activityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        activityViewModel.teamCode.value.let {
            it?.let {
                if (it.isNotBlank()) {
                    binding.number1Tv.text = it[0].toString()
                    binding.number2Tv.text = it[1].toString()
                    binding.number3Tv.text = it[2].toString()
                    binding.number4Tv.text = it[3].toString()
                }
            }
        }

        binding.confirmBt.setOnClickListener {
            (activity as MainActivity).replaceFramgent(HomeFragment())
        }

        return binding.root
    }
}
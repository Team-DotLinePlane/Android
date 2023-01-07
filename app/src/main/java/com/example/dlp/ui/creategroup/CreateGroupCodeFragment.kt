package com.example.dlp.ui.creategroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dlp.HomeFragment
import com.example.dlp.MainActivity
import com.example.dlp.databinding.FragCreategroupBinding
import com.example.dlp.databinding.FragGroupcodeBinding

class CreateGroupCodeFragment : Fragment() {

    private lateinit var binding: FragGroupcodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragGroupcodeBinding.inflate(inflater, container, false)
        binding.confirmBt.setOnClickListener {
            (activity as MainActivity).replaceFramgent(HomeFragment())
        }
        return binding.root
    }
}
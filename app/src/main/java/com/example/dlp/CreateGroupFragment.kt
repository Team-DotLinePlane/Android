package com.example.dlp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.dlp.databinding.FragCreategroupBinding
import com.example.dlp.databinding.FragHomeBinding

class CreateGroupFragment : Fragment() {
    lateinit var binding : FragCreategroupBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_creategroup, container, false)
        return binding.root
    }
}
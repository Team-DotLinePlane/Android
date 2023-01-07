package com.example.dlp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.dlp.databinding.FragHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_home, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        
    }
}
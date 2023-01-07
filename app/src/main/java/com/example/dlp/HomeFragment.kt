package com.example.dlp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.dlp.databinding.FragHomeBinding
import com.example.dlp.ui.vote.VoteActivity

class HomeFragment : Fragment() {
    lateinit var binding: FragHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_home, container, false)
        initView()
        binding.tvCreategroup.setOnClickListener {
            val intent = Intent(context, VoteActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun initView() {
        
    }
}
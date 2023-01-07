package com.example.dlp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dlp.MainActivityViewModel
import com.example.dlp.databinding.FragGroupDetailBinding
import com.example.dlp.databinding.FragMapBinding
import com.example.dlp.ui.map.MapViewModel
import com.example.dlp.ui.map.adapter.MapCategoryAdapter
import net.daum.mf.map.api.MapView

class GroupDetailFragment : Fragment() {
    private lateinit var binding: FragGroupDetailBinding
    private lateinit var activityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragGroupDetailBinding.inflate(inflater, container, false)
        activityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        return binding.root
    }
}
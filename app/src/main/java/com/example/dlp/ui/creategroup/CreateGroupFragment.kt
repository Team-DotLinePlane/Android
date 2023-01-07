package com.example.dlp.ui.creategroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dlp.MainActivity
import com.example.dlp.MainActivityViewModel
import com.example.dlp.databinding.FragCreategroupBinding
import com.example.dlp.databinding.FragMapBinding
import com.example.dlp.ui.map.MapViewModel
import com.example.dlp.ui.map.adapter.MapCategoryAdapter
import net.daum.mf.map.api.MapView

class CreateGroupFragment : Fragment() {

    private lateinit var binding: FragCreategroupBinding
//    private lateinit var viewModel: MapViewModel
//    private lateinit var activityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragCreategroupBinding.inflate(inflater, container, false)
        binding.createNewGroupBt.setOnClickListener {
            (activity as MainActivity).replaceFramgent(CreateGroupCodeFragment())
        }
        return binding.root
    }
}
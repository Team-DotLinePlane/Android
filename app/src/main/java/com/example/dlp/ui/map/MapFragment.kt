package com.example.dlp.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dlp.databinding.FragMapBinding
import com.example.dlp.ui.map.adapter.MapCategoryAdapter


class MapFragment : Fragment() {

    private var _binding: FragMapBinding? = null
    private val binding get() = _binding!!
    private val categoryAdapter: MapCategoryAdapter by lazy {
        MapCategoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragMapBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        categoryAdapter.submitList(CATEGORY_DUMMY)
        binding.apply {
            categoryRv.layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            categoryRv.adapter = categoryAdapter
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val CATEGORY_DUMMY = listOf<String>("양식", "한식", "일식", "중식", "아시안")
    }
}
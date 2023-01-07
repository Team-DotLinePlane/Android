package com.example.dlp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dlp.databinding.FragHistoryBinding
import com.example.dlp.ui.map.MapFragment

class HistoryFragment : Fragment() {
    lateinit var binding: FragHistoryBinding
    private lateinit var activityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_history, container, false)
        activityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        val list: ArrayList<HistoryData> = arrayListOf()
        list.apply {
            add(HistoryData("2023-01-07", "한식", "김치볶음밥"))
            add(HistoryData("2023-01-07", "한식", "김치볶음밥"))
            add(HistoryData("2023-01-07", "한식", "김치볶음밥"))
            add(HistoryData("2023-01-07", "한식", "김치볶음밥"))
            add(HistoryData("2023-01-07", "한식", "김치볶음밥"))
            add(HistoryData("2023-01-07", "한식", "김치볶음밥"))
        }

        val listManager = LinearLayoutManager.HORIZONTAL
        val listRVAdaptor = HistoryListAdaptor(list) { searchStr ->
            activityViewModel.updateSelectedKeyword(searchStr)
            (activity as MainActivity).replaceToMap(MapFragment())
        }
        binding.rvList.adapter = listRVAdaptor

        binding.rvList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }
}
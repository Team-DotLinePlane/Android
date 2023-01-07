package com.example.dlp.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dlp.databinding.FragMapBinding
import com.example.dlp.ui.map.adapter.MapCategoryAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class MapFragment : Fragment() {

    private lateinit var binding: FragMapBinding
    private lateinit var viewModel: MapViewModel
    private lateinit var mapView: MapView
    private val categoryAdapter: MapCategoryAdapter by lazy {
        MapCategoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragMapBinding.inflate(inflater, container, false)
        mapView = MapView(requireContext())
        viewModel = ViewModelProvider(this)[MapViewModel::class.java]
        viewModel.addLocationListener()

        initObserver()
        initView()
        return binding.root
    }

    private fun initObserver() {
        viewModel.userLocation.observe(this.viewLifecycleOwner) {
            it?.let {
                mapView.setMapCenterPoint(
                    MapPoint.mapPointWithGeoCoord(it.latitude, it.longitude),
                    true
                )
                viewModel.getRestaurant(it.latitude, it.longitude)
            }
        }
        viewModel.markers.observe(this.viewLifecycleOwner) {
            it?.let {
                mapView.removeAllPOIItems()
                it.forEach {
                    val marker = MapPOIItem()
                    marker.itemName = it.placeName
                    marker.tag = 0
                    marker.mapPoint =
                        MapPoint.mapPointWithGeoCoord(it.y.toDouble(), it.x.toDouble())
                    marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
                    marker.selectedMarkerType =
                        MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                    mapView.addPOIItem(marker)
                }
            }
        }
    }

    private fun initView() {
        categoryAdapter.submitList(CATEGORY_DUMMY)
        binding.apply {
            categoryRv.layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            categoryRv.adapter = categoryAdapter
        }

        initMapView()
    }

    private fun initMapView() {
        binding.mapView.addView(mapView)
        // 중심점 변경
//        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.removeLocationListener()
    }

    companion object {
        val CATEGORY_DUMMY = listOf("양식", "한식", "일식", "중식", "아시안", "분식", "수식", "장식", "호식")
    }
}
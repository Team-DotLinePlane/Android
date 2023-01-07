package com.example.dlp.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dlp.R
import com.example.dlp.databinding.FragMapBinding
import com.example.dlp.ui.map.adapter.MapCategoryAdapter
import net.daum.mf.map.api.CalloutBalloonAdapter
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
                    marker.itemName = "${it.placeName}|${it.categoryName}|${it.phone}"
                    marker.tag = 0
                    marker.mapPoint =
                        MapPoint.mapPointWithGeoCoord(it.y.toDouble(), it.x.toDouble())
                    marker.markerType = MapPOIItem.MarkerType.CustomImage; // 마커타입을 커스텀 마커로 지정.
                    marker.customImageResourceId = R.drawable.img_custom_marker; // 마커 이미지.
                    marker.isCustomImageAutoscale = true
                    marker.setCustomImageAnchor(0.5f, 1.0f)
                    mapView.addPOIItem(marker)
                }
            }
        }
    }

    private fun initView() {
        categoryAdapter.submitList(CATEGORY_DUMMY)
        categoryAdapter.setOnItemClickListener {
            categoryAdapter.selectedStr = it
            viewModel.setSearchStr(it)
            categoryAdapter.notifyDataSetChanged()
        }
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
        mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))
    }

    inner class CustomBalloonAdapter(inflater: LayoutInflater) : CalloutBalloonAdapter {
        private val mCalloutBalloon: View = inflater.inflate(R.layout.item_balloon_layout, null)
        private val placeName: TextView = mCalloutBalloon.findViewById(R.id.place_name_tv)
        private val categoryName: TextView = mCalloutBalloon.findViewById(R.id.category_tv)
        private val phoneNumber: TextView = mCalloutBalloon.findViewById(R.id.phone_tv)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선
            val poiInfo = poiItem?.itemName!!.split("|")
            placeName.text = poiInfo[0]
            categoryName.text = poiInfo[1]
            phoneNumber.text = poiInfo[2]
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            return mCalloutBalloon
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.removeLocationListener()
    }

    companion object {
        val CATEGORY_DUMMY = listOf("양식", "한식", "일식", "중식", "아시안", "분식", "수식", "장식", "호식")
    }
}
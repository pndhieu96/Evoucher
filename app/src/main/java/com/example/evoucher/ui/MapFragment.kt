package com.example.evoucher.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.databinding.FragmentMapBinding
import com.example.evoucher.model.ChiNhanh
import com.example.evoucher.utils.Utils.Companion.observer
import com.example.evoucher.viewModel.MapVm
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.HashMap


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate) {
    private var mMap: GoogleMap? = null
    private val mHashMap = HashMap<String, String>()
    private val mapVm: MapVm by viewModels()

    val myLocation = LatLng(10.807235853076694, 106.7189197947759)

    override fun initObserve() {
        mapVm.branch.observer(
            viewLifecycleOwner,
            onSuccess = {
                binding.pbLoading.visibility = GONE
                it.forEach {
                    if(!it.kinhDo.isEmpty() && !it.viDo.isEmpty()) {
                        setCustomMarker(it)
                    }
                }
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16f))
                mMap?.setOnMarkerClickListener(onMarkerClick)
            },
            onLoading = {
                binding.pbLoading.visibility = VISIBLE
            },
            onError = {
                binding.pbLoading.visibility = GONE
            }
        )
    }

    override fun initialize() {
        binding.mapView.onCreate(null)
        binding.mapView.onResume()
        try {
            MapsInitializer.initialize(requireContext().applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.mapView.getMapAsync(mapCallBack)
    }

    private var mapCallBack = object : OnMapReadyCallback{
        override fun onMapReady(googleMap: GoogleMap) {
            mMap = googleMap
            mMap?.addMarker(
                MarkerOptions()
                    .position(myLocation)
                    .title("Vị trí của bạn")
            )
            mapVm.getBranch()
        }
    }

    private var onMarkerClick = object : OnMarkerClickListener{
        override fun onMarkerClick(marker: Marker): Boolean {
//            Toast.makeText(context, mHashMap.get(marker.id).toString(), Toast.LENGTH_LONG).show()
            return false
        }
    }

    fun setCustomMarker(chiNhanh: ChiNhanh) {
        val latLng = LatLng(chiNhanh.kinhDo.toDouble(), chiNhanh.viDo.toDouble())
        val blackMarkerIcon : BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_store)
        val markerOptions : MarkerOptions = MarkerOptions().position(latLng)
            .title(chiNhanh.ten)
            .snippet(chiNhanh.diaChi)
            .icon(blackMarkerIcon)
        val marker = mMap?.addMarker(markerOptions)
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))

        marker?.id?.let {
            mHashMap.put(it, UUID.randomUUID().toString())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.onDestroy()
    }
}
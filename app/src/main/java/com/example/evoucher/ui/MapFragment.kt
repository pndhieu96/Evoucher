package com.example.evoucher.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.evoucher.R
import com.example.evoucher.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment() {
    private var mMap: GoogleMap? = null
    private lateinit var binding: FragmentMapBinding

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater)
        binding.mapView.onCreate(savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

            // Add a marker in Sydney and move the camera

            // Add a marker in Sydney and move the camera
            val myLocation = LatLng(10.807235853076694, 106.7189197947759)
            mMap?.addMarker(
                MarkerOptions()
                    .position(myLocation)
                    .title("Vi tri cua ban")
            )

            setCustomMarker(LatLng(10.808421307800092, 106.71689693822319))
            setCustomMarker(LatLng(10.80701321220181, 106.71835734016057))
            setCustomMarker(LatLng(10.806608383494693, 106.72035531336324))
            setCustomMarker(LatLng(10.806581407550953, 106.71600479241356))
            setCustomMarker(LatLng(10.804762546208464, 106.71679215139723))

            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16f))

        }
    }

    fun setCustomMarker(latLng: LatLng) {
        val blackMarkerIcon : BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_store)
        val markerOptions : MarkerOptions = MarkerOptions().position(latLng)
            .title("Bibica")
            .snippet("15 Xo viet nghe tinh, Binh Thanh, TPHCM")
            .icon(blackMarkerIcon)
        mMap?.addMarker(markerOptions)
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }
}
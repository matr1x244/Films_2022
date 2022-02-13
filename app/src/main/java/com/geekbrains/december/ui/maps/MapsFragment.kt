package com.geekbrains.december.ui.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.geekbrains.december.R
import com.geekbrains.december.databinding.FragmentMapsBinding
import com.geekbrains.december.model.AppState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.IOException
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapsFragment : Fragment(), CoroutineScope by MainScope() {

    private lateinit var map: GoogleMap
    private var menu: Menu? = null
    private val markers: ArrayList<Marker> = ArrayList()
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapsViewModel by viewModel()

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true // кнопки увеличение приближение
        map.uiSettings.isMyLocationButtonEnabled = true // моё местонахождение

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {map.isMyLocationEnabled = true }

        /**/
        val LocalStartPositionMaker = LatLng(56.6286309, 47.9279793) //LatLng(56.6286309, 47.9279793)
        val marker = googleMap.addMarker(
            MarkerOptions()
                .position(LocalStartPositionMaker)
                .title("Йошкар-Ола (Кинотеатр Супер8)")
        )
        marker?.let { markers.add(it) }

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LocalStartPositionMaker))

        googleMap.setOnMapLongClickListener { latLng ->
            setMarker(latLng,"Новая точка")
            drawLine()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.maps_google) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        arguments?.getInt(BUNDLE_EXTRA_MAPS)?.let {

            viewModel.loadCountry(it)
            with(binding) {
                viewModel.filmsLiveData.observe(viewLifecycleOwner) { appState ->
                    when (appState) {
                        is AppState.Success -> {
                            searchAddress.setText(appState.filmsData[0].country)
                            initSearchByAddress()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_map, menu)
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_map_mode_normal -> {
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
                return true
            }
            R.id.menu_map_mode_satellite -> {
                map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                return true
            }
            R.id.menu_map_mode_terrain -> {
                map.mapType = GoogleMap.MAP_TYPE_TERRAIN
                return true
            }
        }

        return false
    }

    private fun initSearchByAddress() = with(binding) {
        buttonSearchCountry.setOnClickListener {
            val geoCoder = Geocoder(it.context)
            val searchText = searchAddress.text.toString()
            launch(Dispatchers.IO) {
                try {
                    val addresses = geoCoder.getFromLocationName(searchText, 1)
                    if (addresses.isNotEmpty()) {
                        goToAddress(addresses, searchText)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun goToAddress(addresses: MutableList<Address>, searchText: String) {
        launch {
            val location = LatLng(addresses[0].latitude, addresses[0].longitude)

            setMarker(location, searchText)

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 2f))
        }
    }

    private fun setMarker(location: LatLng, searchText: String) {
        map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
        )?.let { markers.add(it) }
    }

    private fun drawLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val previous: LatLng = markers[last - 1].position
            val current: LatLng = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(previous, current)
                    .color(Color.RED)
                    .width(1f)
            )
        }
    }

    companion object {
        fun newInstance() = MapsFragment()
        const val BUNDLE_EXTRA_MAPS = "films"
        fun newInstance(bundle: Bundle): MapsFragment {
            val fragment = MapsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}

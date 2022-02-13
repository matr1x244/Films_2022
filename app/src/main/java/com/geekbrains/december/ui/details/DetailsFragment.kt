package com.geekbrains.december.ui.details

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import coil.load
import coil.size.Precision
import coil.size.Scale
import com.geekbrains.december.R
import com.geekbrains.december.databinding.DetailsFragmentBinding
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.entities.DataFilms
import com.geekbrains.december.ui.maps.MapsFragment.Companion.BUNDLE_EXTRA_MAPS
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val REFRESH_PERIOD = 1000L
private const val MINIMAL_DISTANCE = 1f

class DetailsFragment: Fragment(), CoroutineScope by MainScope() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //детали.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<DataFilms>(BUNDLE_EXTRA)?.let {
            with(binding) {
                //Передаем данные в detailFragment
                viewModel.filmsLiveData.observe(viewLifecycleOwner) { appState ->
                    when (appState) {
                        is AppState.Error -> {
                            detailsFragment.visibility = View.INVISIBLE
                        }
                        AppState.Loading -> {
                            detailsFragment.visibility = View.INVISIBLE
                        }
                        is AppState.Success -> {
                            detailsFragment.visibility = View.VISIBLE

                            //ЧТО НУЖНО ЗАБРАТЬ И ПОКАЗАТЬ С API прогрузить в поля
                            val url_path_poster_details =
                                "https://st.kp.yandex.net/images/film_big/${appState.filmsData[0].id}.jpg"
                            itemPoster.load(url_path_poster_details) {
                                precision(Precision.EXACT)
                                error(R.drawable.ic_video)
                                scale(Scale.FILL)
                            }
                            listId.text = appState.filmsData[0].id.toString()
                            listTitle.text = appState.filmsData[0].name
                            listTmdb.text = appState.filmsData[0].imdb.toString()
                            listReleaseDate.text = appState.filmsData[0].year.toString()
                            listDescription.text = appState.filmsData[0].description
                            listSlogan.text = appState.filmsData[0].slogan

                            listCountry.text = appState.filmsData[0].country

                            //mapsFragmentFABtnTest.setOnClickListener { checkLocationPermission() }

                            mapsFragmentFABtn.setOnClickListener(object : View.OnClickListener {
                                override fun onClick(v: View?) {
                                    checkLocationPermission()

                                    val manager = activity?.supportFragmentManager
                                    manager?.let { manager ->
                                        val bundle = Bundle().apply {
                                            putInt(BUNDLE_EXTRA_MAPS,appState.filmsData[0].id)
                                        }
                                        requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_detailsFragment_to_navigation_maps, bundle)
/*                                        manager.beginTransaction()
                                            .add(R.id.container, MapsFragment.newInstance(bundle))
                                            .addToBackStack("")
                                            .commitAllowingStateLoss()*/
                                    }
                                }
                            })
                        }
                        }
                    }
                }
                viewModel.loadData(it.id)
            }
        }

        override fun onDestroyView() {
            cancel() // Отмена корутин
            super.onDestroyView()
            _binding = null
        }

    /*Запрос разрешения на maps*/
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        } else {
            permissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    /*Проверяем разрешения на использование GPS*/
    private val permissionResult = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            getLocation()
        } else {
            Toast.makeText(context, getString(R.string.dialog_message_no_gps), Toast.LENGTH_SHORT).show()
        }
    }

    private val onLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            context?.let {
                getAddressAsync(it, location)
            }
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    /*Работа GPS*/
    @SuppressLint("MissingPermission")
    @Suppress("DEPRECATION")
    private fun getLocation() {
        context?.let { context ->
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    REFRESH_PERIOD,
                    MINIMAL_DISTANCE,
                    onLocationListener)
            } else {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location == null) {
                    Toast.makeText(context, getString(R.string.dialog_title_gps_turned_off), Toast.LENGTH_SHORT).show()
                } else {
                    getAddressAsync(context, location)
                }
            }
        }
    }

    private fun getAddressAsync(context: Context, location: Location) {
        val geoCoder = Geocoder(context)
        launch {
            val job = async(Dispatchers.IO) {
                geoCoder.getFromLocation(location.latitude, location.longitude, 1)
            }
            val addresses = job.await()
            showAddressDialog(addresses[0].getAddressLine(0), location)
        }
    }

    private fun showAddressDialog(address: String, location: Location) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_address_title))
                .setMessage(address)
                .setPositiveButton(getString(R.string.dialog_address_title)) { _, _ ->
                //openDetailsFragment(DataFilms(address, location.latitude,location.longitude))) // сюда надо засетить страну происхождения фильма по координатам
                }
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }


        companion object {
            const val BUNDLE_EXTRA = "films"

            fun newInstance(bundle: Bundle): DetailsFragment {
                val fragment = DetailsFragment()
                fragment.arguments = bundle
                return fragment
            }
        }
    }


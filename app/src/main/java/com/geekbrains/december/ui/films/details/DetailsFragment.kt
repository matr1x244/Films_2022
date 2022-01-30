package com.geekbrains.december.ui.films.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import coil.load
import com.geekbrains.december.R
import com.geekbrains.december.databinding.DetailsFragmentBinding
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.FilmsLoaderPoster
import com.geekbrains.december.model.entities.DataFilms
import com.geekbrains.december.model.entities.rest.rest_entities.MovieDetailsDTO
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


class DetailsFragment: Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //детали.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<DataFilms>(BUNDLE_EXTRA)?.let {
            with(binding) {
                //Передаем данные в detailFragment
                //itemPoster.setImageResource(R.drawable.films)

                viewModel.filmsLiveData.observe(viewLifecycleOwner, { appState ->
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

                            /*Загружаем через COIL image*/

                             itemPoster.load("https://ae01.alicdn.com/kf/HTB1MkfEntbJ8KJjy1zjq6yqapXas/-.jpg_q50.jpg"){
                                crossfade(true)
                                placeholder(R.drawable.films)
                            }

                            listId.text = appState.filmsData[0].id.toString()
                            listTitle.text = appState.filmsData[0].name
                            listTmdb.text = appState.filmsData[0].tmdb.toString() // НЕ ПРОГРУЖАЕТ!!!
                            listReleaseDate.text = appState.filmsData[0].year.toString()
                            listDescription.text = appState.filmsData[0].description
                            listSlogan.text = appState.filmsData[0].slogan
                        }
                    }
                })
            }
            viewModel.loadData(it.id)
        }
    }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
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


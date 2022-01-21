package com.geekbrains.december.ui.films.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.december.R
import com.geekbrains.december.databinding.DetailsFragmentBinding
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.entities.DataFilms
import org.koin.androidx.viewmodel.ext.android.viewModel


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
                val films = it.dataMovie
                //Что будем передавать
                itemPosterPath.setImageResource(R.drawable.films)
                //listId.text = films.id.toString()
                //listTitle.text = films.original_title
                listPopularity.text = films.popularity.toString()
                listReleaseDate.text = films.year
                listAbout.text = films.description_about
                listSlogan.text = films.slogan

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
                            listId.text = appState.filmsData[0].id.toString()
                            listTitle.text = appState.filmsData[0].original_title

                        }
                    }
                })
            }
            viewModel.loadData(it.dataMovie.id, it.dataMovie.original_title)
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
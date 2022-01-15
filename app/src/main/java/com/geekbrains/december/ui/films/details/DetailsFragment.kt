package com.geekbrains.december.ui.films.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.december.R
import com.geekbrains.december.databinding.DetailsFragmentBinding
import com.geekbrains.december.model.entities.DataFilms


class DetailsFragment: Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

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

                /*Передаем данные в detailFragment*/
                val films = it.dataMovie
                /*Что будем передавать*/
                itemPosterPath.setImageResource(R.drawable.setting)
                listId.text = films.id.toString()
                listTitle.text = films.title
                listPopularity.text = films.popularity
                listReleaseDate.text = films.release_date
                listPopularity.text = films.popularity
                listAbout.text = films.about_move
            }
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
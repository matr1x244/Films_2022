package com.geekbrains.december.ui.serials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.geekbrains.december.R
import com.geekbrains.december.Service.MyForegroundService
import com.geekbrains.december.databinding.FragmentSerialsBinding
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.entities.DataFilms
import com.geekbrains.december.model.entities.showSnackBarAction
import com.geekbrains.december.model.entities.showSnackBarNoAction
import com.geekbrains.december.ui.films.adapters.FilmsFragmentAdapter
import com.geekbrains.december.ui.films.details.DetailsFragment
import com.geekbrains.december.ui.films.main.FilmsFragment
import com.geekbrains.december.ui.films.main.FilmsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SerialsFragment : Fragment(){

    private val viewModel: SerialsViewModel by viewModel()

    private var _binding: FragmentSerialsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentSerialsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}



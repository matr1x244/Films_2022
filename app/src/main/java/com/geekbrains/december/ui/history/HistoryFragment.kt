package com.geekbrains.december.ui.history

import android.media.Image
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.geekbrains.december.R
import com.geekbrains.december.databinding.FragmentHistoryBinding
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.database.HistoryDAO
import com.geekbrains.december.model.database.HistoryDAO_Impl
import com.geekbrains.december.model.database.HistoryEntity
import com.geekbrains.december.model.entities.showSnackBarAction
import com.geekbrains.december.model.entities.showSnackBarNoAction
import com.geekbrains.december.model.repository.Repository
import com.geekbrains.december.ui.films.adapters.HistoryAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class HistoryFragment: Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by inject()
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        historyFragmentRecyclerview.adapter = adapter
        viewModel.historyLiveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getAllHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                historyFragmentRecyclerview.visibility = View.VISIBLE
                progressBarHistory.visibility = View.GONE
                adapter.setFilms(appState.filmsData)
            }
            is AppState.Loading -> {
                historyFragmentRecyclerview.visibility = View.GONE
                progressBarHistory.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                progressBarHistory.visibility = View.GONE
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}

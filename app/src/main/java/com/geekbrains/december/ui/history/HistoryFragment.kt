package com.geekbrains.december.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.december.databinding.FragmentHistoryBinding
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.entities.DataFilms
import com.geekbrains.december.ui.films.adapters.HistoryAdapter
import org.koin.android.ext.android.inject

class HistoryFragment: Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by inject()

    private val adapterHistory: HistoryAdapter by lazy { HistoryAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(dataFilms: DataFilms) {
                viewModel.deleteMovie(dataFilms) //удаляем
                viewModel.getAllHistory() //обновленная история прогружаем
            }
    })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        historyFragmentRecyclerview.adapter = adapterHistory
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
                adapterHistory.setData(appState.filmsData)
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

    /*кликабельность на удаление*/
    interface OnItemViewClickListener {
        fun onItemViewClick(dataFilms: DataFilms)
    }


    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }

}

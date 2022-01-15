package com.geekbrains.december.ui.films.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.december.R
import com.geekbrains.december.databinding.FragmentFilmsBinding

import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.entities.DataFilms
import com.geekbrains.december.ui.films.adapters.FilmsFragmentAdapter
import com.geekbrains.december.ui.films.details.DetailsFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class FilmsFragment : Fragment() {

    private val viewModel: FilmsViewModel by viewModel()

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    private var adapter: FilmsFragmentAdapter? = null
    private var isDataSetRus: Boolean = true


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?)
    : View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentFAB.setOnClickListener{ changeMovieDataSet()}
            /*Для запуска и обвноления данных*/
            viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})
            viewModel.getFilmsFromLocalSourceRus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeMovieDataSet() = with(binding) {
        if(isDataSetRus){
            viewModel.getFilmsFromLocalSourceWorld()
            mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getFilmsFromLocalSourceRus()
            mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus
    }



    private fun renderData(appState: AppState) = with(binding){
        when(appState){
            is AppState.Success -> {
                progressBar.visibility = View.GONE
                adapter = FilmsFragmentAdapter(object : OnItemViewClickListener{
                    override fun onItemViewClick(films: DataFilms) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(DetailsFragment.BUNDLE_EXTRA, films)
                            }
                            Snackbar.make(binding.mainFragmentFAB,"Успешно", Snackbar.LENGTH_SHORT).show()
                            manager.beginTransaction()
                                .replace(R.id.container_films_fragment,DetailsFragment.newInstance(bundle)) // ЧЕРЕЗ ФОН ИДЁТ КЛИКАБЕЛЬНОСТЬ!!! а если убрать белый фон тогда всё видно...
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                        }
                    }
                }).apply { setFilms(appState.filmsData) }
                mainFragmentRecyclerView.adapter = adapter
            }
            is AppState.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                progressBar.visibility = View.GONE
                //В каком fragmente показывать snackback
                Snackbar.make(binding.mainFragmentFAB,"Error", Snackbar.LENGTH_INDEFINITE).setAction("Reload") {
                    viewModel.getFilmsFromLocalSourceRus()
                }.show()

            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(films: DataFilms)
    }

    companion object{
        fun newInstance() = FilmsFragment()
    }

}


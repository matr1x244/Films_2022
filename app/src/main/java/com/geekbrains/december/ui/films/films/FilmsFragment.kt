package com.geekbrains.december.ui.films.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.geekbrains.december.R
import com.geekbrains.december.databinding.FragmentFilmsBinding
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.entities.DataFilms
import com.geekbrains.december.model.entities.showSnackBarNoAction
import com.geekbrains.december.ui.adapters.RecyclerViewFragmentAdapter
import com.geekbrains.december.ui.details.DetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmsFragment : Fragment() {

    private val viewModel: FilmsViewModel by viewModel()

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    private var adapterFilms: RecyclerViewFragmentAdapter? = null


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?)
    : View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

            /*Запускаем RecyclerView*/
            FilmsFragmentRecyclerView.adapter = adapterFilms
            /*Для запуска и обвноления данных*/
            viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})
            viewModel.getFilmsFromLoad()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun renderData(appState: AppState) = with(binding){
        when(appState){
            is AppState.Success -> {
                FilmsProgressBar.visibility = View.GONE // режим работы прогресс бара
                containerFilmsFragment.showSnackBarNoAction("Загрузка успешна")
                adapterFilms = RecyclerViewFragmentAdapter(object : OnItemViewClickListener{

                    override fun onItemViewClick(films: DataFilms) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(DetailsFragment.BUNDLE_EXTRA, films)
                            }
                            /*я использую. в проекте навхост поэтому детаилс фрагмент нужно открывать так*/
                            requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_films_to_detailsFragment,bundle)

                            /*так просто открываем и передаем в новый фрагмент без использования навхост
                                    manager.beginTransaction()
                                    .add(R.id.container_films_fragment,DetailsFragment.newInstance(bundle)) // ЧЕРЕЗ ФОН ИДЁТ КЛИКАБЕЛЬНОСТЬ!!! а если убрать белый фон тогда всё видно...
                                    .addToBackStack("")
                                    .commitAllowingStateLoss()*/
                        }

                    }
                    /*Обновляем recycleview если подходим к концу*/
                    override fun onDataEnd(from: Int, sizeToRequest: Int) {
                        viewModel.getMoreMovies(from, sizeToRequest)
                    }

                }).apply { setData(appState.filmsData) }
                FilmsFragmentRecyclerView.adapter = adapterFilms
            }
            is AppState.Loading -> {
                FilmsProgressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                FilmsProgressBar.visibility = View.GONE
                containerFilmsFragment.showSnackBarNoAction("Ошибка загрузки")
                //Функцию расширение используем стандартную
            }
        }
    }


    interface OnItemViewClickListener {
        fun onItemViewClick(films: DataFilms)
        /*Для обновления списка*/
        fun onDataEnd(from: Int, sizeToRequest: Int)
    }

    companion object{
        fun newInstance() = FilmsFragment()
    }
}


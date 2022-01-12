package com.geekbrains.december.ui.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.december.R
import com.geekbrains.december.databinding.FragmentFilmsBinding
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.entities.DataFilms
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class FilmsFragment : Fragment(), AdapterRecycleView.OnItemClickListener {

    private val viewModel:  FilmsViewModel by viewModel()
    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*Иницилизация recycleview*/
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerViewFilms)
        //startRecycleFilms(recyclerView)
        /*Иницилизация recycleview*/

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Для запуска и обвноления данных*/
        val observer = Observer<AppState>{ renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getFilms()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) = with(binding){
        when(appState){
            is AppState.Success -> {
                val loadDataBase = appState.dataFilmsData
                progressBar.visibility = View.GONE
                filmsGroup.visibility = View.VISIBLE
                setData(loadDataBase)
                Snackbar.make(containerFilmsFragment,"Success", Snackbar.LENGTH_SHORT).show()
            }
            is AppState.Loading -> {
                filmsGroup.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                progressBar.visibility = View.GONE
               filmsGroup.visibility = View.INVISIBLE
                //В каком fragmente показывать snackback
                Snackbar.make(containerFilmsFragment,"Error", Snackbar.LENGTH_INDEFINITE).setAction("Reload") {
                    viewModel.getFilms()
                }.show()

            }
        }
    }

    /*Обновление данных в ui в каких полях*/
    private fun setData(dataFilmsData: DataFilms) = with(binding) {
        itemPosterPath.setImageResource(R.drawable.setting)
        listId.text = dataFilmsData.dataMovie.id.toString()
        listTitle.text = dataFilmsData.dataMovie.title
        listPopularity.text = dataFilmsData.dataMovie.popularity
        listReleaseDate.text = dataFilmsData.dataMovie.release_date
    }

    companion object{
        fun newInstance() = FilmsFragment()
    }


    // Функция startRecycleFilms
    private fun startRecycleFilms(recyclerView: RecyclerView) {
        listOfFilms // переменная
        // Создаем адаптер
        val myAdapterFilms = AdapterRecycleView(listOfFilms, this)
        // Создаем вид отображения recycleview
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        // устанавливаем вид recycleview
        recyclerView.layoutManager = linearLayoutManager
        //Подключаем адаптер
        recyclerView.adapter = myAdapterFilms
        recyclerView.setHasFixedSize(true)
    }


    //Создаем данные
    private val listOfFilms = mutableListOf(
        DataFilmsRecycleView(R.drawable.films,0,"Москва слезам не верит", "7.9", "2010"),
        DataFilmsRecycleView(R.drawable.films,1,"Новый год", "9.9", "2021"),
        DataFilmsRecycleView(R.drawable.films,2,"Фильм о фильме", "5.0", "1998"),
        DataFilmsRecycleView(R.drawable.serials,3,"Фильм номер 1", "1.0","2005"),
        DataFilmsRecycleView(R.drawable.setting,4,"Фильм номер 5", "8.0", "2002"),
        DataFilmsRecycleView(R.drawable.serials,5,"Фильм номер 8", "1.0", "2014")
    )


    /*Кликабельность реализация*/
    override fun onItemClick(position: Int) {
        //Toast.makeText(activity, "Запускаем: " + listOfFilms[position].title, Toast.LENGTH_SHORT).show()
    }



}


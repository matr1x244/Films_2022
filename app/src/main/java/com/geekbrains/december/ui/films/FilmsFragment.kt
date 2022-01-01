package com.geekbrains.december.ui.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.december.R
import com.geekbrains.december.databinding.FragmentFilmsBinding
import com.geekbrains.december.ui.serials.SerialsFragment
import com.geekbrains.december.ui.setting.SettingFragment

class FilmsFragment : Fragment(), AdapterRecycleView.OnItemClickListenerFilms {

    private lateinit var filmsViewModel: FilmsViewModel

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        filmsViewModel = ViewModelProvider(this).get(FilmsViewModel::class.java)
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*Иницилизация recycleview*/
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerViewFilms)
        startRecycleFilms(recyclerView)
        /*Иницилизация recycleview*/

        return root

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
          Toast.makeText(activity, "Запускаем: " + listOfFilms[position].title, Toast.LENGTH_SHORT).show()
    }



}



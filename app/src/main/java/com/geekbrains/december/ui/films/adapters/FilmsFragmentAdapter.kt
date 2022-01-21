package com.geekbrains.december.ui.films.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.december.R
import com.geekbrains.december.databinding.CardviewMovieBinding
import com.geekbrains.december.model.entities.DataFilms
import com.geekbrains.december.ui.films.main.FilmsFragment



class FilmsFragmentAdapter(private val itemClickListener: FilmsFragment.OnItemViewClickListener): RecyclerView.Adapter<FilmsFragmentAdapter.FilmsViewHolder>() {

    private var filmsData: MutableList<DataFilms> = mutableListOf()//listOf()
    private lateinit var binding: CardviewMovieBinding //задаем шаблон для отображения

    @SuppressLint("NotifyDataSetChanged")
    fun setFilms(data: MutableList<DataFilms>) {
        filmsData = data
        //По обновлению данных в recyclerView очень рекомендую освоить DiffUtil:
        // https://www.raywenderlich.com/21954410-speed-up-your-android-recyclerview-using-diffutil -
        // это один из самых оптимальных механизмов, позволяет не перерисовывать все данные,
        // как это делается например при использовании !notifyDatasetChanged!
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {

        //Задаем данные для макета отображения
        binding = CardviewMovieBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return FilmsViewHolder(binding.root)
    }

    //Загружает данные в указанной позиции в представления, ссылки на которые хранятся в заданном заполнителе представления
    override fun onBindViewHolder(holderFilms: FilmsViewHolder, position: Int) {
        holderFilms.bind(filmsData[position])
    }

    // создаём размер и возвращаем его
    override fun getItemCount() = filmsData.size


    inner class FilmsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(films: DataFilms) = with(binding) {

            /*Какие данные передаем в recycleview*/
            itemPosterPath.setImageResource(R.drawable.films) // ??? Надо разобраться как передать разные постеры
            listId.text = films.dataMovie.id.toString()
            listTitle.text = films.dataMovie.original_title
            listPopularity.text = films.dataMovie.popularity.toString()
            listReleaseDate.text = films.dataMovie.year
            listSlogan.text = films.dataMovie.slogan

            root.setOnClickListener { itemClickListener.onItemViewClick(films)
                Toast.makeText(itemView.context,"Загружаем фильм: ${listTitle.text}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


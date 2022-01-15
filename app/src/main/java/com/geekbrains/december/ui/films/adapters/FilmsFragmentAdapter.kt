package com.geekbrains.december.ui.films.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.december.databinding.CardviewMovieBinding
import com.geekbrains.december.model.entities.DataFilms
import com.geekbrains.december.ui.films.main.FilmsFragment



class FilmsFragmentAdapter(private val itemClickListener: FilmsFragment.OnItemViewClickListener): RecyclerView.Adapter<FilmsFragmentAdapter.FilmsViewHolder>() {

    private var filmsData: List<DataFilms> = listOf()
    private lateinit var binding: CardviewMovieBinding //задаем шаблон для отображения

    @SuppressLint("NotifyDataSetChanged")
    fun setFilms(data: List<DataFilms>) {
        filmsData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {

        //Задаем данные для макета отображения
        binding = CardviewMovieBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return FilmsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holderFilms: FilmsViewHolder, position: Int) {
        holderFilms.bind(filmsData[position])

/*        holderFilms.apply {
            holderFilms.imageFilms.setImageResource(getRussianFilms().get(position).poster_path) // передаём Иконку
            holderFilms.textViewId.text = listOfFilms.get(position).id.toString() // передаем ID фильма
            holderFilms.textViewNameFilms.text = listOfFilms.get(position).title //Передаём имя
            holderFilms.textViewRaiting.text = listOfFilms.get(position).popularity // Передаём рейтинг
            holderFilms.textViewReleaseDate.text = listOfFilms.get(position).release_date // перадаем дату релиза фильма
        }*/

    }

    // создаём размер и возвращаем его
    override fun getItemCount() = filmsData.size


    inner class FilmsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(films: DataFilms) = with(binding) {

            /*Какие данные передаем в recycleview*/

            //itemPosterPath.setImageResource(R.drawable.films) = films.dataMovie.poster_path(R.drawable.films) - не могу понять как передать картинку каждого фильма
            //itemPosterPath.setImageResource(R.drawable.films)
            listId.text = films.dataMovie.id.toString()
            listTitle.text = films.dataMovie.title
            listPopularity.text = films.dataMovie.popularity
            listReleaseDate.text = films.dataMovie.release_date

            root.setOnClickListener { itemClickListener.onItemViewClick(films)
                    //Toast.makeText(itemView.context,"---", Toast.LENGTH_LONG).show() - надо разобраться как сюда передать название фильма например
            }
        }
    }
}


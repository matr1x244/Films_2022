package com.geekbrains.december.ui.films.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Precision
import coil.size.Scale
import com.geekbrains.december.R
import com.geekbrains.december.databinding.CardviewMovieBinding
import com.geekbrains.december.model.entities.DataFilms
import com.geekbrains.december.ui.films.main.FilmsFragment

class RecyclerViewFragmentAdapter(private val itemClickListener: FilmsFragment.OnItemViewClickListener): RecyclerView.Adapter<RecyclerViewFragmentAdapter.FilmsViewHolder>() {

    private val movieList: MutableList<DataFilms> = ArrayList() // задаем что отображать
    private lateinit var binding: CardviewMovieBinding //задаем шаблон для отображения

     /**
     * сетим лист фильмов
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setData(newMovieList: List<DataFilms>) {
        movieList.clear()
        movieList.addAll(newMovieList)

        /**
         *  По обновлению данных в recyclerView очень рекомендую освоить DiffUtil:
            https://www.raywenderlich.com/21954410-speed-up-your-android-recyclerview-using-diffutil -
            это один из самых оптимальных механизмов, позволяет не перерисовывать все данные,
             как это делается например при использовании !notifyDatasetChanged!
         */
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {

        /**
         *  Задаем данные для макета отображения
         */
        binding = CardviewMovieBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return FilmsViewHolder(binding.root)
    }

     /**
     * Загружает данные в указанной позиции в представления, ссылки на которые хранятся в заданном заполнителе представления
     */
    override fun onBindViewHolder(holderFilms: FilmsViewHolder, position: Int) {
        holderFilms.bind(movieList[position])
        /*Список для перезагрузки ТЕСТ при прокручивании*/
        if (position == movieList.size - 1){
            itemClickListener.onDataEnd(movieList.size +1, sizeToRequest = 10)
        }
        /*Список для перезагрузки ТЕСТ при прокручивании*/
    }

    /**
     * создаём размер и возвращаем его
     */
    override fun getItemCount() = movieList.size

    inner class FilmsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(films: DataFilms) = with(binding) {

            /*Какие данные передаем в recycleview*/

            val url_path_posters_adapter = "https://st.kp.yandex.net/images/film_big/${films.id}.jpg"
            itemPoster.load(url_path_posters_adapter) {
                precision(Precision.EXACT)
                error(R.drawable.ic_video)
                scale(Scale.FILL)}

            listId.text = films.id.toString() // ID
            listTitle.text = films.name // Название
            listTmdb.text = films.imdb.toString() // Рейтинг
            listReleaseDate.text = films.year.toString() // Год релиза
            listType.text = films.type //

            root.setOnClickListener { itemClickListener.onItemViewClick(films)
                Toast.makeText(itemView.context,"Загружаем фильм: ${listTitle.text}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


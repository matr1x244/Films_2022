package com.geekbrains.december.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Precision
import coil.size.Scale
import com.geekbrains.december.R
import com.geekbrains.december.databinding.CardviewMovieHistoryListBinding
import com.geekbrains.december.model.entities.DataFilms
import com.geekbrains.december.model.entities.showSnackBarNoAction
import com.geekbrains.december.ui.history.HistoryFragment

class HistoryAdapter(private val itemClickListener: HistoryFragment.OnItemViewClickListenerHistory): RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var movieList: List<DataFilms> = arrayListOf()

    fun setData(newMovieList: List<DataFilms>) {
        this.movieList = newMovieList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(CardviewMovieHistoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount() = movieList.size


    inner class RecyclerItemViewHolder(private val binding: CardviewMovieHistoryListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(films: DataFilms) = with(binding) {
            if (layoutPosition != RecyclerView.NO_POSITION){
                /*Какие данные передаем в recycleview*/
                itemPosterHistory.load(films.poster) {
                    precision(Precision.EXACT)
                    error(R.drawable.ic_video)
                    scale(Scale.FILL)}

                listIdHistory.text = films.id.toString() // ID
                listTitleHistory.text = films.name // Название
                listTmdbHistory.text = films.imdb.toString() // Рейтинг
                listReleaseDateHistory.text = films.year.toString() // Год релиза

                /*Кнопка удаления из базы данных истории фильма*/
                imageButtonDelete.setOnClickListener {
                    itemClickListener.onItemViewClick(films)
                    imageButtonDelete.showSnackBarNoAction("Удалено из истории: ${listTitleHistory.text}")
                }
                root.setOnClickListener {
                    Toast.makeText(itemView.context,"${listTitleHistory.text}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
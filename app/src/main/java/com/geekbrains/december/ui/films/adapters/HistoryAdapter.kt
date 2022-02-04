package com.geekbrains.december.ui.films.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Precision
import coil.size.Scale
import com.geekbrains.december.R
import com.geekbrains.december.databinding.CardviewMovieBinding
import com.geekbrains.december.model.entities.DataFilms

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var movieList: MutableList<DataFilms> = ArrayList()

    fun setFilms(newMovieList: List<DataFilms>) {
        this.movieList = newMovieList as MutableList<DataFilms>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            CardviewMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount() = movieList.size

    inner class RecyclerItemViewHolder(private val binding: CardviewMovieBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(films: DataFilms) = with(binding) {

                /*Какие данные передаем в recycleview*/
                listTitle.text = films.name // Название

                root.setOnClickListener {
                    Toast.makeText(itemView.context,"Загружаем фильм: ${listTitle.text}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

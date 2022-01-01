package com.geekbrains.december.ui.films

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.december.R

class AdapterRecycleView (var listOfFilms: MutableList<DataFilmsRecycleView>, val listener: OnItemClickListenerFilms) : RecyclerView.Adapter<AdapterRecycleView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Задаем данные для макета отображения
        val viewFilms = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(viewFilms)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {
            holder.imageFilms.setImageResource(listOfFilms.get(position).poster_path) // передаём Иконку
            holder.textViewId.text = listOfFilms.get(position).id.toString() // передаем ID фильма
            holder.textViewNameFilms.text = listOfFilms.get(position).title //Передаём имя
            holder.textViewRaiting.text = listOfFilms.get(position).popularity // Передаём рейтинг
            holder.textViewReleaseDate.text = listOfFilms.get(position).release_date // перадаем дату релиза фильма
        }

    }

    // создаём размер и возвращаем его
    override fun getItemCount(): Int {
        return listOfFilms.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {

        val imageFilms: ImageView
        val textViewId: TextView
        val textViewNameFilms: TextView
        val textViewRaiting: TextView
        val textViewReleaseDate: TextView

        init {
            itemView.setOnClickListener{
                val position = adapterPosition
                listener.onItemClick(position)
            }
            imageFilms = itemView.findViewById(R.id.item_poster_path)
            textViewId = itemView.findViewById(R.id.list_id)
            textViewNameFilms = itemView.findViewById(R.id.list_title)
            textViewRaiting = itemView.findViewById(R.id.list_popularity)
            textViewReleaseDate = itemView.findViewById(R.id.list_release_date)
        }


        /* Кликабельность реализация*/
        override fun onClick(vClick: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

        /*Интерфейс кликабельность*/
        interface OnItemClickListenerFilms{
            fun onItemClick(position: Int)
    }

}
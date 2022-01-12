package com.geekbrains.december.model.entities

import com.geekbrains.december.R

data class DataFilms(
    val dataMovie: DataMovie = getDefaultFilms(),
    var poster_path: Int = R.drawable.serials,
    val id: Int = 1,
    var title: String = "FILMS",
    var popularity: String = "10.0",
    var release_date: String = "1999"
)

fun getDefaultFilms() = DataMovie(R.drawable.films,500,"Фильм приложение 2022 г.", "7.7", "2022")

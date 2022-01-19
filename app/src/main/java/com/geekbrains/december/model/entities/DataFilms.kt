package com.geekbrains.december.model.entities

import android.os.Parcelable
import com.geekbrains.december.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataFilms(
    val dataMovie: DataMovie = getDefaultFilms(),
    var poster_path: Int = R.drawable.serials,
    val id: Int = 0,
    var title_name: String = "FILMS",
    var popularity: String = "0.0",
    var year: String = "0000",
    var description_about: String = "О фильме",
    var slogan: String = "0000"
) : Parcelable

fun getDefaultFilms() = DataMovie(R.drawable.serials,
    326,
    "Побег из Шоушенка",
    "10.0",
    "1994",
    "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника...",
    "Страх - это кандалы. Надежда - это свобода")


fun getWorldFilms() = mutableListOf( // не верно отображает иконку poster_path !!!!

        DataFilms(DataMovie(R.drawable.serials,3,"Иностранный фильм 1","1.0","2050","Это описание фильма #3", "Слоган #3")),
        DataFilms(DataMovie(R.drawable.films,4,"Иностранный фильм 2","2.0","2040","Это описание фильма #4","Слоган #4")),
        DataFilms(DataMovie(R.drawable.setting,5,"Иностранный фильм 3","3.0","2030","Это описание фильма #5","Слоган #5"))
    )

fun getRussianFilms() = mutableListOf(

        DataFilms(DataMovie(R.drawable.serials,0,"РУССКИЙ ФИЛЬМ 1", "4.0", "2020","Это описание фильма #0","Слоган #0")),
        DataFilms(DataMovie(R.drawable.films,1,"РУССКИЙ ФИЛЬМ 2", "5.0", "2010","Это описание фильма #1","Слоган #1")),
        DataFilms(DataMovie(R.drawable.setting,2,"РУССКИЙ ФИЛЬМ 3", "6.0", "2000","Это описание фильма #2","Слоган #2"))
    )

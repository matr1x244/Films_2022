package com.geekbrains.december.model.entities

import android.os.Parcelable
import com.geekbrains.december.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataFilms(
    val dataMovie: DataMovie = getDefaultFilms(),
    var poster_path: Int = R.drawable.films,
    val id: Int = 0,
    var title: String = "FILMS",
    var popularity: String = "0.0",
    var release_date: String = "0000",
    var about_move: String = "О фильме"
) : Parcelable

fun getDefaultFilms() = DataMovie(R.drawable.films,0,"Фильм заставка", "0.0", "1999","Это описание фильма Default")


fun getWorldFilms() = listOf( // не верно отображает иконку poster_path !!!!

        DataFilms(DataMovie(R.drawable.serials,3,"Иностранный фильм 1","1.0","2050","Это описание фильма #3")),
        DataFilms(DataMovie(R.drawable.serials,4,"Иностранный фильм 2","2.0","2040","Это описание фильма #4")),
        DataFilms(DataMovie(R.drawable.serials,5,"Иностранный фильм 3","3.0","2030","Это описание фильма #5"))
    )

fun getRussianFilms() = listOf(

        DataFilms(DataMovie(R.drawable.films,0,"РУССКИЙ ФИЛЬМ 1", "4.0", "2020","Это описание фильма #0")),
        DataFilms(DataMovie(R.drawable.films,1,"РУССКИЙ ФИЛЬМ 2", "5.0", "2010","Это описание фильма #1")),
        DataFilms(DataMovie(R.drawable.films,2,"РУССКИЙ ФИЛЬМ 3", "6.0", "2000","Это описание фильма #2"))
    )

package com.geekbrains.december.model.entities

import android.os.Parcelable
import com.geekbrains.december.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataFilms(

    val dataMovie: DataMovie = getDefaultFilms(),
    var poster: Int = R.drawable.serials,
    val id: Int = 0,
    var name: String? = "name",
    var tmdb: Double = 0.0,
    var year: Int = 0,
    var description: String? = "description",
    var slogan: String? = "slogan"

    ) : Parcelable

fun getDefaultFilms() = DataMovie(R.drawable.films,
    500,
    "Фильм по дефолту",
    10.0,
    2099,
    "Описание дефолтного фильма",
    "Дефолтный слоган"
)

fun getWorldFilms() = listOf( // не верно отображает иконку poster_path !!!!

        DataFilms(DataMovie(R.drawable.films,320,"ДЕФОЛТ",10.0,9999,"ДЕФОЛТ: Это описание фильма","ДЕФОЛТ: Слоган фильм"))
    )

fun getRussianFilms() = listOf(

        DataFilms(DataMovie(R.drawable.serials,666,"Форсаж",5.0,2001,"ЛОКАЛЬНОЕ ОПИСАНИЕ: Его зовут Брайан, и он - фанатик турбин и нитроускорителей.","ЛОКАЛЬНЫЙ СЛОГАН: Если у тебя есть то, что нужно... ты можешь получить всё")),
        DataFilms(DataMovie(R.drawable.serials,500,"Собачий полдень",4.0,1975,"ЛОКАЛЬНОЕ ОПИСАНИЕ: В жаркий бруклинский полдень трое воодушевленных неудачников решают ограбить банк.","ЛОКАЛЬНЫЙ СЛОГАН: Ограбление банка должно было занять десять минут..."))
)

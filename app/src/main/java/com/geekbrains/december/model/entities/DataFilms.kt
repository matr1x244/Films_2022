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
    var rating_kinopoisk: String = "rating_kinopoisk",
    var year: Int = 0,
    var description: String? = "description",

    ) : Parcelable

fun getDefaultFilms() = DataMovie(R.drawable.serials,
    500,
    "Фильм по дефолту",
    "99.99",
    2099,
    "Описание дефолтного фильма",
    325
)

fun getWorldFilms() = mutableListOf( // не верно отображает иконку poster_path !!!!

        DataFilms(DataMovie(R.drawable.serials,326,"Побег из Шоушенка 1","1.0",2050,"Это описание фильма #3",326))
    )

fun getRussianFilms() = mutableListOf(

    DataFilms(DataMovie(
        R.drawable.serials,
        326,
        "Крик ужаса",
        "1.0",
        1958,
        "Банда террористов объявила, что в пассажирском самолёте заложена бомба.  Чтобы получить требуемый выкуп в полмиллиона долларов, преступники похитили семью инженера, разработавшего взрывное устройство.",
        326))
    )

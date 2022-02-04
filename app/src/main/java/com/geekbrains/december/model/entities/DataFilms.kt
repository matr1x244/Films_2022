package com.geekbrains.december.model.entities

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class DataFilms(

    var poster: String? = "",
    val id: Int = 0,
    var name: String? = "name",
    var imdb: Double = 0.0,
    var year: Int? = 0,
    var description: String? = "description",
    var slogan: String? = "slogan",
    var type: String? = ""

    ) : Parcelable

/*fun getMovieServers() : List<DataFilms>{
    return listOf()
}

fun getRussianFilms() : List<DataFilms>{
    return listOf()
}*/


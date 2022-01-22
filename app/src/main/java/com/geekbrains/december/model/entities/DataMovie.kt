package com.geekbrains.december.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*Вид и тип данных для recycleview*/

@Parcelize
data class DataMovie(

    var poster: Int,
    val id: Long,
    var name: String,
    var rating_kinopoisk: String,
    var year: Int,
    var description: String,
    var slogan: String,

    var search: Long


) : Parcelable


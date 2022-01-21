package com.geekbrains.december.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*Вид и тип данных для recycleview*/

@Parcelize
data class DataMovie(
    var poster_path: Int,
    val id: Int,
    var original_title: String,
    var popularity: Double,
    var year: String,
    var description_about: String,
    var slogan: String

) : Parcelable


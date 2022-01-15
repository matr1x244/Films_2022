package com.geekbrains.december.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*Вид и тип данных для recycleview*/

@Parcelize
data class DataMovie (
    var poster_path: Int,
    val id: Int,
    var title: String,
    var popularity: String,
    var release_date: String,
    var about_move: String
) : Parcelable


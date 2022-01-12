package com.geekbrains.december.model.entities

/*Вид и тип данных для recycleview*/

data class DataMovie (
    var poster_path: Int,
    val id: Int,
    var title: String,
    var popularity: String,
    var release_date: String,
)


package com.geekbrains.december.ui.films

/*Вид и тип данных для recycleview*/

data class DataFilmsRecycleView(
    var poster_path: Int,
    val id: Int,
    var title: String,
    var popularity: String,
    var release_date: String)




package com.geekbrains.december.model.repository

import com.geekbrains.december.model.entities.DataFilms

interface Repository {

    fun getMovieFromServer(id: Int): DataFilms //передаем ID фильма

    fun getMovieFromServerTrends(): List<DataFilms>

    /*Список для перезагрузки ТЕСТ при прокручивании*/
    fun getMovieFromServerTrendsReload(): List<DataFilms>

}
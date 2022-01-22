package com.geekbrains.december.model.repository

import com.geekbrains.december.model.entities.DataFilms

interface Repository {

    fun getMovieFromServer(search: Int): DataFilms

    fun getMovieFromLocalStorageRus(): MutableList<DataFilms>

    fun getMovieFromLocalStorageWorld(): MutableList<DataFilms>

}
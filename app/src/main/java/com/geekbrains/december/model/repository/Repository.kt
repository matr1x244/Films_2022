package com.geekbrains.december.model.repository

import com.geekbrains.december.model.entities.DataFilms

interface Repository {

    fun getMovieFromServer(id: Int, original_title: String): DataFilms

    fun getMovieFromLocalStorageRus(): MutableList<DataFilms>

    fun getMovieFromLocalStorageWorld(): MutableList<DataFilms>

}
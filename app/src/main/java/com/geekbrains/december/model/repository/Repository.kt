package com.geekbrains.december.model.repository

import com.geekbrains.december.model.entities.DataFilms

interface Repository {

    fun getMovieFromServer(id: Int): DataFilms

    fun getMovieFromLocalStorageRus(): List<DataFilms>

    fun getMovieFromLocalStorageWorld(): List<DataFilms>

}
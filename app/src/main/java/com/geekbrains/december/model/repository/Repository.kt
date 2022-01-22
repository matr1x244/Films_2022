package com.geekbrains.december.model.repository

import com.geekbrains.december.model.entities.DataFilms

interface Repository {

    fun getMovieFromServer(search: Long): DataFilms

    fun getMovieFromLocalStorageRus(): List<DataFilms>

    fun getMovieFromLocalStorageWorld(): List<DataFilms>

}
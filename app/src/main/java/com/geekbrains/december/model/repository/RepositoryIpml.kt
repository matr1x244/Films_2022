package com.geekbrains.december.model.repository

import com.geekbrains.december.model.entities.DataFilms

class RepositoryIpml: Repository {

    override fun getMovieFromServer() = DataFilms()

    override fun getMovieFromLocalStorage() = DataFilms()
}
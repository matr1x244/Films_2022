package com.geekbrains.december.model.repository

import com.geekbrains.december.model.entities.*

class RepositoryIpml: Repository {

    override fun getMovieFromServer(search: Int) = DataFilms()

    override fun getMovieFromLocalStorageRus() = getRussianFilms()

    override fun getMovieFromLocalStorageWorld() = getWorldFilms()

}
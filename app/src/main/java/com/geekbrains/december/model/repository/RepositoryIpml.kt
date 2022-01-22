package com.geekbrains.december.model.repository

import com.geekbrains.december.model.FilmsLoader
import com.geekbrains.december.model.entities.*

class RepositoryIpml: Repository {

    override fun getMovieFromServer(search: Long) : DataFilms {

        //запускаем загружчик и что нужно
        val dto = FilmsLoader.loadFilms(search)

        return DataFilms(
            name = dto?.movies?.name,
            year = dto?.movies?.year?: 0,
            description = dto?.movies?.description
        )
    }

    override fun getMovieFromLocalStorageRus() = getRussianFilms()

    override fun getMovieFromLocalStorageWorld() = getWorldFilms()

}
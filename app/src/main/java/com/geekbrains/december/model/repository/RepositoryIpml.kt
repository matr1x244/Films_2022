package com.geekbrains.december.model.repository

import com.geekbrains.december.model.FilmsLoader
import com.geekbrains.december.model.entities.*

class RepositoryIpml: Repository {

    override fun getMovieFromServer(id: Int) : DataFilms {

        //запускаем загружчик и что нужно показать
        val dto = FilmsLoader.loadFilms(id)

        return DataFilms(

            id = dto?.id?: 0,
            name = dto?.name,
            year = dto?.year?: 0,
            description = dto?.description,
            slogan = dto?.slogan,
            tmdb = dto?.tmbd?: 0.0 // НЕ ПРОГРУЖАЕТ..
        )
    }

    override fun getMovieFromLocalStorageRus() = getRussianFilms()

    override fun getMovieFromLocalStorageWorld() = getWorldFilms()

}
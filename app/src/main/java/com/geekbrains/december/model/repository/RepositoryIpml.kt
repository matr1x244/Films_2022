package com.geekbrains.december.model.repository

import com.geekbrains.december.model.FilmsLoaderDetails
import com.geekbrains.december.model.FilmsLoaderRecycle
import com.geekbrains.december.model.entities.*

class RepositoryIpml: Repository {

    /*ПЕРЕДАЕМ ДЕТАЛИ по фильму*/
    override fun getMovieFromServer(id: Int): DataFilms {
        //запускаем загружчик и что нужно показать
        val dto = FilmsLoaderDetails.loadFilmsDetails(id)
        return DataFilms(
            id = dto?.id ?: 0,
            name = dto?.name,
            year = dto?.year ?: 0,
            tmdb = dto?.tmbd ?: 0.0, // НЕ ПРОГРУЖАЕТ..
            description = dto?.description,
            slogan = dto?.slogan
        )
    }

    /*Список*/
    override fun getMovieFromServerTrends(): List <DataFilms> {
        val dtoTrendsServer = FilmsLoaderRecycle.loadFilmsRecycle()?.docs
        val listMovieTrends = mutableListOf<DataFilms>()
        dtoTrendsServer?.forEach {
            listMovieTrends.add(
                DataFilms(
                    id = it?.id?: 0,
                    name = it.name,
                    year = it?.year?: 0,
                    tmdb = it?.tmbd?: 0.0, // НЕ ПРОГРУЖАЕТ..
                    slogan = it?.slogan // - почему то не сетит в reycleview слоган..
                )
            )
        }
        return listMovieTrends
    }
}


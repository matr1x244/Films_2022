package com.geekbrains.december.model.repository

import com.geekbrains.december.model.FilmsLoaderDetails
import com.geekbrains.december.model.FilmsLoaderRecycle
import com.geekbrains.december.model.FilmsLoaderRecycleReload
import com.geekbrains.december.model.entities.*

class RepositoryIpml: Repository {

    /*ПЕРЕДАЕМ ДЕТАЛИ по фильму*/
    override fun getMovieFromServer(id: Int): DataFilms {
        //запускаем загружчик и что нужно показать

        val dto = FilmsLoaderDetails.loadFilmsDetails(id) //стандартный загрузчик

        /*для загрузки через retrofit синхронно execute().body()*/
        //val dto = MovieRepo.api.getMovieDetails(id).execute().body()

/*
        *//* для загрузки через retrofit асинхронно enqueue*//*

        val dto = MovieRepo.api.getMovieDetails(id).enqueue(object : Callback<MovieDetailsDTO>{
            override fun onResponse(call: Call<MovieDetailsDTO>, response: Response<MovieDetailsDTO>) {
                if(response.isSuccessful){
                    val data = response.body()
                }
            }
            override fun onFailure(call: Call<MovieDetailsDTO>, t: Throwable) {

            }
        })*/

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

    /*Список для перезагрузки ТЕСТ при прокручивании*/
    override fun getMovieFromServerTrendsReload(): List <DataFilms> {
        val dtoTrendsServerReload = FilmsLoaderRecycleReload.loadFilmsRecycleReload()?.docs
        val listMovieTrends = mutableListOf<DataFilms>()
        dtoTrendsServerReload?.forEach {
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


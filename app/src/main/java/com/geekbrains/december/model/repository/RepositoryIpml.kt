package com.geekbrains.december.model.repository


import android.graphics.Movie
import android.os.AsyncTask
import androidx.loader.content.AsyncTaskLoader
import com.geekbrains.december.model.database.Database
import com.geekbrains.december.model.database.HistoryDAO
import com.geekbrains.december.model.database.HistoryEntity
import com.geekbrains.december.model.entities.*
import com.geekbrains.december.model.entities.rest.rest_entities.retrofit.MovieRepo

class RepositoryIpml: Repository {

    /*ПЕРЕДАЕМ ДЕТАЛИ по фильму*/
    override fun getMovieFromServerDetail(id: Int?): DataFilms {

        //запускаем загружчик и что нужно показать
        //val dto = FilmsLoaderDetails.loadFilmsDetails(id) //стандартный загрузчик

        /*для загрузки через retrofit синхронно execute().body()*/
        val dto = MovieRepo.api.getMovieDetails(
            id,
            field = "id",
            token = "1KK4612-HEMM8RX-P3QSGPJ-VR0AQ82"
        ).execute().body()

/*
        *//* для загрузки через retrofit асинхронно enqueue*//*
        val dto = MovieRepo.api.getMovieDetails(id).enqueue(object : Callback<MovieDetailsDTO>{
            override fun onResponse(call: Call<MovieDetailsDTO>, response: Response<MovieDetailsDTO>) {
                if(response.isSuccessful){
                    val data = response.body() } }
            override fun onFailure(call: Call<MovieDetailsDTO>, t: Throwable) { }
        })*/

        return DataFilms(
            id = dto?.id ?: 0,
            name = dto?.name,
            year = dto?.year ?: 0,
            imdb = dto?.rating?.imdb ?: 0.0,
            description = dto?.description,
            poster = dto?.poster?.url,
            slogan = dto?.slogan,
        )
    }

    /*Список*/
    override fun getMovieFromServerFilms(): List<DataFilms> {
        val dtoFilmsServers = MovieRepo.api.getMovieRecyclerFilms(
            fieldRating = "rating.kp",
            searchRating = "7-10",
            fieldYear = "year",
            searchYear = "2005",
            fieldTypeNimber = "typeNumber",
            searchTypeNumber = "1",
            token = "1KK4612-HEMM8RX-P3QSGPJ-VR0AQ82"
        ).execute().body()

        val dtoLoad = dtoFilmsServers?.docs
        val listMovieFilms = mutableListOf<DataFilms>()
        dtoLoad?.forEach {
            listMovieFilms.add(
                DataFilms(
                    id = it?.id ?: 0,
                    name = it?.name,
                    year = it?.year ?: 0,
                    imdb = it?.rating?.imdb ?: 0.0,
                    poster = it?.poster?.url,
                    type = it?.type
                )
            )
        }
        return listMovieFilms
    }

    override fun getMovieFromServerFilmsReload(): List<DataFilms> {
        val dtoFilmsServersReload = MovieRepo.api.getMovieRecyclerFilms(
            fieldRating = "rating.kp",
            searchRating = "2-5",
            fieldYear = "year",
            searchYear = "2018",
            fieldTypeNimber = "typeNumber",
            searchTypeNumber = "1",
            token = "1KK4612-HEMM8RX-P3QSGPJ-VR0AQ82"
        ).execute().body()

        val dtoServerReload = dtoFilmsServersReload?.docs
        val listMovieFilmsReload = mutableListOf<DataFilms>()
       dtoServerReload?.forEach {
            listMovieFilmsReload.add(
                DataFilms(
                    id = it?.id ?: 0,
                    name = it?.name,
                    year = it?.year ?: 0,
                    imdb = it?.rating?.imdb ?: 0.0,
                    poster = it?.poster?.url,
                    type = it?.type
                )
            )
        }
        return listMovieFilmsReload
    }


    /*Список*/
    override fun getMovieFromServerSerials(): List<DataFilms> {
        val dtoSerialsServers = MovieRepo.api.getMovieRecyclerSerials(
            fieldRating = "rating.kp",
            searchRating = "1-5",
            fieldYear = "year",
            searchYear = "2020-2022",
            fieldTypeNimber = "typeNumber",
            searchTypeNumber = "2",
            token = "1KK4612-HEMM8RX-P3QSGPJ-VR0AQ82"
        ).execute().body()

        val dtoLoad = dtoSerialsServers?.docs
        val listMovieSerials = mutableListOf<DataFilms>()
        dtoLoad?.forEach {
            listMovieSerials.add(
                DataFilms(
                    id = it?.id ?: 0,
                    name = it?.name,
                    year = it?.year ?: 0,
                    imdb = it?.rating?.imdb ?: 0.0,
                    poster = it?.poster?.url,
                    type = it?.type
                )
            )
        }
        return listMovieSerials
    }


    /**
     * Для базы данных
     */
    override fun getAllHistory(): List<DataFilms> {
        return convertHistoryEntityToMovie(Database.db.historyDao().all())
    }

    override fun saveEntity(dataFilms: DataFilms) {
        Database.db.historyDao().insert(convertMovieToEntity(dataFilms))
    }


    /*Не работает удаление из базы данных*/
    override fun deleteEntity(dataFilms: DataFilms) {
        Database.db.historyDao().deleteByMovieName(convertMovieToEntity(dataFilms).nameMovieEntity)
    }


    /*Не работает удаление из базы данных*/

    private fun convertHistoryEntityToMovie(entityList: List<HistoryEntity>): List<DataFilms> {
        return entityList.map {
            DataFilms(
                 it.posterMovieEntity,
                 it.idMovieEntity,
                 it.nameMovieEntity,
                 it.tmdbMovieEntity,
                 it.yearMovieEntity)
        }
    }

    private fun convertMovieToEntity(dataFilms: DataFilms): HistoryEntity {
        return HistoryEntity(
            0,
            dataFilms.name,
            dataFilms.imdb,
            dataFilms.year,
            dataFilms.poster)
    }

    /**
     * Для базы данных
     */

    /*Список для перезагрузки ТЕСТ при прокручивании через стандартный загрузчик*/
/*    override fun getMovieFromServerFilmsReload(): List <DataFilms> {
        val dtoTrendsServerReload = FilmsLoaderRecycleReload.loadFilmsRecycleReload()?.docs
        val listMovieTrends = mutableListOf<DataFilms>()
        dtoTrendsServerReload?.forEach {
            listMovieTrends.add(
                DataFilms(
                    id = it?.id?: 0,
                    name = it?.name,
                    year = it?.year?: 0,
                    imdb = it?.rating?.imdb?: 0.0,
                    poster = it?.poster?.url,
                    slogan = it?.slogan
                )
            )
        }
        return listMovieTrends
    }*/
}


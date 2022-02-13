package com.geekbrains.december.model.repository

import com.geekbrains.december.model.entities.DataFilms

interface Repository {

    fun getMovieFromServerDetail(id: Int?): DataFilms //передаем ID фильма

    fun getMovieFromServerFilms(): List<DataFilms>

    fun getMovieFromServerSerials() : List<DataFilms>

    /**
     * Для базы данных
     */
    fun getAllHistory(): List<DataFilms>

    fun saveEntity(dataFilms: DataFilms)

    /*Удаление по кнопке*/
    fun deleteEntity(dataFilms: DataFilms)

    fun deleteEntityAll()

    /**
     * Список для перезагрузки ТЕСТ при прокручивании
     */

    fun getMovieFromServerFilmsReload(): List<DataFilms>


}
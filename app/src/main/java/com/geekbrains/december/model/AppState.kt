package com.geekbrains.december.model


import com.geekbrains.december.model.entities.DataFilms

sealed class AppState{

    data class Success(val filmsData: List<DataFilms>): AppState()

    data class Error(val error: Throwable): AppState()

    object Loading: AppState()
}

package com.geekbrains.december.model

import com.geekbrains.december.model.entities.DataFilms

sealed class AppState{

    data class Success(val dataFilmsData: DataFilms): AppState()
    data class Error(val error: Throwable): AppState()

    object Loading: AppState()
}

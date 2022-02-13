package com.geekbrains.december.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.entities.DataFilms
import com.geekbrains.december.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HistoryViewModel (private val repository: Repository) : ViewModel() {

    val historyLiveData: MutableLiveData<AppState> = MutableLiveData()

    val historyLiveDataDelete = MutableLiveData<Unit>()

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            historyLiveData.postValue(AppState.Success(repository.getAllHistory()))
        }
    }

    /*удаление элементы списка из истории*/
    fun deleteMovie(dataFilms: DataFilms) {
        viewModelScope.launch(Dispatchers.IO) {
            historyLiveDataDelete.postValue(repository.deleteEntity(dataFilms))
        }
    }

    fun deleteMovieAll() {
        viewModelScope.launch(Dispatchers.IO) {
            historyLiveDataDelete.postValue(repository.deleteEntityAll())
        }
    }

}



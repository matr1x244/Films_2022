package com.geekbrains.december.ui.films.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.repository.Repository

class DetailsViewModel (private val repository: Repository) : ViewModel() {

    private val localLiveData: MutableLiveData<AppState> = MutableLiveData()
    val filmsLiveData: LiveData<AppState>
        get() {
            return localLiveData
        }

    fun loadData(search: Long) {
        localLiveData.value = AppState.Loading
        Thread {
            val data = repository.getMovieFromServer(search)
            localLiveData.postValue(AppState.Success(mutableListOf(data)))
        }.start()
    }
}


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

    fun loadData(id: Int, original_title: String) {
        localLiveData.value = AppState.Loading
        Thread {
            val data = repository.getMovieFromServer(id, original_title)
            localLiveData.postValue(AppState.Success(mutableListOf(data)))
        }.start()
    }
}


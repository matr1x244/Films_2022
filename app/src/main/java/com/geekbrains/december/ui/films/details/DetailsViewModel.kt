package com.geekbrains.december.ui.films.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel (private val repository: Repository) : ViewModel() {

    private val localLiveData: MutableLiveData<AppState> = MutableLiveData()

    val filmsLiveData: LiveData<AppState>
        get() {
            return localLiveData
        }

    fun loadData(id: Int) {
        localLiveData.value = AppState.Loading
        /*корутины без использования Thread*/
        viewModelScope.launch(Dispatchers.IO){
            val data = repository.getMovieFromServer(id) // берем в потоке id
            withContext(Dispatchers.Main){
                localLiveData.value = (AppState.Success(listOf(data))) // записываем в livedata "value) данные
            }
        }

/*        Thread {
            val data = repository.getMovieFromServer(id) // берем в потоке id
            localLiveData.postValue(AppState.Success(listOf(data))) // записываем в livedata "postvalue) данные
        }.start()*/

    }
}


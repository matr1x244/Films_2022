package com.geekbrains.december.ui.films.films
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FilmsViewModel(private val repository: Repository) : ViewModel() {

    private val liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveData // подписываемся на данные которые меняются

    fun getFilmsFromLoad() = getDataFromLoad()

    private fun getDataFromLoad() {

        liveData.value = AppState.Loading
         /**
         * корутины без использования Thread
         */
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000) //  задержка
            liveData.postValue(AppState.Success(repository.getMovieFromServerFilms()))
        }
    }


    /*Список для перезагрузки ТЕСТ при прокручивании*/
    fun getMoreMovies(from: Int, sizeToRequest: Int){

        //repository.getMovieFromServerFilmsReload()

        liveData.value = AppState.Loading
        /**
         * корутины без использования Thread
         */
        viewModelScope.launch(Dispatchers.IO) {
            liveData.postValue(AppState.Success(repository.getMovieFromServerFilmsReload()))
        }
    }
    /*Список для перезагрузки ТЕСТ при прокручивании*/

    override fun onCleared() {
        super.onCleared()
    }

}


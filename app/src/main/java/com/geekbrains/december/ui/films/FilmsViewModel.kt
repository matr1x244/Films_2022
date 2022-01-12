package com.geekbrains.december.ui.films
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.repository.Repository
import java.lang.Thread.sleep


class FilmsViewModel(private val repository: Repository) : ViewModel() {

        private val liveData = MutableLiveData<AppState>() // подписываемся на данные которые меняются

        fun getLiveData(): LiveData<AppState> = liveData //

        fun getFilms() = getDataFromLocalSource() // Забрать данные

    private fun getDataFromLocalSource() {
            liveData.value = AppState.Loading
            Thread {
                sleep(1000)
                liveData.postValue(AppState.Success(repository.getMovieFromLocalStorage())) //обязательно postValue для обновления в потоке ui
            }.start() // запускаем
        }

    override fun onCleared() {
        super.onCleared()
    }

}


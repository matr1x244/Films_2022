package com.geekbrains.december.ui.films.main
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.repository.Repository
import java.lang.Thread.sleep


class FilmsViewModel(private val repository: Repository) : ViewModel() {

    private val liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveData // подписываемся на данные которые меняются

    fun getFilmsFromLoad() = getDataFromLoad()

    private fun getDataFromLoad() {

        liveData.value = AppState.Loading
            Thread {
                sleep(1000)
                liveData.postValue(AppState.Success(repository.getMovieFromServerTrends()))
            }.start()

        }

    override fun onCleared() {
        super.onCleared()
    }

}


package com.geekbrains.december.ui.films.main
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.repository.Repository
import com.geekbrains.december.model.repository.RepositoryIpml
import java.lang.Thread.sleep


class FilmsViewModel(private val repository: Repository) : ViewModel() {

    private val liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveData // подписываемся на данные которые меняются

    fun getFilmsFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getFilmsFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    private fun getDataFromLocalSource(isRussian: Boolean) {
            liveData.value = AppState.Loading
            Thread {
                sleep(1000)
               liveData.postValue(    //обязательно postValue для обновления в потоке ui
                   if(isRussian) {
                       AppState.Success(repository.getMovieFromLocalStorageRus())
                   } else {
                       AppState.Success(repository.getMovieFromLocalStorageWorld())
                   }
               )
            }.start() // запускаем
        }

    override fun onCleared() {
        super.onCleared()
    }

}


package com.geekbrains.december.ui.serials

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SerialsViewModel(private val repository: Repository) : ViewModel() {

    private val liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveData // подписываемся на данные которые меняются

    fun getSerialFromLoad() = getDataFromLoadSerial()

    private fun getDataFromLoadSerial() {
        liveData.value = AppState.Loading
        viewModelScope.launch (Dispatchers.IO){
            liveData.postValue(AppState.Success(repository.getMovieFromServerSerials()))
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}


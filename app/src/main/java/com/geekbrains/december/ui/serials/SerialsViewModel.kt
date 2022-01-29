package com.geekbrains.december.ui.serials

import androidx.lifecycle.ViewModel
import com.geekbrains.december.model.repository.Repository


class SerialsViewModel(private val repository: Repository) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }
}


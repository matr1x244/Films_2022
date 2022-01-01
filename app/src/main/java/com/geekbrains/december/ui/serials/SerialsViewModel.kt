package com.geekbrains.december.ui.serials

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SerialsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "СЕРИАЛЫ"
    }
    val text: LiveData<String> = _text
}
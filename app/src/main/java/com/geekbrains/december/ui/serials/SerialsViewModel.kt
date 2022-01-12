package com.geekbrains.december.ui.serials

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class SerialsViewModel() : ViewModel() {

     private val textView = MutableLiveData<String>().apply {
        value = "СЕРИАЛЫ ТЕСТОВАЯ НАДПИСЬ"
    }
        val text: LiveData<String> = textView
}
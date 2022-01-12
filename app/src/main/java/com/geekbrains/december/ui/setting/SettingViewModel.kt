package com.geekbrains.december.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel() : ViewModel() {


    private val textView = MutableLiveData<String>().apply {
        value = "НАСТРОЙКИ ТЕСТОВАЯ НАДПИСЬ"
    }

    val text: LiveData<String> = textView
}